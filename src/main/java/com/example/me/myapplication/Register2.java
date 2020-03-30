package com.example.me.myapplication;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;

//회원가입 완료 닉네임과 프로필 사진 입력 받기
public class Register2 extends AppCompatActivity {


    private static int PICK_IMAGE_REQUEST = 1;
    ImageView pictureview;
    Bitmap profillimage;
    String email;
    String password;
    SharedPreferences loginshare;


    Uri uri;
    EditText heightedittext;
    EditText weightedittext;
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;
    String nickname;
    String height;
    String weight;
    String path = "~";
    Button register2cancelbutton;
    Button registerendbutton;
    EditText nicknameedittext;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        ActivityCompat.requestPermissions(Register2.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);


        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        firebaseAuth = FirebaseAuth.getInstance();

        heightedittext=findViewById(R.id.heightedittext);

        weightedittext =findViewById(R.id.weightedittext);

        nicknameedittext = (EditText) findViewById(R.id.nicknameedittext);

       register2cancelbutton = (Button) findViewById(R.id.register2cancelbutton);
        registerendbutton = (Button) findViewById(R.id.registerendbutton);

        register2cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




    }
    //다른 갤러리에서 이미지 가져오기 암시적인텐트
    public void loadImagefromGallery(View view) {
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
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
              //  int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
              //  Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);
                int mDegree =90;
                pictureview = (ImageView) findViewById(R.id.pictureview2);
                pictureview.setImageBitmap(rotateImage(bitmap, mDegree));



             //   pictureview = (ImageView) findViewById(R.id.pictureview2);
             //   pictureview.setImageBitmap(bitmap);

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
    protected void onResume() {
        super.onResume();

        registerendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nickname = nicknameedittext.getText().toString();
                height =heightedittext.getText().toString();
                weight =weightedittext.getText().toString();
                if(height.getBytes().length <= 0){
                    height="~";
                }
                if(weight.getBytes().length <= 0){
                    weight="~";
                }

                if(nickname.getBytes().length <= 0) {
                    Toast.makeText(Register2.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();

                }else{
                    Bitmap profill = profillimage;

                    //파이어베이스에 등록
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register2.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        currentUser = firebaseAuth.getCurrentUser();

                                        loginshare = getSharedPreferences(currentUser.getEmail(), MODE_PRIVATE);
                                        SharedPreferences.Editor editor = loginshare.edit();


                                        String profillshared = nickname + "!&%!" + height + "!&%!" + weight + "!&%!" + path;

                                        //셰어드에 프로필 등록
                                        editor.putString("프로필", profillshared);
                                        editor.commit();
                                        // Toast.makeText(Register2.this, currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                                        Intent tologinintent = new Intent();
                                        setResult(RESULT_OK, tologinintent);
                                        finish();
                                    } else {
                                        Toast.makeText(Register2.this, "가입 실패, 인터넷연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }




                            });
                }
            }
        });
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
                    Toast.makeText(Register2.this, "승인이 거부되었습니다.", Toast.LENGTH_SHORT).show();
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






