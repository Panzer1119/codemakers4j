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

package de.codemakers;/*
 *    Copyright 2018 Paul Hagedorn (Panzer1119)
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

import de.codemakers.base.CJP;
import de.codemakers.base.action.Action;
import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.action.RunningAction;

import java.util.concurrent.TimeUnit;

public class ActionTest {

    public static final void main(String[] args) {
        CJP.shutdown().queueAfter(5, TimeUnit.SECONDS);
        final ReturningAction<String> test_1 = Action.ofToughSupplier(() -> {
            Thread.sleep(3000);
            return "Test 1 Re";
        });
        test_1.queue(System.out::println, System.err::println);
        System.out.println("Test 1");
        final RunningAction test_2 = Action.ofToughRunnable(() -> {
            Thread.sleep(3000);
        });
        test_2.queue(() -> System.out.println("Test 2 Re"), System.err::println);
        System.out.println("Test 2");
    }
}
