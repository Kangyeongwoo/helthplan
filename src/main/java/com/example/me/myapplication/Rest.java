package com.example.me.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Rest extends AppCompatActivity {
    MediaPlayer mp;
    TextView timeText;
    Resttimer resttimer;

    Dynamicbutton dynamicbutton;
    Siren siren;
    LinearLayout restlinear;
    Button restbutton;
    ProgressBar pBar;
    Progressbarset progressbarset;
    int time;

    String resttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        resttime = getIntent().getStringExtra("resttime");

        time=Integer.parseInt(resttime);

        timeText = (TextView) findViewById(R.id.timeText);

        ImageButton restback = (ImageButton) findViewById(R.id.restback);
        restbutton = (Button) findViewById(R.id.restbutton);
        int actionset = getIntent().getIntExtra("actionset", 0);

        restlinear = (LinearLayout) findViewById(R.id.restlinear);
        pBar = (ProgressBar) findViewById(R.id.progressBar);

        Handler dynamicbuttonhandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {

                    if (msg.arg1 == 0) {
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                        lp.setMargins(0, 0, 0, 0);
                        restbutton.setLayoutParams(lp);

                    } else if (msg.arg1 == 1) {
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                        lp.setMargins(50, 50, 50, 50);
                        restbutton.setLayoutParams(lp);
                    }
                }
            }


        };

        dynamicbutton = new Dynamicbutton(dynamicbuttonhandler);

        siren = new Siren();

        restbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Handler timerhandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    timeText.setText(String.valueOf(msg.arg1) + " 초");

                    if (msg.arg1 == 0) {
                        timeText.setText("휴식 종료");
                        pBar.setVisibility(View.INVISIBLE);
                        dynamicbutton.start();
                        siren.start();
                    }
                }
            }


        };

        resttimer = new Resttimer(timerhandler,time);

        resttimer.start();

        restback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed();

            }
        });

        Handler progressbarsethandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 2) {
                    pBar.setMax(time);
                    pBar.setProgress(time-msg.arg1);

                }
            }


        };

       progressbarset = new Progressbarset(progressbarsethandler,time);

       progressbarset.start();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("액션스타트", "onDestroy");
        resttimer.interrupt();
        dynamicbutton.interrupt();
        siren.interrupt();
        progressbarset.interrupt();
        Toast.makeText(Rest.this, " Tip. 운동 후에 너무 오래 쉬게 되면 오히려 근육이 더 피로해져요", Toast.LENGTH_LONG).show();


    }

    class Resttimer extends Thread {

        Handler handler;
        int time;

        Resttimer(Handler handler, int time) {
            this.handler = handler;
            this.time= time;
        }


        @Override
        public void run() {


            try {
                for (int i = time; i >= 0; i--) {


                    Message msg = new Message();
                    msg.what = 0;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    Log.d("subthread", String.valueOf(msg.arg1));
                    Thread.sleep(1000);


                }
            } catch (Exception e) {


            }
        }


    }

    class Siren extends Thread {

        @Override
        public void run() {



                mp = MediaPlayer.create(Rest.this, R.raw.vibratingpump);
                mp.setLooping(false);

                while (true) {
                    try {
                    mp.start();

                    Thread.sleep(19000);

                    mp.stop();
                    } catch (Exception e) {

                        mp.stop();
                    }
                }


        }


    }

    class Progressbarset extends Thread{

        Handler handler;
        int time;

        Progressbarset(Handler handler, int time) {
            this.handler = handler;
            this.time= time;
        }


        @Override
        public void run(){
            try {
                for (int i = 0; i <= time; i++) {


                    Message msg = new Message();
                    msg.what = 2;
                    msg.arg1 = i;
                    handler.sendMessage(msg);

                    Thread.sleep(1000);


                }
            } catch (Exception e) {


            }
        }
        }




    class Dynamicbutton extends Thread {

        Handler handler;

        Dynamicbutton(Handler handler) {
            this.handler = handler;
        }


        @Override
        public void run() {


            try {
                int i = 1;
                //1일때 작아짐
                //0일때 커짐
                while (true) {


                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    Log.d("subthread", String.valueOf(msg.arg1));
                    Thread.sleep(500);

                    if (i == 0) {
                        i = 1;
                    } else if (i == 1) {
                        i = 0;
                    }
                }

            } catch (Exception e) {


            }
        }


    }

}