/*
 *    Copyright 2021 Paul Hagedorn (Panzer1119)
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

package de.codemakers.database;

import de.codemakers.database.connectors.DatabaseConnector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PrivateTest {
    
    public static DatabaseConnector databaseConnector;
    
    @BeforeAll
    static void setUp() {
        databaseConnector = new DatabaseConnector(null, null) {
            @Override
            protected void initDefaultData() {
                //Nothing
            }
        };
    }
    
    @Test
    void testHibernate() {
    
    }
    
}
