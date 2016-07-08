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
    EditText mTxtDate;
    Bundle mBundle;

    /*
    * Currently the mode (edit or add) is determined by a getExtras() bundle existing or not.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_items);

        mTxtLabel = (EditText) findViewById(R.id.etLabel);
        mTxtDate = (EditText) findViewById(R.id.etDate);
        mBundle = getIntent().getExtras();

        if (mBundle != null) {
            mTodoId = mBundle.getLong("todoId");
            mTodo = Todo.findById(Todo.class, mTodoId);

            mTxtLabel.setText(mTodo.label);
            mTxtDate.setText(mTodo.date);
        }
    }

    public void onSave(View view) {
        String label = mTxtLabel.getText().toString();
        String date = mTxtDate.getText().toString();

        if (mBundle != null) {
            //-- Updating a todo item
            mTodo.label = label;
            mTodo.date = date;
            mTodo.save();
        } else {
            //-- Adding a todo item
            mTodo = new Todo(label, date);
            mTodo.save();
        }

        //-- TODO: Perhaps a better way to communicate data changed to MainActivity
        Intent i = new Intent(EditItemsActivity.this, MainActivity.class);
        startActivityForResult(i, 99);

        finish();
    }
}
