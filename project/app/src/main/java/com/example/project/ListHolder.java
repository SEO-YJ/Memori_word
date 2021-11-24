package com.example.project;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListHolder {
    public TextView listNameText;
    public TextView listSizeText;
    public Button showListBtn;
    public Button quizPageBtn;

    public ListHolder(View root){
        listNameText = root.findViewById(R.id.listname);
        listSizeText = root.findViewById(R.id.listsize);
        showListBtn = root.findViewById(R.id.show_list_btn);
        quizPageBtn = root.findViewById(R.id.quiz_page_btn);
    }



}
