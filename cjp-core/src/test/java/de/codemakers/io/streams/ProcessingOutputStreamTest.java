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

package de.codemakers.io.streams;

import de.codemakers.io.processing.Base64DecodingProcessor;
import de.codemakers.io.processing.Base64EncodingProcessor;
import org.apache.commons.io.IOUtils;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.Base64;

public class ProcessingOutputStreamTest {
    
    public static final void main(String[] args) throws Exception {
        final PipedOutputStream pipedOutputStream = new PipedOutputStream();
        final PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        final ProcessingOutputStream processingOutputStream = new ProcessingOutputStream(pipedOutputStream, new Base64EncodingProcessor());
        final String string_1 = "Test String, test this please!";
        System.out.println("string_1           =" + string_1);
        final byte[] bytes_1 = string_1.getBytes();
        System.out.println("bytes_1            =" + Arrays.toString(bytes_1));
        for (byte b : bytes_1) {
            processingOutputStream.write(b);
        }
        processingOutputStream.close();
        final String base64_1_string_org = Base64.getEncoder().encodeToString(bytes_1);
        System.out.println("base64_1_string_org=" + base64_1_string_org);
        final byte[] base64_1_bytes_org = Base64.getEncoder().encode(string_1.getBytes());
        System.out.println("base64_1_bytes_org =" + Arrays.toString(base64_1_bytes_org));
        //final byte[] bytes_ = IOUtils.toByteArray(pipedInputStream);
        final ProcessingInputStream processingInputStream = new ProcessingInputStream(pipedInputStream, new Base64DecodingProcessor());
        final byte[] bytes_ = IOUtils.toByteArray(processingInputStream);
        System.out.println("bytes_             =" + Arrays.toString(bytes_));
        System.out.println("bytes_            S=" + new String(bytes_));
    }
    
}
