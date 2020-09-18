package com.example.mycloud;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class SignUpActivity extends Fragment {
    private ProgressBar progressBar;
    private EditText signUpName,signUpEmail,signUpPassword,signUpConfirmPassword;
    private Button createBtn;
    private FrameLayout signUpFrame;
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
            }
        });

        return view;
    }
}