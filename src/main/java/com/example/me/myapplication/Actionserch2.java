package com.example.me.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

//검색 페이지 검색어로 운동을 검색할 수 있어야 한다 // 개발 중
public class Actionserch2 extends AppCompatActivity {
    String text;
    TextView recomandserch1;
    TextView recomandserch2;
    TextView recomandserch3;
    TextView recomandserch4;
    TextView recomandserch5;
    EditText serchedittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionserch2);

        recomandserch1=(TextView)findViewById(R.id.recomandserch1);
        recomandserch2=(TextView)findViewById(R.id.recomandserch2);
        recomandserch3=(TextView)findViewById(R.id.recomandserch3);
        recomandserch4=(TextView)findViewById(R.id.recomandserch4);
        recomandserch5=(TextView)findViewById(R.id.recomandserch5);

        recomandserch1.setText("팔 운동");
        recomandserch2.setText("복근 운동");
        recomandserch3.setText("다리 운동");
        recomandserch4.setText("코어 운동");
        recomandserch5.setText("등 운동");



        serchedittext = (EditText)findViewById(R.id.serchedittext);

        ImageButton actionserch2backbutton = (ImageButton)findViewById(R.id.actionserch2backbutton);

        ImageButton serchButton = (ImageButton)findViewById(R.id.serchButton);

        actionserch2backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        serchedittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view,int KeyCode , KeyEvent keyEvent) {

                if(KeyCode == KeyEvent.KEYCODE_ENTER){

                    text = serchedittext.getText().toString();
                    Intent intent = new Intent(Actionserch2.this, Actionlist1.class);
                    intent.putExtra("actioncategory", "어깨");

                    startActivity(intent);

                    return true;

                }

                return false;
            }
        });

        serchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               text = serchedittext.getText().toString();
                Intent intent = new Intent(Actionserch2.this, Actionlist1.class);
                intent.putExtra("actioncategory", "어깨");

                startActivity(intent);
            }
        });



    }



    @Override
    protected void onRestart(){
        super.onRestart();

        serchedittext.setText(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
