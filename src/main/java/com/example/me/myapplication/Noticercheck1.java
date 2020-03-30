package com.example.me.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class Noticercheck1 extends AppCompatActivity {


    String usernickname;
    String userimage;
    String title;
    String content;
    ArrayList<String> sendactiontitle;
    HashMap<String,ArrayList<String>> sendhashmapactionname;
    HashMap<String,ArrayList<String>> sendhashmapactionset;
    HashMap<String,String> resttimearray;

    TextView edittilecheck;
    TextView editcontentcheck;
    RecyclerView noticechecklistrecycle;
    TextView nicknamecheck;

    ImageButton noticecheckbackbutton;
    Button noticecheckbutton;

    SharedPreferences sf;

    ArrayList<String> actionarraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticecheck1);

        usernickname=getIntent().getStringExtra("usernickname");
        userimage=getIntent().getStringExtra("userimage");
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");

        sendactiontitle=(ArrayList<String>)getIntent().getSerializableExtra("sendactiontitle");
        sendhashmapactionname=(HashMap<String,ArrayList<String>>)getIntent().getSerializableExtra("sendhashmapactionnamelist");
        sendhashmapactionset=(HashMap<String,ArrayList<String>>)getIntent().getSerializableExtra("sendhashmapactionsetlist");
        resttimearray=(HashMap<String,String>)getIntent().getSerializableExtra("resttime");


        edittilecheck=findViewById(R.id.edittitlecheck);
        editcontentcheck=findViewById(R.id.editcontentcheck);
        nicknamecheck=findViewById(R.id.nicknamecheck);

        nicknamecheck.setText(usernickname+"님의 게시글");
        edittilecheck.setText(title);
        editcontentcheck.setText(content);

        actionarraylist=new ArrayList<String>();


        noticechecklistrecycle=findViewById(R.id.noticechecklistrecycle);
        final RecyclerViewAdapter_noticeregister2 adapter = new RecyclerViewAdapter_noticeregister2(this,sendactiontitle,sendhashmapactionname,sendhashmapactionset,resttimearray,R.layout.row_noticeregisterrecycle1);
        noticechecklistrecycle.setAdapter(adapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noticechecklistrecycle.setLayoutManager(layoutManager);

        noticechecklistrecycle.setItemAnimator(new DefaultItemAnimator());



        noticecheckbackbutton=findViewById(R.id.noticecheckbackbutton);

        noticecheckbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        noticecheckbutton=findViewById(R.id.noticecheckbutton);




        noticecheckbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> savelist_sub = adapter.getsendtitle();
                if (savelist_sub.size() != 0) {

                    HashMap<String, ArrayList<String>> saveactionname = adapter.getsendhashmapactionname();

                    HashMap<String, ArrayList<String>> saveactionset = adapter.getsendhashmapactionset();

                    HashMap<String, String> saveresttime = adapter.getsendresttimelist();

                    sf = getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getEmail(), MODE_PRIVATE);

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

                    for (int i = 0; i < savelist_sub.size(); i++) {
                        String savelist_sub_name = savelist_sub.get(i);

                        for (int j = 0; ; j++) {
                            if (actionarraylist.contains(savelist_sub_name)) {
                                savelist_sub_name = savelist_sub.get(i) + String.valueOf(j);

                            } else {

                                break;
                            }
                        }
                        sharedlist = sharedlist + savelist_sub_name + "]" + saveactionname.get(savelist_sub.get(i)) + saveactionset.get(savelist_sub.get(i)) + "[" + saveresttime.get(savelist_sub.get(i)) + "!@!";

                    }

                    SharedPreferences.Editor editor = sf.edit();

                    editor.putString("actionlist", sharedlist);
                    editor.commit();

                    Intent toactionserch1_2 = new Intent(Noticercheck1.this, Actionserch1.class);

                    startActivity(toactionserch1_2);
                    finish();

                }else{
                    Toast.makeText(Noticercheck1.this, "리스트가 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
