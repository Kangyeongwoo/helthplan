package com.example.me.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> itemsname;
    private int itemLayout;


    public RecyclerViewAdapter(Context context , ArrayList<String> itemsname, int itemLayout ){

        this.context = context;
        this.itemsname = itemsname;
        this.itemLayout = itemLayout;


    }

    //메인의 오늘의 운동 (소운동 리스트)를 리사이클러 뷰로 보여줌
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("리사이클" , "뷰홀더 생성");
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, int position ) {
        holder.text.setText(itemsname.get(holder.getLayoutPosition()));
        Log.d("리사이클", "뷰홀더 바인더");

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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(context,Actionlist2.class);
                intent3.putExtra("name", itemsname.get(holder.getLayoutPosition()));
                context.startActivity(intent3);

            }
        });
/*
        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
            }
        });



  */  }

    @Override
    public int getItemCount() {
        return itemsname.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        Button deletebutton;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.todayaction);
            image = itemView.findViewById(R.id.mainactimage);

        }
    }
}
