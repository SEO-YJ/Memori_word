package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ListPage extends AppCompatActivity implements View.OnClickListener {

    Handler mHandler = new Handler();

    ListView listView;
    ReadAndWrite DBHelper;

    ArrayList<String> nameList;
    ArrayList<String> meanList;
    ArrayList<String> spellingList;
    String userID;

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        listView = findViewById(R.id.list_view);

        nameList =  (ArrayList<String>) getIntent().getSerializableExtra("nameList");
        meanList =  (ArrayList<String>) getIntent().getSerializableExtra("meanList");
        spellingList =  (ArrayList<String>) getIntent().getSerializableExtra("spellingList");
        userID = (String)getIntent().getSerializableExtra("UID");


        Log.d("mean", meanList.size() + "");
        //DBHelper = new ReadAndWrite(userID, nameList, meanList, spellingList);

        //DBHelper.getFirstListListener(nameList.get(0));

        Log.d("name list get(0)", nameList.get(0) + "");
        Log.d("userid", userID);
        Log.d("mean", meanList.size() + "");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("mean", meanList.size() + "");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meanList);
        listView.setAdapter(arrayAdapter);

        Intent intent = new Intent(this, QuizPage.class);
        intent.putExtra("nameList", this.nameList);

        intent.putExtra("meanList", this.meanList);
        intent.putExtra("spellingList", this.spellingList);
        //startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Log.d("mean", meanList.size() + "");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meanList);
        listView.setAdapter(arrayAdapter);
        //startActivity(new Intent(this, QuizPage.class));
    }

}