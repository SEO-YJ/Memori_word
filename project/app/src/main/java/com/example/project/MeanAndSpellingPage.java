package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MeanAndSpellingPage extends AppCompatActivity {

    TextView title;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean_and_spelling_page);

        title = findViewById(R.id.title);
        listView = findViewById(R.id.list_view);



    }
}