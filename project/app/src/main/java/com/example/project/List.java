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

        nameList =  (ArrayList<String>) getIntent().getSerializableExtra("nameList");
        userID = (String)getIntent().getSerializableExtra("UID");
        meanList = new ArrayList<>();
        spellingList = new ArrayList<>();

        DBHelper = new ReadAndWrite(userID, nameList, meanList, spellingList);

        listView = findViewById(R.id.listview);

        ArrayList<Listitem> items = new ArrayList<>();
        for(int i =0; i < nameList.size(); i++){
            Log.d("list name get", nameList.get(i));
            items.add(new Listitem(nameList.get(i), "1"));
        }

        ListitemAdapter ListAdapter = new ListitemAdapter(this, R.layout.item_listview, items,
                userID ,nameList, meanList, spellingList);

        listView.setAdapter(ListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setAdapter(arrayAdapter);
                Thread holderThread = new Thread("holderThread"){
                    @Override
                    public void run() {
                        super.run();

                        try {
                            ListHolder holder = (ListHolder) view.getTag();
                            String listName = holder.listNameText.getText().toString();
                            DBHelper.addWordEventListener(DBHelper.userDatabase.child(listName));
                            DBHelper.getFirstListListener(listName);

                            Thread.sleep(100);

                            Intent intent = new Intent(List.this, MeanAndSpellingPage.class);
                            intent.putExtra("list name", listName);
                            intent.putExtra("meanList", meanList);
                            intent.putExtra("spelling", spellingList);
                            intent.putExtra("UID", userID);

                            startActivity(intent);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                holderThread.start();



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