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

package de.codemakers.math;

import de.codemakers.base.util.interfaces.ByteSerializable;
import de.codemakers.base.util.interfaces.Copyable;

public abstract class AbstractNumber implements ByteSerializable, Copyable {
    
    public abstract AbstractNumber add(AbstractNumber number);
    
    public abstract AbstractNumber subtract(AbstractNumber number);
    
    public abstract AbstractNumber multiply(AbstractNumber number);
    
    public abstract AbstractNumber divide(AbstractNumber number);
    
    public abstract AbstractNumber pow(AbstractNumber number);
    
    public abstract AbstractNumber mod(AbstractNumber number);
    
    public abstract AbstractNumber negate();
    
    public abstract AbstractNumber inverse();
    
    public abstract AbstractNumber sgn();
    
    public abstract double toDouble();
    
    public abstract float toFloat();
    
    public abstract long toLong();
    
    public abstract int toInt();
    
    public abstract short toShort();
    
    public abstract byte toByte();
    
}
