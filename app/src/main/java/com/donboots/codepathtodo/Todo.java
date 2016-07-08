package com.donboots.codepathtodo;

import com.orm.SugarRecord;

public class Todo extends SugarRecord {
    String label;
    String date;

    public Todo() { }

    public Todo(String label, String date) {
        this.label = label;
        this.date = date;
    }
}
