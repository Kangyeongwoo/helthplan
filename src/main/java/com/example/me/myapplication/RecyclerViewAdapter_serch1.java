package com.example.me.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RecyclerViewAdapter_serch1 extends RecyclerView.Adapter<RecyclerViewAdapter_serch1.ViewHolder> {

    private Context context;
     ArrayList<String> items;
    private int itemLayout;
     ArrayList<String> subitems;
     ArrayList<String> itemsset;

    HashMap<String, ArrayList<String>> hashmapactionname;
    HashMap<String, ArrayList<String>> hashmapactionset;
    HashMap<String,String> resttimelist;

    HashMap<String, ArrayList<String>> semdhashmapactionname;
    HashMap<String, ArrayList<String>> sendhashmapactionset;
    HashMap<String, String> sendhashmapactionposition;

    float height;

    public RecyclerViewAdapter_serch1(Context context , ArrayList<String> items, HashMap<String, ArrayList<String>> hashmapactionname , HashMap<String, ArrayList<String>> hashmapactionset,HashMap<String,String> resttimelist, int itemLayout ){

        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
        this.hashmapactionname=hashmapactionname;
        this.hashmapactionset=hashmapactionset;
        this.semdhashmapactionname = new HashMap<String, ArrayList<String>>();
        this.sendhashmapactionset= new HashMap<String, ArrayList<String>>();
        this.sendhashmapactionposition= new HashMap<String, String>();
        this.resttimelist=resttimelist;
    }

    //운동 분류 리스트와 소운동 리스트를 받는다
    //소운동 리스트로 또다시 리사이클러 뷰를 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("리사이클" , "뷰홀더 생성");
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position ) {
        holder.text.setText(items.get(holder.getLayoutPosition()));

        subitems = hashmapactionname.get(items.get(holder.getLayoutPosition()));

        itemsset = hashmapactionset.get(items.get(holder.getLayoutPosition()));

        semdhashmapactionname.put(items.get(holder.getLayoutPosition()),subitems);

        sendhashmapactionset.put(items.get(holder.getLayoutPosition()),itemsset);

      //  sendhashmapactionposition.put(items.get(holder.getLayoutPosition()),String.valueOf(holder.getLayoutPosition()));

        if(subitems.size()<6){
            holder.morebutton.setVisibility(View.INVISIBLE);
            holder.morebutton.setEnabled(false);
        }


        Log.d("리사이클", "뷰홀더 바인더");

        RecyclerViewAdapter_serch1_2 adapter = new RecyclerViewAdapter_serch1_2(context, subitems, itemsset, R.layout.row_serch1_2);

        Log.d("리사이클" , "어댑터 적용");

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        holder.recyclerView.setLayoutManager(layoutManager);

        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());

        holder.recyclerView.setAdapter(adapter);



        holder.morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.morebutton.getText().equals("+ 더보기")) {
                    height = context.getResources().getDimension(R.dimen.image_height_one);
                    holder.controlconstraint.setMaxHeight(((int) height) * (semdhashmapactionname.get(items.get(holder.getLayoutPosition())).size()));
                    holder.morebutton.setText("접기");
                }else{
                    height = context.getResources().getDimension(R.dimen.image_height_max);
                    holder.controlconstraint.setMaxHeight((int) height);
                    holder.morebutton.setText("+ 더보기");
                }

            }
        });



        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("운동 목록 삭제");
                builder.setMessage("정말 목록을 삭제하시겠습니까?");
                builder.setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                semdhashmapactionname.remove(items.get(holder.getLayoutPosition()));
                                sendhashmapactionset.remove(items.get(holder.getLayoutPosition()));
                                items.remove(holder.getLayoutPosition());
                                notifyItemRemoved(holder.getLayoutPosition());
                            }
                        });
                builder.setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

        holder.modefybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(context, Actionserch3.class);

                intent3.putExtra("item", items.get(holder.getLayoutPosition()));
                intent3.putExtra("subitems", semdhashmapactionname.get(items.get(holder.getLayoutPosition())));
                intent3.putExtra("itemsset", sendhashmapactionset.get(items.get(holder.getLayoutPosition())));
                intent3.putExtra("resttime", resttimelist.get(items.get(holder.getLayoutPosition())));
                context.startActivity(intent3);

            }
        });






    }

    @Override
    public int getItemCount() {
        return items.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageButton deletebutton;
        RecyclerView recyclerView;
        Button morebutton;
        ImageButton modefybutton;
        ConstraintLayout controlconstraint;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.listnametext);
            text.bringToFront();
            recyclerView = itemView.findViewById(R.id.actionlistrecycle);
            deletebutton =itemView.findViewById(R.id.delete);
            morebutton =itemView.findViewById(R.id.morebutton);
            controlconstraint=itemView.findViewById(R.id.controlconstraint);
            modefybutton=itemView.findViewById(R.id.modefybutton);
        }
    }

    public HashMap<String, ArrayList<String>> getsemdhashmapactionname() {
        return semdhashmapactionname;
    }
    public HashMap<String, ArrayList<String>> getsendhashmapactionset() {
        return sendhashmapactionset;
    }
  //  public HashMap<String, String> getsendhashmapactionposition() {
//        return sendhashmapactionposition;
 //   }
    public ArrayList<String>  getsenditems() {
        return  items;
    }
}


