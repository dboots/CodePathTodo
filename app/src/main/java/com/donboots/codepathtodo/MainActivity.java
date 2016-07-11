package com.donboots.codepathtodo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends FragmentActivity implements EditFragment.EditNameDialogListener {
    ListView lvItems;
    TodoAdapter todoAdapter;
    FragmentManager fm;
    EditFragment editDialog;
    SwipeDetector swipeDetector;

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
        swipeDetector = new SwipeDetector();

        readItems();

        lvItems.setAdapter(todoAdapter);
        lvItems.setOnTouchListener(swipeDetector);

        lvItems.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.d("Swiper", "position: " + position);
                if (swipeDetector.swipeDetected()) {
                    String action = swipeDetector.getAction().toString();
                    Log.d("Swiper", action);
                    switch(action) {
                        case "BT":
                            Log.d("Swiper", "Swipe Up");
                            break;
                        case "TB":
                            Log.d("Swiper", "Swipe Down");
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoAdapter.getItem(position);
                todo.delete();
                readItems();
                return true;
            };
        });

        /*
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
        */
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
