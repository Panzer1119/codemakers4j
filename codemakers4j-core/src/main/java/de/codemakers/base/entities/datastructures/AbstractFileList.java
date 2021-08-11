/*
 *    Copyright 2018 - 2021 Paul Hagedorn (Panzer1119)
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

package de.codemakers.base.entities.datastructures;

import de.codemakers.base.Standard;
import de.codemakers.io.file.AdvancedFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractFileList<E> implements ConvertingCollection<E, String> {
    
    public static final String DEFAULT_FILE_EXTENSION = "txt";
    public static final String DEFAULT_DELIMITER = "=";
    public static final Pattern DEFAULT_PATTERN_DELIMITER = Pattern.compile("(\\d+)=(.*)");
    
    protected static AdvancedFile getRandomFile() {
        return new AdvancedFile(Standard.silentError(() -> File.createTempFile(AbstractFileList.class.getSimpleName(), "." + DEFAULT_FILE_EXTENSION)));
    }
    
    protected AdvancedFile file;
    
    public AbstractFileList() {
        this(getRandomFile());
    }
    
    public AbstractFileList(AdvancedFile file) {
        this.file = Objects.requireNonNull(file, "file");
        init();
    }
    
    public AdvancedFile getFile() {
        return file;
    }
    
    private void init() {
        if (!getFile().exists()) {
            getFile().createNewFileWithoutException();
        }
    }
    
    private Scanner createScanner() {
        return Standard.silentError(() -> new Scanner(getFile().toFile()));
    }
    
    private BufferedWriter createBufferedWriter(boolean append) {
        return getFile().createBufferedWriterWithoutException(append);
    }
    
    private String getLine(int index) {
        final String index_ = index + DEFAULT_DELIMITER;
        try (final Scanner scanner = createScanner()) {
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.startsWith(index_)) {
                    return line.substring(index_.length());
                }
            }
        }
        return null;
    }
    
    private E getElement(int index) {
        final String line = getLine(index);
        if (line == null) {
            return null;
        }
        return toElement(line);
    }
    
    private boolean addElement(E e) {
        return addElement(size(), e);
    }
    
    private boolean addElement(int index, E e) {
        final String toWrite = fromElement(e);
        try (final BufferedWriter bufferedWriter = createBufferedWriter(true)) {
            bufferedWriter.write(index);
            bufferedWriter.write(DEFAULT_DELIMITER);
            if (toWrite != null) {
                bufferedWriter.write(toWrite);
            }
            bufferedWriter.newLine();
            bufferedWriter.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    private boolean setElement(int index, E e) {
        final String index_ = index + DEFAULT_DELIMITER;
        final AdvancedFile temp = getRandomFile();
        temp.createNewFileWithoutException();
        try (final PrintWriter printWriter = new PrintWriter(new FileWriter(temp.toFile()))) {
            Files.lines(getFile().toPath()).filter((line) -> !line.startsWith(index_)).forEach(printWriter::println);
            printWriter.flush();
            printWriter.close();
            try (final BufferedWriter bufferedWriter = createBufferedWriter(false)) {
                Files.lines(temp.toPath()).forEach((line) -> Standard.silentError(() -> {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }));
                bufferedWriter.flush();
            }
            temp.deleteWithoutException();
        } catch (Exception ex) {
            temp.deleteWithoutException();
            return false;
        }
        if (e != null) {
            return addElement(index, e);
        }
        return true;
    }
    
    private boolean removeElement(int index) {
        return setElement(index, null);
    }
    
    /*
    private E removeElement(int index) {
        final String line = getLine(index);
        if (line == null) {
            return null;
        }
        setElement(index, null);
        return toElement(line);
    }
    */
    
    public int indexOf(Object object) {
        try (final Scanner scanner = createScanner()) {
            String line;
            E e;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                final Matcher matcher = DEFAULT_PATTERN_DELIMITER.matcher(line);
                if (!matcher.matches()) {
                    continue;
                }
                e = toElement(matcher.group(2));
                if (Objects.equals(object, e)) {
                    return Integer.parseInt(matcher.group(1));
                }
            }
        }
        return -1;
    }
    
    protected int lineIndexOf(Object object) {
        try (final Scanner scanner = createScanner()) {
            String line;
            E e;
            int counter = 0;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                e = toElement(line);
                if (Objects.equals(object, e)) {
                    return counter;
                }
                if (!line.isEmpty()) {
                    counter++;
                }
            }
        }
        return -1;
    }
    
    @Override
    public int size() {
        try (final Scanner scanner = createScanner()) {
            int counter = 0;
            while (scanner.hasNextLine()) {
                if (!scanner.nextLine().isEmpty()) {
                    counter++;
                }
            }
            return counter;
        }
    }
    
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
    @Override
    public boolean contains(Object object) {
        return indexOf(object) != -1;
    }
    
    @Override
    public Iterator<E> iterator() {
        return Arrays.asList((E[]) toArray()).iterator();
    }
    
    @Override
    public Object[] toArray() {
        final Object[] array = new Object[size()];
        try (final Scanner scanner = createScanner()) {
            String line;
            E e;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                final Matcher matcher = DEFAULT_PATTERN_DELIMITER.matcher(line);
                if (!matcher.matches()) {
                    continue;
                }
                e = toElement(matcher.group(2));
                array[Integer.parseInt(matcher.group(1))] = e;
            }
        }
        return array;
    }
    
    @Override
    public <T> T[] toArray(T[] array) {
        return (T[]) toArray();
    }
    
    @Override
    public boolean add(E e) {
        return addElement(e);
    }
    
    @Override
    public boolean remove(Object object) {
        final int index = indexOf(object);
        if (index == -1) {
            return false;
        }
        return removeElement(index);
    }
    
    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object object : collection) {
            if (!contains(object)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (E e : collection) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }
    
    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object object : collection) {
            if (remove(object)) {
                modified = true;
            }
        }
        return modified;
    }
    
    @Override
    public boolean retainAll(Collection<?> collection) {
        final List<Integer> toRemove = new ArrayList<>();
        try (final Scanner scanner = createScanner()) {
            String line;
            E e;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                final Matcher matcher = DEFAULT_PATTERN_DELIMITER.matcher(line);
                if (!matcher.matches()) {
                    continue;
                }
                e = toElement(matcher.group(2));
                if (!collection.contains(e)) {
                    toRemove.add(Integer.parseInt(matcher.group(1)));
                }
            }
        }
        return toRemove.stream().allMatch(this::removeElement);
    }
    
    @Override
    public void clear() {
        getFile().deleteWithoutException();
        getFile().createNewFileWithoutException();
    }
    
}
