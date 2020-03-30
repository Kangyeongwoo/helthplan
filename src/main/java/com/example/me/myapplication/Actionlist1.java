package com.example.me.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//선택할 수 있는 운동 들이 정렬 되는 액티비티
public class Actionlist1 extends AppCompatActivity {

    ArrayList<String> actionname;
    TextView actionname1;
    TextView actionname2;
    TextView actionpart1;
    TextView actionpart2;
    ImageButton actionlist1backbutton;
    ImageButton actionlist1savebutton;
    CheckBox action1checkbox;
    CheckBox action2checkbox;
    ArrayList<String> itemsname;
    ArrayList<String> itemspart;
    ArrayList<String> plusitems;
    ArrayList<String> plusitems2;
    String plusitemname;
    String plusresttime;


    ArrayList<String> plusitemsset;
    ArrayList<String> plusitemsset2;
    HashMap<String,String> plusitemshashset;

    String actioncategory;
    String backactionname;
    TextView text;
    String selectaction;
    public static Activity Actionlist1Activity;

    Spinner spinner;

    RecyclerView recyclerView;
    RecyclerViewAdapter_actionlist1 adapter;
    LinearLayoutManager layoutManager;


    ArrayList<String> checkitemsname1;
    ArrayList<String> checkitemsname2;
    ArrayList<String> checkitemsname3;
    ArrayList<String> checkitemsname4;
    ArrayList<String> checkitemsname;

    ArrayList<String> thischeckbox;

    int count1;
    int count2;
    int count3;
    int count4;


    String[] list;
    ArrayAdapter spinneradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionlist1);
        actionlist1savebutton = (ImageButton)findViewById(R.id.actionlist1save);

        Actionlist1Activity=Actionlist1.this;

        ImageButton actionlist1serch = (ImageButton)findViewById(R.id.actionlist1serch);


        actionlist1backbutton=(ImageButton)findViewById(R.id.actionlist1backbutton);
        actionlist1backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        //운동에 대한 상세정보를 보러 갈 수 있다.
        actionlist1serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toactionserch2 = new Intent (Actionlist1.this, Actionserch2.class);
                startActivity(toactionserch2);

            }
        });






    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("testcheck", "onresume");
        count1=0;
        count2=0;
        count3=0;
        count4=0;



        Log.d("testcheck","자동");

        checkitemsname =new ArrayList<>();
        thischeckbox=new ArrayList<>();
        checkitemsname1= new ArrayList<>();
        checkitemsname2=new ArrayList<>();
        checkitemsname3=new ArrayList<>();
        checkitemsname4=new ArrayList<>();
        //상세 저장 페이지 에서 운동을 추가 하려고 할때 받는 데이터
        plusitems = new ArrayList<>();
        //체크가 유지되도록 한다.
        plusitems = (ArrayList<String>) getIntent().getSerializableExtra("plusitemname");


        thischeckbox = plusitems;

        plusitems2= new ArrayList<>();
        plusitems2 = (ArrayList<String>) getIntent().getSerializableExtra("plusitemname");

        plusitemsset = new ArrayList<>();
        plusitemsset2 = new ArrayList<>();

        //다시 돌아갈 때 세트가 유지되도록 한다.
        plusitemsset = (ArrayList<String>) getIntent().getSerializableExtra("plusitemset");

        plusitemshashset=new HashMap<String,String>();
   //     plusitemshashset = (HashMap<String, String>) getIntent().getSerializableExtra("plusitemset");
        if(plusitems2!=null&&plusitemsset!=null) {
            for (int i = 0; i < plusitems2.size(); i++) {
                plusitemshashset.put(plusitems2.get(i), plusitemsset.get(i));
            }
        }
        actioncategory = getIntent().getStringExtra("actioncategory")+"운동";
      //  backactionname=getIntent().getStringExtra("backactionlistname");

        text = (TextView)findViewById(R.id.actioncategorytext);
        if(backactionname==null) {
            //    text.setText(actioncategory);
        }else{
            //   text.setText(backactionname);
        }


        actionlist1backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        spinner =(Spinner)findViewById(R.id.spinner3);
        //운동 분류, 스피너로 보여짐
        list = new String[]{"어깨운동", "다리운동", "코어운동", "가슴운동"};

        spinneradapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);

        spinner.setAdapter(spinneradapter);

        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("spinner", "실행");
                selectaction = spinner.getSelectedItem().toString();


                if(selectaction.equals("어깨운동")){
                    count1++;
                    itemsname = new ArrayList<>(Arrays.asList("프론트 레터럴 레이즈", "사이드 레터럴 레이즈", "벤트오버 레터럴 레이즈", "숄더프레스", "시티드 프론트 덤벨 레이즈", "시티드 벤트오버 레이즈"));
                    itemspart = new ArrayList<>(Arrays.asList("전면 삼각근", "측면 삼각근", "후면 삼각근", "전면 삼각근", "전면 삼각근" , "후면 삼각근"));

                }else if(selectaction.equals("다리운동")){
                    count2++;
                    itemsname = new ArrayList<>(Arrays.asList("스쿼트", "레그 익스텐션", "레그 컬", "레그 프레스", "런지" ,"덤벨 런지"));
                    itemspart = new ArrayList<>(Arrays.asList("대퇴 이두근, 대퇴 사두근, 둔근 ", "대퇴 이두근", "대퇴 사두근","대퇴 이두근", "대퇴 이두근, 대퇴 사두근, 장요근" ,"대퇴 이두근, 대퇴 사두근, 둔근 "));

                }else if(selectaction.equals("코어운동")){
                    count3++;
                    itemsname = new ArrayList<>(Arrays.asList("플랭크", "바이시클 크런치", "디클라인 크런치", "슈퍼맨 푸쉬업", "사이드 플랭크", "덤벨 사이드 밴드"));
                    itemspart = new ArrayList<>(Arrays.asList("복근, 대퇴사두근", "하복근, 대퇴근", "상복근, 기립근", "대흉근, 복근", "측복근, 기립근" , "측복근"));

                }else if(selectaction.equals("가슴운동")){
                    count4++;
                    itemsname = new ArrayList<>(Arrays.asList("벤치 프레스", "딥스", "인클라인 벤치 프레스", "디클라인 벤치 프레스", "다이아몬드 푸쉬업" , "플라이"));
                    itemspart = new ArrayList<>(Arrays.asList("대흉근", "대흉근 하부, 삼두", "대흉근 상부", "대흉근 하부", "대흉근, 삼두" , "대흉근"));

                }


                //리사이클러 뷰 생성
                if(checkitemsname1!=null&&selectaction.equals("어깨운동")){
                    if(count1>1){
                      thischeckbox=checkitemsname1;
                    }else {
                        thischeckbox=plusitems;
                    }
                }else if(checkitemsname2!=null&&selectaction.equals("다리운동")){
                    if(count2>1){
                      thischeckbox=checkitemsname2;
                    }else {
                        thischeckbox=plusitems;
                    }
                }else if(checkitemsname3!=null&&selectaction.equals("코어운동")){
                    if(count3>1){
                      thischeckbox=checkitemsname3;
                    }else {
                        thischeckbox=plusitems;
                    }
                }else if(checkitemsname4!=null&&selectaction.equals("유산소운동")){
                    if(count4>1){
                      thischeckbox=checkitemsname4;
                    }else {
                        thischeckbox=plusitems;
                    }
                }

                Log.d("testcheck" , String.valueOf(thischeckbox));
               recyclerView = findViewById(R.id.actionlist1recycle);
                Log.d("리사이클" , "recycler 뷰 생성");

                adapter = new RecyclerViewAdapter_actionlist1(Actionlist1.this, itemsname, itemspart, thischeckbox , R.layout.row_actionlist1recycle );

                Log.d("리사이클" , "어댑터 적용");
                recyclerView.setAdapter(adapter);

                layoutManager = new LinearLayoutManager(Actionlist1.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setItemAnimator(new DefaultItemAnimator());

             //   spinner.setSelection(0);
                if(selectaction.equals("어깨운동")) {

                        checkitemsname1 = ((RecyclerViewAdapter_actionlist1) adapter).getCheckitemsname();

                }else if(selectaction.equals("다리운동")){

                        checkitemsname2 = ((RecyclerViewAdapter_actionlist1) adapter).getCheckitemsname();

                }else if(selectaction.equals("코어운동")){


                        checkitemsname3 = ((RecyclerViewAdapter_actionlist1) adapter).getCheckitemsname();

                }else if(selectaction.equals("가슴운동")){

                        checkitemsname4 = ((RecyclerViewAdapter_actionlist1) adapter).getCheckitemsname();

                }
                Log.d("countxxx", String.valueOf(count1));
                Log.d("countxxx", String.valueOf(count2));
                Log.d("countxxx", String.valueOf(count3));
                Log.d("countxxx", String.valueOf(count4));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        plusitemname=getIntent().getStringExtra("plusitemlistname");
        plusresttime=getIntent().getStringExtra("plusresttime");


        //상세저장 페이지로 이동
        actionlist1savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ArrayList<String> checkitemsname = ((RecyclerViewAdapter_actionlist1)adapter).getCheckitemsname();
               if(checkitemsname1.size()!=0) {
                   checkitemsname.addAll(checkitemsname1);
                   Log.d("testcheck" , String.valueOf(checkitemsname1));
               }
               if(checkitemsname2.size()!=0) {
                   checkitemsname.addAll(checkitemsname2);
                   Log.d("testcheck" , String.valueOf(checkitemsname2));
               }
               if(checkitemsname3.size()!=0) {
                   checkitemsname.addAll(checkitemsname3);
                   Log.d("testcheck" , String.valueOf(checkitemsname3));
               }
               if(checkitemsname4.size()!=0) {
                   checkitemsname.addAll(checkitemsname4);
                   Log.d("testcheck" , String.valueOf(checkitemsname4));
               }

               if(plusitems!=null) {
                   if (count1 >= 1) {
                       itemsname = new ArrayList<>(Arrays.asList("프론트 레터럴 레이즈", "사이드 레터럴 레이즈", "벤트오버 레터럴 레이즈", "숄더프레스", "시티드 프론트 덤벨 레이즈", "시티드 벤트오버 레이즈"));

                       for(int i=0; i<itemsname.size() ;i++){
                           if(plusitems.contains(itemsname.get(i))){
                               plusitems.remove(itemsname.get(i));
                           }

                       }

                   }
                   if (count2 >= 1) {
                       itemsname = new ArrayList<>(Arrays.asList("스쿼트", "레그 익스텐션", "레그 컬", "레그 프레스", "런지" ,"덤벨 런지"));
                       for(int i=0; i<itemsname.size() ;i++){
                           if(plusitems.contains(itemsname.get(i))){
                               plusitems.remove(itemsname.get(i));
                           }

                       }
                   }
                   if (count3 >= 1) {
                       itemsname = new ArrayList<>(Arrays.asList("플랭크", "바이시클 크런치", "디클라인 크런치", "슈퍼맨 푸쉬업", "사이드 플랭크", "덤벨 사이드 밴드"));
                       for(int i=0; i<itemsname.size() ;i++){
                           if(plusitems.contains(itemsname.get(i))){
                               plusitems.remove(itemsname.get(i));
                           }

                       }
                   }
                   if (count4 >= 1) {
                       itemsname = new ArrayList<>(Arrays.asList("벤치 프레스", "딥스", "인클라인 벤치 프레스", "디클라인 벤치 프레스", "다이아몬드 푸쉬업" , "플라이"));
                       for(int i=0; i<itemsname.size() ;i++){
                           if(plusitems.contains(itemsname.get(i))){
                               plusitems.remove(itemsname.get(i));
                           }

                       }
                   }
                   if(plusitems.size()!=0){
                       checkitemsname.addAll(plusitems);
                   }

                   for(int i=0; i<checkitemsname.size();i++){
                       if(plusitemshashset.containsKey(checkitemsname.get(i))){
                           plusitemsset2.add(plusitemshashset.get(checkitemsname.get(i)));
                       }
                   }
                   if(plusitemsset2.size()<checkitemsname.size()){
                       for(int i=0; i<checkitemsname.size()-plusitemsset2.size();i++){
                           plusitemsset2.add("");
                       }
                   }
               }


               //해쉬셋에서 checkitemsname을 골라서 plusitemsset배열에 담는다.




                Log.d("checktitem", String.valueOf(checkitemsname));
                if(checkitemsname.size()!=0) {
                    Intent toactionserch3 = new Intent(Actionlist1.this, Actionserch3.class);
                    toactionserch3.putExtra("checkitemsname", checkitemsname);
                    toactionserch3.putExtra("plusitemsset", plusitemsset2);
                   // toactionserch3.putExtra("category", text.getText().toString());
                    toactionserch3.putExtra("plusitemname", plusitemname);
                    toactionserch3.putExtra("plusresttime", plusresttime);
                    startActivity(toactionserch3);
                }else{
                    Toast.makeText(Actionlist1.this,"운동을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onNewIntent(Intent toactionlist1intent){
        super.onNewIntent(toactionlist1intent);
        setIntent(toactionlist1intent);


    }
}
