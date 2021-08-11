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

package de.codemakers.io.image;

import de.codemakers.base.util.ConvertUtil;

public class QRCodeGeneratorTest {
    
    public static void main(String[] args) {
        final Container container = new Container(33, 33);
    }
    
    static class Container {
        
        private final int width;
        private final int height;
        private final boolean[][] data;
    
        public Container(int byteCount) {
            this(byteCount, 0.5);
        }
        
        public Container(int byteCount, double errorCorrection) {
            //this(length, length);
            this(0/0, 0/0);
        }
        
        public Container(int width, int height) {
            this.width = width;
            this.height = height;
            this.data = new boolean[width][height];
        }
        
        public int getWidth() {
            return width;
        }
        
        public int getHeight() {
            return height;
        }
        
        public boolean[][] getData() {
            return data;
        }
        
        public boolean get(int x, int y) {
            return data[x][y];
        }
        
        public void set(int x, int y, boolean state) {
            data[x][y] = state;
        }
        
        public boolean isInBounds(int x, int y) {
            return x >= 0 && x < width && y >= 0 && y < height;
        }
        
        public boolean[] getDataAs1DArray() {
            final boolean[] output = new boolean[width * height];
            for (int i = 0; i < width; i++) {
                System.arraycopy(data[i], 0, output, i * height, height);
            }
            return output;
        }
        
        public byte[] toBytes() {
            return ConvertUtil.booleanArrayToByteArray(getDataAs1DArray()); //TODO Maybe fix this and let it accept arrays that length is not a multiple of Byte.SIZE
        }
        
    }
    
}
