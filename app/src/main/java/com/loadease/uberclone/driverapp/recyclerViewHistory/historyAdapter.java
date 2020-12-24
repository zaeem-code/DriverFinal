package com.loadease.uberclone.driverapp.recyclerViewHistory;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loadease.uberclone.driverapp.Model.History;
import com.loadease.uberclone.driverapp.R;

import java.util.ArrayList;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder>{
    Context context;
    ArrayList<History> items;
    ClickListener listener;
    ViewHolder viewHolder;

    public historyAdapter(Context context, ArrayList<History> items, ClickListener listener ){
        this.context=context;
        this.items=items;
        this.listener=listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.template_history,viewGroup,false);
        viewHolder=new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.toadd.setText("From: "+items.get(i).getFromAddress());
        viewHolder.fromadd.setText("TP: "+items.get(i).getDestinationString());
        viewHolder.tvdate.setText(items.get(i).getDate());
        Log.v("hassan","---> :"+items.get(i).getDate());
        viewHolder.tvfare.setText(items.get(i).getFare());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView toadd, fromadd,tvdate,tvfare;
        ClickListener listener;
        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            toadd=itemView.findViewById(R.id.toadd);
            fromadd=itemView.findViewById(R.id.fromadd);

            tvdate=itemView.findViewById(R.id.tvdate);
            tvfare=itemView.findViewById(R.id.tvfare);
            this.listener=listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.listener.onClick(view, getAdapterPosition());
        }
    }
}
