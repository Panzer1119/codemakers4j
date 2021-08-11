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

package de.codemakers.math.eval_1.value_double;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.codemakers.base.entities.LinkedObject;
import de.codemakers.base.reflection.ReflectionUtil;
import de.codemakers.base.util.StringUtil;
import de.codemakers.math.eval_1.Evaluator;
import de.codemakers.math.eval_1.Operator;
import de.codemakers.math.eval_1.Value;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DoubleEvaluator extends Evaluator<Double> {
    
    public static final String REGEX_NUMBER = "([0-9]+(?:\\.[0-9]+)?)";
    public static final Pattern PATTERN_NUMBER = Pattern.compile(REGEX_NUMBER);
    
    public static final Value<Double> ZERO = new DoubleValue();
    public LinkedObject temp;
    private List<LinkedObject> storage;
    
    public static BiMap<String, Operator> resolveOperators() {
        final List<Operator> operators = resolveOperatorsDirect();
        final BiMap<String, Operator> operators_ = HashBiMap.create();
        operators.forEach((operator) -> operators_.put(operator.operationSign(), operator));
        return operators_;
    }
    
    public static List<Operator> resolveOperatorsDirect() {
        return ReflectionUtil.getTypesAnnotatedWith(Operator.class).stream().filter((op) -> DoubleOperationValue.class.isAssignableFrom(op)).map((op) -> op.getAnnotation(Operator.class)).collect(Collectors.toList());
    }
    
    public static double evalLinkedObject(LinkedObject linkedObject) {
        Objects.requireNonNull(linkedObject);
        final AtomicReference<Double> result = new AtomicReference<>(0.0);
        Object last = null;
        LinkedObject temp = linkedObject.getRootParent();
        while (temp.getChild() != null) {
            temp = temp.getChild();
            if (temp.getObject() instanceof Double) {
                if (last == null) {
                    result.set((double) temp.getObject());
                    last = temp.getObject();
                } else {
                    //What?
                }
            } else if (temp.getObject() instanceof String) {
                //What?
            } else if (temp.getObject() instanceof LinkedObject) {
                //What?
            } else {
                throw new InvalidParameterException("\"" + temp.getObject() + "\" is invalid");
            }
        }
        return result.get();
    }
    
    @Override
    protected Double evalIntern(String expression) {
        Objects.requireNonNull(expression);
        expression = expression.trim();
        expression = expression.replaceAll("\\s", "");
        if (expression.isEmpty()) {
            return 0.0;
        }
        final int count_po = StringUtil.count(expression, "(");
        final int count_pc = StringUtil.count(expression, ")");
        if (count_po != count_pc) {
            if (count_po < count_pc) {
                throw new IllegalArgumentException("Needs " + (count_pc - count_po) + " more opened parentheses");
            } else {
                throw new IllegalArgumentException("Needs " + (count_po - count_pc) + " more closed parentheses");
            }
        }
        final BiMap<String, Operator> operators = resolveOperators();
        storage = new ArrayList<>();
        storage.add(null);
        while (!expression.isEmpty()) {
            System.out.println("expression  1: " + expression);
            final Matcher matcher = PATTERN_NUMBER.matcher(expression);
            if (matcher.find() && matcher.start() == 0) {
                final double value = Double.parseDouble(matcher.group(1));
                final LinkedObject linkedObject = storage.get(storage.size() - 1);
                storage.set(storage.size() - 1, new LinkedObject(value, linkedObject, null));
                System.out.println("================================================================================");
                System.out.println("1===============================================================================");
                System.out.println("================================================================================");
                System.out.println("linkedObject: " + storage.get(0));
                expression = expression.substring(matcher.end());
                System.out.println(" expression 2: " + expression);
            } else {
                if (expression.startsWith("(")) {
                    //storage.add(storage.get(storage.size() - 1));
                    ////final LinkedObject temp = new LinkedObject(null);
                    ////final LinkedObject linkedObject = storage.get(storage.size() - 1);
                    ////storage.set(storage.size() - 1, new LinkedObject(temp, linkedObject));
                    //storage.get(storage.size() - 1).setObject(linkedObject);
                    ////storage.add(temp);
                    storage.add(null);
                    expression = expression.substring(1);
                } else if (expression.startsWith(")")) {
                    //storage.set(storage.size() - 2, storage.get(storage.size() - 1));
                    //final LinkedObject linkedObject = storage.get(storage.size() - 2);
                    //storage.set(storage.size() - 2, new LinkedObject(storage.get(storage.size() - 1), linkedObject));
                    final LinkedObject linkedObject = storage.get(storage.size() - 2);
                    storage.set(storage.size() - 2, new LinkedObject(storage.get(storage.size() - 1), linkedObject, null));
                    storage.remove(storage.size() - 1);
                    expression = expression.substring(1);
                } else {
                    for (String operatorSign : operators.keySet()) {
                        if (expression.startsWith(operatorSign)) {
                            final LinkedObject linkedObject = storage.get(storage.size() - 1);
                            storage.set(storage.size() - 1, new LinkedObject(operatorSign, linkedObject, null));
                            System.out.println("================================================================================");
                            System.out.println("2===============================================================================");
                            System.out.println("================================================================================");
                            System.out.println("linkedObject: " + storage.get(0));
                            expression = expression.substring(operatorSign.length());
                            break;
                        }
                    }
                }
            }
        }
        temp = storage.get(0).getRootChild();
        return evalLinkedObject(temp);
    }
    
}
