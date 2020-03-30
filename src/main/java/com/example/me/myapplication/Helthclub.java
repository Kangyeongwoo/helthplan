package com.example.me.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

//헬스클럽의 상세정보가 들어있다.
public class Helthclub extends AppCompatActivity {

    TextView helthclubnametextview;
    TextView helthclubadresstextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helthclub);

        helthclubnametextview =findViewById(R.id.helthnametextview);

        helthclubadresstextview=findViewById(R.id.helthadresstextview);

        ImageButton helthclubbackbutton = (ImageButton)findViewById(R.id.helthclubbackbutton);

        String helthclubname = getIntent().getStringExtra("name");
        String helthclubaddress = getIntent().getStringExtra("adress");

        helthclubnametextview.setText(helthclubname);
        helthclubadresstextview.setText(helthclubaddress);


        helthclubbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Button callbutton = (Button)findViewById(R.id.callbutton2);

        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01039218825"));
                try {
                    startActivity(intent);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
