/*
 *     Copyright 2018 Paul Hagedorn (Panzer1119)
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

package de.codemakers.io.file.t1;

import java.io.File;
import java.nio.file.Files;

public class TestAdvancedFile {
    
    public static final String FILE_SEPARATOR_WINDOWS = "\\";
    public static final String FILE_SEPARATOR_UNIX = "/";
    private final String separator;
    private PathEntry path = null;
    
    public TestAdvancedFile(String path) {
        boolean isUnix = path.contains(FILE_SEPARATOR_UNIX);
        if (isUnix && !path.contains(FILE_SEPARATOR_WINDOWS)) {
            separator = FILE_SEPARATOR_UNIX;
        } else if (!isUnix && path.contains(FILE_SEPARATOR_WINDOWS)) {
            separator = FILE_SEPARATOR_WINDOWS;
        } else {
            throw new RuntimeException("Path may not contain Unix and Windows file separators at the same time");
        }
        final String[] split = path.split(isUnix ? separator : separator + separator);
        for (String p : split) {
            if (this.path == null) {
                this.path = new PathEntry(p, false);
            } else {
                this.path = new PathEntry(this.path, p, p.contains("."));
            }
        }
    }
    
    public final PathEntry getPath() {
        return path;
    }
    
    public final String getSeparator() {
        return separator;
    }
    
    public final String getPathString() {
        return path.toPathString(getSeparator());
    }
    
    public final byte[] toBytes() throws Exception {
        PathEntry pathEntry = path;
        FileProvider fileProvider = null;
        while (pathEntry != null) {
            if (pathEntry.getProvider() != null) {
                fileProvider = pathEntry.getProvider();
                break;
            }
            pathEntry = pathEntry.getParent();
        }
        if (fileProvider == null) {
            return Files.readAllBytes(new File(getPathString()).toPath());
        } else {
            System.out.println("QUESTION : path.toPathString(separator)                    : " + path.toPathString(separator));
            System.out.println("QUESTION : pathEntry.toPathString(separator)               : " + pathEntry.toPathString(separator));
            System.out.println("QUESTION : path.subtract(pathEntry).toPathString(separator): " + path.subtract(pathEntry).toPathString(separator));
            return fileProvider.readFile(path.subtract(pathEntry).toPathString(separator));
        }
    }
    
    @Override
    public String toString() {
        return "TestAdvancedFile{" + "path=" + path + ", separator='" + separator + '\'' + '}';
    }
    
}
