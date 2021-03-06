package com.example.project;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static int check = 0;

    FirebaseUser user;
    String userID;
    String listName;
    ReadAndWrite DBHelper;

    Button quizBtn;

    EditText listname;
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

        listname= findViewById(R.id.listname);
        meanView = findViewById(R.id.mean_view);
        spellingView = findViewById(R.id.spelling_view);
        saveBtn = findViewById(R.id.save_btn);
        loadBtn = findViewById(R.id.load_btn);
        deleteBtn = findViewById(R.id.delete_btn);
        listView = findViewById(R.id.list_view);

        quizBtn = findViewById(R.id.quiz_btn);
        quizBtn.setOnClickListener(this);

        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        nameList = new ArrayList<>();
        meanList = new ArrayList<>();
        spellingList = new ArrayList<>();

    }


    // ?????? LoginForm??????????????? onCreate()?????? ???????????????...
    // ?????? ??????????????? oncreate -> onStart -> resumed -> paused -> stopped ????????? ?????? restart??? ???????????????
    // ?????? onStart()??? ????????????.
    // onCreate()?????? userID??? ?????? ????????? ??? ?????? ??????????????? ???????????? ?????? ???????????? ????????????.
    // ???????????? ????????? ????????? ????????? ??? ?????? onStart()??? ?????? ?????? ??? userID??? ?????? ?????? ????????? ???????????? ?????? ??????.
    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            userID = user.getUid();
            Log.d("user", "userID");
            DBHelper = new ReadAndWrite(userID, nameList, meanList, spellingList);


            Log.d("onStart", "..." + ++check);
            //DBHelper = new ReadAndWrite(userID, nameList, meanList, spellingList);
            DBHelper.getFirstListListener();

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);
            listView.setAdapter(arrayAdapter);

        }


    }


    // ??????????????? ?????? ????????? ??????????????? ???????????? ???????????? ????????? ????????? ??? ??????.
    // Thread.sleep()?????? ????????? ????????? ??????????????? ????????? ??? ?????? ????????? ??????
    // ????????? ???????????? ???????????? ?????? ?????? ??? ?????? ????????? ?????? ??? ??? ?????? ????????? ?????????.
    @Override
    public void onClick(View view) {
        String listName = listname.getText().toString();
        String mean = meanView.getText().toString();
        String spelling = spellingView.getText().toString();

        if(view == saveBtn){
            if(!listName.equals("") && !mean.equals("") && !spelling.equals("")){
                DBHelper.writeNewWord(listName, mean, spelling);
            }
        }
        else if(view == loadBtn){
            //DBHelper.writeNewList("new!");
            //DBHelper.writeNewWord("new!", mean, spelling);
            //DBHelper.updateWord(listName, mean, spelling);
        }
        else if(view == deleteBtn){
            if(!listName.equals("") && !mean.equals("") && !spelling.equals("")){
                DBHelper.deleteWord(listName, spelling);
            }
        }
        else if(view == quizBtn){
            // ????????? ????????? ??? ?????????...
            Thread meanThread = new Thread("mean Thread"){
                public void run(){
                    //DBHelper.getFirstListListener(nameList.get(0));
                    //DBHelper.addWordEventListener(DBHelper.userDatabase.child(nameList.get(0)));
                    Log.d("mean list", "" + spellingList.size());

                    try {
                        Thread.sleep(100);
                        Intent intent = new Intent(MainActivity.this, List.class);
                        intent.putExtra("nameList", nameList);
                        intent.putExtra("meanList", meanList);
                        intent.putExtra("spellingList", spellingList);
                        intent.putExtra("UID", userID);

                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            };
            meanThread.start();

        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meanList);
        listView.setAdapter(arrayAdapter);
    }

    // ?????? ?????????...
    public static void linkAndMerge(AuthCredential credential){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser prevUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth.getInstance().signOut();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = task.getResult().getUser();
                        Log.d("current user", currentUser.getUid());
                        Log.d("prev user", prevUser.getUid());

                        ReadAndWrite prevDB = new ReadAndWrite(prevUser.getUid());
                        ReadAndWrite currnetDB = new ReadAndWrite(currentUser.getUid());
                        currnetDB.getFirstListListener();
                        prevDB.getFirstListListener();

                        Log.d("prev DB", "" + prevDB.nameList.size());
                        Log.d("current DB", "" + currnetDB.nameList.size());

                        currnetDB.mergeDatabase(prevDB);
                        //prevUser.delete();
                    }
                });
    }



}