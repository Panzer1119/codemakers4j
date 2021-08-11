/*
 *     Copyright 2018 - 2020 Paul Hagedorn (Panzer1119)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package de.codemakers;/*
 *    Copyright 2018 Paul Hagedorn (Panzer1119)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class TCPJarCommunicator {

    public static final long ID = System.currentTimeMillis();

    private static final int PORT = 7484;
    private static ServerSocket SERVER_SOCKET = null;
    private static Socket CLIENT_SOCKET = null;
    private static ObjectOutputStream OOS = null;
    private static Thread SERVER_THREAD = null;
    private static Thread CLIENT_THREAD = null;
    private static final Map<Long, JarSocket> SOCKETS = new ConcurrentHashMap<>();
    private static final List<Consumer<JarNetData>> LISTENERS = new ArrayList<>();
    private static final int MAX_SERVER_START_TEST_TRIES = 4;
    private static final int MAX_SERVER_START_TRIES = 10;
    private static final int MAX_CLIENT_START_TRIES = 3;
    private static final int SERVER_START_TEST_RETRY_TIME = 150;
    private static final int SERVER_START_RETRY_TIME = 50;
    private static final int CLIENT_START_RETRY_TIME = 100;

    static {
        if (initServer(0,0)) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdownServer()));
        }
    }

    public static final void init() {
        /*
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        */
    }

    public static final boolean send(long id, Serializable data) {
        try {
            if (OOS == null) {
                return false;
            }
            OOS.writeObject(new JarNetData(ID, id, data));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static final boolean addListener(Consumer<JarNetData> consumer) {
        return LISTENERS.add(consumer);
    }

    public static final boolean clearListeners() {
        LISTENERS.clear();
        return true;
    }

    private static final boolean initServer(int start_tries, int start_test_tries) {
        if (start_tries > MAX_SERVER_START_TRIES) {
            System.err.println("Could not start the " + TCPJarCommunicator.class.getSimpleName() + " server on port " + PORT);
            return false;
        } else if (start_test_tries > MAX_SERVER_START_TEST_TRIES) {
            //System.out.println("Some Instance is already running the Server!");
            initClient(0);
            return false;
        }
        try {
            SERVER_SOCKET = new ServerSocket(PORT);
            SERVER_THREAD = new Thread(() -> {
                try {
                    while (true) {
                        final Socket socket = SERVER_SOCKET.accept();
                        final Thread thread = new Thread(() -> {
                            try {
                                final ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                                final long id = ois.readLong();
                                SOCKETS.put(id, new JarSocket(id, socket, oos, ois));
                                Object object = null;
                                while ((object = ois.readObject()) != null) {
                                    if (object instanceof JarNetData) {
                                        trigger((JarNetData) object);
                                    }
                                }
                            } catch (Exception ex) {
                                if (!(ex instanceof SocketException)) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            //System.out.println("Server started after " + (start_tries + start_test_tries) + " tries"); //TODO Remove this debug code
            SERVER_THREAD.start();
            //System.out.println("Server Thread started"); //TODO Remove this debug code
            return true;
        } catch (BindException ex) {
            try {
                Thread.sleep(SERVER_START_TEST_RETRY_TIME);
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
            return initServer(start_tries, ++start_test_tries);
        } catch (Exception ex) {
            try {
                Thread.sleep(SERVER_START_RETRY_TIME);
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
            return initServer(++start_tries, start_test_tries);
        }
    }

    private static final boolean initClient(int start_tries) {
        if (start_tries > MAX_CLIENT_START_TRIES) {
            return false;
        }
        try {
            CLIENT_SOCKET = new Socket(InetAddress.getByName("localhost"), PORT);
            CLIENT_THREAD = new Thread(() -> {
                try {
                    OOS = new ObjectOutputStream(CLIENT_SOCKET.getOutputStream());
                    OOS.writeLong(ID);
                    final ObjectInputStream ois = new ObjectInputStream(CLIENT_SOCKET.getInputStream());
                    Object object = null;
                    while ((object = ois.readObject()) != null) {
                        if (object instanceof JarNetData) {
                            trigger((JarNetData) object);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                initServer(0, 0);
            });
            //System.out.println("Client started after " + start_tries + " tries"); //TODO Remove this debug code
            CLIENT_THREAD.start();
            //System.out.println("Client Thread started"); //TODO Remove this debug code
            return true;
        } catch (Exception ex) {
            try {
                Thread.sleep(CLIENT_START_RETRY_TIME);
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
            return initClient(++start_tries);
        }
    }

    private static final boolean shutdownServer() {
        if (SERVER_SOCKET == null) {
            return false;
        }
        //System.out.println("Server shutdown upcoming"); //TODO Remove this debug code
        try {
            SERVER_SOCKET.close();
            SERVER_THREAD.interrupt();
            SERVER_SOCKET = null;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static final boolean trigger(JarNetData data) {
        try {
            if (ID == data.target_id) {
                LISTENERS.parallelStream().forEach((consumer) -> consumer.accept(data));
            } else {
                final JarSocket jarSocket = SOCKETS.get(data.target_id);
                if (jarSocket == null) {
                    return false;
                }
                jarSocket.sendObject(data);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static class JarSocket {

        private final Long id;
        private final Socket socket;
        private final ObjectOutputStream oos;
        private final ObjectInputStream ois;

        public JarSocket(Long id, Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
            this.id = id;
            this.socket = socket;
            this.oos = oos;
            this.ois = ois;
        }

        public final Long getID() {
            return id;
        }

        public final Socket getSocket() {
            return socket;
        }

        public final ObjectOutputStream getOOS() {
            return oos;
        }

        public final ObjectInputStream getOIS() {
            return ois;
        }

        public final void sendObject(JarNetData data) throws Exception {
            if (oos != null) {
                oos.writeObject(data);
            }
        }

    }

    public static class JarNetData implements Serializable {

        private final long source_id;
        private final long target_id;
        private final Serializable object;

        public JarNetData(long source_id, long target_id, Serializable object) {
            this.source_id = source_id;
            this.target_id = target_id;
            this.object = object;
        }

        public final long getSourceID() {
            return source_id;
        }

        public final long getTargetID() {
            return target_id;
        }

        public final Serializable getObject() {
            return object;
        }

        public final <T> T getData() {
            return (T) object;
        }

        @Override
        public final String toString() {
            return "JarNetData{" + "source_id=" + source_id + ", target_id=" + target_id + ", object=" + object + '}';
        }

    }

}
