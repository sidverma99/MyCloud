package com.example.mycloud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private EditText signInEmail,signInPassword;
    private Button forgotPassword,signInBtn;
    private TextView signUp;
    private ProgressBar mProgressBar;
    private ScrollView mScrollView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        forgotPassword=(Button)findViewById(R.id.forgotPassword_btn);
        signInBtn=(Button)findViewById(R.id.signIn_btn);
        signUp=(TextView)findViewById(R.id.signUp_btn);
        signInEmail=(EditText)findViewById(R.id.signIn_email);
        signInPassword=(EditText)findViewById(R.id.signIn_password);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mScrollView=(ScrollView)findViewById(R.id.scrollView);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword forgotPassword=new ForgotPassword();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.first_activity,forgotPassword,"second transaction").addToBackStack(null).commit();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity signUpFragment=new SignUpActivity();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.first_activity,signUpFragment,"first transaction").addToBackStack(null).commit();
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
