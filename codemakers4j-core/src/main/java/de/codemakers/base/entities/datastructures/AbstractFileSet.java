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
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractFileSet<E> implements ConvertingSet<E, String> {
    
    public static final String DEFAULT_FILE_EXTENSION = "txt";
    
    protected static AdvancedFile getRandomFile() {
        return new AdvancedFile(Standard.silentError(() -> File.createTempFile(AbstractFileSet.class.getSimpleName(), "." + DEFAULT_FILE_EXTENSION)));
    }
    
    protected AdvancedFile file;
    
    public AbstractFileSet() {
        this(getRandomFile());
    }
    
    public AbstractFileSet(AdvancedFile file) {
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
    
    private boolean addElement(E e) {
        final String toWrite = fromElement(e);
        if (toWrite != null) {
            return false;
        }
        try (final BufferedWriter bufferedWriter = createBufferedWriter(true)) {
            bufferedWriter.write(toWrite);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    private boolean setElement(int lineIndex, E e) {
        final AdvancedFile temp = getRandomFile();
        temp.createNewFileWithoutException();
        try (final PrintWriter printWriter = new PrintWriter(new FileWriter(temp.toFile()))) {
            final AtomicInteger counter = new AtomicInteger(0);
            Files.lines(getFile().toPath()).filter((line) -> counter.getAndIncrement() != lineIndex).forEach(printWriter::println);
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
            return addElement(e);
        }
        return true;
    }
    
    private boolean removeElement(int lineIndex) {
        return setElement(lineIndex, null);
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
        return lineIndexOf(object) != -1;
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
            int counter = 0;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                e = toElement(line);
                array[counter++] = e;
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
        final int lineIndex = lineIndexOf(object);
        if (lineIndex == -1) {
            return false;
        }
        return removeElement(lineIndex);
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
            int counter = 0;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                e = toElement(line);
                if (!collection.contains(e)) {
                    toRemove.add(counter);
                }
                counter++;
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
