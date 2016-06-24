package com.donboots.codepathtodo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {
    final String FIRST_RUN = "firstRun";
    final String DATABASE = "Todo";
    final String TBL_TODO = "TodoItems";

    ListView lvItems;
    EditText txtLabel;

    SQLiteDatabase db;
    SimpleCursorAdapter cursorAdapter;

    //-- PreferenceManager to get/set FIRST_RUN bool
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        db = openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);


        //-- If it is the first time being run, create TBL_TODO (Id, Label) table
        if(!prefs.getBoolean(FIRST_RUN, false)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(FIRST_RUN, true);
            editor.commit();

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TBL_TODO + "(_id INTEGER PRIMARY KEY, Label VARCHAR);");
        }

        lvItems = (ListView) findViewById(R.id.lvItems);
        txtLabel = (EditText) findViewById(R.id.txtLabel);

        readItems();
        handleExtras();

        lvItems.setAdapter(cursorAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) cursorAdapter.getItem(position);
                String todoId = cursor.getString(cursor.getColumnIndex("_id"));
                db.delete(TBL_TODO, "_id=" + todoId, null);
                readItems();
                return true;
            };
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) cursorAdapter.getItem(position);
                String todoId = cursor.getString(cursor.getColumnIndex("_id"));
                String todoLabel = cursor.getString(cursor.getColumnIndex("Label"));

                Bundle bundle = new Bundle();
                bundle.putString("todoId", todoId);
                bundle.putString("todoLabel", todoLabel);

                Intent i = new Intent(MainActivity.this, EditItemsActivity.class);
                i.putExtras(bundle);

                startActivity(i);
            };
        });
    }

    private void handleExtras() {
        Bundle bundle = getIntent().getExtras();
        String todoLabel = bundle.getString("todoLabel");
        String todoId = bundle.getString("todoId");
        ContentValues values = new ContentValues();
        values.put("Label", todoLabel);

        db.update(TBL_TODO, values, "_id=" + todoId, null);
        readItems();
    }

    private void readItems() {
        Cursor rs = db.rawQuery("SELECT * FROM " + TBL_TODO, null);
        rs.moveToFirst();

        String[] columns = new String[] {
                "Label"
        };

        int[] to = new int[] {
                android.R.id.text1
        };

        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, rs, columns, to, 0);

        cursorAdapter.changeCursor(rs);
        lvItems.setAdapter(cursorAdapter);
    }

    public void onAddItem(View view) {
        String label = txtLabel.getText().toString();
        ContentValues values = new ContentValues();
        values.put("Label", label);

        db.insert(TBL_TODO, null, values);

        readItems();

        txtLabel.setText("");
    }
}
