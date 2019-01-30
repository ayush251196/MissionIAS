package com.example.ayush.missionias;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private ArrayList<String> names;

    public CustomAdapter(ArrayList<String> names){
        this.names=names;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_card_view,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder myViewHolder, int i) {

        String name=names.get(i);
        myViewHolder.textView.setText(name);
    }

    @Override
    public int getItemCount() {

        return names.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public  TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.txt_name);

        }
    }

}