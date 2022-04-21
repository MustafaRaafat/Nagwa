package com.example.nagwa.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nagwa.R;
import com.example.nagwa.models.NagwaModel;

import java.util.ArrayList;
import java.util.List;

public class NagwaAdapter extends RecyclerView.Adapter<NagwaAdapter.Holder> {
    private List<NagwaModel> data=new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.nagwa_item,parent,false));
    }

    public void setData(List<NagwaModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.type.setText(data.get(position).getType());

    }

    @Override
    public int getItemCount() {
        if (data==null){
            return 0;
        }
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name,type;
        Button download;
        ProgressBar progressBar;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.item_name);
            type=itemView.findViewById(R.id.item_type);
            download=itemView.findViewById(R.id.item_download_but);
            progressBar=itemView.findViewById(R.id.item_progressbar);
        }
    }
}
