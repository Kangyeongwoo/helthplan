package com.example.me.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RecyclerViewAdapter_Helthlocation extends RecyclerView.Adapter<RecyclerViewAdapter_Helthlocation.ViewHolder> {

    private Context context;
    private ArrayList<String[]> helthclublist;
    private int itemLayout;
    String resultText;
    String phone;

    public RecyclerViewAdapter_Helthlocation(Context context , ArrayList<String[]> helthclublist, int itemLayout ){

        this.context = context;
        this.helthclublist = helthclublist;
        this.itemLayout = itemLayout;


    }



    @NonNull
    @Override
    public RecyclerViewAdapter_Helthlocation.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent,false);
        return new RecyclerViewAdapter_Helthlocation.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {



        holder.placename.setText(helthclublist.get(holder.getLayoutPosition())[0]);

        holder.placeaddress.setText(helthclublist.get(holder.getLayoutPosition())[1]);

        if(helthclublist.get(holder.getLayoutPosition())[5].equals("~")){
            Picasso.with(context).load(R.drawable.imagenull).into(holder.helthclubimage);
        }else{
            Picasso.with(context)
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+helthclublist.get(holder.getLayoutPosition())[5]+"&key=AIzaSyCM_wygaB3RdtdAcdFpDHOTIo_Y5stCI-Q")
                    .into(holder.helthclubimage);
        }


        try {
            resultText=new Task(helthclublist.get(holder.getLayoutPosition())[4]).execute().get();

            try{
                JSONObject jsonObject = new JSONObject(resultText);

                String parseresult = jsonObject.getString("result");

                JSONObject subJsonObject2 = new JSONObject(parseresult);

                String phonenumber = subJsonObject2.getString("formatted_phone_number");

                Log.d("phonephone",phonenumber);

                phone = "tel:"+phonenumber;


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        holder.callbutton2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_DIAL);
               if(phone!=null) {
                   intent.setData(Uri.parse(phone));
                   try {
                       context.startActivity(intent);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }else{
                   Toast.makeText(context, "등록된 번호가 없습니다." , Toast.LENGTH_SHORT).show();
               }
           }
       });

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent tomaptointent =new Intent(context,Map.class);
               tomaptointent.putExtra("resultlist",helthclublist);
               tomaptointent.putExtra("focusname",helthclublist.get(holder.getLayoutPosition())[0]);
               tomaptointent.putExtra("focus","on");
               context.startActivity(tomaptointent);


           }
       });



    }

    @Override
    public int getItemCount() {
        return helthclublist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView helthclubimage;
        TextView placename;
        TextView placeaddress;
        ImageButton callbutton2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            callbutton2=itemView.findViewById(R.id.callbutton2);
            helthclubimage=itemView.findViewById(R.id.helthclubimage);
            placename=itemView.findViewById(R.id.placename);
            placeaddress=itemView.findViewById(R.id.placeaddress);

        }
    }

    public class Task extends AsyncTask<String, Integer, String> {

        String clientKey = "AIzaSyCM_wygaB3RdtdAcdFpDHOTIo_Y5stCI-Q";;
        private String str, receiveMsg;
        String placeid;


        Task(String placeid){
            this.placeid=placeid;
        }


        @Override
        protected String doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL("https://maps.googleapis.com/maps/api/place/details/json?placeid="+placeid+"&fields=formatted_phone_number&key="+clientKey);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("receiveMsg : ", String.valueOf(url));

                    reader.close();
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return receiveMsg;

        }
    }

}
