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

package de.codemakers.base.util;

import de.codemakers.base.Standard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class XMLUtil {
    
    public static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    public static final DocumentBuilder DOCUMENT_BUILDER = Standard.silentError(DOCUMENT_BUILDER_FACTORY::newDocumentBuilder);
    
    public static Document parseToMemoryOrNull(File file) {
        try {
            return parseToMemory(file);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static Document parseToMemory(File file) throws IOException, SAXException {
        return DOCUMENT_BUILDER.parse(file);
    }
    
    public static Document parseToMemoryOrNull(String uri) {
        try {
            return parseToMemory(uri);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static Document parseToMemory(String uri) throws IOException, SAXException {
        return DOCUMENT_BUILDER.parse(uri);
    }
    
    public static Document parseToMemoryOrNull(InputSource inputSource) {
        try {
            return parseToMemory(inputSource);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static Document parseToMemory(InputSource inputSource) throws IOException, SAXException {
        return DOCUMENT_BUILDER.parse(inputSource);
    }
    
    public static Document parseToMemoryOrNull(InputStream inputStream) {
        try {
            return parseToMemory(inputStream);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static Document parseToMemory(InputStream inputStream) throws IOException, SAXException {
        return DOCUMENT_BUILDER.parse(inputStream);
    }
    
    public static Document parseToMemoryOrNull(InputStream inputStream, String systemId) {
        try {
            return parseToMemory(inputStream, systemId);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static Document parseToMemory(InputStream inputStream, String systemId) throws IOException, SAXException {
        return DOCUMENT_BUILDER.parse(inputStream, systemId);
    }
    
    public static Stream<Element> stream(NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength()).boxed().map(nodeList::item).map(Element.class::cast);
    }
    
}
