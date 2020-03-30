package com.example.me.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;



public class RecyclerViewAdapter_actionhistory extends RecyclerView.Adapter<RecyclerViewAdapter_actionhistory.ViewHolder> {
    private Context context;
    ArrayList<String> daylist;
    private int itemLayout;
    String thisyear;
    String thismonth;
    String[] actionhistoryarray;
    String todayyear;
    String todaymonth;
    String todayday;

    public RecyclerViewAdapter_actionhistory(Context context , ArrayList<String> daylist,String thisyear, String thismonth, String[] actionhistoryarray,String[] todayarray, int itemLayout ) {
        this.context = context;
        this.daylist =daylist;
        this.itemLayout = itemLayout;
        this.thisyear =thisyear;
        this.thismonth =thismonth;
        this.actionhistoryarray =actionhistoryarray;
        this.todayyear=todayarray[0];
        this.todaymonth=todayarray[1];
        this.todayday=todayarray[2];

    }

    @NonNull
    @Override

    public RecyclerViewAdapter_actionhistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return new RecyclerViewAdapter_actionhistory.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {





        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
        int devicewidth = displaymetrics.widthPixels / 7;

        holder.itemView.getLayoutParams().width = devicewidth;

        holder.date.setText(daylist.get(holder.getLayoutPosition()));


        for(int i=0; i<actionhistoryarray.length; i++) {

            String[] actionhistoryarraydetail = actionhistoryarray[i].split("!#!");

            if(actionhistoryarraydetail.length!=1){
            String[] datedetail = actionhistoryarraydetail[1].split("-");

            if(datedetail[2].equals("01")){
                datedetail[2]="1";
            }else if(datedetail[2].equals("02")){
                datedetail[2]="2";
            }else if(datedetail[2].equals("03")){
                datedetail[2]="3";
            }else if(datedetail[2].equals("04")){
                datedetail[2]="4";
            }else if(datedetail[2].equals("05")){
                datedetail[2]="5";
            }else if(datedetail[2].equals("06")){
                datedetail[2]="6";
            }else if(datedetail[2].equals("07")){
                datedetail[2]="7";
            }else if(datedetail[2].equals("08")){
                datedetail[2]="8";
            }else if(datedetail[2].equals("09")){
                datedetail[2]="9";
            }


                Log.d("serchsave3",datedetail[0]);
                Log.d("serchsave3",datedetail[1]);
                Log.d("serchsave3",datedetail[2]);
                Log.d("serchsave3",thisyear);
                Log.d("serchsave3",thismonth);
                Log.d("serchsave3",daylist.get(holder.getLayoutPosition()));
             //   String[] datedetail = actionhistoryarraydetail[1].split("-");
                if (thisyear.equals(datedetail[0]) && thismonth.equals(datedetail[1]) && daylist.get(holder.getLayoutPosition()).equals(datedetail[2])) {

                    holder.actiontext.setText(actionhistoryarraydetail[0]);

                    if (actionhistoryarraydetail[2].equals("완료")) {
                        holder.checkimage.setImageResource(R.drawable.greenone);
                    } else if (actionhistoryarraydetail[2].equals("미완료")) {
                        holder.checkimage.setImageResource(R.drawable.yellowone);
                    }

                    if(!actionhistoryarraydetail[3].equals("~")) {

                        holder.calendarhight.setText(actionhistoryarraydetail[3] + "cm");
                        holder.calendarhight.setVisibility(View.VISIBLE);
                        holder.heightname.setVisibility(View.VISIBLE);

                    }
                    if(!actionhistoryarraydetail[4].equals("~")) {
                        holder.calendarweight.setText(actionhistoryarraydetail[4]+"kg");
                        holder.calendarweight.setVisibility(View.VISIBLE);
                        holder.weightname.setVisibility(View.VISIBLE);

                    }




                }



            }
            if(thisyear.equals(todayyear) && thismonth.equals(todaymonth) && daylist.get(holder.getLayoutPosition()).equals(todayday)){
                Log.d("todaytime",todayyear);
                Log.d("todaytime",todaymonth);
                Log.d("todaytime",todayday);

                holder.date.setTextColor(Color.rgb(214,117,148));
                holder.date.setTypeface(Typeface.DEFAULT_BOLD);
            }
        }



    }


    @Override
    public int getItemCount() {
        return daylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;

        TextView actiontext;

        ImageView checkimage;

        TextView calendarhight;

        TextView calendarweight;

        TextView weightname;

        TextView heightname;

        public ViewHolder(View itemView) {
            super(itemView);

            date= itemView.findViewById(R.id.date);

            actiontext=itemView.findViewById(R.id.actiontext);

            checkimage=itemView.findViewById(R.id.checkimage);

            calendarhight = itemView.findViewById(R.id.calendarheight);

            calendarweight = itemView.findViewById(R.id.calendarweight);
            heightname =itemView.findViewById(R.id.heightname);
            weightname = itemView.findViewById(R.id.weightname);

        }

    }

}
