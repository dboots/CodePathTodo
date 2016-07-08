package com.donboots.codepathtodo;

import com.orm.SugarRecord;

public class Todo extends SugarRecord {
    String label;

    public Todo() { }

    public Todo(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
