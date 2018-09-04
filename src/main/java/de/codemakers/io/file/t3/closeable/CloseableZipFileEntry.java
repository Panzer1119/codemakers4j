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

package de.codemakers.io.file.t3.closeable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CloseableZipFileEntry extends CloseableZipEntry<ZipFile> {
    
    public CloseableZipFileEntry(ZipFile zipFile, ZipEntry zipEntry) {
        super(zipFile, zipEntry);
    }
    
    public final ZipFile getZipFile() {
        return getCloseable();
    }
    
    @Override
    public <R> R close(Function<ZipEntry, R> function, Consumer<Throwable> failureClosing) throws Exception {
        return super.close(function, failureClosing);
    }
    
    @Override
    public <R> R close(Function<ZipEntry, R> function, Consumer<Throwable> failureFunction, Consumer<Throwable> failureClosing) {
        return super.close(function, failureFunction, failureClosing);
    }
    
    @Override
    public <R> R closeWithoutException(Function<ZipEntry, R> function, Consumer<Throwable> failureFunction) {
        return super.closeWithoutException(function, failureFunction);
    }
    
    @Override
    public <R> R closeWithoutException(Function<ZipEntry, R> function) {
        return super.closeWithoutException(function);
    }
    
}