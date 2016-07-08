package com.donboots.codepathtodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemsActivity extends Activity {
    Todo mTodo;
    long mTodoId;
    EditText mTxtLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_items);

        mTxtLabel = (EditText) findViewById(R.id.txtLabel);

        Bundle bundle = getIntent().getExtras();
        mTodoId = bundle.getLong("todoId");
        mTodo = Todo.findById(Todo.class, mTodoId);

        mTxtLabel.setText(mTodo.label);
    }

    public void onSave(View view) {
        mTodo.label = mTxtLabel.getText().toString();
        mTodo.save();

        Intent i = new Intent(EditItemsActivity.this, MainActivity.class);
        startActivityForResult(i, 99);

        finish();
    }
}
