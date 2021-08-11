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

package de.codemakers.io.file.filters;

import de.codemakers.io.file.AdvancedFile;

@FunctionalInterface
public interface AdvancedFilenameFilter extends AdvancedFileFilter {
    
    boolean test(AdvancedFile parent, String name);
    
    @Override
    default boolean test(AdvancedFile file) {
        if (file == null) {
            return false;
        }
        return test(file.getParentFile(), file.getName());
    }
    
}
