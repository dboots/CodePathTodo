package com.donboots.codepathtodo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<Todo> list;
    private Context mContext;

    public TodoAdapter(Context context) {
        myInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setData(List<Todo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Todo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = myInflater.inflate(R.layout.items_todo, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SwipeDetector swipeDetector = new SwipeDetector();
        convertView.setOnTouchListener(swipeDetector);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("convertView", "I am here");
            }
        });

        Todo todo = getItem(position);

        holder.label.setText(todo.label);
        holder.date.setText(todo.date);

        return convertView;
    }

    private class ViewHolder {
        TextView label;
        TextView date;

        public ViewHolder(View view) {
            label = (TextView) view.findViewById(R.id.etLabel);
            date = (TextView) view.findViewById(R.id.etDate);
        }

    }
}
