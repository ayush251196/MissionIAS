package com.example.ayush.missionias;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private ArrayList<String> names;
    private Context homescreen;
    private OnItemClickListener mlistener;
   public interface OnItemClickListener{
       void onItemClick(int pos);
   }
   public void setOnClickListener(OnItemClickListener listener){
       mlistener=listener;


   }

    public CustomAdapter(ArrayList<String> names,Context homescreen){
        this.names=names;
        this.homescreen=homescreen;

    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_card_view,viewGroup,false);
        return new MyViewHolder(view,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder myViewHolder, int i) {

        final String name=names.get(i);
        myViewHolder.textView.setText(name);


    }

    @Override
    public int getItemCount() {

        return names.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public  TextView textView;
        public FrameLayout frameLayout;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textView=itemView.findViewById(R.id.txt_name);
            frameLayout=itemView.findViewById(R.id.fr1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}