package com.donboots.codepathtodo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends FragmentActivity implements EditFragment.EditNameDialogListener {
    ListView lvItems;
    TodoAdapter todoAdapter;
    FragmentManager fm;
    EditFragment editDialog;

    @Override
    public void onFinishEditDialog(Todo todo) {
        readItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        todoAdapter = new TodoAdapter(this);
        lvItems = (ListView) findViewById(R.id.lvItems);

        readItems();

        lvItems.setAdapter(todoAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoAdapter.getItem(position);
                todo.delete();
                readItems();
                return true;
            };
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoAdapter.getItem(position);

                editDialog = new EditFragment();

                Bundle bundle = new Bundle();
                bundle.putLong("todoId", todo.getId());

                editDialog.setArguments(bundle);
                editDialog.show(fm, "activity_edit_items");
            };
        });
    }

    private void readItems() {
        todoAdapter.notifyDataSetChanged();
        todoAdapter.setData(Todo.listAll(Todo.class));
    }

    public void onAddItem(View view) {
        editDialog = new EditFragment();
        editDialog.show(fm, "activity_edit_items");
    }


}
