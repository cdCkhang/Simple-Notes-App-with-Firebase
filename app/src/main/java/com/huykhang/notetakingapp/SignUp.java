package com.huykhang.notetakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUser;




public class SignUp extends AppCompatActivity
{
    private EditText msSignUpEmail,mSignUpPassword,mSignUpRePassword;
    private RelativeLayout mSignUp;
    private TextView mGoToLogin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        msSignUpEmail = findViewById(R.id.SignUpInput_Email);
        mSignUpPassword = findViewById(R.id.SignUpInput_Password);
        mSignUpRePassword = findViewById(R.id.SignUpInput_RePassword);
        mSignUp = findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();

        mGoToLogin = findViewById(R.id.goToLogin);
        mGoToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String mail=msSignUpEmail.getText().toString().trim();
                String password=mSignUpPassword.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"All Fields are Required",Toast.LENGTH_SHORT).show();
                }
                else if(!checkPasswordsMatch())
                {
                    Toast.makeText(getApplicationContext(),"Password is not match please check again",Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<7) {
                    Toast.makeText(getApplicationContext(),"Password Should Greater than 7 Digits",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Failed To Register",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private boolean checkPasswordsMatch() {
        String password = mSignUpPassword.getText().toString();
        String confirmPassword = mSignUpRePassword.getText().toString();
        return password.equals(confirmPassword);
    }
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Verification Email is sent, " +
                                    "please verify!",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(SignUp.this,MainActivity.class));
                }
            });
        }

        else
        {
            Toast.makeText(getApplicationContext(),"Failed To Send Verification Email",Toast.LENGTH_SHORT).show();
        }
    }

}
