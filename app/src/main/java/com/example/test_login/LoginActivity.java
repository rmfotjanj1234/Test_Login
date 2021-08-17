package com.example.test_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText idET, pwET;
    Button loginBtn, signupBtn, pwResetBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idET = findViewById(R.id.idET);
        pwET = findViewById(R.id.pwET);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        pwResetBtn = findViewById(R.id.pwResetBtn);

        loginBtn.setOnClickListener(onClickListener);
        signupBtn.setOnClickListener(onClickListener);
        pwResetBtn.setOnClickListener(onClickListener);

        mAuth = FirebaseAuth.getInstance();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginBtn:
                    String id, pw;

                    id = idET.getText().toString();
                    pw = pwET.getText().toString();

                    mAuth.signInWithEmailAndPassword(id, pw)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        showToast(LoginActivity.this, "로그인 성공");
                                        myStartActivity(MainActivity.class);
                                    } else {
                                        showToast(LoginActivity.this, "로그인 실패");
                                    }
                                }
                            });
                    break;
                case R.id.signupBtn:
                    myStartActivity(SignUpActivity.class);
                    break;
                case R.id.pwResetBtn:
                    myStartActivity(PasswordResetActivity.class);
                    break;
            }
        }
    };

    public static void showToast(Activity activity, String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
