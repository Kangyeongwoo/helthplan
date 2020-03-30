package com.example.me.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter_serch3 extends RecyclerView.Adapter<RecyclerViewAdapter_serch3.ViewHolder>{

    private Context context;
    private ArrayList<String> itemsname;
    private ArrayList<String> itemspart;
    private int itemLayout;
    ArrayList<String> checkitemsname;
    ArrayList<String> checkitemsset;
    ArrayList<String> alreadyitemset;

    public RecyclerViewAdapter_serch3(Context context , ArrayList<String> itemsname,ArrayList<String> alreadyitemset, int itemLayout ) {

        this.context = context;
        this.itemsname = itemsname;
      //  this.itemspart = itemspart;
        this.itemLayout = itemLayout;
        this.alreadyitemset =alreadyitemset;
        this.checkitemsname = new ArrayList<>();
        this.checkitemsset = new ArrayList<>();

    }

    @NonNull
    @Override
    public RecyclerViewAdapter_serch3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("리사이클" , "뷰홀더 생성");
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return new RecyclerViewAdapter_serch3.ViewHolder(v);
    }



    //리사이클러 뷰 안에 스피너가 있으며 미리 지정된 값이 있으면 세팅한 상태로 보여진다.
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter_serch3.ViewHolder holder, int position ) {

        String[] list1 ={"","1","2", "3","4","5","6","7","8","9","10"};

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




        holder.spinner1.bringToFront();

        ArrayAdapter spinneradapter = new ArrayAdapter(context ,android.R.layout.simple_spinner_item, list1);

        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        holder.spinner1.setAdapter(spinneradapter);

        if(alreadyitemset.size()!=0){
            try {
                holder.spinner1.setSelection(Integer.parseInt(alreadyitemset.get(holder.getLayoutPosition())));
            }catch(Exception e){

            }
        }

        //미리 공값 세팅
        checkitemsset.add( holder.getLayoutPosition() , holder.spinner1.getSelectedItem().toString());

        holder.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    checkitemsset.set( holder.getLayoutPosition() , holder.spinner1.getSelectedItem().toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        holder.actionname1.setText(itemsname.get(holder.getLayoutPosition()));
        Log.d("리사이클", "뷰홀더 바인더");



        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsname.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                checkitemsname.remove(holder.getLayoutPosition());
            }
        });

        checkitemsname.add(itemsname.get(holder.getLayoutPosition()));

    }



    @Override
    public int getItemCount() {
        return itemsname.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView actionname1;
        ImageView image;
        TextView actionpart1;
        ImageButton deletebutton;
        Spinner spinner1;


        public ViewHolder(View itemView) {
            super(itemView);
            actionname1 = itemView.findViewById(R.id.textView2);
           // actionpart1 = itemView.findViewById(R.id.textView3);
            deletebutton = itemView.findViewById(R.id.deletebutton2);
            spinner1 = itemView.findViewById(R.id.spinner1);
            image = itemView.findViewById(R.id.serch3actimage);



        }

    }

    public ArrayList<String> getCheckitemsname2() {
        return checkitemsname;
    }
    public ArrayList<String> getCheckitemsset2() {
        return checkitemsset;
    }
}
