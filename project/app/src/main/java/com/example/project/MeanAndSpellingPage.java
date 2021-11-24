package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MeanAndSpellingPage extends AppCompatActivity {

    TextView title;
    ListView listView;

    String listName;
    ArrayList<String> meanList;
    ArrayList<String> spellingList;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean_and_spelling_page);

        title = findViewById(R.id.title);
        listView = findViewById(R.id.mean_spelling_list);

        listName =  (String) getIntent().getSerializableExtra("list name");
        meanList =  (ArrayList<String>) getIntent().getSerializableExtra("meanList");
        spellingList =  (ArrayList<String>) getIntent().getSerializableExtra("spellingList");
        userID = (String)getIntent().getSerializableExtra("UID");


        ArrayList<Listitem> items = new ArrayList<>();
        for(int i =0; i < meanList.size(); i++){
            Log.d(meanList.get(i),meanList.get(i));
            items.add(new Listitem(meanList.get(i), meanList.get(i)));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meanList);
        listView.setAdapter(arrayAdapter);
        //ListitemAdapter ListAdapter = new ListitemAdapter(this, R.layout.item_listview, items,
                //userID, new ArrayList<>(), meanList, spellingList);

        //listView.setAdapter(ListAdapter);

    }
}