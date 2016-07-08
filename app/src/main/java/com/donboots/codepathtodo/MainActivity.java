package com.donboots.codepathtodo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
    ListView lvItems;
    EditText txtLabel;

    TodoAdapter todoAdapter;

    //-- PreferenceManager to get/set FIRST_RUN bool
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoAdapter = new TodoAdapter(this);

        lvItems = (ListView) findViewById(R.id.lvItems);
        txtLabel = (EditText) findViewById(R.id.txtLabel);

        readItems();

        lvItems.setAdapter(todoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoAdapter.getItem(position);

                todo.delete();
                todoAdapter.notifyDataSetChanged();

                readItems();
                return true;
            };
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoAdapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putLong("todoId", todo.getId());

                Intent i = new Intent(MainActivity.this, EditItemsActivity.class);
                i.putExtras(bundle);

                startActivity(i);
            };
        });
    }

    private void readItems() {
        todoAdapter.notifyDataSetChanged();
        todoAdapter.setData(Todo.listAll(Todo.class));
    }

    public void onAddItem(View view) {
        String label = txtLabel.getText().toString();

        Todo todo = new Todo(label);
        todo.save();

        readItems();

        txtLabel.setText("");
    }
}
