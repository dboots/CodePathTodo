package com.donboots.codepathtodo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EditFragment extends DialogFragment {
    private EditText mLabel;
    private EditText mDate;
    private Button btnSave;
    private Button btnCancel;
    private long mTodoId;
    private Todo mTodo;

    public interface EditNameDialogListener {
        void onFinishEditDialog(Todo todo);
    }

    public EditFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_items, container);

        mLabel = (EditText) view.findViewById(R.id.etLabel);
        mDate = (EditText) view.findViewById(R.id.etDate);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        if (getArguments() != null) {
            mTodoId = getArguments().getLong("todoId");
            mTodo = Todo.findById(Todo.class, mTodoId);

            mLabel.setText(mTodo.label);
            mDate.setText(mTodo.date);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = mLabel.getText().toString();
                String date = mDate.getText().toString();

                if (getArguments() != null) {
                    //-- Updating a todo item
                    mTodo.label = label;
                    mTodo.date = date;
                    mTodo.save();
                } else {
                    //-- Adding a todo item
                    mTodo = new Todo(label, date);
                    mTodo.save();
                }

                EditNameDialogListener listener = (EditNameDialogListener) getActivity();
                listener.onFinishEditDialog(mTodo);
                dismiss();
            }
        });

        return view;
    }
}
