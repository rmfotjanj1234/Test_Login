package com.example.test_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText idET, pwET, pwCheckET;
    Button signupBtn, loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        idET = findViewById(R.id.idET);
        pwET = findViewById(R.id.pwET);
        pwCheckET = findViewById(R.id.pwCheckET);
        signupBtn = findViewById(R.id.signupBtn);
        loginBtn = findViewById(R.id.loginBtn);

        signupBtn.setOnClickListener(onClickListener);
        loginBtn.setOnClickListener(onClickListener);

        mAuth = FirebaseAuth.getInstance();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signupBtn:
                    String id, pw, pwCheck;

                    id = idET.getText().toString();
                    pw = pwET.getText().toString();
                    pwCheck = pwCheckET.getText().toString();

                    if(id.length() > 0 && pw.length() >0 && pwCheck.length() > 0){
                        if(pw.equals(pwCheck) == true){
                            mAuth.createUserWithEmailAndPassword(id, pw)
                                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                showToast(SignUpActivity.this, "회원가입에 성공하였습니다.");
                                                myStartActivity(LoginActivity.class);
                                            } else {
                                                if(task.getException() != null){
                                                    showToast(SignUpActivity.this, task.getException().toString());
                                                }
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.loginBtn:
                    myStartActivity(LoginActivity.class);
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
