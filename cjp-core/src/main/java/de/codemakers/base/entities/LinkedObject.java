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

package de.codemakers.base.entities;

public class LinkedObject<T> {
    
    private LinkedObject<T> parent;
    private T object;
    private transient LinkedObject<T> child;
    
    public LinkedObject(T object) {
        this(object, null, null);
    }
    
    public LinkedObject(T object, LinkedObject<T> parent, LinkedObject<T> child) {
        this.object = object;
        setParent(parent);
        setChild(child);
    }
    
    public LinkedObject<T> getRootParent() {
        LinkedObject<T> root = this;
        while (root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }
    
    public LinkedObject<T> getParent() {
        return parent;
    }
    
    public LinkedObject<T> setParent(LinkedObject<T> parent) {
        this.parent = parent;
        if (parent != null) {
            if (parent.getChild() != null) {
                throw new RuntimeException("Error parent is already associated with a child");
            }
            parent.child = this;
        }
        return this;
    }
    
    public T getObject() {
        return object;
    }
    
    public LinkedObject<T> setObject(T object) {
        this.object = object;
        return this;
    }
    
    public LinkedObject<T> getRootChild() {
        LinkedObject<T> child = this;
        while (child.getChild() != null) {
            child = child.getChild();
        }
        return child;
    }
    
    public LinkedObject<T> getChild() {
        return child;
    }
    
    public LinkedObject<T> setChild(LinkedObject<T> child) {
        this.child = child;
        if (child != null) {
            if (child.getParent() != null) {
                throw new RuntimeException("Error child is already associated with a parent");
            }
            child.parent = this;
        }
        return this;
    }
    
    @Override
    public String toString() {
        return toString(0, parent != null, parent == null);
    }
    
    public String toString(int depth, boolean showParents, boolean showChilds) {
        String tabs = "";
        for (int i = 0; i < depth; i++) {
            tabs += "\t";
        }
        return String.format("%n%1$s%2$s {%n%1$s\tparent=%3$s%n%1$s\tobject=%4$s%n%1$s\tchild=%5$s%n%1$s}", tabs, getClass().getSimpleName(), (showParents ? parent == null ? parent : parent.toString(depth + 1, showParents, showChilds) : "NN"), (object == null || !(object instanceof LinkedObject)) ? object : ((LinkedObject) object).toString(depth + 1, showParents, showChilds), (showChilds ? child == null ? child : child.toString(depth + 1, showParents, showChilds) : "NN"));
    }
    
}
