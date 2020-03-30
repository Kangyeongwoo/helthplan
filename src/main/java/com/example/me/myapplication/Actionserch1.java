package com.example.me.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//목록이 보이는 장소 저장과 불러오기가 이루어짐 (셰어드)
// 검색은 스피너를 통해 진행된다.

public class Actionserch1 extends AppCompatActivity {

    public static Activity Actionserch1Activity;
    ArrayList<String> createItemsetList;
    ArrayList<String> createItemList;
    ArrayList<String> createsubItemList;
    String listname;
    HashMap<String, ArrayList<String>> hashmapactionname;
    HashMap<String, ArrayList<String>> hashmapactionset;
    String todayactioncategory;

    MainActivity a_Mainactivity =(MainActivity)MainActivity._MainActivity;
    String savecheck;
    SharedPreferences sf;
    RecyclerViewAdapter_serch1 adapter;
    TextView serchtext;

    HashMap<String, ArrayList<String>> saveactionname;
    HashMap<String, ArrayList<String>> saveactionset;
    HashMap<String, String>saveresttime;
    ArrayList<String> saveactionposition;

    HashMap<String,String> resttimelist;

    String resttime;
    String originallistname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionserch1);

        resttimelist=new HashMap<String,String>();

        Actionserch1Activity = Actionserch1.this;
        //운동 분류 리스트
        createItemList = new ArrayList<>();
        //소 운동 리스트
        createsubItemList = new ArrayList<>();
        //소 운동 세트 리스트
        createItemsetList = new ArrayList<>();

        //키 운동분류, 밸류 소운동 리스트
        hashmapactionname = new HashMap<String, ArrayList<String>>();
        //키 운동분류, 소 운동 세트 리스트
        hashmapactionset = new HashMap<String, ArrayList<String>>();


        //셰어드 데이터 가져오기
        sf = getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getEmail(), MODE_PRIVATE);

        String sharedlist = sf.getString("actionlist", "");
           Log.d("save",sharedlist);

        //actionlist가 비어있지 않을 때
        if (sharedlist != "") {
            //운동 분류 별로 나누기

            String[] sharedaction = sharedlist.split("!@!");
            //   Log.d("save",sharedaction[0]);

            //운동 분류를 운동이름, 소운동 , 소운동 세트, 리사이클러뷰 포지션 으로 나누기
            for (int j = 0; j < sharedaction.length; j++) {
                String[] sharedsubdetail = sharedaction[j].split("]\\[");
                createItemList.add(sharedsubdetail[0]);
                resttimelist.put(sharedsubdetail[0],sharedsubdetail[3]);
                Log.d("save",sharedsubdetail[3]);
            }

            //운동 분류로 나누기
            for (int i = 0; i < sharedaction.length; i++) {
                String[] sharedaction2 = sharedlist.split("!@!");

                //운동이름, 소운동, 소운동 세트, 포지션으로 자르기
                for (int j = 0; j < sharedaction2.length; j++) {
                    String[] sharedsubdetail = sharedaction2[j].split("]\\[");

                    //소운동을 각각으로 자르기
                    String[] instanceaction = sharedsubdetail[1].split(", ");
                    for (int k = 0; k < instanceaction.length; k++) {
                        createsubItemList.add(instanceaction[k]);
                    }

                    //소운동 세트를 각각 자르기
                    String[] instanceactionset = sharedsubdetail[2].split(", ");
                    for (int k = 0; k < instanceactionset.length; k++) {
                        createItemsetList.add(instanceactionset[k]);
                    }

                    //운동 , 소운동
                    hashmapactionname.put(sharedsubdetail[0], createsubItemList);
                    //운동 , 소운동 세트
                    hashmapactionset.put(sharedsubdetail[0], createItemsetList);
                    //소운동 리스트, 소운동세트리스트 초기화
                    createsubItemList=new ArrayList<>();
                    createItemsetList=new ArrayList<>();
                }
            }
        }
    }

    //액티비티가 켜있기 때문에 온 리쥼에서 실행
  @Override
    protected void onResume(){
        super.onResume();


      savecheck=getIntent().getStringExtra("savecheck");

      //홈으로 가는 버튼
      ImageButton actionserch1backbutton = (ImageButton)findViewById(R.id.actionserch1backbutton);


      actionserch1backbutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          onBackPressed();

          }
      });


      //플러스 버튼을 클릭하면 스피너 활성화
      FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton6);

      floatingActionButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(Actionserch1.this, Actionlist1.class);
              startActivity(intent);


          }
      });

      serchtext =findViewById(R.id.serchtext);
      serchtext.setClickable(true);
      serchtext.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(Actionserch1.this, Actionlist1.class);
              startActivity(intent);
          }
      });



      //아이템리스트와 서브 아이템 리스트가 있으면 리사이클러뷰 생성
      if(createItemList != null && createsubItemList!= null) {

          RecyclerView recyclerView = findViewById(R.id.actionserch1recycle);
          Log.d("리사이클", "recycler 뷰 생성");

          adapter = new RecyclerViewAdapter_serch1(this, createItemList, hashmapactionname,hashmapactionset,resttimelist, R.layout.row_serch1recycle);

          Log.d("리사이클", "어댑터 적용");
          recyclerView.setAdapter(adapter);


          LinearLayoutManager layoutManager = new LinearLayoutManager(this);
          layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
          recyclerView.setLayoutManager(layoutManager);

          recyclerView.setItemAnimator(new DefaultItemAnimator());


      }

    }

    //회면이 가려지면 있던 데이터 들을 셰어드에 저장
    @Override
    protected void onPause() {
        super.onPause();

        //운동 분류 , 소운동 해쉬맵
     //   saveactionname = adapter.getsemdhashmapactionname();
        saveactionname = hashmapactionname;

        //운동분류, 소운동 세트 해쉬맵
     //   saveactionset = adapter.getsendhashmapactionset();
        saveactionset = hashmapactionset;

        //운동분류, 포지션 해쉬맵
     //   saveactionposition = adapter.getsendhashmapactionposition();
     //   saveactionposition = adapter.getsenditems();
        saveactionposition = createItemList;

        saveresttime = resttimelist;

        sf = getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getEmail(),MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();

        //키인 운동 뷴류를 뽑아서 리스트에 저장
        ArrayList<String> savelist_sub = new ArrayList<>();

        savelist_sub = saveactionposition;

        String list="";

        //셰어드에 운동 분류, 소운동 리스트, 소운동 세트 리스트, 포지션을 저장
        for(int i=0; i<savelist_sub.size(); i++){

            list = list + savelist_sub.get(i)+"]"+saveactionname.get(savelist_sub.get(i))+saveactionset.get(savelist_sub.get(i))+"["+saveresttime.get(savelist_sub.get(i))+"!@!";

        }

        // +saveactionposition.get(savelist_sub.get(i))"

        editor.putString("actionlist",list);
        editor.commit();



    }



    //actionserch3 상세저장 페이지에서 데이터를 받아옴
    @Override
    protected void onNewIntent(Intent toactionserch1) {
        super.onNewIntent(toactionserch1);
        setIntent(toactionserch1);

        originallistname =toactionserch1.getStringExtra("actionlistoriginalname");

        listname = toactionserch1.getStringExtra("actionlistname");

        if(originallistname!=null){
            Log.d("savename",originallistname);
            if(originallistname.equals(listname)){
                if(!createItemList.contains(listname)) {
                    createItemList.add(listname);
                    Log.d("savename",listname);
                }
            }else{
                    int index = createItemList.indexOf(originallistname);
                    createItemList.add(index,listname);
                    createItemList.remove(originallistname);
                    Log.d("savenamecreate", String.valueOf(createItemList));
            }


        }else{
            Log.d("savename",listname);
            if(!createItemList.contains(listname)) {
                createItemList.add(listname);
            }else{

            }
        }



        createsubItemList = (ArrayList<String>) toactionserch1.getSerializableExtra("checkitemsname");

        createItemsetList = (ArrayList<String>) toactionserch1.getSerializableExtra("checkitemsset");

        hashmapactionname.put(listname, createsubItemList);

        hashmapactionset.put(listname, createItemsetList);

        resttime =toactionserch1.getStringExtra("resttime");

        resttimelist.put(listname,resttime);
    }
/*
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }
    private boolean userIsInteracting;
*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
/*    final Spinner spinner =(Spinner)findViewById(R.id.spinner2);
     //운동 분류, 스피너로 보여짐
      String[] list ={"먼저 운동 분류를 골라 주세요","어깨", "다리","코어","요가","직접선택"};

      ArrayAdapter spinneradapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);

      spinner.setAdapter(spinneradapter);


      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           //   if (userIsInteracting) {

                  if(spinner.getSelectedItem().toString().equals("직접선택")) {
                      Intent intent = new Intent(Actionserch1.this, Actionserch2.class);
                      startActivity(intent);

                  }else if(spinner.getSelectedItem().toString().equals("먼저 운동 분류를 골라 주세요")){


                  }else{
                      String text = spinner.getSelectedItem().toString();
                      spinner.setSelection(0);
                      Intent intent = new Intent(Actionserch1.this, Actionlist1.class);
                      intent.putExtra("actioncategory", text);
                      startActivity(intent);

                  }
           //   }



          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });

      */
