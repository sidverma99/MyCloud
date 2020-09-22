package com.example.mycloud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static int PICK_PDF_CODE=2342;
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private StorageReference mStorageReference;
    private String userId;
    private upload uploadData;
    private String fileName;
    private List<upload> uploadList=new ArrayList<>();
    private UploadAdapter uploadAdapter;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Log.d("action bar","action bar made");
        fab=(FloatingActionButton) findViewById(R.id.fab);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLinearLayoutManager=new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        uploadAdapter=new UploadAdapter(getApplicationContext(),uploadList);
        mRecyclerView.setAdapter(uploadAdapter);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("uploads").child(userId);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"check ur storage permission",Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                final MaterialAutoCompleteTextView input=new MaterialAutoCompleteTextView(MainActivity.this);
                input.setHint("File Name");
                input.setFloatingLabelTextSize(50);
                input.setFloatingLabelPadding(50);
                input.setIconLeft(R.drawable.add);
                input.setMaxCharacters(45);
                input.setShowClearButton(true);
                input.setPaddings(5,10,50,50);
                builder.setView(input);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().startsWith(" ")) {
                            Toast.makeText(MainActivity.this, "File name cannot start with space.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (input.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "File name cannot be empty.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        fileName=input.getText().toString();
                        Intent intent=new Intent();
                        intent.setType("application/pdf");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"select Picture"),PICK_PDF_CODE);
                    }
                });
                Dialog dialog=builder.create();
                dialog.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_PDF_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            if (data.getData()!=null){
                if (data.getData()!=null){
                    uploadFile(data.getData());
                    mDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            uploadList.clear();
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                upload newUpload=dataSnapshot.getValue(upload.class);
                                uploadList.add(newUpload);
                            }
                            Log.d("data size",Integer.toString(uploadList.size()));
                            uploadAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(),"some error",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"No file chosen",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void uploadFile(Uri data){
        mStorageReference= FirebaseStorage.getInstance().getReference().child(fileName+".pdf");
        mStorageReference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadData=new upload(fileName+".pdf",taskSnapshot.getUploadSessionUri().toString());
                mDatabaseReference.push().setValue(uploadData);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            }
        });
    }
}