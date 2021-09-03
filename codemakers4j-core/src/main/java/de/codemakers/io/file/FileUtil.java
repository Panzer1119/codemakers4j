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

package de.codemakers.io.file;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    
    public static final ContentInfoUtil CONTENT_INFO_UTIL = new ContentInfoUtil();
    
    public static final Pattern PATTERN_INVALID_FILENAME_WINDOWS = Pattern.compile("[<>:\"/|?*]");
    
    private static final Logger logger = LogManager.getLogger(FileUtil.class);
    
    public static Optional<ContentInfo> findMatch(String filePath) {
        try {
            return Optional.ofNullable(CONTENT_INFO_UTIL.findMatch(filePath));
        } catch (IOException e) {
            logger.error("Error while testing for ContentInfo on filePath: " + filePath, e);
            return Optional.empty();
        }
    }
    
    public static Optional<ContentInfo> findMatch(File file) {
        try {
            return Optional.ofNullable(CONTENT_INFO_UTIL.findMatch(file));
        } catch (IOException e) {
            logger.error("Error while testing for ContentInfo on file: " + file, e);
            return Optional.empty();
        }
    }
    
    public static Optional<ContentInfo> findMatch(InputStream inputStream) {
        try {
            return Optional.ofNullable(CONTENT_INFO_UTIL.findMatch(inputStream));
        } catch (IOException e) {
            logger.error("Error while testing for ContentInfo", e);
            return Optional.empty();
        }
    }
    
    public static Optional<ContentInfo> findMatch(byte[] bytes) {
        return Optional.ofNullable(CONTENT_INFO_UTIL.findMatch(bytes));
    }
    
    public static String toValidFilename(String filename) {
        return toValidFilename(filename, "");
    }
    
    public static String toValidFilename(String filename, String replacement) {
        if (filename == null) {
            return null;
        }
        return PATTERN_INVALID_FILENAME_WINDOWS.matcher(filename).replaceAll(Matcher.quoteReplacement(replacement));
    }
    
}
