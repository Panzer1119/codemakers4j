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

package de.codemakers.base.util.units;

public enum DataUnit {
    BIT(false, 10, 0, "bit", "b") {
        @Override
        public long toBits(long amount) {
            return amount;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount / 8;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount / 1_000 / 8;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount / 1_000_000 / 8;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount / 1_000_000_000 / 8;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount / 1_000_000_000_000L;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount / 1_000_000_000_000L / 8;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000_000_000_000_000L;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toZettabits(long amount) {
            throw new UnsupportedOperationException("Bits can not be converted to Zettabits");
        }
        
        @Override
        public long toZettaBytes(long amount) {
            throw new UnsupportedOperationException("Bits can not be converted to Zettabytes");
        }
        
        @Override
        public long toYottabits(long amount) {
            throw new UnsupportedOperationException("Bits can not be converted to Yottabits");
        }
        
        @Override
        public long toYottaBytes(long amount) {
            throw new UnsupportedOperationException("Bits can not be converted to Yottabytes");
        }
    },
    BYTE(true, 10, 0, "byte", "B") {
        @Override
        public long toBits(long amount) {
            return amount * 8;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount / 1_000 * 8;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount / 1_000_000 * 8;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount / 1_000_000_000 * 8;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount / 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount / 1_000_000_000_000L;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000_000_000_000_000L;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toZettabits(long amount) {
            throw new UnsupportedOperationException("Bytes can not be converted to Zettabits");
        }
        
        @Override
        public long toZettaBytes(long amount) {
            throw new UnsupportedOperationException("Bytes can not be converted to Zettabytes");
        }
        
        @Override
        public long toYottabits(long amount) {
            throw new UnsupportedOperationException("Bytes can not be converted to Yottabits");
        }
        
        @Override
        public long toYottaBytes(long amount) {
            throw new UnsupportedOperationException("Bytes can not be converted to Yottabytes");
        }
    },
    //
    KILOBIT(false, 10, 3, "kilobit", "kb") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000 / 8;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount / 8;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount / 1_000 / 8;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount / 1_000_000 / 8;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount / 1_000_000_000 / 8;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000_000_000_000L;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000_000_000_000L / 8;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000_000_000_000L;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toYottabits(long amount) {
            throw new UnsupportedOperationException("Kilobits can not be converted to Yottabits");
        }
        
        @Override
        public long toYottaBytes(long amount) {
            throw new UnsupportedOperationException("Kilobits can not be converted to Yottabytes");
        }
    },
    KILOBYTE(true, 10, 3, "kilobyte", "kB") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000 * 8;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 8;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount / 1_000 * 8;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount / 1_000_000 * 8;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount / 1_000_000_000 * 8;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000_000_000_000L;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000_000_000_000L;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toYottabits(long amount) {
            throw new UnsupportedOperationException("KiloBytes can not be converted to Yottabits");
        }
        
        @Override
        public long toYottaBytes(long amount) {
            throw new UnsupportedOperationException("KiloBytes can not be converted to Yottabytes");
        }
    },
    KIBIBIT(false, 2, 10, "kibibit", "Kib"),
    KIBIBYTE(true, 2, 10, "kibibyte", "KiB"),
    //
    MEGABIT(false, 10, 6, "megabit", "mb") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000 / 8;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000 / 8;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount / 8;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount / 1_000 / 8;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount / 1_000_000 / 8;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000_000_000 / 8;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000_000_000L;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000_000_000L / 8;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000_000_000_000L;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000_000_000_000_000L / 8;
        }
    },
    MEGABYTE(true, 10, 6, "megabyte", "MB") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000 * 8;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000 * 8;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 8;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount / 1_000 * 8;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount / 1_000_000 * 8;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000_000_000 * 8;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000_000_000L;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000_000_000_000L;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000_000_000_000_000L;
        }
    },
    MEBIBIT(false, 2, 20, "mebibit", "Mib"),
    MEBIBYTE(true, 2, 20, "mebibyte", "MiB"),
    //
    GIGABIT(false, 10, 9, "gigabit", "Gb") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000_000 / 8;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000 / 8;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000 / 8;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount / 8;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount / 1_000 / 8;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000_000 / 8;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000_000 / 8;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000_000_000L;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000_000_000L / 8;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000_000_000_000L;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000_000_000_000L / 8;
        }
    },
    GIGABYTE(true, 10, 9, "gigabyte", "GB") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000_000 * 8;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000 * 8;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000 * 8;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 8;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount / 1_000 * 8;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000_000 * 8;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000_000 * 8;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000_000_000L;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000_000_000_000L;
        }
    },
    GIBIBIT(false, 2, 30, "gibibit", "Gib"),
    GIBIBYTE(true, 2, 30, "gibibyte", "GiB"),
    //
    TERABIT(false, 10, 12, "terabit", "Tb") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000_000_000L / 8;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000_000 / 8;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000 / 8;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000 / 8;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount / 8;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000 / 8;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000 / 8;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000_000 / 8;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000_000_000L;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000_000_000L / 8;
        }
    },
    TERABYTE(true, 10, 12, "terabyte", "TB") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000_000 * 8;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000 * 8;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000 * 8;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount * 8;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount / 1_000 * 8;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000_000 * 8;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000_000 * 8;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000_000_000L;
        }
    },
    TEBIBIT(false, 2, 40, "tebibit", "Tib"),
    TEBIBYTE(true, 2, 40, "tebibyte", "TiB"),
    //
    PETABIT(false, 10, 15, "petabit", "Pb") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000_000_000_000L;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000_000_000L / 8;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000_000 / 8;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000_000 / 8;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount * 1_000 / 8;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount / 8;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000 / 8;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000 / 8;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000_000;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000_000 / 8;
        }
    },
    PETABYTE(true, 10, 15, "petabyte", "PB") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000_000_000_000L;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000_000 * 8;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000_000 * 8;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount * 1_000 * 8;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount * 8;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount / 1_000 * 8;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000_000 * 8;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000_000 * 8;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000_000;
        }
    },
    PEBIBIT(false, 2, 50, "pebibit", "Pib"),
    PEBIBYTE(true, 2, 50, "pebibyte", "PiB"),
    //
    EXABIT(false, 10, 18, "exabit", "Eb") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000_000_000_000L;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000_000_000L / 8;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000_000_000 / 8;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount * 1_000_000 / 8;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount * 1_000 / 8;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount / 8;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000 / 8;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000 / 8;
        }
    },
    EXABYTE(true, 10, 18, "exabyte", "EB") {
        @Override
        public long toBits(long amount) {
            return amount * 1_000_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toBytes(long amount) {
            return amount * 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000_000_000_000L;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000_000_000 * 8;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount * 1_000_000 * 8;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount * 1_000 * 8;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount * 8;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount / 1_000 * 8;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000_000 * 8;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000_000;
        }
    },
    EXBIBIT(false, 2, 60, "exbibit", "Eib"),
    EXBIBYTE(true, 2, 60, "exbibyte", "EiB"),
    //
    ZETTABIT(false, 10, 21, "zettabit", "Zb") {
        @Override
        public long toBits(long amount) {
            throw new UnsupportedOperationException("Zettabits can not be converted to bits");
        }
        
        @Override
        public long toBytes(long amount) {
            throw new UnsupportedOperationException("Zettabits can not be converted to Bytes");
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000_000_000_000L;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000_000_000_000L / 8;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount * 1_000_000_000 / 8;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount * 1_000_000 / 8;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount * 1_000 / 8;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount / 8;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000 / 8;
        }
    },
    ZETTABYTE(true, 10, 21, "zettabyte", "ZB") {
        @Override
        public long toBits(long amount) {
            throw new UnsupportedOperationException("ZettaBytes can not be converted to bits");
        }
        
        @Override
        public long toBytes(long amount) {
            throw new UnsupportedOperationException("ZettaBytes can not be converted to Bytes");
        }
        
        @Override
        public long toKilobits(long amount) {
            return amount * 1_000_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toKiloBytes(long amount) {
            return amount * 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000_000_000_000L;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount * 1_000_000_000 * 8;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount * 1_000_000 * 8;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount * 1_000 * 8;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount * 8;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount / 1_000 * 8;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 1_000;
        }
    },
    ZEBIBIT(false, 2, 70, "zebibit", "Zib"),
    ZEBIBYTE(true, 2, 70, "zebibyte", "ziB"),
    //
    YOTTABIT(false, 10, 24, "yottabit", "Yb") {
        @Override
        public long toBits(long amount) {
            throw new UnsupportedOperationException("Yottabits can not be converted to bits");
        }
        
        @Override
        public long toBytes(long amount) {
            throw new UnsupportedOperationException("Yottabits can not be converted to Bytes");
        }
        
        @Override
        public long toKilobits(long amount) {
            throw new UnsupportedOperationException("Yottabits can not be converted to Kilobits");
        }
        
        @Override
        public long toKiloBytes(long amount) {
            throw new UnsupportedOperationException("Yottabits can not be converted to KiloBytes");
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000_000_000_000_000L;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000_000_000_000_000L / 8;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount * 1_000_000_000_000L / 8;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount * 1_000_000_000 / 8;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount * 1_000_000 / 8;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount * 1_000 / 8;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount / 8;
        }
    },
    YOTTABYTE(true, 10, 24, "yottabyte", "YB") {
        @Override
        public long toBits(long amount) {
            throw new UnsupportedOperationException("YottaBytes can not be converted to bits");
        }
        
        @Override
        public long toBytes(long amount) {
            throw new UnsupportedOperationException("YottaBytes can not be converted to Bytes");
        }
        
        @Override
        public long toKilobits(long amount) {
            throw new UnsupportedOperationException("YottaBytes can not be converted to Kilobits");
        }
        
        @Override
        public long toKiloBytes(long amount) {
            throw new UnsupportedOperationException("YottaBytes can not be converted to KiloBytes");
        }
        
        @Override
        public long toMegabits(long amount) {
            return amount * 1_000_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toMegaBytes(long amount) {
            return amount * 1_000_000_000_000_000_000L;
        }
        
        @Override
        public long toGigabits(long amount) {
            return amount * 1_000_000_000_000_000L * 8;
        }
        
        @Override
        public long toGigaBytes(long amount) {
            return amount * 1_000_000_000_000_000L;
        }
        
        @Override
        public long toTerabits(long amount) {
            return amount * 1_000_000_000_000L * 8;
        }
        
        @Override
        public long toTeraBytes(long amount) {
            return amount * 1_000_000_000_000L;
        }
        
        @Override
        public long toPetabits(long amount) {
            return amount * 1_000_000_000 * 8;
        }
        
        @Override
        public long toPetaBytes(long amount) {
            return amount * 1_000_000_000;
        }
        
        @Override
        public long toExabits(long amount) {
            return amount * 1_000_000 * 8;
        }
        
        @Override
        public long toExaBytes(long amount) {
            return amount * 1_000_000;
        }
        
        @Override
        public long toZettabits(long amount) {
            return amount * 1_000 * 8;
        }
        
        @Override
        public long toZettaBytes(long amount) {
            return amount * 1_000;
        }
        
        @Override
        public long toYottabits(long amount) {
            return amount * 8;
        }
        
        @Override
        public long toYottaBytes(long amount) {
            return amount;
        }
    },
    YOBIBIT(false, 2, 80, "yobibit", "Yib"),
    YOBIBYTE(true, 2, 80, "yobibyte", "YiB");
    
    private final boolean isByte;
    private final int base;
    private final long exponent;
    private final String name;
    private final String symbol;
    
    DataUnit(boolean isByte, int base, long exponent, String name, String symbol) {
        this.isByte = isByte;
        this.base = base;
        this.exponent = exponent;
        this.name = name;
        this.symbol = symbol;
    }
    
    public static DataUnit ofSymbol(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            return null;
        }
        for (DataUnit dataUnit : values()) {
            if (symbol.equals(dataUnit.getSymbol())) {
                return dataUnit;
            }
        }
        return null;
    }
    
    public static DataUnit ofName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (DataUnit dataUnit : values()) {
            if (name.equals(dataUnit.getName())) {
                return dataUnit;
            }
        }
        return null;
    }
    
    public final boolean isByte() {
        return isByte;
    }
    
    public final int getBase() {
        return base;
    }
    
    public final long getExponent() {
        return exponent;
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getSymbol() {
        return symbol;
    }
    
    public final int getMinByteArrayLength(long amount) {
        return (int) Math.ceil(toBytes(amount));
    }
    
    public final byte[] createByteArray(long amount) {
        return new byte[getMinByteArrayLength(amount)];
    }
    
    public long toBits(long amount) {
        if (isByte()) {
            return (long) (Math.pow(base, exponent) * amount * 8);
        } else {
            return (long) (Math.pow(base, exponent) * amount);
        }
    }
    
    public long toBytes(long amount) {
        if (isByte()) {
            return (long) (Math.pow(base, exponent) * amount);
        } else {
            return (long) (Math.pow(base, exponent) * amount / 8);
        }
    }
    
    public long toKilobits(long amount) {
        return (long) (toKibibits(amount) * 1.024);
    }
    
    public long toKiloBytes(long amount) {
        return (long) (toKibiBytes(amount) * 1.024);
    }
    
    public long toMegabits(long amount) {
        return (long) (toMebibits(amount) * 1.048576);
    }
    
    public long toMegaBytes(long amount) {
        return (long) (toMebiBytes(amount) * 1.048576);
    }
    
    public long toGigabits(long amount) {
        return (long) (toGibibits(amount) * 1.073741824);
    }
    
    public long toGigaBytes(long amount) {
        return (long) (toGibiBytes(amount) * 1.073741824);
    }
    
    public long toTerabits(long amount) {
        return (long) (toTebibits(amount) * 1.099511627776);
    }
    
    public long toTeraBytes(long amount) {
        return (long) (toTebiBytes(amount) * 1.099511627776);
    }
    
    public long toPetabits(long amount) {
        return (long) (toPebibits(amount) * 1.125899906842624);
    }
    
    public long toPetaBytes(long amount) {
        return (long) (toPebiBytes(amount) * 1.125899906842624);
    }
    
    public long toExabits(long amount) {
        return (long) (toExbibits(amount) * 1.152921504606846976);
    }
    
    public long toExaBytes(long amount) {
        return (long) (toExbiBytes(amount) * 1.152921504606846976);
    }
    
    public long toZettabits(long amount) {
        return (long) (toZebibits(amount) * 1.180591620717411303424);
    }
    
    public long toZettaBytes(long amount) {
        return (long) (toZebiBytes(amount) * 1.180591620717411303424);
    }
    
    public long toYottabits(long amount) {
        return (long) (toYobibits(amount) * 1.208925819614629174706176);
    }
    
    public long toYottaBytes(long amount) {
        return (long) (toYobiBytes(amount) * 1.208925819614629174706176);
    }
    
    //
    
    public long toKibibits(long amount) {
        return (long) (toKilobits(amount) * 0.9765625);
    }
    
    public long toKibiBytes(long amount) {
        return (long) (toKiloBytes(amount) * 0.9765625);
    }
    
    public long toMebibits(long amount) {
        return (long) (toMegabits(amount) * 0.95367431640625);
    }
    
    public long toMebiBytes(long amount) {
        return (long) (toMegaBytes(amount) * 0.95367431640625);
    }
    
    public long toGibibits(long amount) {
        return (long) (toGigabits(amount) * 0.931322574615478515625);
    }
    
    public long toGibiBytes(long amount) {
        return (long) (toGigaBytes(amount) * 0.931322574615478515625);
    }
    
    public long toTebibits(long amount) {
        return (long) (toTerabits(amount) * 0.9094947017729282379150390625);
    }
    
    public long toTebiBytes(long amount) {
        return (long) (toTeraBytes(amount) * 0.9094947017729282379150390625);
    }
    
    public long toPebibits(long amount) {
        return (long) (toPetabits(amount) * 0.88817841970012523233890533447265625);
    }
    
    public long toPebiBytes(long amount) {
        return (long) (toPetaBytes(amount) * 0.88817841970012523233890533447265625);
    }
    
    public long toExbibits(long amount) {
        return (long) (toExabits(amount) * 0.867361737988403547205962240695953369140625);
    }
    
    public long toExbiBytes(long amount) {
        return (long) (toExaBytes(amount) * 0.867361737988403547205962240695953369140625);
    }
    
    public long toZebibits(long amount) {
        return (long) (toZettabits(amount) * 0.8470329472543003390683225006796419620513916015625);
    }
    
    public long toZebiBytes(long amount) {
        return (long) (toZettaBytes(amount) * 0.8470329472543003390683225006796419620513916015625);
    }
    
    public long toYobibits(long amount) {
        return (long) (toYottabits(amount) * 0.82718061255302767487140869206996285356581211090087890625);
    }
    
    public long toYobiBytes(long amount) {
        return (long) (toYottaBytes(amount) * 0.82718061255302767487140869206996285356581211090087890625);
    }
    
    @Override
    public final String toString() {
        return getClass().getSimpleName() + "{" + "isByte=" + isByte + ", base=" + base + ", exponent=" + exponent + ", name='" + name + '\'' + ", symbol='" + symbol + '\'' + '}';
    }
    
}
