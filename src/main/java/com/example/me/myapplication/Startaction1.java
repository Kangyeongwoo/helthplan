package com.example.me.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Startaction1 extends AppCompatActivity {

    String actioncategory;
    String actionname1;
    String actionname2;
    int actionset1=0;
    int actionset2=0;
    String set1;
    String set2;
    TextView setnumber;
    String actionset;
    Button startaction1button;
    String todayactioncategory1;
    ImageButton startactionbackbutton;
    TextView todayactionname;
    TextView sumset;
    //운동이름, 세트
    HashMap<String, String> hashmap;
    //운동이름
    ArrayList<String> keyName;
    String todaylist;
    SharedPreferences sf;
    String[] profillarray;
    String resttime;
    String height;
    String weight;
    int i;
    int j;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startaction1);

        image=findViewById(R.id.startactionimage);
        todayactioncategory1=getIntent().getStringExtra("todaycategory");
        keyName=(ArrayList<String>) getIntent().getSerializableExtra("todayactionshuffle");
        resttime=getIntent().getStringExtra("resttime");

        TextView todayactioncategory = (TextView)findViewById(R.id.todayactioncategory) ;
        todayactioncategory.setText(todayactioncategory1);
        sumset = (TextView)findViewById(R.id.sumset) ;


        todayactionname = (TextView)findViewById(R.id.todayactionname);

        startactionbackbutton =(ImageButton)findViewById(R.id.startactionbackbutton);

        setnumber=(TextView)findViewById(R.id.setnumber);
        startaction1button =(Button)findViewById(R.id.startaction1button);

        Collections.shuffle(keyName);

        i=0;
        j=0;


        startaction1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent torestintent = new Intent(Startaction1.this, Rest.class);
                torestintent.putExtra("resttime",resttime);
                startActivity(torestintent);
            }
        });





        hashmap =(HashMap<String, String>) getIntent().getSerializableExtra("todayactionhashmap");

        sf = getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getEmail(),MODE_PRIVATE);
        String text = sf.getString("프로필", "");
        profillarray = text.split("!&%!");

        height=profillarray[1];



        weight=profillarray[2];



        SharedPreferences.Editor editor = sf.edit();

        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
        Date time = new Date();
        String time1 = format1.format(time);

        todaylist = todayactioncategory1+"!#!"+time1+"!#!"+"미완료"+"!#!"+height+"!#!"+weight;

        editor.putString("today",todaylist);
        editor.commit();


    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("test2", "액티비티 시작");
        Intent intent = new Intent(


                getApplicationContext(),//현재제어권자
                metronomeservice.class); // 이동할 컴포넌트
        startService(intent);


    }




    @Override
    protected void onResume(){
        super.onResume();
        //rest 갔다 올때 마다 세트 증가, 모든 세트가 충족되면다음 운동으로 변경 , 모든 운동이 충족되면 운동 종료


        j++;
        if(j==Integer.parseInt(hashmap.get(keyName.get(i)))+1){
            j=1;
            i++;
        }
        if(i<keyName.size()) {
            todayactionname.setText(keyName.get(i));

            if(keyName.get(i).equals("사이드 레터럴 레이즈")){
                image.setImageResource(R.drawable.sideleteral);
            }else if(keyName.get(i).equals("프론트 레터럴 레이즈")){
                image.setImageResource(R.drawable.frontraise);
            }else if(keyName.get(i).equals("벤트오버 레터럴 레이즈")){
                image.setImageResource(R.drawable.rearraise);
            }else if(keyName.get(i).equals("숄더프레스")){
                image.setImageResource(R.drawable.shoulderpress);
            }else if(keyName.get(i).equals("시티드 프론트 덤벨 레이즈")){
                image.setImageResource(R.drawable.seatfront);
            }else if(keyName.get(i).equals("시티드 벤트오버 레이즈")){
                image.setImageResource(R.drawable.seatbentover);
            }

            else if(keyName.get(i).equals("스쿼트")){
                image.setImageResource(R.drawable.squat);
            }else if(keyName.get(i).equals("레그 익스텐션")){
                image.setImageResource(R.drawable.legextention);
            }else if(keyName.get(i).equals("레그 컬")){
                image.setImageResource(R.drawable.legcurl);
            }else if(keyName.get(i).equals("레그 프레스")){
                image.setImageResource(R.drawable.legpress);
            }else if(keyName.get(i).equals("런지")){
                image.setImageResource(R.drawable.lunge);
            }else if(keyName.get(i).equals("덤벨 런지")){
                image.setImageResource(R.drawable.dumbbelllunge);
            }

            else if(keyName.get(i).equals("플랭크")){
                image.setImageResource(R.drawable.plank);
            }else if(keyName.get(i).equals("바이시클 크런치")){
                image.setImageResource(R.drawable.bicyclecrunch);
            }else if(keyName.get(i).equals("디클라인 크런치")){
                image.setImageResource(R.drawable.declinecrunch);
            }else if(keyName.get(i).equals("슈퍼맨 푸쉬업")){
                image.setImageResource(R.drawable.superman);
            }else if(keyName.get(i).equals("사이드 플랭크")){
                image.setImageResource(R.drawable.sideplank);
            }else if(keyName.get(i).equals("덤벨 사이드 밴드")){
                image.setImageResource(R.drawable.dumbbellsidebend);
            }

            else if(keyName.get(i).equals("벤치 프레스")){
                image.setImageResource(R.drawable.benchpress);
            }else if(keyName.get(i).equals("딥스")){
                image.setImageResource(R.drawable.dipth);
            }else if(keyName.get(i).equals("인클라인 벤치 프레스")){
                image.setImageResource(R.drawable.inclinebenchpress);
            }else if(keyName.get(i).equals("디클라인 벤치 프레스")){
                image.setImageResource(R.drawable.declinebenchpress);
            }else if(keyName.get(i).equals("다이아몬드 푸쉬업")){
                image.setImageResource(R.drawable.diamondpushup);
            }else if(keyName.get(i).equals("플라이")){
                image.setImageResource(R.drawable.meachinefly);
            }

            sumset.setText("총 " + Integer.parseInt(hashmap.get(keyName.get(i))) + " 세트");
            actionset = String.valueOf(j) + " 세트 진행";
            setnumber.setText(actionset);

            if(i==keyName.size()-1&&j==Integer.parseInt(hashmap.get(keyName.get(i)))){
                SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
                Date time = new Date();
                String time1 = format1.format(time);
                startaction1button.setText("오늘의 운동 종료");
                startaction1button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sf = getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getEmail(),MODE_PRIVATE);
                        SharedPreferences.Editor editor = sf.edit();

                        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
                        Date time = new Date();
                        String time1 = format1.format(time);

                        todaylist = todayactioncategory1+"!#!"+time1+"!#!"+"완료"+"!#!"+height+"!#!"+weight;

                        editor.putString("today",todaylist);
                        editor.commit();

                        finish();
                    }
                });
               // Toast.makeText(this,time1,Toast.LENGTH_LONG).show();
            }

        }

        startactionbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }    @Override
    protected void onPause(){
        super.onPause();
        Log.i("액션스타트","onPause");

    }    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("액션스타트","onDestroy");

    }    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("액션스타트","onRestart");

    }    @Override
    protected void onStop(){
        super.onStop();
        Log.d("test2", "액티비티 끝");

        Intent intent = new Intent(
                getApplicationContext(),//현재제어권자
                metronomeservice.class); // 이동할 컴포넌트
        stopService(intent);




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }



}



