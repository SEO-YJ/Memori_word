package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstPage extends AppCompatActivity{

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        ViewGroup linear = findViewById(R.id.linear_viewGroup);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUser != null){
                    Log.d("user", mUser.getUid());
                    startActivity(new Intent(FirstPage.this, MainActivity.class));
                }
                else{
                    Log.d("user", null);
                    startActivity(new Intent(FirstPage.this, LoginForm.class));
                }
            }
        });



    }


}