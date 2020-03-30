package com.example.me.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
//회원가입 첫번째 액티비티 이메일과 비밀번호를 입력 받는다.
//이메일은 형식을 지켜야 하며, 비밀번호는 6자리 이상

public class Register extends AppCompatActivity {
    ImageView pictureview;
    Bitmap scaled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //이메일 입력, 비밀번호 입력, 비밀번호 확인 창
        final EditText inputemailedittext =(EditText)findViewById(R.id.inputemailedittext) ;
        final EditText inputpasswordedittext =(EditText)findViewById(R.id.inputpasswordedittext) ;
        final EditText inputpasswordcheckedittext =(EditText)findViewById(R.id.inputpasswordcheckedittext) ;


        final Button toregister2 = (Button)findViewById(R.id.registerfinishbutton);
      //  final EditText nicknameedittext =(EditText)findViewById(R.id.nicknameedittext);


        toregister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String inputemail = inputemailedittext.getText().toString();
                String inputpassword = inputpasswordedittext.getText().toString();
                String inputpasswordcheck=inputpasswordcheckedittext.getText().toString();
            //   String nickname = nicknameedittext.getText().toString();

                //비밀번호와 확인이 같을 때
                if(inputpassword.equals(inputpasswordcheck)) {
                    //이메일 입력, 비밀번호 입력 전달
                    Intent tologinintent = new Intent(Register.this, Register2.class);
                    tologinintent.putExtra("email",inputemail);
                    tologinintent.putExtra("password",inputpassword);
                //    tologinintent.putExtra("nickname",nickname);
                   // tologinintent.putExtra("profill",(Bitmap)scaled);
                    tologinintent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    startActivity(tologinintent);
                    finish();

                }else{
                    Toast.makeText(Register.this, "비밀번호를 확인해 주십시오",Toast.LENGTH_SHORT).show();
                }
            }
        });


        final Button register1cancelbutton = (Button)findViewById(R.id.register1cancelbutton);

        register1cancelbutton.setOnClickListener(new View.OnClickListener() {
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



}
