package com.donboots.codepathtodo;

import com.orm.SugarRecord;

public class Todo extends SugarRecord {
    String label;
    String date;
    int sort;

    public Todo() { }

    public Todo(String label, String date, int sort) {
        this.label = label;
        this.date = date;
        this.sort = sort;
    }
}
