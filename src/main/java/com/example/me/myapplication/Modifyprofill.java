package com.example.me.myapplication;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Modifyprofill extends AppCompatActivity {

    private static int PICK_IMAGE_REQUEST = 1;
    ImageView pictureview2;
    Bitmap profillimage2;

    SharedPreferences loginshare;
    String nickname2;
    SharedPreferences sf;
    Uri uri;
    EditText heightedittext2;
    EditText weightedittext2;
    FirebaseUser currentUser2;
    FirebaseAuth firebaseAuth2;
    String height2;
    String weight2;
    Button register2cancelbutton2;
    Button registerendbutton2;
    EditText nicknameedittext2;
    String nickname;
    String height;
    String weight;
    String path = "~";
    ArrayList<String> documentidlistlist;
    FirebaseFirestore db;
    String nowuid;
    Bitmap myBitmap;
    FirebaseStorage storage;
    ImageView pictureview3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyprofill);



        pictureview2=findViewById(R.id.pictureview2);

        heightedittext2=findViewById(R.id.heightedittext2);

        weightedittext2=findViewById(R.id.weightedittext2);

        nicknameedittext2=findViewById(R.id.nicknameedittext2);

        pictureview3=findViewById(R.id.pictureView3);

        register2cancelbutton2=findViewById(R.id.register2cancelbutton2);

        registerendbutton2=findViewById(R.id.registerendbutton2);


        documentidlistlist = new ArrayList<String>();

        currentUser2 = FirebaseAuth.getInstance().getCurrentUser();
        nowuid=currentUser2.getUid();

        sf = getSharedPreferences(currentUser2.getEmail(), MODE_PRIVATE);


        String text = sf.getString("프로필", "");
        String[] profillarray = text.split("!&%!");
        if (profillarray.length != 1) {
            nicknameedittext2.setText(profillarray[0]);

        }

        if(!profillarray[1].equals("~")){
            heightedittext2.setText(profillarray[1]+" cm");
        }else{

        }

        if(!profillarray[2].equals("~")){
            weightedittext2.setText(profillarray[2]+" kg");
        }else{

        }

        if (!profillarray[3].equals("~")) {

            path = profillarray[3];
            Log.d("imimimimpath", text);
            myBitmap = BitmapFactory.decodeFile(path);
            int mDegree =90;
            myBitmap = rotateImage(myBitmap, mDegree);

            int nh = (int) (myBitmap.getHeight() * (1024.0 / myBitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 1024, nh, true);
            BitmapDrawable ob = new BitmapDrawable(scaled);
            if (Build.VERSION.SDK_INT > 16) {
                pictureview2.setBackground(ob);

            } else {
                pictureview2.setImageBitmap(scaled);

            }
        }else{

        }

        register2cancelbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        db = FirebaseFirestore.getInstance();

        db.collection("noticewrite")
                .whereEqualTo("useruid", nowuid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //   Log.d("documenttest", document.getId() + " => " + document.getData());
                                documentidlistlist.add(String.valueOf(document.getId()));
                            }

                        } else {
                            Log.d("documenttest", "fail");
                        }
                    }
                });




    }

    @Override
    protected void onResume() {
        super.onResume();

        registerendbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                nickname = nicknameedittext2.getText().toString();
                height =heightedittext2.getText().toString();
                weight =weightedittext2.getText().toString();
                if(height.getBytes().length <= 0){
                    height="~";
                }
                if(weight.getBytes().length <= 0){
                    weight="~";
                }

                if(nickname.getBytes().length <= 0) {
                    Toast.makeText(Modifyprofill.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();

                }else{

                    loginshare = getSharedPreferences(currentUser2.getEmail(), MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginshare.edit();

                    for(int i=0;i<documentidlistlist.size();i++){

                        db.collection("noticewrite").document(documentidlistlist.get(i))
                                .update("nickname", nickname)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }



                    String profillshared = nickname + "!&%!" + height + "!&%!" + weight + "!&%!" + path;

                    Log.d("testtodaylist",profillshared);
                    if(!path.equals("~")){
                        Bitmap savemyBitmap = BitmapFactory.decodeFile(path);
                        storage = FirebaseStorage.getInstance("gs://myapplication-16620.appspot.com");
                        StorageReference storageRef = storage.getReference();
                        StorageReference profillRef = storageRef.child(currentUser2.getUid());
                        Log.d("uidtest",currentUser2.getUid());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        savemyBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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

                    //셰어드에 프로필 등록
                    editor.putString("프로필", profillshared);
                    editor.commit();
                    // Toast.makeText(Register2.this, currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                    Intent tologinintent = new Intent();
                    setResult(RESULT_OK, tologinintent);
                    finish();

                }
            }
        });
    }







    public void loadImagefromGallery2(View view) {
        //Intent 생성
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
        intent.setType("image/*"); //이미지만 보이게
        //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //이미지 선택작업을 후의 결과 처리
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                uri = data.getData();

                try {
                    path = getPath(this, uri);
                }catch(Exception e){

                }

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                int mDegree =90;
                bitmap = rotateImage(bitmap, mDegree);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                pictureview2.setVisibility(View.INVISIBLE);
                pictureview3.setVisibility(View.VISIBLE);

                pictureview3.setImageBitmap(scaled);

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (InterruptedIOException e) {
            Toast.makeText(this, "이미지 로딩 실패, 다시 시도해주세요", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public static String getPath(final Context context, final Uri uri) {
        if (Build.VERSION.SDK_INT > 19) {
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } // TODO handle non-primary volumes } //
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                } // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            } // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            } // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        }
        return null;
    }

    /** * Get the value of the data column for this Uri. This is useful for * MediaStore Uris, and other file-based ContentProviders.
     *  * * @param context The context. * @param uri The Uri to query. * @param selection (Optional) Filter used in the query. *
     *  @param selectionArgs (Optional) Selection arguments used in the query. * @return The value of the _data column, which is typically a file path.
     *  */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }
    /** * @param uri The Uri to check. * @return Whether the Uri authority is ExternalStorageProvider. */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    /** * @param uri The Uri to check. * @return Whether the Uri authority is DownloadsProvider.
     *  */ public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    /** * @param uri The Uri to check. * @return Whether the Uri authority is MediaProvider. */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted and now can proceed
                    //a sample method called

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Modifyprofill.this, "승인이 거부되었습니다.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // add other cases for more permissions
        }
    }
    public Bitmap rotateImage(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }
}
