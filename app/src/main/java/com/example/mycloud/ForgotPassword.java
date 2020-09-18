package com.example.mycloud;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends Fragment {

    private EditText forgotPswrdEmail;
    private Button sendLink;

    public ForgotPassword() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_forgot_password, container, false);
        forgotPswrdEmail=(EditText)view.findViewById(R.id.forgotPaswrd_email);
        sendLink=(Button)view.findViewById(R.id.sendLink_btn);
        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}