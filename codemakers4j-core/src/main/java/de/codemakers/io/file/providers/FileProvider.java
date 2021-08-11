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

package de.codemakers.io.file.providers;

import de.codemakers.base.util.tough.ToughSupplier;
import de.codemakers.io.file.IFile;
import de.codemakers.io.file.filters.AdvancedFileFilter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

public abstract class FileProvider<T extends IFile> implements Serializable { //TODO Make some "RARProvider", which can at least read/list .rar files, and maybe autodetect WinRaR installations for optionally writing/editing .rar files (Make some OS function for that, so that this framework has some useful features (finding WinRaR installations platform independent))
    
    public List<T> listFiles(T parent, T file, boolean recursive, ToughSupplier<InputStream> inputStreamSupplier) throws Exception {
        return listFiles(parent, file, recursive, inputStreamSupplier, null);
    }
    
    public abstract List<T> listFiles(T parent, T file, boolean recursive, ToughSupplier<InputStream> inputStreamSupplier, AdvancedFileFilter advancedFileFilter) throws Exception;
    
    public abstract boolean isFile(T parent, T file, ToughSupplier<InputStream> inputStreamSupplier) throws Exception;
    
    public abstract boolean isDirectory(T parent, T file, ToughSupplier<InputStream> inputStreamSupplier) throws Exception;
    
    public abstract boolean exists(T parent, T file, ToughSupplier<InputStream> inputStreamSupplier) throws Exception;
    
    public abstract InputStream createInputStream(T parent, T file, ToughSupplier<InputStream> inputStreamSupplier) throws Exception;
    
    public abstract byte[] readBytes(T parent, T file, ToughSupplier<InputStream> inputStreamSupplier) throws Exception;
    
    public abstract boolean canWrite(T parent, T file);
    
    public abstract OutputStream createOutputStream(T parent, T file, boolean append) throws Exception;
    
    public abstract boolean writeBytes(T parent, T file, byte[] data) throws Exception;
    
    public abstract boolean createNewFile(T parent, T file) throws Exception;
    
    public abstract boolean delete(T parent, T file) throws Exception;
    
    public abstract boolean mkdir(T parent, T file) throws Exception;
    
    public abstract boolean mkdirs(T parent, T file) throws Exception;
    
    public boolean test(T file) {
        return test((T) file.getParentFile(), file.getName(), file, file.exists());
    }
    
    public abstract boolean test(T parent, String name, T file, boolean exists);
    
    public abstract int getPriority(T parent, String name);
    
    public void processPaths(T parent, String path, List<String> paths) {
    }
    
}
