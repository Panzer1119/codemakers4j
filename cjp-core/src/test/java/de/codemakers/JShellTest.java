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

package de.codemakers;

import de.codemakers.base.CJP;
import de.codemakers.base.Standard;
import de.codemakers.base.scripting.JavaScriptEngine;
import de.codemakers.base.scripting.JavaScriptEngineBuilder;
import de.codemakers.io.SerializationUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JShellTest {
    
    public static boolean AUTO_SAVE_RESULTS = true;
    public static boolean AUTO_ADD_RETURN = true;
    public static int lastResult = 0;
    
    public static final JavaScriptEngineBuilder JAVA_SCRIPT_ENGINE_BUILDER = new JavaScriptEngineBuilder().addImports(SerializationUtil.class);
    public static final JavaScriptEngine JAVA_SCRIPT_ENGINE = JAVA_SCRIPT_ENGINE_BUILDER.build();
    
    public static void main(String[] args) {
        Standard.async(JShellTest::startLoop);
    }
    
    private static void startLoop() {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                processLine(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static boolean processLine(String line) throws Exception {
        if (JAVA_SCRIPT_ENGINE == null) {
            System.err.println("JAVA_SCRIPT_ENGINE is null");
            return false;
        }
        if (line.startsWith("/")) {
            return processCommand(line.substring("/".length()));
        } else {
            if (AUTO_ADD_RETURN && !line.startsWith("return ")) {
                line = "return " + line;
            }
            JAVA_SCRIPT_ENGINE.executeLarge(line).queue((result) -> {
                if (AUTO_SAVE_RESULTS) {
                    lastResult++;
                    final String variable = String.format("$%d", lastResult);
                    JAVA_SCRIPT_ENGINE.put(variable, result);
                    System.out.println(String.format("%s => %s", variable, result));
                } else {
                    System.out.println("=> " + result);
                }
            }, System.err::println);
            return true;
        }
    }
    
    private static boolean processCommand(String command) throws Exception {
        if (JAVA_SCRIPT_ENGINE == null) {
            return false;
        }
        boolean recognized_command = true;
        if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("stop") || command.equalsIgnoreCase("shutdown") || command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("q")) {
            System.out.println("Stopped the " + JavaScriptEngine.class.getSimpleName());
            CJP.shutdown().complete();
        } else if (command.equalsIgnoreCase("reset") || command.equalsIgnoreCase("reboot") || command.equalsIgnoreCase("restart")) {
            JAVA_SCRIPT_ENGINE.reset();
            System.out.println("Resetted the " + JavaScriptEngine.class.getSimpleName());
        } else if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("h")) {
            System.out.println(String.format("Usage:\n" + "/exit - Exits the %s\n" + "/get VARIABLE - Returns the DATA for the VARIABLE\n" + "/help - Shows this help\n" + "/reset - Resets the %s\n" + "/set VARIABLE DATA - Sets the DATA to the VARIABLE\n" + "/set VARIABLE /eval CODE - Sets the evaluated CODE to the VARIABLE\n" + "/toggle auto_add_return - Toggles if a missing \"return\" will be added before each CODE (Value: %b)\n" + "/toggle auto_save_results - Toggles if a result from an evaluation should be saved in a VARIABLE (Value: %b)\n", JShellTest.class.getSimpleName(), JavaScriptEngine.class.getSimpleName(), AUTO_ADD_RETURN, AUTO_SAVE_RESULTS));
        } else if (command.startsWith("toggle ")) {
            command = command.substring("toggle ".length());
            if (command.equalsIgnoreCase("auto_save_results") || command.equalsIgnoreCase("asr")) {
                AUTO_SAVE_RESULTS = !AUTO_SAVE_RESULTS;
                System.out.println("Toggled AUTO_SAVE_RESULTS to " + AUTO_SAVE_RESULTS);
            } else if (command.equalsIgnoreCase("auto_add_return") || command.equalsIgnoreCase("aar")) {
                AUTO_ADD_RETURN = !AUTO_ADD_RETURN;
                System.out.println("Toggled AUTO_ADD_RETURN to " + AUTO_ADD_RETURN);
            } else {
                recognized_command = false;
            }
        } else if (command.startsWith("put ") || command.startsWith("set ")) {
            command = command.substring("put ".length());
            final String variable = command.substring(0, command.indexOf(" "));
            Object data = command.substring(variable.length() + " ".length());
            if (data.toString().startsWith("/eval return ")) {
                data = data.toString().substring("/eval ".length());
                if (AUTO_ADD_RETURN && !data.toString().startsWith("return ")) {
                    data = "return " + data.toString();
                }
                data = JAVA_SCRIPT_ENGINE.execute(data.toString());
            }
            JAVA_SCRIPT_ENGINE.put(variable, data);
            System.out.println(String.format("Setted \"%s\" to \"%s\"", variable, data));
        } else if (command.startsWith("get ")) {
            command = command.substring("get ".length());
            System.out.println(String.format("\"%s\" is \"%s\"", command, JAVA_SCRIPT_ENGINE.get(command)));
        } else {
            recognized_command = false;
        }
        if (!recognized_command) {
            System.err.println(String.format("Unrecognized command: %s (Use /help for help)", command));
        }
        return true;
    }
    
}
