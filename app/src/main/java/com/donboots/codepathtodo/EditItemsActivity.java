package com.donboots.codepathtodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemsActivity extends Activity {
    String todoLabel;
    String todoId;
    EditText txtLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_items);

        txtLabel = (EditText) findViewById(R.id.txtLabel);

        Bundle bundle = getIntent().getExtras();
        todoLabel = bundle.getString("todoLabel");
        todoId = bundle.getString("todoId");

        txtLabel.setText(todoLabel);
    }

    public void onSave(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("todoLabel", txtLabel.getText().toString());
        bundle.putString("todoId", todoId);

        Intent i = new Intent(EditItemsActivity.this, MainActivity.class);
        i.putExtras(bundle);
        startActivityForResult(i, 99);
        finish();
    }
}
