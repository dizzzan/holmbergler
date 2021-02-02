package com.zybooks.milestone4;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    List< ToDo > arrayList;
    ToDoListAdapter adapter;
    Integer lastKeyID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, ?> allSharedPreferences = preferences.getAll();
        arrayList = new ArrayList<ToDo>();

        for (Map.Entry<String, ?> entry : allSharedPreferences.entrySet()) {
            ToDo toDo = new ToDo();
            toDo.id = Integer.parseInt(entry.getKey());
            toDo.name = entry.getValue().toString();

            arrayList.add(toDo);
            lastKeyID = toDo.id;
        }

        adapter = new ToDoListAdapter(this, (ArrayList<ToDo>) arrayList);

        ListView listView = (ListView) findViewById(R.id.lstView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                removeItemFromList(position);
                return true;
            }
        });

        Button addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText txtName = (EditText) findViewById(R.id.txtName);
                String name = txtName.getText().toString();

                if (name.trim().length() > 0) {
                    lastKeyID++;

                    ToDo toDo = new ToDo();
                    toDo.id = lastKeyID;
                    toDo.name = name;

                    arrayList.add(toDo);
                    adapter.notifyDataSetChanged();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(toDo.id.toString(), toDo.name);
                    editor.apply();

                    txtName.setText("");
                }
            }
        });

        Button clearBtn = (Button) findViewById(R.id.btnClear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText txtName = (EditText) findViewById(R.id.txtName);
                txtName.setText("");
            }
        });

        // button listener goes here

        Button allView = (Button) findViewById(R.id.btnShowAllTasks);
        allView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(this, AllTasksActivity.class);
                intent.putExtra("array", (Serializable) arrayList);
                startActivity(intent);
            }
        });
        
        
    }

    protected void removeItemFromList(final int position)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToDo toDo = arrayList.get(position);

                arrayList.remove(position);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove(toDo.id.toString());
                editor.apply();
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }
}

class ToDo implements Serializable
{
    Integer id;
    String name;
}

class ToDoListAdapter extends ArrayAdapter<ToDo>
{
    public ToDoListAdapter(Context context, ArrayList<ToDo> toDoList) {
        super(context, 0, toDoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToDo toDo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        name.setText(toDo.name);

        return convertView;
    }
}
