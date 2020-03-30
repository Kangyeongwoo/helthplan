package com.example.me.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Noticeboard extends AppCompatActivity {
    private DatabaseReference mDatabase;

    ImageButton noticeboardbackbutton;
    Button noticeboardregisterbutton;

    ImageButton refreashbutton;

    ArrayList<String> documentidlistlist;
    ArrayList<String> useruidlist;
    ArrayList<String> usernicknamelist;
    ArrayList<String> userimagelist;
    ArrayList<String> titlelist;
    ArrayList<String> contentlist;
    ArrayList<ArrayList<String>> sendactiontitlelist;
    ArrayList<HashMap<String,ArrayList<String>>> sendhashmapactionnamelist;
    ArrayList<HashMap<String,ArrayList<String>>> sendhashmapactionsetlist;
    ArrayList<HashMap<String,String>> resttimearraylist;

    RecyclerView recyclerView;
    RecyclerViewAdapter_noticeboard adapter;


    public static Activity NoticeboardActivity;


    ProgressBar progressBar2;

    Spinner spinner;
    String[] list;
    ArrayAdapter spinneradapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    String nowuid;
    int flag =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard);

        NoticeboardActivity=Noticeboard.this;

        refreashbutton=findViewById(R.id.refreashbutton);
        progressBar2=findViewById(R.id.progressBar2);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        nowuid =currentUser.getUid();

        noticeboardbackbutton=findViewById(R.id.noticeboardbackbutton);
        noticeboardbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        noticeboardregisterbutton=findViewById(R.id.noticeboardregisterbutton);
        noticeboardregisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tonoticeregisterintent = new Intent(Noticeboard.this,Noticeregister1.class);
                startActivity(tonoticeregisterintent);

            }
        });

        refreashbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Noticeboard.this.onStart();
            }
        });


        spinner =(Spinner)findViewById(R.id.noticecategoryspinner);
        //운동 분류, 스피너로 보여짐
        list = new String[]{"전체 게시글", "내가 쓴 글"};

        spinneradapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);

        spinner.setAdapter(spinneradapter);


        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("spinner", "실행");
                if(flag!=0) {
                    Noticeboard.this.onStart();
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();



        if(spinner.getSelectedItem().toString().equals("전체 게시글")) {
            progressBar2 = findViewById(R.id.progressBar2);
            documentidlistlist = new ArrayList<String>();
            useruidlist = new ArrayList<String>();
            usernicknamelist = new ArrayList<String>();
            userimagelist = new ArrayList<String>();
            titlelist = new ArrayList<String>();
            contentlist = new ArrayList<String>();
            sendactiontitlelist = new ArrayList<ArrayList<String>>();
            sendhashmapactionnamelist = new ArrayList<HashMap<String, ArrayList<String>>>();
            sendhashmapactionsetlist = new ArrayList<HashMap<String, ArrayList<String>>>();
            resttimearraylist = new ArrayList<HashMap<String, String>>();

            Log.d("testspinner" ,"ok");

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("noticewrite")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {


                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //   Log.d("documenttest", document.getId() + " => " + document.getData());

                                    documentidlistlist.add(String.valueOf(document.getId()));
                                    sendhashmapactionsetlist.add((HashMap<String, ArrayList<String>>) document.getData().get("sendhashmapactionset"));
                                    sendhashmapactionnamelist.add((HashMap<String, ArrayList<String>>) document.getData().get("sendhashmapactionname"));
                                    sendactiontitlelist.add((ArrayList<String>) document.getData().get("sendactiontitle"));
                                    usernicknamelist.add(String.valueOf(document.getData().get("nickname")));
                                    titlelist.add(String.valueOf(document.getData().get("title")));
                                    contentlist.add(String.valueOf(document.getData().get("content")));
                                    useruidlist.add(String.valueOf(document.getData().get("useruid")));
                                    userimagelist.add(String.valueOf(document.getData().get("userimage")));
                                    resttimearraylist.add((HashMap<String, String>) document.getData().get("resttimelist"));
                                }
                                progressBar2.setVisibility(View.GONE);
                                Noticeboard.this.onResume();
                                flag=1;
                            } else {
                                Log.d("documenttest", "fail");
                            }
                        }
                    });

        }else if(spinner.getSelectedItem().toString().equals("내가 쓴 글")){
            progressBar2 = findViewById(R.id.progressBar2);
            documentidlistlist = new ArrayList<String>();
            useruidlist = new ArrayList<String>();
            usernicknamelist = new ArrayList<String>();
            userimagelist = new ArrayList<String>();
            titlelist = new ArrayList<String>();
            contentlist = new ArrayList<String>();
            sendactiontitlelist = new ArrayList<ArrayList<String>>();
            sendhashmapactionnamelist = new ArrayList<HashMap<String, ArrayList<String>>>();
            sendhashmapactionsetlist = new ArrayList<HashMap<String, ArrayList<String>>>();
            resttimearraylist = new ArrayList<HashMap<String, String>>();

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("noticewrite")
                    .whereEqualTo("useruid", nowuid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {


                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //   Log.d("documenttest", document.getId() + " => " + document.getData());

                                    documentidlistlist.add(String.valueOf(document.getId()));
                                    sendhashmapactionsetlist.add((HashMap<String, ArrayList<String>>) document.getData().get("sendhashmapactionset"));
                                    sendhashmapactionnamelist.add((HashMap<String, ArrayList<String>>) document.getData().get("sendhashmapactionname"));
                                    sendactiontitlelist.add((ArrayList<String>) document.getData().get("sendactiontitle"));
                                    usernicknamelist.add(String.valueOf(document.getData().get("nickname")));
                                    titlelist.add(String.valueOf(document.getData().get("title")));
                                    contentlist.add(String.valueOf(document.getData().get("content")));
                                    useruidlist.add(String.valueOf(document.getData().get("useruid")));
                                    userimagelist.add(String.valueOf(document.getData().get("userimage")));
                                    resttimearraylist.add((HashMap<String, String>) document.getData().get("resttimelist"));
                                }
                                progressBar2.setVisibility(View.GONE);
                                Noticeboard.this.onResume();
                                flag=1;
                            } else {
                                Log.d("documenttest", "fail");
                            }
                        }
                    });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();



        recyclerView = findViewById(R.id.noticeboardrecycle);

        adapter = new RecyclerViewAdapter_noticeboard(this,documentidlistlist, useruidlist,usernicknamelist,
                userimagelist,titlelist,contentlist,sendactiontitlelist,sendhashmapactionnamelist,sendhashmapactionsetlist,resttimearraylist,nowuid,refreashbutton, R.layout.row_noticeboard);

        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    }

