package com.donboots.codepathtodo;

import android.content.ClipData;
import android.content.Context;
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

        Todo todo = getItem(position);

        holder.label.setText(todo.label);
        holder.position.setText(todo.getId().toString());

        return convertView;
    }

    private class ViewHolder {
        TextView label;
        TextView position;

        public ViewHolder(View view) {
            label = (TextView) view.findViewById(R.id.txtLabel);
            position = (TextView) view.findViewById(R.id.txtPosition);
        }

    }
}
