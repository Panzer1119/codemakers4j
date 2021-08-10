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

import com.j256.simplemagic.ContentInfo;
import de.codemakers.io.file.FileUtil;
import de.codemakers.io.file.IFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class ContentInfoFileFilter implements Predicate<IFile<?, ?>> {
    
    private final List<ContentInfo> contentInfos = new ArrayList<>();
    
    public ContentInfoFileFilter(ContentInfo... contentInfos) {
        this(Arrays.asList(contentInfos));
    }
    
    public ContentInfoFileFilter(Collection<ContentInfo> contentInfos) {
        this.contentInfos.addAll(contentInfos);
    }
    
    @Override
    public boolean test(IFile<?, ?> iFile) {
        if (iFile == null || !iFile.exists() || !iFile.isFile()) {
            return false;
        }
        return FileUtil.findMatch(iFile.createInputStreamWithoutException())
                .map(contentInfos::contains)
                .orElse(false);
    }
    
    @Override
    public String toString() {
        return "MagicNumberFileFilter{" + "contentInfos=" + contentInfos + '}';
    }
    
}
