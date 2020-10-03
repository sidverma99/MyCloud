package com.example.mycloud;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private List<upload> uploadList;
    private List<upload> uploadListFiltered;
    private DatabaseReference databaseReference;
    public UploadAdapter(Context context,List<upload> uploadList,DatabaseReference mDatabaseReference){
        this.mContext=context;
        this.uploadList=uploadList;
        this.uploadListFiltered=uploadList;
        this.databaseReference=mDatabaseReference;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.upload_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        upload myUpload=uploadListFiltered.get(position);
        holder.fileName.setText(myUpload.getName());
    }

    @Override
    public int getItemCount() {
        return uploadListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search=constraint.toString();
                if(search.isEmpty()){
                    uploadListFiltered=uploadList;
                } else {
                    List<upload> filteredList=new ArrayList<>();
                    for(upload row:uploadList){
                        if(row.getName().toLowerCase().contains(search.toLowerCase())){
                            filteredList.add(row);
                        }
                    }
                    uploadListFiltered=filteredList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=uploadListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                uploadListFiltered=(ArrayList<upload>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fileName;
        public RelativeLayout viewBackground, viewForeground;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName=(TextView)itemView.findViewById(R.id.fileName);
            viewForeground=(RelativeLayout)itemView.findViewById(R.id.view_foreground);
        }
    }
    public void removeData(int position){
        uploadListFiltered.remove(position);
        notifyDataSetChanged();
    }
}
