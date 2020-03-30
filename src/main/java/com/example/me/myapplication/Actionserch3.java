package com.example.me.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class Actionserch3 extends AppCompatActivity {

    TextView serch3listname;
    String text;
    String text2;
    ImageButton actionserch3savebutton;
    String actioncategory;
    String acionlistname;
    TextView plusactionlist;


    ArrayList<String> alreadyset;

    ArrayList<String> checkitemsname;
    String itemname ;
    ArrayList<String> subitems;
    ArrayList<String> itemsset;
    ArrayList<String> plusset;
    HashMap<String,String> plushashset;
    String plusitemname;
    String plusresttime;


    String receiveresttime;
    SharedPreferences sf;

    Actionlist1 aActionlist1=(Actionlist1)Actionlist1.Actionlist1Activity;
    Actionserch1 aActionserch1=(Actionserch1)Actionserch1.Actionserch1Activity;

    String resttime;
    Spinner spinner;
    String[] timelist;
    ArrayAdapter spinnertimeadapter;
    String selectactiontime;
    HashMap<String,String> resttimehash;
    String originalitemname;

    ArrayList<String> actionarraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionserch3);
        checkitemsname = (ArrayList<String>) getIntent().getSerializableExtra("checkitemsname");
        actionarraylist=new ArrayList<String>();


        sf = getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getEmail(),MODE_PRIVATE);

        String sharedlist = sf.getString("actionlist", "");
        if (sharedlist != "") {
            //운동 분류 별로 나누기

            String[] sharedaction = sharedlist.split("!@!");
            //   Log.d("save",sharedaction[0]);

            //운동 분류를 운동이름, 소운동 , 소운동 세트, 리사이클러뷰 포지션 으로 나누기
            for (int j = 0; j < sharedaction.length; j++) {
                String[] sharedsubdetail = sharedaction[j].split("]\\[");
                actionarraylist.add(sharedsubdetail[0]);
            }
        }


        resttimehash =new HashMap<>();

        resttime="60";

        //serch1에서 온거
        originalitemname=getIntent().getStringExtra("item");
        itemname=getIntent().getStringExtra("item");
        subitems=(ArrayList<String>) getIntent().getSerializableExtra("subitems");
        itemsset=(ArrayList<String>) getIntent().getSerializableExtra("itemsset");
        receiveresttime = getIntent().getStringExtra("resttime");


        serch3listname = (TextView)findViewById(R.id.listname);


        //actionlist1에서 온거
      //  actioncategory = getIntent().getStringExtra("category");

        plusset = (ArrayList<String>) getIntent().getSerializableExtra("plusitemsset");
        plusitemname = getIntent().getStringExtra("plusitemname");
        plusresttime = getIntent().getStringExtra("plusresttime");

        if(receiveresttime!=null){
            resttime=receiveresttime;
        }
        if(plusitemname!=null){
            serch3listname.setText(plusitemname);
        }
        if(plusresttime!=null){
            resttime=plusresttime;
        }

        plushashset=new HashMap<String,String>();
        alreadyset=new ArrayList<>();
        if(itemsset!=null){
            alreadyset=itemsset;
        }else if(plusset!=null){
            alreadyset=plusset;
        }



        if(itemname!=null) {
            serch3listname.setText(itemname);
        }else{

        }

        plusactionlist = (TextView)findViewById(R.id.plusactionlist);
        plusactionlist.isClickable();

        actionserch3savebutton = (ImageButton)findViewById(R.id.actionserch3savebutton);


        ImageButton actionserch3backbutton = (ImageButton)findViewById(R.id.actionserch3backbutton);

        actionserch3backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });



    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }
    private boolean userIsInteracting;


    @Override
    protected void onResume(){
        super.onResume();
        // serch1에서 바로 온거






        spinner =(Spinner)findViewById(R.id.spinnertime);
        //운동 분류, 스피너로 보여짐
        timelist = new String[]{"1세트 운동 후 30초 휴식", "1세트 운동 후 60초 휴식", "1세트 운동 후 90초 휴식", "1세트 운동 후 120초 휴식"};

        spinnertimeadapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, timelist);

        spinner.setAdapter(spinnertimeadapter);

        if(resttime.equals("30")) {
            spinner.setSelection(0);
        }else if(resttime.equals("60")) {
            spinner.setSelection(1);
        }else if(resttime.equals("90")) {
            spinner.setSelection(2);
        }else if(resttime.equals("120")) {
            spinner.setSelection(3);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectactiontime = spinner.getSelectedItem().toString();

                if(selectactiontime.equals("1세트 운동 후 30초 휴식")){
                    resttime="30";
                }else if(selectactiontime.equals("1세트 운동 후 60초 휴식")){
                    resttime="60";
                }else if(selectactiontime.equals("1세트 운동 후 90초 휴식")){
                    resttime="90";
                }else if(selectactiontime.equals("1세트 운동 후 120초 휴식")){
                    resttime="120";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        //serch1에서온거
        if(itemname != null){

            RecyclerView recyclerView = findViewById(R.id.actionserch3recycle);
            Log.d("리사이클", "recycler 뷰 생성");

            final RecyclerViewAdapter_serch3 adapter = new RecyclerViewAdapter_serch3(this, subitems,alreadyset, R.layout.row_serch3recycle);

            Log.d("리사이클", "어댑터 적용");
            recyclerView.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

            plusactionlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    acionlistname = serch3listname.getText().toString();
                    ArrayList<String> checkitemsname2 = ((RecyclerViewAdapter_serch3) adapter).getCheckitemsname2();
                    ArrayList<String> checkitemsset2 = ((RecyclerViewAdapter_serch3) adapter).getCheckitemsset2();


                    Log.d("plushash",String.valueOf(checkitemsname2));
                    Log.d("plushash",String.valueOf(checkitemsset2));
                    Log.d("plushash",String.valueOf(plushashset));
                    Intent toactionlist1intent = new Intent(Actionserch3.this, Actionlist1.class);
                    toactionlist1intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    toactionlist1intent.putExtra("plusitemname", checkitemsname2);
                    toactionlist1intent.putExtra("plusitemset", checkitemsset2);
                    toactionlist1intent.putExtra("plusitemlistname", acionlistname);
                    toactionlist1intent.putExtra("plusresttime", resttime);
                    //해쉬맵 보내기

                    startActivity(toactionlist1intent);
                    Actionserch3.this.finish();

                }
            });

            actionserch3savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> checkitemsname2 = ((RecyclerViewAdapter_serch3) adapter).getCheckitemsname2();
                    ArrayList<String> checkitemsset2 = ((RecyclerViewAdapter_serch3) adapter).getCheckitemsset2();
                    acionlistname = serch3listname.getText().toString();
                    Log.d("save", String.valueOf(acionlistname.length()));


                        if (acionlistname.length() != 0) {

                            Intent toactionserch1 = new Intent(Actionserch3.this, Actionserch1.class);
                            if (!checkitemsset2.contains("")) {
                                if (originalitemname != null) {
                                    toactionserch1.putExtra("actionlistoriginalname", originalitemname);
                                    toactionserch1.putExtra("actionlistname", acionlistname);
                                    toactionserch1.putExtra("savecheck", "ok");
                                    toactionserch1.putExtra("checkitemsset", checkitemsset2);
                                    toactionserch1.putExtra("checkitemsname", checkitemsname2);
                                    toactionserch1.putExtra("resttime", resttime);


                                    toactionserch1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(toactionserch1);
                                    if (aActionlist1 != null) {
                                        aActionlist1.finish();
                                    }
                                    Actionserch3.this.finish();

                                }else{
                                    if (actionarraylist.contains(acionlistname)) {
                                        Toast.makeText(Actionserch3.this, "이미 존재하는 이름입니다", Toast.LENGTH_SHORT).show();
                                    }else{
                                        toactionserch1.putExtra("actionlistname", acionlistname);
                                        toactionserch1.putExtra("savecheck", "ok");
                                        toactionserch1.putExtra("checkitemsset", checkitemsset2);
                                        toactionserch1.putExtra("checkitemsname", checkitemsname2);
                                        toactionserch1.putExtra("resttime", resttime);


                                        toactionserch1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(toactionserch1);
                                        if (aActionlist1 != null) {
                                            aActionlist1.finish();
                                        }
                                        Actionserch3.this.finish();

                                    }
                                }

                            } else {
                                Toast.makeText(Actionserch3.this, "세트를 골라주세요", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(Actionserch3.this, "리스트 이름을 작성해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
            });



        }else {


            RecyclerView recyclerView = findViewById(R.id.actionserch3recycle);
            Log.d("리사이클", "recycler 뷰 생성");

            final RecyclerViewAdapter_serch3 adapter = new RecyclerViewAdapter_serch3(this, checkitemsname,alreadyset, R.layout.row_serch3recycle);

            Log.d("리사이클", "어댑터 적용");
            recyclerView.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

            plusactionlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acionlistname = serch3listname.getText().toString();

                    ArrayList<String> checkitemsname2 = ((RecyclerViewAdapter_serch3) adapter).getCheckitemsname2();
                    ArrayList<String> checkitemsset2 = ((RecyclerViewAdapter_serch3) adapter).getCheckitemsset2();

                    Intent toactionlist1intent = new Intent(Actionserch3.this, Actionlist1.class);
                    toactionlist1intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);


                    toactionlist1intent.putExtra("plusitemset", checkitemsset2);
                    toactionlist1intent.putExtra("plusitemname", checkitemsname2);
                    toactionlist1intent.putExtra("plusitemlistname", acionlistname);
                    toactionlist1intent.putExtra("plusresttime", resttime);


                    startActivity(toactionlist1intent);
                    Actionserch3.this.finish();
                }
            });

            actionserch3savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> checkitemsname2 = ((RecyclerViewAdapter_serch3) adapter).getCheckitemsname2();
                    ArrayList<String> checkitemsset2 = ((RecyclerViewAdapter_serch3) adapter).getCheckitemsset2();
                    acionlistname = serch3listname.getText().toString();
                    Log.d("save", String.valueOf(acionlistname.length()));


                        if (acionlistname.length() != 0) {

                            if (!checkitemsset2.contains("")) {
                                Intent toactionserch1 = new Intent(Actionserch3.this, Actionserch1.class);

                                toactionserch1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                if (originalitemname != null) {
                                    toactionserch1.putExtra("actionlistoriginalname", originalitemname);
                                    toactionserch1.putExtra("actionlistname", acionlistname);
                                    toactionserch1.putExtra("savecheck", "ok");
                                    toactionserch1.putExtra("checkitemsset", checkitemsset2);
                                    toactionserch1.putExtra("checkitemsname", checkitemsname2);
                                    toactionserch1.putExtra("resttime", resttime);
                                    startActivity(toactionserch1);
                                    if (aActionlist1 != null) {
                                        aActionlist1.finish();
                                    }
                                    Actionserch3.this.finish();

                                }else{
                                    if (actionarraylist.contains(acionlistname)) {
                                        Toast.makeText(Actionserch3.this, "이미 존재하는 이름입니다", Toast.LENGTH_SHORT).show();
                                    }else {
                                        toactionserch1.putExtra("actionlistname", acionlistname);
                                        toactionserch1.putExtra("savecheck", "ok");
                                        toactionserch1.putExtra("checkitemsset", checkitemsset2);
                                        toactionserch1.putExtra("checkitemsname", checkitemsname2);
                                        toactionserch1.putExtra("resttime", resttime);
                                        startActivity(toactionserch1);
                                        if (aActionlist1 != null) {
                                            aActionlist1.finish();
                                        }
                                        Actionserch3.this.finish();
                                    }
                                }

                            } else {
                                Toast.makeText(Actionserch3.this, "세트를 골라주세요", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(Actionserch3.this, "리스트 이름을 작성해주세요", Toast.LENGTH_SHORT).show();
                        }
                }
            });

        }


    }





    @Override
    protected void onPause(){
        super.onPause();
        Log.i("액션스타트","onResume");

      //  Toast.makeText(Actionserch3.this, " 데이터가 임시보관 되었습니다. ", Toast.LENGTH_SHORT).show();


    }


}
