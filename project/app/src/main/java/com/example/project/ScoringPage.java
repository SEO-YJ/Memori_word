package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScoringPage extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    Button button;
    int[] wrongcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring_page);

        textView1 = findViewById(R.id.textview1);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);
        button = findViewById(R.id.button);

        int correct = ((QuizPage)QuizPage.context_correct).correct;
        int wrong = ((QuizPage)QuizPage.context_wrong).wrong;
        int all = ((QuizPage)QuizPage.context_allQuestion).allQuestion;

        textView2.setText("맞은 갯수="+correct+", 틀린 갯수="+wrong);
        textView3.setText(correct+"/"+all);


        Intent intent = getIntent();
        wrongcheck = intent.getIntArrayExtra("wrongs");

    }
    public void onClick(View view){

        ((QuizPage)QuizPage.context_correct).correct = 0;
        ((QuizPage)QuizPage.context_wrong).wrong = 0;

        Intent intent = new Intent();
        intent.putExtra("wrongs2", wrongcheck);

        startActivity(new Intent("com.example.wordrecord.QuizPage"));
    }
}