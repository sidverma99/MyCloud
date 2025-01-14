package com.example.mycloud;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private EditText signInEmail,signInPassword;
    private Button forgotPassword,signInBtn;
    private TextView signUp;
    private ProgressBar mProgressBar;
    private ScrollView mScrollView;
    private FirebaseAuth auth;
    private CheckBox rememberUser;
    public boolean rememberMe=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();
        setContentView(R.layout.sign_in);
        forgotPassword=(Button)findViewById(R.id.forgotPassword_btn);
        signInBtn=(Button)findViewById(R.id.signIn_btn);
        signUp=(TextView)findViewById(R.id.signUp_btn);
        signInEmail=(EditText)findViewById(R.id.signIn_email);
        signInPassword=(EditText)findViewById(R.id.signIn_password);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mScrollView=(ScrollView)findViewById(R.id.scrollView);
        rememberUser=(CheckBox) findViewById(R.id.remember_me);
        rememberUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rememberMe==false){
                    rememberMe=true;
                }
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeKeyboard();
                ForgotPassword forgotPassword=new ForgotPassword();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.first_activity,forgotPassword,"second transaction").addToBackStack(null).commit();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeKeyboard();
                SignUpActivity signUpFragment=new SignUpActivity();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.first_activity,signUpFragment,"first transaction").addToBackStack(null).commit();
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeKeyboard();
                mScrollView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                String email=signInEmail.getText().toString().trim();
                String password=signInPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    mProgressBar.setVisibility(View.GONE);
                    mScrollView.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Please enter email and password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    mProgressBar.setVisibility(View.GONE);
                    mScrollView.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Please enter your email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mProgressBar.setVisibility(View.GONE);
                    mScrollView.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Please enter your password",Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            mProgressBar.setVisibility(View.GONE);
                            mScrollView.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),getString(R.string.auth_failed),Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignInActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
    public void removeKeyboard(){
        View v=this.getCurrentFocus();
        if(v!=null){
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }
}
