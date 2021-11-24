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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class List extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;

    TextView showText;

    ReadAndWrite DBHelper;

    ArrayList<String> nameList;
    ArrayList<String> meanList;
    ArrayList<String> spellingList;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        showText = findViewById(R.id.show_list_btn);

        nameList =  (ArrayList<String>) getIntent().getSerializableExtra("nameList");
        userID = (String)getIntent().getSerializableExtra("UID");
        meanList =  (ArrayList<String>) getIntent().getSerializableExtra("meanList");
        spellingList =  (ArrayList<String>) getIntent().getSerializableExtra("spellingList");

        DBHelper = new ReadAndWrite(userID, nameList, meanList, spellingList);

        listView = findViewById(R.id.listview);

        ArrayList<Listitem> items = new ArrayList<>();
        for(int i =0; i < nameList.size(); i++){
            Log.d("list name get", nameList.get(i));
            items.add(new Listitem(nameList.get(i), ""));
        }

        ListitemAdapter ListAdapter = new ListitemAdapter(this, R.layout.item_listview, items,
                userID ,new ArrayList<>(), meanList, spellingList);

        listView.setAdapter(ListAdapter);

        //listView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(position+"", position+"");

        if(view == showText){
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
    }



    /*@Override
    public void onClick(View v) {
        Log.d("mean", meanList.size() + "");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meanList);
        listView.setAdapter(arrayAdapter);
        startActivity(new Intent(this, QuizPage.class));
    }*/


}