package com.example.me.myapplication;

import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Actionhistory extends AppCompatActivity {
    private Calendar mCal;

    private ArrayList<String> dayList;
    TextView monthtext;

    SharedPreferences sf;

    String actionhistory;
    String todaylist;

    ImageButton actionhistorybackbutton;

    ImageButton calendarbackbutton;
    ImageButton calendarfowardbutton;

    int thisyearint;
    int thismonthint;

    String[] actionhistoryarray2;
    String[] actionhistoryarray;

    String[] todaydarry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionhistory);

        calendarbackbutton =findViewById(R.id.calendarback);
        calendarfowardbutton=findViewById(R.id.calendarfoward);

        actionhistorybackbutton=findViewById(R.id.actionhistorybackbutton);
        actionhistorybackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        sf = getSharedPreferences(currentFirebaseUser.getEmail(),MODE_PRIVATE);

        actionhistory =sf.getString("actionhistiory","");
        todaylist = sf.getString("today","");

        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date time2 = new Date();
        String time3 = format2.format(time2);
        todaydarry = time3.split("-");

        if(todaydarry[2].equals("01")){
            todaydarry[2]="1";
        }else if(todaydarry[2].equals("02")){
            todaydarry[2]="2";
        }else if(todaydarry[2].equals("03")){
            todaydarry[2]="3";
        }else if(todaydarry[2].equals("04")){
            todaydarry[2]="4";
        }else if(todaydarry[2].equals("05")){
            todaydarry[2]="5";
        }else if(todaydarry[2].equals("06")){
            todaydarry[2]="6";
        }else if(todaydarry[2].equals("07")){
            todaydarry[2]="7";
        }else if(todaydarry[2].equals("08")){
            todaydarry[2]="8";
        }else if(todaydarry[2].equals("09")){
            todaydarry[2]="9";
        }else{

        }



        Log.d("serchday", actionhistory);
        actionhistoryarray2 = actionhistory.split("!@!");
        Log.d("serchday", String.valueOf(actionhistoryarray2.length));
        actionhistoryarray = new String[actionhistoryarray2.length];

        for(int i=0; i<actionhistoryarray2.length-1;i++){
            actionhistoryarray[i]=actionhistoryarray2[i+1];
        }
        actionhistoryarray[actionhistoryarray2.length-1] = todaylist;

        Log.d("serchday", String.valueOf(actionhistoryarray.length));

        monthtext=findViewById(R.id.monthtext);


        long now = System.currentTimeMillis();

        final Date date = new Date(now);

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);

        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);


        String thisyearsrc =curYearFormat.format(date);
        String thismonthsrc =curMonthFormat.format(date);


        thisyearint = Integer.parseInt(thisyearsrc);

        thismonthint = Integer.parseInt(thismonthsrc) - 1;


        dayList = new ArrayList<String>();


        mCal = Calendar.getInstance();

        mCal.set(thisyearint,  thismonthint, 1);

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        //1일 - 요일 매칭 시키기 위해 공백 add

        for (int i = 1; i < dayNum; i++) {

            dayList.add("");

        }

        String thisyear = String.valueOf(thisyearint);

        String thismonth = null;
        if(thismonthint+1 == 1){
            thismonth="01";
        }else if(thismonthint+1 == 2){
            thismonth="02";
        }else if(thismonthint+1 == 3){
            thismonth="03";
        }else if(thismonthint+1 == 4){
            thismonth="04";
        }else if(thismonthint+1 == 5){
            thismonth="05";
        }else if(thismonthint+1 == 6){
            thismonth="06";
        }else if(thismonthint+1 == 7){
            thismonth="07";
        }else if(thismonthint+1 == 8){
            thismonth="08";
        }else if(thismonthint+1 == 9){
            thismonth="09";
        }else{
            thismonth=String.valueOf(thismonthint+1);
        }



        Log.d("dateserch" , thisyear);
        Log.d("dateserch" , thismonth);

        monthtext.setText(thisyear+"년 "+thismonth+"월");

        setCalendarDate(mCal.get(Calendar.MONTH) + 1);
        Log.d("리사이클", "recycler 뷰 생성");

        RecyclerView recyclerView = findViewById(R.id.calendar);
        Log.d("리사이클", "recycler 뷰 생성");

        RecyclerViewAdapter_actionhistory adapter = new RecyclerViewAdapter_actionhistory(this, dayList,thisyear,thismonth,actionhistoryarray,todaydarry, R.layout.row_calendarrecycle);

        Log.d("리사이클", "어댑터 적용");
        recyclerView.setAdapter(adapter);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());



        Log.d("dayList",String.valueOf(dayList));



    }
    @Override
    protected void onResume() {

        super.onResume();

        calendarbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                thismonthint = thismonthint-1;
                if(thismonthint<0){
                    thisyearint=thisyearint-1;
                    thismonthint=11;
                }

                dayList = new ArrayList<String>();


                mCal = Calendar.getInstance();

                mCal.set(thisyearint,  thismonthint, 1);

                int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

                //1일 - 요일 매칭 시키기 위해 공백 add

                for (int i = 1; i < dayNum; i++) {

                    dayList.add("");

                }

                String thisyear = String.valueOf(thisyearint);

                String thismonth = null;
                if(thismonthint+1 == 1){
                    thismonth="01";
                }else if(thismonthint+1 == 2){
                    thismonth="02";
                }else if(thismonthint+1 == 3){
                    thismonth="03";
                }else if(thismonthint+1 == 4){
                    thismonth="04";
                }else if(thismonthint+1 == 5){
                    thismonth="05";
                }else if(thismonthint+1 == 6){
                    thismonth="06";
                }else if(thismonthint+1 == 7){
                    thismonth="07";
                }else if(thismonthint+1 == 8){
                    thismonth="08";
                }else if(thismonthint+1 == 9){
                    thismonth="09";
                }else{
                    thismonth=String.valueOf(thismonthint+1);
                }



                Log.d("dateserch" , thisyear);
                Log.d("dateserch" , thismonth);

                monthtext.setText(thisyear+"년 "+thismonth+"월");

                setCalendarDate(mCal.get(Calendar.MONTH) + 1);
                Log.d("리사이클", "recycler 뷰 생성");

                RecyclerView recyclerView = findViewById(R.id.calendar);
                Log.d("리사이클", "recycler 뷰 생성");

                RecyclerViewAdapter_actionhistory adapter = new RecyclerViewAdapter_actionhistory(Actionhistory.this, dayList,thisyear,thismonth,actionhistoryarray,todaydarry, R.layout.row_calendarrecycle);

                Log.d("리사이클", "어댑터 적용");
                recyclerView.setAdapter(adapter);

                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(manager);

                recyclerView.setItemAnimator(new DefaultItemAnimator());



                Log.d("dayList",String.valueOf(dayList));




            }
        });








        calendarfowardbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thismonthint = thismonthint+1;
                if(thismonthint>11){
                    thisyearint=thisyearint+1;
                    thismonthint=0;
                }

                dayList = new ArrayList<String>();


                mCal = Calendar.getInstance();

                mCal.set(thisyearint,  thismonthint, 1);

                int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

                //1일 - 요일 매칭 시키기 위해 공백 add

                for (int i = 1; i < dayNum; i++) {

                    dayList.add("");

                }

                String thisyear = String.valueOf(thisyearint);

                String thismonth = null;
                if(thismonthint+1 == 1){
                    thismonth="01";
                }else if(thismonthint+1 == 2){
                    thismonth="02";
                }else if(thismonthint+1 == 3){
                    thismonth="03";
                }else if(thismonthint+1 == 4){
                    thismonth="04";
                }else if(thismonthint+1 == 5){
                    thismonth="05";
                }else if(thismonthint+1 == 6){
                    thismonth="06";
                }else if(thismonthint+1 == 7){
                    thismonth="07";
                }else if(thismonthint+1 == 8){
                    thismonth="08";
                }else if(thismonthint+1 == 9){
                    thismonth="09";
                }else{
                    thismonth=String.valueOf(thismonthint+1);
                }



                Log.d("dateserch" , thisyear);
                Log.d("dateserch" , thismonth);

                monthtext.setText(thisyear+"년 "+thismonth+"월");

                setCalendarDate(mCal.get(Calendar.MONTH) + 1);
                Log.d("리사이클", "recycler 뷰 생성");

                RecyclerView recyclerView = findViewById(R.id.calendar);
                Log.d("리사이클", "recycler 뷰 생성");

                RecyclerViewAdapter_actionhistory adapter = new RecyclerViewAdapter_actionhistory(Actionhistory.this, dayList,thisyear,thismonth,actionhistoryarray,todaydarry, R.layout.row_calendarrecycle);

                Log.d("리사이클", "어댑터 적용");
                recyclerView.setAdapter(adapter);

                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(manager);

                recyclerView.setItemAnimator(new DefaultItemAnimator());



                Log.d("dayList",String.valueOf(dayList));





            }
        });








    }








    private void setCalendarDate(int month) {

        mCal.set(Calendar.MONTH, month - 1);



        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            dayList.add("" + (i + 1));

        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
