package com.example.mycloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends Fragment {
    private ProgressBar progressBar;
    private EditText signUpName,signUpEmail,signUpPassword,signUpConfirmPassword;
    private Button createBtn;
    private FrameLayout signUpFrame;
    private FirebaseAuth auth;
    private Toolbar toolbar;

    public SignUpActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sign_up, container, false);
        auth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        signUpEmail=(EditText)view.findViewById(R.id.signUp_email);
        signUpPassword=(EditText)view.findViewById(R.id.signUp_password);
        signUpConfirmPassword=(EditText)view.findViewById(R.id.signUp_confirmPassword);
        signUpName=(EditText)view.findViewById(R.id.signUp_name);
        createBtn=(Button)view.findViewById(R.id.signUp_btn);
        signUpFrame=(FrameLayout)view.findViewById(R.id.sign_up_fragment);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKey();
                progressBar.setVisibility(View.VISIBLE);
                String name,email,password,confirmPassword;
                name=signUpName.getText().toString().trim();
                email=signUpEmail.getText().toString().trim();
                password=signUpPassword.getText().toString().trim();
                confirmPassword=signUpConfirmPassword.getText().toString().trim();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean b=password.equals(confirmPassword);
                if(b==false){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Both password do not match",Toast.LENGTH_SHORT).show();
                }
                if(password.length()<8){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Password length should be 8",Toast.LENGTH_SHORT).show();
                    return;
                }
                int u=0,l=0,s=0,no=0;
                for(int i=0;i<password.length();i++){
                    char ch=password.charAt(i);
                    if(Character.isUpperCase(ch)) u++;
                    if(Character.isLowerCase(ch)) l++;
                    if(Character.isDigit(ch)) no++;
                    if(!Character.isDigit(ch) && !Character.isLetter(ch) && !Character.isSpaceChar(ch)) s++;
                }
                if(u==0 || l==0 || no==0 || s==0){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Password is not strong",Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                            Toast.makeText(getContext(),"Authentication failed",Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getActivity(),MainActivity.class));
                            getActivity().finish();
                        }
                    }
                });
            }
        });
        return view;
    }
    private void hideKey(){
        InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view=(View)getView().getRootView();
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}