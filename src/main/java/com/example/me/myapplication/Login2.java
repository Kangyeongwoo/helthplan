package com.example.me.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//로그인 액티비티 아이디, 비밀번호가 파이어베이스에 저장되어있다.

public class Login2 extends AppCompatActivity {
    final int INTENT_REQUEST_CODE=1;
    String hardemail;
    String hardpassword;
    Bitmap profill;
    Button loginButton;
    EditText emailedittext;
    EditText passwordedittext;
    Button registerbutton;
    Intent intent;
    String nickname;
    String Sharedtext;
    String[] Sharedarray;
    FirebaseAuth firebaseAuth;
    CheckBox autologincheckbox;
    SharedPreferences sf;
    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        //임시로그인 버튼


        autologincheckbox =findViewById(R.id.autologincheckbox);
        registerbutton = (Button)findViewById(R.id.registerbutton);

        TextView healthtitle =findViewById(R.id.helthtitle);
        loginButton = (Button)findViewById(R.id.loginbutton);

        emailedittext = (EditText)findViewById(R.id.emailedittext);
        passwordedittext = (EditText)findViewById(R.id.passwordedittext);
        Button findpasswordbutton =findViewById(R.id.findpasswordbutton);

        firebaseAuth = firebaseAuth.getInstance();

        sf = getSharedPreferences("autologin", MODE_PRIVATE);
        hardemail= sf.getString("email","");
        hardpassword =sf.getString("password","");


        ImageButton lodingbutton = findViewById(R.id.lodingbutton);


        if(!hardemail.equals("")&&!hardpassword.equals("")){
            email =hardemail;
            password=hardpassword;

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login2.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login2.this, MainActivity.class);
                                startActivity(intent);
                                Login2.this.finish();
                            } else {
                                Toast.makeText(Login2.this, "이메일과 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                                healthtitle.setVisibility(View.GONE);
                                lodingbutton.setVisibility(View.GONE);
                                registerbutton.setVisibility(View.VISIBLE);
                                findpasswordbutton.setVisibility(View.VISIBLE);
                            }
                        }
                    });



        }else{
            healthtitle.setVisibility(View.GONE);
            lodingbutton.setVisibility(View.GONE);
            registerbutton.setVisibility(View.VISIBLE);
            findpasswordbutton.setVisibility(View.VISIBLE);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailedittext.getText().toString();
                password=passwordedittext.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    if(autologincheckbox.isChecked()) {

                                        SharedPreferences.Editor editor = sf.edit();
                                        editor.putString("email", email);
                                        editor.putString("password", password);
                                        editor.commit();
                                    }

                                    Intent intent = new Intent(Login2.this, MainActivity.class);
                                    startActivity(intent);
                                    Login2.this.finish();
                                } else {
                                    Toast.makeText(Login2.this, "이메일과 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }


        });



        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent toregisterintent = new Intent(Login2.this, Register.class);

                startActivityForResult(toregisterintent, INTENT_REQUEST_CODE);

            }
        });

    }

    //1페이지가 열려있기에 온리쥼에서 작동
    @Override
    protected void onResume(){
        super.onResume();


        //로그인 파이어베이스 이용



    }







}
