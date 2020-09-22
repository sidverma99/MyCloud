package com.example.mycloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.ViewHolder> {
    private Context mContext;
    private List<upload> uploadList;
    public UploadAdapter(Context context,List<upload> uploadList){
        this.mContext=context;
        this.uploadList=uploadList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.upload_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        upload myUpload=uploadList.get(position);
        holder.fileName.setText(myUpload.getName());
    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fileName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName=(TextView)itemView.findViewById(R.id.fileName);
        }
    }
}
