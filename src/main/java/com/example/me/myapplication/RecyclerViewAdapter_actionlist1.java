package com.example.me.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//선택할 수 있는 운동 리스트를 보여줌
public class RecyclerViewAdapter_actionlist1 extends RecyclerView.Adapter<RecyclerViewAdapter_actionlist1.ViewHolder>{

    private Context context;
    private ArrayList<String> itemsname;
    private ArrayList<String> itemspart;
    private int itemLayout;
    ArrayList<String> checkitemsname;
    ArrayList<String> plusitems;
    ArrayList<Uri> test;

    public RecyclerViewAdapter_actionlist1(Context context , ArrayList<String> itemsname, ArrayList<String> itemspart, ArrayList<String> plusitems, int itemLayout ){

        this.context = context;
        this.itemsname = itemsname;
        this.itemspart = itemspart;
        this.itemLayout = itemLayout;
        this.checkitemsname = new ArrayList<>();
        this.plusitems = plusitems;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter_actionlist1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("리사이클" , "뷰홀더 생성");
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return new RecyclerViewAdapter_actionlist1.ViewHolder(v);
    }

    //아이템이 선택 될 때마다 리스트에 추가하고 제거한다.
    //플러스 아이템이 있으면 미리 체크 되도록 한다.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position ) {
        holder.actionname1.setText(itemsname.get(holder.getLayoutPosition()));
        Log.d("리사이클", "뷰홀더 바인더");
        holder.actionpart1.setText(itemspart.get(holder.getLayoutPosition()));


        if(itemsname.get(holder.getLayoutPosition()).equals("사이드 레터럴 레이즈")){
            holder.image.setImageResource(R.drawable.sideleteral);

        }else if(itemsname.get(holder.getLayoutPosition()).equals("프론트 레터럴 레이즈")){
              holder.image.setImageResource(R.drawable.frontraise);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("벤트오버 레터럴 레이즈")){
              holder.image.setImageResource(R.drawable.rearraise);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("숄더프레스")){
            holder.image.setImageResource(R.drawable.shoulderpress);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("시티드 프론트 덤벨 레이즈")){
             holder.image.setImageResource(R.drawable.seatfront);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("시티드 벤트오버 레이즈")){
              holder.image.setImageResource(R.drawable.seatbentover);
        }

        else if(itemsname.get(holder.getLayoutPosition()).equals("스쿼트")){
             holder.image.setImageResource(R.drawable.squat);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("레그 익스텐션")){
             holder.image.setImageResource(R.drawable.legextention);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("레그 컬")){
             holder.image.setImageResource(R.drawable.legcurl);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("레그 프레스")){
             holder.image.setImageResource(R.drawable.legpress);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("런지")){
             holder.image.setImageResource(R.drawable.lunge);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("덤벨 런지")){
             holder.image.setImageResource(R.drawable.dumbbelllunge);
        }

        else if(itemsname.get(holder.getLayoutPosition()).equals("플랭크")){
             holder.image.setImageResource(R.drawable.plank);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("바이시클 크런치")){
             holder.image.setImageResource(R.drawable.bicyclecrunch);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("디클라인 크런치")){
             holder.image.setImageResource(R.drawable.declinecrunch);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("슈퍼맨 푸쉬업")){
             holder.image.setImageResource(R.drawable.superman);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("사이드 플랭크")){
             holder.image.setImageResource(R.drawable.sideplank);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("덤벨 사이드 밴드")){
             holder.image.setImageResource(R.drawable.dumbbellsidebend);
        }

        else if(itemsname.get(holder.getLayoutPosition()).equals("벤치 프레스")){
             holder.image.setImageResource(R.drawable.benchpress);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("딥스")){
             holder.image.setImageResource(R.drawable.dipth);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("인클라인 벤치 프레스")){
             holder.image.setImageResource(R.drawable.inclinebenchpress);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("디클라인 벤치 프레스")){
             holder.image.setImageResource(R.drawable.declinebenchpress);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("다이아몬드 푸쉬업")){
             holder.image.setImageResource(R.drawable.diamondpushup);
        }else if(itemsname.get(holder.getLayoutPosition()).equals("플라이")){
             holder.image.setImageResource(R.drawable.meachinefly);
        }

        holder.action1checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(holder.action1checkBox.isChecked()){
                    checkitemsname.add(itemsname.get(holder.getLayoutPosition()));

                }else{
                    checkitemsname.remove(itemsname.get(holder.getLayoutPosition()));
                }
            }
        });

        if(plusitems != null && plusitems.contains(itemsname.get(holder.getLayoutPosition()))){

            Log.d("textcheck2", String.valueOf(plusitems));

            holder.action1checkBox.performClick();



        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toactionlist2 = new Intent(context, Actionlist2.class);
                toactionlist2.putExtra("name",itemsname.get(holder.getLayoutPosition()));
                context.startActivity(toactionlist2);
            }
        });


    }





    @Override
    public int getItemCount() {
        return itemsname.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView actionname1;
        ImageView image;
        TextView actionpart1;
        CheckBox action1checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            actionname1 = itemView.findViewById(R.id.actionname1);
            actionpart1 = itemView.findViewById(R.id.actionpart1);
            action1checkBox = itemView.findViewById(R.id.action1checkBox);
            image=itemView.findViewById(R.id.act1imageview);
        }
    }
    public ArrayList<String> getCheckitemsname() {
        return checkitemsname;
    }

}
