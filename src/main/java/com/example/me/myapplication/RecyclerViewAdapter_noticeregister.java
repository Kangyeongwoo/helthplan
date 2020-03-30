package com.example.me.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter_noticeregister extends RecyclerView.Adapter<RecyclerViewAdapter_noticeregister.ViewHolder>{


    Context context;
    ArrayList<String> items;
    private int itemLayout;
    ArrayList<String> subitems;
    ArrayList<String> itemsset;

    HashMap<String, ArrayList<String>> hashmapactionname;
    HashMap<String, ArrayList<String>> hashmapactionset;

    HashMap<String, ArrayList<String>> sendhashmapactionname;
    HashMap<String, ArrayList<String>> sendhashmapactionset;
    HashMap<String, String> sendhashmapactionposition;

    float height;

    ArrayList<String> sendtitle;
    HashMap<String,String> resttimelist;
    HashMap<String,String> sendresttimelist;

    RecyclerViewAdapter_noticeregister(Context context , ArrayList<String> items, HashMap<String, ArrayList<String>> hashmapactionname , HashMap<String, ArrayList<String>> hashmapactionset,HashMap<String,String> resttimelist, int itemLayout ){
        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
        this.hashmapactionname=hashmapactionname;
        this.hashmapactionset=hashmapactionset;
        this.sendhashmapactionname = new HashMap<String, ArrayList<String>>();
        this.sendhashmapactionset= new HashMap<String, ArrayList<String>>();
        this.sendhashmapactionposition= new HashMap<String, String>();
        this.sendtitle =new ArrayList<String>();
        this.resttimelist=resttimelist;
        this.sendresttimelist=new HashMap<String,String>();
    }



    @Override
    public RecyclerViewAdapter_noticeregister.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("리사이클" , "뷰홀더 생성");
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return new RecyclerViewAdapter_noticeregister.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter_noticeregister.ViewHolder holder, int position) {


        holder.text.setText(items.get(holder.getLayoutPosition()));

        subitems = hashmapactionname.get(items.get(holder.getLayoutPosition()));

        itemsset = hashmapactionset.get(items.get(holder.getLayoutPosition()));



        if(subitems.size()<6){
            holder.morbutton.setVisibility(View.INVISIBLE);
            holder.morbutton.setEnabled(false);
        }

        RecyclerViewAdapter_serch1_2 adapter = new RecyclerViewAdapter_serch1_2(context, subitems, itemsset, R.layout.row_noticeregisterrecycle2);

        Log.d("리사이클" , "어댑터 적용");

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        holder.recyclerView.setLayoutManager(layoutManager);

        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());

        holder.recyclerView.setAdapter(adapter);

        holder.morbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.morbutton.getText().equals("+ 더보기")) {
                    height = context.getResources().getDimension(R.dimen.image_height_one);
                    holder.controlconstraint.setMaxHeight(((int) height) * (hashmapactionname.get(items.get(holder.getLayoutPosition())).size()));
                    holder.morbutton.setText("접기");
                }else{
                    height = context.getResources().getDimension(R.dimen.image_height_max);
                    holder.controlconstraint.setMaxHeight((int) height);
                    holder.morbutton.setText("+ 더보기");
                }

            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(holder.checkBox.isChecked()){
                    sendtitle.add(items.get(holder.getLayoutPosition()));

                    sendhashmapactionname.put(items.get(holder.getLayoutPosition()),subitems);

                    sendhashmapactionset.put(items.get(holder.getLayoutPosition()),itemsset);

                    sendresttimelist.put(items.get(holder.getLayoutPosition()),resttimelist.get(items.get(holder.getLayoutPosition())));
                }else{
                    sendtitle.remove(items.get(holder.getLayoutPosition()));

                    sendhashmapactionname.remove(items.get(holder.getLayoutPosition()));

                    sendhashmapactionset.remove(items.get(holder.getLayoutPosition()));

                    sendresttimelist.remove(items.get(holder.getLayoutPosition()));
                }

            }
        });
    }

    @Override
    public int getItemCount() {

            return items.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        RecyclerView recyclerView;
        ConstraintLayout controlconstraint;
        CheckBox checkBox;
        Button morbutton;

        public ViewHolder(View itemView) {
            super(itemView);

            text=itemView.findViewById(R.id.textView30);

            recyclerView=itemView.findViewById(R.id.noticeregisterrecyclerView);
            controlconstraint=itemView.findViewById(R.id.noticeregisterconstraint);
            checkBox=itemView.findViewById(R.id.listcheckBox);
            morbutton=itemView.findViewById(R.id.noticemorebutton);
        }



    }

    public ArrayList<String> getsendtitle() {
        return sendtitle;
    }

    public HashMap<String, ArrayList<String>> getsendhashmapactionname() {
        return sendhashmapactionname;
    }

    public HashMap<String, ArrayList<String>> getsendhashmapactionset() {
        return sendhashmapactionset;
    }
    public HashMap<String, String> getsendresttimelist() {
        return sendresttimelist;
    }

}
