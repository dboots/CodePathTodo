package com.donboots.codepathtodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemsActivity extends Activity {
    String todo;
    int todoPosition;
    EditText txtTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_items);

        txtTodo = (EditText) findViewById(R.id.txtTodo);

        todo = getIntent().getStringExtra("todo");
        todoPosition = getIntent().getIntExtra("todoPosition", -1);
        txtTodo.setText(todo);
    }

    public void onSave(View view) {
        Intent i = new Intent(EditItemsActivity.this, MainActivity.class);
        i.putExtra("todo", txtTodo.getText().toString());
        i.putExtra("todoPosition", todoPosition);
        startActivityForResult(i, 99);
        finish();
    }
}
