package com.example.me.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter_noticeboard extends RecyclerView.Adapter<RecyclerViewAdapter_noticeboard.ViewHolder>{


    ArrayList<String> documentidlistlist;
     Context context;
    private int itemLayout;
    ArrayList<String> useruidlist;
    ArrayList<String> usernicknamelist;
    ArrayList<String> userimagelist;
    ArrayList<String> titlelist;
    ArrayList<String> contentlist;
    ArrayList<ArrayList<String>> sendactiontitlelist;
    ArrayList<HashMap<String,ArrayList<String>>> sendhashmapactionnamelist;
    ArrayList<HashMap<String,ArrayList<String>>> sendhashmapactionsetlist;
    ArrayList<HashMap<String,String>> resttimearraylist;
    String nowuid;
    ImageButton refreashbutton;

    RecyclerViewAdapter_noticeboard(Context context,
                                    ArrayList<String> documentidlistlist,
                                    ArrayList<String> useruidlist,
                                    ArrayList<String> usernicknamelist,
                                    ArrayList<String> userimagelist,
                                    ArrayList<String> titlelist,
                                    ArrayList<String> contentlist,
                                    ArrayList<ArrayList<String>> sendactiontitlelist,
                                    ArrayList<HashMap<String,ArrayList<String>>> sendhashmapactionnamelist,
                                    ArrayList<HashMap<String,ArrayList<String>>> sendhashmapactionsetlist,
                                    ArrayList<HashMap<String,String>> resttimearraylist,
                                    String nowuid,
                                    ImageButton refreashbutton,
                                    int itemLayout)

    {

        this.itemLayout=itemLayout;
        this.context=context;
        this.documentidlistlist = documentidlistlist;
        this.useruidlist = useruidlist;
        this.usernicknamelist = usernicknamelist;
        this.userimagelist = userimagelist;
        this.titlelist = titlelist;
        this.contentlist = contentlist;
        this.sendactiontitlelist = sendactiontitlelist;
        this.sendhashmapactionnamelist = sendhashmapactionnamelist;
        this.sendhashmapactionsetlist = sendhashmapactionsetlist;
        this.resttimearraylist = resttimearraylist;
        this.nowuid=nowuid;
        this.refreashbutton=refreashbutton;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("리사이클" , "뷰홀더 생성");
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return new RecyclerViewAdapter_noticeboard.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.noticemodifybutton.setVisibility(View.INVISIBLE);
        holder.noticedeletebutton.setVisibility(View.INVISIBLE);
        holder.noticedeletebutton.setEnabled(false);
        holder.noticemodifybutton.setEnabled(false);


        holder.noticeusernickname.setText(usernicknamelist.get(holder.getLayoutPosition()));
        holder.titletext.setText(titlelist.get(holder.getLayoutPosition()));
        holder.contenttext.setText(contentlist.get(holder.getLayoutPosition()));
        holder.tagtext.setText(String.valueOf(sendactiontitlelist.get(holder.getLayoutPosition())));

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://myapplication-16620.appspot.com");
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child(useruidlist.get(holder.getLayoutPosition()));

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).rotate(90).into(holder.noticeprofillimage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        Log.d("storagetest", String.valueOf(pathReference));





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("noticewrite").document(documentidlistlist.get(holder.getLayoutPosition()));
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                if(!String.valueOf(document.getData().get("useruid")).equals(useruidlist.get(holder.getLayoutPosition()))||
                                   !String.valueOf(document.getData().get("nickname")).equals(usernicknamelist.get(holder.getLayoutPosition()))||
                                   !String.valueOf(document.getData().get("userimage")).equals(userimagelist.get(holder.getLayoutPosition())) ||
                                   !String.valueOf(document.getData().get("title")).equals(titlelist.get(holder.getLayoutPosition()))   ||
                                   !String.valueOf(document.getData().get("content")).equals(contentlist.get(holder.getLayoutPosition()))){

                                    Toast.makeText(context,"변경된 게시글입니다.",Toast.LENGTH_SHORT).show();
                                    refreashbutton.performClick();
                                } else{
                                    Intent tonoticecheck1 = new Intent(context, Noticercheck1.class);
                                    tonoticecheck1.putExtra("usernickname",usernicknamelist.get(holder.getLayoutPosition()));
                                    tonoticecheck1.putExtra("userimage",userimagelist.get(holder.getLayoutPosition()));
                                    tonoticecheck1.putExtra("title",titlelist.get(holder.getLayoutPosition()));
                                    tonoticecheck1.putExtra("content",contentlist.get(holder.getLayoutPosition()));
                                    tonoticecheck1.putExtra("sendactiontitle",sendactiontitlelist.get(holder.getLayoutPosition()));
                                    tonoticecheck1.putExtra("sendhashmapactionnamelist",sendhashmapactionnamelist.get(holder.getLayoutPosition()));
                                    tonoticecheck1.putExtra("sendhashmapactionsetlist",sendhashmapactionsetlist.get(holder.getLayoutPosition()));
                                    tonoticecheck1.putExtra("resttime",resttimearraylist.get(holder.getLayoutPosition()));
                                    context.startActivity(tonoticecheck1);

                                }

                            } else {
                                Toast.makeText(context,"삭제된 게시글입니다.",Toast.LENGTH_SHORT).show();
                                refreashbutton.performClick();
                            }
                        } else {

                        }
                    }
                });




            }
        });

        if(nowuid.equals(useruidlist.get(holder.getLayoutPosition()))){

            holder.noticemodifybutton.setVisibility(View.VISIBLE);
            holder.noticedeletebutton.setVisibility(View.VISIBLE);
            holder.noticedeletebutton.setEnabled(true);
            holder.noticemodifybutton.setEnabled(true);

        }

        holder.noticemodifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tonoticemodify =new Intent(context,Noticemodify.class);
                tonoticemodify.putExtra("documentid",documentidlistlist.get(holder.getLayoutPosition()));
                tonoticemodify.putExtra("useruid",useruidlist.get(holder.getLayoutPosition()));
                tonoticemodify.putExtra("usernickname",usernicknamelist.get(holder.getLayoutPosition()));
                tonoticemodify.putExtra("userimage",userimagelist.get(holder.getLayoutPosition()));
                tonoticemodify.putExtra("title",titlelist.get(holder.getLayoutPosition()));
                tonoticemodify.putExtra("content",contentlist.get(holder.getLayoutPosition()));
                tonoticemodify.putExtra("sendactiontitle",sendactiontitlelist.get(holder.getLayoutPosition()));
                tonoticemodify.putExtra("sendhashmapactionnamelist",sendhashmapactionnamelist.get(holder.getLayoutPosition()));
                tonoticemodify.putExtra("sendhashmapactionsetlist",sendhashmapactionsetlist.get(holder.getLayoutPosition()));
                tonoticemodify.putExtra("resttime",resttimearraylist.get(holder.getLayoutPosition()));
                context.startActivity(tonoticemodify);

            }
        });


        holder.noticedeletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("운동 목록 삭제");
                builder.setMessage("정말 목록을 삭제하시겠습니까?");
                builder.setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("noticewrite").document(documentidlistlist.get(holder.getLayoutPosition()))
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                documentidlistlist.remove(holder.getLayoutPosition()) ;
                                                useruidlist.remove(holder.getLayoutPosition()) ;
                                                usernicknamelist.remove(holder.getLayoutPosition()) ;
                                                userimagelist.remove(holder.getLayoutPosition()) ;
                                                titlelist.remove(holder.getLayoutPosition()) ;
                                                contentlist.remove(holder.getLayoutPosition()) ;
                                                sendactiontitlelist.remove(holder.getLayoutPosition()) ;
                                                sendhashmapactionnamelist.remove(holder.getLayoutPosition()) ;
                                                sendhashmapactionsetlist.remove(holder.getLayoutPosition()) ;
                                                resttimearraylist.remove(holder.getLayoutPosition()) ;
                                                notifyItemRemoved(holder.getLayoutPosition());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });

                            }
                        });
                builder.setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return documentidlistlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView noticeprofillimage;
        TextView noticeusernickname;
        TextView titletext;
        TextView tagtext;
        TextView contenttext;
        ImageButton noticemodifybutton;
        ImageButton noticedeletebutton;

        public ViewHolder( View itemView) {
            super(itemView);
            noticeprofillimage=itemView.findViewById(R.id.noticeprofillimage);
            noticeusernickname=itemView.findViewById(R.id.noticeusernickname);
            titletext=itemView.findViewById(R.id.titletext);
            tagtext=itemView.findViewById(R.id.tagtext);
            contenttext=itemView.findViewById(R.id.contenttext);
            noticemodifybutton=itemView.findViewById(R.id.noticemodify);
            noticedeletebutton=itemView.findViewById(R.id.noticedelete);
        }
    }
}
