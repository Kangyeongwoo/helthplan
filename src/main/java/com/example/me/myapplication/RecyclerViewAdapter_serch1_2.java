package com.example.me.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter_serch1_2 extends RecyclerView.Adapter<RecyclerViewAdapter_serch1_2.ViewHolder> {

    private Context context;
    private ArrayList<String> items;
    private int itemLayout;
    private ArrayList<String> itemsset;

    public RecyclerViewAdapter_serch1_2(Context context , ArrayList<String> items ,ArrayList<String> itemsset, int itemLayout ){

        this.context = context;
        this.items = items;
        this.itemsset = itemsset;
        this.itemLayout=itemLayout;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("리사이클" , "뷰홀더 생성");
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return new ViewHolder(v);
    }

    //소운동 리스트와 소운동 세트 리스트를 통해서 리사이클러뷰를 만든다.
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter_serch1_2.ViewHolder holder, int position) {
        holder.actionname.setText(items.get(holder.getLayoutPosition()));
        Log.d("리사이클", "뷰홀더 바인더");
        holder.actionset.setText(itemsset.get(holder.getLayoutPosition()));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView actionname;
        TextView actionset;

        public ViewHolder(View itemView) {
            super(itemView);
            actionname = itemView.findViewById(R.id.actionnamenotice);
            actionset =itemView.findViewById(R.id.actionsetnotice);

        }
    }

}




