package com.zybooks.milestone4;



import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllTasksActivity extends AppCompatActivity {

    private static final String TAG = "AllTasksActivity";
    List< ToDo > arrayList;
    ToDoListAdapter adapter;
    Integer lastKeyID = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);


        LoadDAta();



    }

    private void LoadDAta(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, ?> allSharedPreferences = preferences.getAll();
        arrayList = new ArrayList<ToDo>();

        for (Map.Entry<String, ?> entry: allSharedPreferences.entrySet()){
            ToDo toDo = new ToDo();
            toDo.id = Integer.parseInt(entry.getKey());
            toDo.name = entry.getValue().toString();

            arrayList.add(toDo);
            lastKeyID = toDo.id;
        }

        adapter = new ToDoListAdapter(this, (ArrayList<ToDo>) arrayList);

        ListView listView = (ListView) findViewById(R.id.lstView);
        listView.setAdapter(adapter);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //removeItemFromList(position);
                return true;
            }
        });




    }



}
