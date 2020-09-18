package com.example.mycloud;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends Fragment {

    private EditText forgotPswrdEmail;
    private Button sendLink;
    private FirebaseAuth auth;
    private ProgressBar mProgressBar;

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
        auth=FirebaseAuth.getInstance();
        mProgressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        sendLink=(Button)view.findViewById(R.id.sendLink_btn);
        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                hideKeyboard();
                String email=forgotPswrdEmail.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(),"Enter registered email",Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mProgressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"We have sent you instructions to reset your password!",Toast.LENGTH_SHORT).show();
                        } else {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(),"Failed to send reset email!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }
    private void hideKeyboard(){
        InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view=(View)getView().getRootView();
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}