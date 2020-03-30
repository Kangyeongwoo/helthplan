package com.example.me.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Noticeregister1 extends AppCompatActivity {
    ArrayList<String> createItemsetList;
    ArrayList<String> createItemList;
    ArrayList<String> createsubItemList;
    HashMap<String, ArrayList<String>> hashmapactionname;
    HashMap<String, ArrayList<String>> hashmapactionset;

    ImageButton noticeregisterbackbutton;
    Button noticeregisterbutton;
    SharedPreferences sf;
    RecyclerViewAdapter_noticeregister adapter;

    String nickname;
    String title;
    String content;
    String useruid;

    EditText titletext;
    EditText contenttext;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    ArrayList<String> sendactiontitle;
    HashMap<String, ArrayList<String>> sendhashmapactionname;
    HashMap<String, ArrayList<String>> sendhashmapactionset;
    HashMap<String, String> sendresttime;

    String path;
    Bitmap myBitmap;
    String bitmaptostring;

    HashMap<String,String> resttimelist;

    Noticeboard aNoticeboard=(Noticeboard)Noticeboard.NoticeboardActivity;

    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeregister1);

        storage = FirebaseStorage.getInstance("gs://myapplication-16620.appspot.com");
        StorageReference storageRef = storage.getReference();

        titletext=findViewById(R.id.edittitle);
        contenttext=findViewById(R.id.editcontent);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        sendactiontitle=new ArrayList<>();
        sendhashmapactionname=new HashMap<String, ArrayList<String>>();
        sendhashmapactionset=new HashMap<String, ArrayList<String>>();
        sendresttime=new HashMap<String, String>();


        noticeregisterbutton =findViewById(R.id.noticeregisterbutton);

        noticeregisterbackbutton=findViewById(R.id.noticeregisterbackbutton);
        noticeregisterbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        sf = getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getEmail(), MODE_PRIVATE);

        String sharedlist = sf.getString("actionlist", "");
        Log.d("save",sharedlist);

        String text = sf.getString("프로필", "");
        Log.d("프로필",text);

        final String[] profillarray = text.split("!&%!");


        path = profillarray[3];
        Log.d("imimimimpath", text);
        if(!path.equals("~")) {
            myBitmap = BitmapFactory.decodeFile(path);
        }else{

        }

        bitmaptostring="~";
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

        resttimelist= new HashMap<String,String>();


        if (sharedlist != "") {
            //운동 분류 별로 나누기

            String[] sharedaction = sharedlist.split("!@!");
            //   Log.d("save",sharedaction[0]);

            //운동 분류를 운동이름, 소운동 , 소운동 세트, 리사이클러뷰 포지션 으로 나누기
            for (int j = 0; j < sharedaction.length; j++) {
                String[] sharedsubdetail = sharedaction[j].split("]\\[");
                createItemList.add(sharedsubdetail[0]);
                resttimelist.put(sharedsubdetail[0],sharedsubdetail[3]);
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
        if(createItemList != null && createsubItemList!= null) {

            RecyclerView recyclerView = findViewById(R.id.noticelistrecycle);
            Log.d("리사이클", "recycler 뷰 생성");

            adapter = new RecyclerViewAdapter_noticeregister(this, createItemList, hashmapactionname,hashmapactionset,resttimelist, R.layout.row_noticeregisterrecycle1);

            Log.d("리사이클", "어댑터 적용");
            recyclerView.setAdapter(adapter);


            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());


        }
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        noticeregisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useruid= currentUser.getUid();
                nickname = profillarray[0];
                title= titletext.getText().toString();
                content = contenttext.getText().toString();
                sendactiontitle = adapter.getsendtitle();
                sendhashmapactionname = adapter.getsendhashmapactionname();
                sendhashmapactionset=adapter.getsendhashmapactionset();
                sendresttime=adapter.getsendresttimelist();


                if(title.length()!=0){
                if(content.length()!=0) {
                    HashMap<String, Object> noticewrite = new HashMap<>();
                    noticewrite.put("useruid", useruid);
                    noticewrite.put("nickname", nickname);
                    noticewrite.put("userimage", bitmaptostring);
                    noticewrite.put("title", title);
                    noticewrite.put("content", content);
                    noticewrite.put("sendactiontitle", sendactiontitle);
                    noticewrite.put("sendhashmapactionname", sendhashmapactionname);
                    noticewrite.put("sendhashmapactionset", sendhashmapactionset);
                    noticewrite.put("resttimelist", sendresttime);


                    db.collection("noticewrite")
                            .add(noticewrite)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    if(myBitmap!=null){
                                        StorageReference profillRef = storageRef.child(useruid);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                        byte[] data = baos.toByteArray();

                                        UploadTask uploadTask = profillRef.putBytes(data);
                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle unsuccessful uploads
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                                // ...
                                            }
                                        });
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                    aNoticeboard.finish();

                    Intent tonoticeboard = new Intent(Noticeregister1.this, Noticeboard.class);
                    startActivity(tonoticeboard);
                    Noticeregister1.this.finish();
                }else{
                    Toast.makeText(Noticeregister1.this,"내용을 입력해 주십시오",Toast.LENGTH_SHORT).show();
                }
                }else{
                    Toast.makeText(Noticeregister1.this,"제목을 입력해 주십시오",Toast.LENGTH_SHORT).show();

                }

                }

        });


    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
