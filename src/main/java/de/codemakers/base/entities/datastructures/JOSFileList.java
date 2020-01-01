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

package de.codemakers.base.entities.datastructures;

import de.codemakers.io.SerializationUtil;
import de.codemakers.io.file.AdvancedFile;

import java.io.Serializable;
import java.util.Base64;

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
        try {
            return SerializationUtil.bytesToObject(Base64.getDecoder().decode(line), (Class<E>) null);
        } catch (Exception ex) {
            return null;
        }
    }
    
    @Override
    public String fromElement(E e) {
        try {
            return Base64.getEncoder().encodeToString(SerializationUtil.objectToBytes(e));
        } catch (Exception ex) {
            return null;
        }
    }
    
}
