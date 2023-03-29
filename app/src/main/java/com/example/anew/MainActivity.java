package com.example.anew;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText todoEditText;
    private Switch urgentSwitch;
    private Button addButton;
    private ListView todoListView;

    private List<TodoItem> todoList = new ArrayList<>();
    private TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoEditText = findViewById(R.id.edit_Text);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        addButton = findViewById(R.id.add_button);
        todoListView = findViewById(R.id.listView);

        // Create the adapter and attach it to the ListView
        todoListAdapter = new TodoListAdapter();
        todoListView.setAdapter(todoListAdapter);

        // Set a long click listener on the ListView
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(position);
                return true;
            }
        });

        // Set a click listener on the Add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodoItem();
            }
        });
    }

    private void addTodoItem() {
        String todoText = todoEditText.getText().toString().trim();
        boolean isUrgent = urgentSwitch.isChecked();
        if (!todoText.isEmpty()) {
            todoList.add(new TodoItem(todoText, isUrgent));
            todoListAdapter.notifyDataSetChanged();
            todoEditText.setText("");
        }
    }

    private void showDeleteConfirmationDialog(final int position) {
        String deleteDialogMessage = getResources().getString(R.string.delete_dialog_message, position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_dialog_title)
                .setMessage(deleteDialogMessage)
                .setPositiveButton(R.string.delete_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todoList.remove(position);
                        todoListAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.delete_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private class TodoListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return todoList.size();
        }

        @Override
        public Object getItem(int position) {
            return todoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.row_layout, parent,
                        false);
            }

            TodoItem todoItem = todoList.get(position);
            TextView todoTextView = convertView.findViewById(R.id.todo_textview);
            todoTextView.setText(todoItem.getTodoText());
            todoTextView.setTextSize(20);

            if (todoItem.isUrgent()) {
                convertView.setBackgroundColor(Color.RED);
                todoTextView.setTextColor(Color.WHITE);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                todoTextView.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }
}
