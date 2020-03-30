package com.example.me.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Passwordmodify extends AppCompatActivity {

    String password;
    String passwordcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordmodify);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        EditText modifypasswordedittext = findViewById(R.id.modifypasswordedittext);
        EditText modifypasswordcheckedittext = findViewById(R.id.modifypasswordcheckedittext);

        Button passwordmodifycancelbutton =findViewById(R.id.passwordmodifycancelbutton);
        Button passwordmodifyfinishbutton =findViewById(R.id.passwordmodifyfinishbutton);

        passwordmodifycancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        passwordmodifyfinishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = modifypasswordedittext.getText().toString();
                passwordcheck = modifypasswordcheckedittext.getText().toString();

                if(password.equals(passwordcheck)){


                    user.updatePassword(password)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Passwordmodify.this, "바밀번호가 변경되었습니다", Toast.LENGTH_LONG).show();
                                    }finish();
                                }
                            });


                }else{
                    Toast.makeText(Passwordmodify.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
