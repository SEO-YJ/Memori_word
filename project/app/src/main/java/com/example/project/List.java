package com.example.project;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    ListView listView;
    ListitemAdapter adapter;

    /*
        Handler mHandler = new Handler();
    */
    ReadAndWrite DBHelper;

    ArrayList<String> nameList;
    ArrayList<String> meanList;
    ArrayList<String> spellingList;
    String userID;

    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listview);


        nameList =  (ArrayList<String>) getIntent().getSerializableExtra("nameList");
        userID = (String)getIntent().getSerializableExtra("UID");
        meanList = new ArrayList<>();
        spellingList = new ArrayList<>();

        DBHelper = new ReadAndWrite(userID, nameList, meanList, spellingList);
        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setAdapter(arrayAdapter);



                startActivity(new Intent(List.this, QuizPage.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /*@Override
    public void onClick(View v) {
        Log.d("mean", meanList.size() + "");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meanList);
        listView.setAdapter(arrayAdapter);
        startActivity(new Intent(this, QuizPage.class));
    }*/


}