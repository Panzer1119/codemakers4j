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

import de.codemakers.io.SerializationUtil;
import de.codemakers.io.file.AdvancedFile;

import java.io.Serializable;
import java.util.Base64;
import java.util.Optional;

/**
 * Java Object Serialized File List
 */
public class JOSFileList<E extends Serializable> extends AbstractFileList<E> {
    
    public JOSFileList() {
        super();
    }
    
    public JOSFileList(AdvancedFile file) {
        super(file);
    }
    
    @Override
    public E toElement(String line) {
        final byte[] data = Base64.getDecoder().decode(line);
        final Optional<E> optional = SerializationUtil.bytesToObject(data);
        return optional.orElse(null);
    }
    
    @Override
    public String fromElement(E e) {
        final Optional<byte[]> optional = SerializationUtil.objectToBytes(e);
        return optional.map(Base64.getEncoder()::encodeToString).orElse(null);
    }
    
}
