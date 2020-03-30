package com.example.me.myapplication;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Activity _MainActivity;
    String savecheck = "";
    ArrayList<String> createItemsubList;
    ArrayList<String> createItemset;
    HashMap<String, String> todayactionhashmap;
    String todaycategory;
    SharedPreferences sf;
    SharedPreferences autologin;
    TextView havenotlist;
    String sharedlist;

    String resttime;

    String todaylist;
    String actionhistory;

    FirebaseAuth firebaseAuth;

    TextView nicknametext;
    int currentimageindex = 0;
    ImageView mainactimage;
    int[] IMAGE_IDS;
    imagethread thread;
    ImageButton menubutton;
    DrawerLayout menudrawer;
    View drawerview;

    ImageView profillimageview_menu;
    TextView emailtextview_menu;
    TextView nicknametextview_menu;
    TextView heighttextview_menu;
    TextView weighttextview_menu;
    Button profillmodifybutton;
    Button settingbutton;
    Button logoutbutton;


    String[] todaydetail;

    TextView todaysetactionname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //다른액티비티에서 끌수있도록 객체화
        _MainActivity = MainActivity.this;
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        sf = getSharedPreferences(currentFirebaseUser.getEmail(), MODE_PRIVATE);

        //드로어 메뉴에 관한선
        menudrawer =findViewById(R.id.menudrawer);
        drawerview=findViewById(R.id.drawer);
        todaysetactionname=findViewById(R.id.todaysetactionname);
        profillimageview_menu = findViewById(R.id.profillimageview_menu);
        emailtextview_menu= findViewById(R.id.emailtextview_menu);
        nicknametextview_menu= findViewById(R.id.nicknametextview_menu);
        heighttextview_menu= findViewById(R.id.heighttextview_menu);
        weighttextview_menu= findViewById(R.id.weighttextview_menu);
        profillmodifybutton= findViewById(R.id.profillmodifybutton);
        settingbutton= findViewById(R.id.settingbutton);
        logoutbutton= findViewById(R.id.logoutbutton);


        firebaseAuth = FirebaseAuth.getInstance();

        //메인의 닉네임 텍스트와 날짜 계산
        nicknametext = findViewById(R.id.nicknametext);



        //오늘의 운동 제목과 소운동, 세트 가져오기
        menubutton = (ImageButton) findViewById(R.id.menubutton);

        havenotlist = (TextView) findViewById(R.id.havenotlist);
        todaycategory = getIntent().getStringExtra("tadaycategory");

        //저장 기록이 있는지 확인
        savecheck = getIntent().getStringExtra("savecheck");


        //소운동 등록 리사이클러뷰 생성


        //메뉴 보이기


        //목록 만들기, 보러가기
        Button activitylistbutton = (Button) findViewById(R.id.locationbutton1);

        activitylistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toactionserch1intent = new Intent(MainActivity.this, Actionserch1.class);
                startActivity(toactionserch1intent);


            }
        });


        //주변헬스장 보기
        Button serchhelthclubbutton = (Button) findViewById(R.id.serchhelthclubbutton);

        serchhelthclubbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tohelthlocation = new Intent(MainActivity.this, Helthlocation.class);

                startActivity(tohelthlocation);
            }
        });


        settingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topasswordmodify = new Intent(MainActivity.this, Passwordmodify.class);
                startActivity(topasswordmodify);
            }
        });

        //하단네비게이션
        Handler imagehandler = new Handler();


    }

    @Override
    protected void onResume() {
        Log.d("savemain", "onresume");
        super.onResume();

        //오늘 운동한게 있는지 읽어오기
        todaylist = sf.getString("today", "");

        //이전에 운동한게 있는지 읽어오기
        actionhistory = sf.getString("actionhistiory", "");

        String text = sf.getString("프로필", "");

        //오늘은동이 있으면 운동 시간 분류 자르기
        todaydetail = todaylist.split("!#!");


        String[] profillarray = text.split("!&%!");

        //오늘 운동기록이 있으면
        //todaylist에 셰어드 저장
        if(!todaylist.equals("")){
            todaydetail[3]=profillarray[1];
            todaydetail[4]=profillarray[2];
            todaylist=todaydetail[0]+"!#!"+todaydetail[1]+"!#!"+todaydetail[2]+"!#!"+todaydetail[3]+"!#!"+todaydetail[4];
            SharedPreferences.Editor editor = sf.edit();
            editor.putString("today", todaylist);
            editor.commit();

        }






        // String time1 = "2019-05-09";




        todayactionhashmap = new HashMap<String, String>();
        createItemsubList = new ArrayList<>();
        createItemset = new ArrayList<>();

        mainactimage = findViewById(R.id.mainactimage);
        IMAGE_IDS = new int[]{R.drawable.runner, R.drawable.healthpeople, R.drawable.lifting};

        thread = new imagethread();
        thread.setDaemon(true);
        thread.start();



        //프로필을 읽어서 세팅
        if (profillarray.length != 1) {
            nicknametext.setText(profillarray[0]);
            nicknametextview_menu.setText(profillarray[0]);
        }

        if(!profillarray[1].equals("~")){
            heighttextview_menu.setText(profillarray[1]+" cm");
        }else{
            heighttextview_menu.setText("");
        }

        if(!profillarray[2].equals("~")){
            weighttextview_menu.setText(profillarray[2]+" kg");
        }else{
            heighttextview_menu.setText("");
        }

        if (!profillarray[3].equals("~")) {

            String path = profillarray[3];
            Log.d("imimimimpath", path);
            Bitmap myBitmap = BitmapFactory.decodeFile(path);
            int mDegree =90;
            myBitmap = rotateImage(myBitmap, mDegree);

            int nh = (int) (myBitmap.getHeight() * (1024.0 / myBitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 1024, nh, true);
            BitmapDrawable ob = new BitmapDrawable(scaled);
            if (Build.VERSION.SDK_INT > 16) {
                menubutton.setBackground(ob);
                profillimageview_menu.setBackground(ob);
            } else {
              //  menubutton.setImageBitmap(scaled);
              //  profillimageview_menu.setImageBitmap(scaled);
            }
        }else{

        }
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        emailtextview_menu.setText(currentUser.getEmail());

        profillmodifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modifyprofillintent = new Intent(MainActivity.this, Modifyprofill.class);
                startActivity(modifyprofillintent);
            }
        });

    //    settingbutton


        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autologin = getSharedPreferences("autologin", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = autologin.edit();
                editor2.clear();
                editor2.commit();

                Intent logout = new Intent(MainActivity.this, Login2.class);
                startActivity(logout);
                finish();
            }
        });




        //운동목록 가져오기;
        sharedlist = sf.getString("actionlist", "");


        //저장 기록이 있을 때만 오늘의 목록이 보임

        if (sharedlist != "") {
            havenotlist.setVisibility(View.INVISIBLE);

            // 운동 기록이 있으면 다음 운동을 세팅

            if (actionhistory != "") {

                String[] sharedactionhistory = actionhistory.split("!@!");

                String[] sharedactionhistorydetail = sharedactionhistory[sharedactionhistory.length - 1].split("!#!");

                String[] sharedaction = sharedlist.split("!@!");

                String[] sharedactiondetail;

                String[] actioncategory = new String[sharedaction.length];

                for (int i = 0; i < sharedaction.length; i++) {
                    sharedactiondetail = sharedaction[i].split("]\\[");
                    actioncategory[i] = sharedactiondetail[0];
                }

                String savetoday = "";

                for (int i = 0; i < actioncategory.length; i++) {
                    if (sharedactionhistorydetail[0].equals(actioncategory[i])) {

                        if (i == actioncategory.length - 1) {
                            savetoday = actioncategory[0];
                            break;
                        } else {
                            savetoday = actioncategory[i + 1];
                            break;
                        }
                    } else {

                    }
                }

                int todayindex = 0;

                for (int i = 0; i < sharedaction.length; i++) {
                    sharedactiondetail = sharedaction[i].split("]\\[");

                    if (sharedactiondetail[0].equals(savetoday)) {
                        todayindex = i;
                        break;
                    }

                }

                sharedaction = sharedlist.split("!@!");
                sharedactiondetail = sharedaction[todayindex].split("]\\[");
                String[] sharedactiondetailname = sharedactiondetail[1].split(", ");
                String[] sharedactiondetailset = sharedactiondetail[2].split(", ");
                todaycategory = sharedactiondetail[0];
                todaysetactionname.setText(todaycategory);
                resttime=sharedactiondetail[3];
                for (int i = 0; i < sharedactiondetailname.length; i++) {
                    createItemsubList.add(sharedactiondetailname[i]);
                    //   Collections.shuffle(createItemsubList);
                }
                for (int i = 0; i < sharedactiondetailname.length; i++) {
                    todayactionhashmap.put(sharedactiondetailname[i], sharedactiondetailset[i]);
                }


            } else {
                //운동기록이 없으면 첫번째걸 세팅
                String[] sharedaction = sharedlist.split("!@!");
                String[] sharedactiondetail = sharedaction[0].split("]\\[");
                String[] sharedactiondetailname = sharedactiondetail[1].split(", ");
                String[] sharedactiondetailset = sharedactiondetail[2].split(", ");
                todaycategory = sharedactiondetail[0];
                todaysetactionname.setText(todaycategory);
                resttime=sharedactiondetail[3];
                for (int i = 0; i < sharedactiondetailname.length; i++) {
                    createItemsubList.add(sharedactiondetailname[i]);
                    //   Collections.shuffle(createItemsubList);
                }
                for (int i = 0; i < sharedactiondetailname.length; i++) {
                    todayactionhashmap.put(sharedactiondetailname[i], sharedactiondetailset[i]);
                }

            }

        } else {
            //운동 목록이 없으면 오늘의 운동 안보이기
            havenotlist.setVisibility(View.VISIBLE);
        }



        if (createItemsubList != null) {

            RecyclerView recyclerView = findViewById(R.id.mainrecycle);
            Log.d("리사이클", "recycler 뷰 생성");

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, createItemsubList, R.layout.row_mainrecycle);

            Log.d("리사이클", "어댑터 적용");
            recyclerView.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView_main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menuitem_home:
                        Intent toactionhistory = new Intent(MainActivity.this, Actionhistory.class);

                        startActivity(toactionhistory);

                        return true;

                    case R.id.menuitem_startact:
                        if (sharedlist != "") {
                            Intent tostartactionintent = new Intent(MainActivity.this, Startaction1.class);
                            tostartactionintent.putExtra("todaycategory", todaycategory);
                            tostartactionintent.putExtra("todayactionshuffle", createItemsubList);
                            tostartactionintent.putExtra("todayactionhashmap", todayactionhashmap);
                            tostartactionintent.putExtra("resttime", resttime);
                            startActivity(tostartactionintent);

                            return true;

                        } else {

                            Toast.makeText(MainActivity.this, "운동 목록을 등록해 주십시오.", Toast.LENGTH_SHORT).show();
                            return false;

                        }
                    case R.id.menuitem_actlist:

                        Intent toactionlistintent = new Intent(MainActivity.this, Actionserch1.class);
                        startActivity(toactionlistintent);

                        return true;
                    case R.id.menuitem_noticeboard:

                        Intent tonoticeboardintent = new Intent(MainActivity.this, Noticeboard.class);
                        startActivity(tonoticeboardintent);

                        return true;
                }

                return false;
            }
        });

        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menudrawer.openDrawer(drawerview);

            }
        });




        /*
        Button buttonCloseDrawer = (Button) findViewById(R.id.closedrawer);
        buttonCloseDrawer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                menudrawer.closeDrawers();
            }
        });
*/



    }





    //이미지 변경 쓰레드 인터럽트 퍼즈에서 진행
    @Override
    protected void onPause() {
        Log.d("savemain", "onresume");
        super.onPause();
        thread.interrupt();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        String time1 = format1.format(time);


        if (todaylist != "" && todaydetail[2].equals("완료")) {
            actionhistory = actionhistory + "!@!" + todaylist;
            todaylist = "";
            SharedPreferences.Editor editor = sf.edit();
            editor.putString("actionhistiory", actionhistory);
            Log.d("save2", actionhistory);
            editor.putString("today", todaylist);
            editor.commit();
          //날짜가 바뀔때도
        } else {
            if(todaydetail.length!=1) {
                Log.d("plushash",String.valueOf(todaydetail[1]));
                if (!todaydetail[1].equals(time1)) {
                    actionhistory = actionhistory + "!@!" + todaylist;
                    todaylist = "";
                    SharedPreferences.Editor editor = sf.edit();
                    editor.putString("actionhistiory", actionhistory);
                    Log.d("save2", actionhistory);
                    editor.putString("today", todaylist);
                    editor.commit();
                }
            }
        }


    }



    //저장된 이미지를 쓰레드를 통해 계속 변경
    class imagethread extends Thread {

        @Override
        public void run() {


            try {


                while (true) {

                    currentimageindex++;
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // Runnable 의 Run() 메소드에서 UI 접근


                            mainactimage.setImageResource(IMAGE_IDS[currentimageindex % IMAGE_IDS.length]);
                        }
                    });

                    Thread.sleep(5000);
                }

            } catch (Exception e) {


            }
        }


    }

    Handler handler = new Handler();
    public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }
}


