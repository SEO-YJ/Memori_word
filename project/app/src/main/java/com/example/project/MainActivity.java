package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseUser user;
    String userID;
    String listName;
    ReadAndWrite DBHelper;

    EditText meanView;
    EditText spellingView;
    Button saveBtn;
    Button loadBtn;
    Button deleteBtn;
    ListView listView;

    ArrayList<String> nameList;
    ArrayList<String> meanList;
    ArrayList<String> spellingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meanView = findViewById(R.id.mean_view);
        spellingView = findViewById(R.id.spelling_view);
        saveBtn = findViewById(R.id.save_btn);
        loadBtn = findViewById(R.id.load_btn);
        deleteBtn = findViewById(R.id.delete_btn);
        listView = findViewById(R.id.list_view);

        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        nameList = new ArrayList<>();
        meanList = new ArrayList<>();
        spellingList = new ArrayList<>();

        startActivity(new Intent(this, LoginForm.class));

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        Log.d("user", "userID");
        DBHelper = new ReadAndWrite(userID, nameList, meanList, spellingList);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //DBHelper = new ReadAndWrite(userID, nameList, meanList, spellingList);
        DBHelper.getFirstListListener();

        if(nameList.size() != 0)
        {
            listName = nameList.get(0);
            Log.d("list name : ", listName);
        }
        else
            listName = "list name";

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View view) {
        String mean = meanView.getText().toString();
        String spelling = spellingView.getText().toString();

        if(view == saveBtn){
            if(!mean.equals("") && !spelling.equals("")){
                DBHelper.writeNewWord(listName, mean, spelling);
            }
        }
        else if(view == loadBtn){
            //DBHelper.writeNewList("new!");
            DBHelper.writeNewWord("new!", mean, spelling);
            //DBHelper.updateWord(listName, mean, spelling);
        }
        else if(view == deleteBtn){
            if(!mean.equals("") && !spelling.equals("")){
                DBHelper.deleteWord(listName, mean, spelling);
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meanList);
        listView.setAdapter(arrayAdapter);
    }
}