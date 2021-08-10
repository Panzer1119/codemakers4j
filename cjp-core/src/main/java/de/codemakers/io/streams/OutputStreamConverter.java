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

package de.codemakers.io.streams;

import de.codemakers.base.exceptions.RethrownRuntimeException;
import de.codemakers.base.util.tough.ToughFunction;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.*;

public class OutputStreamConverter implements ToughFunction<OutputStream, OutputStream> {
    
    protected ToughFunction<OutputStream, OutputStream> outputStreamFunction;
    
    public OutputStreamConverter() {
        this(null);
    }
    
    public OutputStreamConverter(ToughFunction<OutputStream, OutputStream> outputStreamFunction) {
        this.outputStreamFunction = outputStreamFunction;
    }
    
    public ToughFunction<OutputStream, OutputStream> getOutputStreamFunction() {
        return outputStreamFunction;
    }
    
    public OutputStreamConverter setOutputStreamFunction(ToughFunction<OutputStream, OutputStream> outputStreamFunction) {
        this.outputStreamFunction = outputStreamFunction;
        return this;
    }
    
    public OutputStreamConverter addOutputStreamFunction(ToughFunction<OutputStream, OutputStream> outputStreamFunction) {
        if (this.outputStreamFunction == null || outputStreamFunction == null) {
            this.outputStreamFunction = outputStreamFunction;
        } else {
            final ToughFunction<OutputStream, OutputStream> temp = this.outputStreamFunction;
            this.outputStreamFunction = (outputStream) -> outputStreamFunction.apply(temp.apply(outputStream));
        }
        return this;
    }
    
    public OutputStreamConverter addOutputStreamFunctions(ToughFunction<OutputStream, OutputStream>... outputStreamFunctions) {
        for (ToughFunction<OutputStream, OutputStream> outputStreamFunction : outputStreamFunctions) {
            addOutputStreamFunction(outputStreamFunction);
        }
        return this;
    }
    
    @Override
    public OutputStream apply(OutputStream outputStream) throws Exception {
        if (outputStreamFunction == null || outputStream == null) {
            return null;
        }
        return outputStreamFunction.apply(outputStream);
    }
    
    public <T extends OutputStream> T convert(Class<T> clazz, OutputStream outputStream) {
        return (T) applyWithoutException(outputStream);
    }
    
    public <T extends OutputStream> T convert(OutputStream outputStream) {
        return (T) applyWithoutException(outputStream);
    }
    
    public OutputStreamConverter toBufferedOutputStream() {
        return addOutputStreamFunction(BufferedOutputStream::new);
    }
    
    public OutputStreamConverter toBufferedOutputStream(final int bufferSize) {
        return addOutputStreamFunction((outputStream) -> new BufferedOutputStream(outputStream, bufferSize));
    }
    
    public OutputStreamConverter toDataOutputStream() {
        return addOutputStreamFunction(DataOutputStream::new);
    }
    
    public OutputStreamConverter toObjectOutputStream() {
        try {
            return addOutputStreamFunction(ObjectOutputStream::new);
        } catch (Exception ex) {
            throw new RethrownRuntimeException(ex);
        }
    }
    
    public OutputStreamConverter toDeflaterOutputStream() {
        return addOutputStreamFunction(DeflaterOutputStream::new);
    }
    
    public OutputStreamConverter toDeflaterOutputStream(final Deflater deflater) {
        return addOutputStreamFunction((outputStream) -> new DeflaterOutputStream(outputStream, deflater));
    }
    
    public OutputStreamConverter toDeflaterOutputStream(final Deflater deflater, final int bufferSize) {
        return addOutputStreamFunction((outputStream) -> new DeflaterOutputStream(outputStream, deflater, bufferSize));
    }
    
    public OutputStreamConverter toInflaterOutputStream() {
        return addOutputStreamFunction(InflaterOutputStream::new);
    }
    
    public OutputStreamConverter toInflaterOutputStream(final Inflater inflater) {
        return addOutputStreamFunction((outputStream) -> new InflaterOutputStream(outputStream, inflater));
    }
    
    public OutputStreamConverter toInflaterOutputStream(final Inflater inflater, final int bufferSize) {
        return addOutputStreamFunction((outputStream) -> new InflaterOutputStream(outputStream, inflater, bufferSize));
    }
    
    public OutputStreamConverter toGZIPOutputStream() {
        return addOutputStreamFunction(GZIPOutputStream::new);
    }
    
    public OutputStreamConverter toCipherOutputStream(final Cipher cipher) {
        return addOutputStreamFunction((outputStream) -> new CipherOutputStream(outputStream, cipher));
    }
    
    public OutputStreamBuilder toOutputStreamBuilder(OutputStream outputStream) {
        return new OutputStreamBuilder(convert(outputStream));
    }
    
    @Override
    public String toString() {
        return "OutputStreamConverter{" + "outputStreamFunction=" + outputStreamFunction + '}';
    }
    
}
