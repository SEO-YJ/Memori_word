package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginForm extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        // 서버의 클라이언트 ID를 requestIdToken메소드에 전달해야 한다.
// <string name="default_web_client_id" translatable="false">773840236497-q3tpj1nlqgh0ekm7u4m8avdurhk7q2pn.apps.googleusercontent.com</string>
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("773840236497-q3tpj1nlqgh0ekm7u4m8avdurhk7q2pn.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // FirebaseAuth 객체의 공유 인스턴스를 가져온다.
        mAuth = FirebaseAuth.getInstance();


        SignInButton button = findViewById(R.id.login_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    //
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // signInIntent로 보낸게 맞는지.. 옆에 requestCode를 같이 보내 같은 requestcode를 받으면 동일 인텐트
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign이 성공했다면, GoogleSignInAccount 객체에서 ID토큰을 가져와서
                // Firebase 사용자 인증 정보로 교환하고 해당 정보를 사용해 Firebase에 인증
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    // SignInIntent를 구글 클라이언트 측에 보낸다. startActivityForResult메서드로 인텐트를 보내서
    // 보낸 인텐트에 대해 결과를 onActivityResult로 받을 준비를 한다.
    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    // 생명주기 함수로 onCreate() 이후 실행 된다.
    // mAuth.getCurrentUser()메서드로 현재 사용자의 정보를 가져온다.
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    // 사용자가 정상적으로 로그인 하면 GoogleSignInAccout 객체에서 ID 토큰을 가져와서 Firebase 사용자 인증 정보로
    // 교환하고 해당 정보를 사용해 Firebase에 인증한다.
    private void firebaseAuthWithGoogle(String idToken) {
        // signInWithCredential 에 대한 호출이 성공하면
        // getCurrentUser 메소드로 사용자의 계정 데이터를 가져올 수 있다.
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign이 성공하면 , 현재 유저 정보를 보낸다.
                            // FirebaseUser 객체에서 사용자의 기본 프로필 정보를 가져올 수 있더,
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // sign이 실패 했을 때는 null을 보낸다,,,
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(Object o) {
    }


}