package com.example.me.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;


//상세 운동 데이터 운동 방법, 운동 설명, 운동 이미지, 운동 영상이  있다.

public class Actionlist2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionlist2);

        ImageView actionimage = findViewById(R.id.actionimage);

        TextView actiondetailname = (TextView)findViewById(R.id.actiondetailname);

        String actionname = getIntent().getStringExtra("name");

        actiondetailname.setText(actionname);

        TextView actionexplainview = (TextView)findViewById(R.id.actionexplain);
        TextView actionmethodview= (TextView)findViewById(R.id.actionmethod);

        Button connectinternet = (Button)findViewById(R.id.connectinternet);


        ImageButton actionlist2backbutton = (ImageButton)findViewById(R.id.actionlist2backbutton);



        actionlist2backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //어깨운동 1
        if(actionname.equals("프론트 레터럴 레이즈")) {
            String actionexplain = "어깨 앞쪽 부위인 전면삼각근을 집중적으로 발달시키기 위한 운동입니다. 무겁게 들어 올리면 코어 근육, 특히 허리에 크게 무리가 올수 있습니다. 그러므로 적응력을 높이기 위해 서서히 체중을 늘리십시오.";
            String actionmethod ="①양손으로 허벅지 앞에서 체중 판을 들고 똑바로 세우십시오. 손은 3시와 9시 위치에 있어야합니다. ②등을 똑바로하고 팔꿈치를 약간 구부린 상태에서 팔이 바닥과 평행 할 때까지 앞쪽으로 판을 올리며 숨을 내쉽니다.③ 팔을 아래로 내립니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.frontraise);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%ED%94%84%EB%A1%A0%ED%8A%B8%EB%A0%88%EC%9D%B4%EC%A6%88"));
                    startActivity(intent);
                }
            });

        }else if(actionname.equals("사이드 레터럴 레이즈")) {
            String actionexplain = "어깨 옆쪽 부위인 측면삼각근을 집중적으로 발달시키기 위한 운동입니다. 어깨 관절만의 움직임으로 동작이 구성되는 단순 관절 운동이므로, 무거운 중량으로 실시하는 것은 의미가 없으며, 적정한 중량을 완벽히 컨트롤하며 천천히 동작을 실시하여 목표 근육에 집중하는 것이 운동 효과의 성패를 가릅니다.";
            String actionmethod ="① 양손에 덤벨을 하나씩 쥐고, 앞으로 약간 숙이고, 덤벨을 앞쪽으로 모아 팔 길이로 위치시킵니다. 덤벨을 흔들어 반동을 주면서 들지 않도록 하기 위해, 완전히 정지한 상태에서 각 반복을 시작합니다. ②중량을 양옆 바깥 위쪽으로 들어올리는데, 손목은 약간 틀어 덤벨의 뒤쪽이 앞쪽보다 높아지도록 합니다. ③덤벨을 어깨 높이보다 약간 높은 지점까지 들어올리고, 그런 다음 천천히, 끝까지 저항하면서 중량을 내립니다.  ";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.sideleteral);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EC%82%AC%EC%9D%B4%EB%93%9C+%EB%A0%88%ED%84%B0%EB%9F%B4+%EB%A0%88%EC%9D%B4%EC%A6%88"));
                    startActivity(intent);
                }
            });

        }else if(actionname.equals("벤트오버 레터럴 레이즈")) {
            String actionexplain = "어깨 뒤쪽 부위인 후면삼각근을 집중적으로 발달시키기 위한 운동입니다. 손바닥이 안쪽을 향하게 그립하고 팔뚝이 상체에 수직으로 올려져야 부상을 줄이고 근육에 집중할 수 있습니다.";
            String actionmethod ="① 벤치에 엎드려 각 손에 덤벨을 잡습니다. ② 팔꿈치를 약간 구부린 상태에서 덤벨을 양쪽으로 들어 올리면서 숨을 내쉽니다. ③ 덤벨을 시작 위치로 내리면서 숨을 들이쉽니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.rearraise);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%B2%A4%ED%8A%B8%EC%98%A4%EB%B2%84+%EB%A0%88%ED%84%B0%EB%9F%B4+%EB%A0%88%EC%9D%B4%EC%A6%88"));
                    startActivity(intent);
                }
            });

        }else if(actionname.equals("숄더프레스")) {
            String actionexplain = "어깨 앞쪽 부위인 전면삼각근, 옆쪽 측면삼각근을 집중적으로 발달시키기 위한 운동입니다. 어깨 너비보다 약간 넓은 그립을 사용하여 잡으십시오. 중량이 높을 시 부상의 위험이 있어 기구를 사용하거나 앉아서 하는 방식도 좋습니다.";
            String actionmethod ="① 막대 아래에 서서 어깨 너비보다 약간 넓은 그립을 사용하여 잡으십시오. ② 팔이 거의 완전히 펴질 때까지 바를 위쪽으로 밀 때 숨을 내쉽니다. ③ 바를 가슴 위쪽으로 내릴 때 숨을 들이쉽니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.shoulderpress);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EC%88%84%EB%8D%94+%ED%94%84%EB%A0%88%EC%8A%A4"));
                    startActivity(intent);
                }
            });

        }else if(actionname.equals("시티드 프론트 덤벨 레이즈")) {
            String actionexplain = "어깨 앞쪽 부위인 전면삼각근을 집중적으로 발달시키기 위한 운동입니다.";
            String actionmethod ="① 각 손에 덤벨을 들고 의자 또는 벤치에 앉아 팔을 내립니. ② 팔꿈치를 약간 구부린 자세로 팔을 수평 위로 약간 올릴 때까지 덤벨을 반원형으로 앞으로 올려 올리면서 숨을 내쉽니다. ③ 덤벨을 시작 위치로 낮출 때 숨을 들이쉽니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.seatfront);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EC%8B%9C%ED%8B%B0%EB%93%9C+%ED%94%84%EB%A1%A0%ED%8A%B8+%EB%A0%88%EC%9D%B4%EC%A6%88"));
                    startActivity(intent);
                }
            });

        }else if(actionname.equals("시티드 벤트오버 레이즈")) {
            String actionexplain = "어깨 뒤쪽 부위인 후면삼각근을 집중적으로 발달시키기 위한 운동입니다. 근육 집중을 위해 손바닥이 서로 마주보아야합니다";
            String actionmethod ="① 한 쌍의 덤벨을 들고 벤치 끝에 앉는다. 몸통이 수평에 가까워 질 때까지 앞으로 몸을 숙이고 손바닥이 서로 마주보아야합니다. ② 팔을 약간 구부리고 팔꿈치를 몸통에 수직으로 유지하면서 팔꿈치가 어깨 높이가 될 때까지 팔을 양쪽으로 들어올리며 숨을 내쉽니다. ③ 숨을 들이쉬며 팔을 시작 위치로 내립니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.seatbentover);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EC%8B%9C%ED%8B%B0%EB%93%9C+%EB%B2%A4%ED%8A%B8%EC%98%A4%EB%B2%84"));
                    startActivity(intent);
                }
            });

            //다리운동
        }else if(actionname.equals("스쿼트")) {
            String actionexplain = "허벅지 앞쪽 부위인 대퇴근, 엉덩이 부위의 둔근을 집중적으로 발달시키기 위한 운동입니다. 부가적으로 기립근등 전신의 근육이 작용합니다.";
            String actionmethod ="① 어깨 너비만큼 발을 벌리고 무릎과 발은 같은 방향을 향해야합니다. 균형을 위해 팔을 앞으로 뻗어주는 것이 좋습니다. ② 적어도 허벅지가 바닥과 평행을 이룰 때까지 내려가야하며, 엉덩이와 무릎을 동시에 구부리고 쪼그리고 앉아서 머리를 위로하고 몸통을 똑바로 세워 두십시오. 몸이 앞쪽으로 기울어 지면 안됩니다. ③ 출발 위치로 돌아갈 때" +
                    "숨을 내쉽니.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.squat);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EC%8A%A4%EC%BF%BC%ED%8A%B8"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("레그 익스텐션")) {
            String actionexplain = "허벅지 앞쪽 부위인 대퇴 사두근을 집중적으로 발달시키기 위한 운동입니다. 너무 무거워지면 무릎에 좋지 않을 수 있습니다.";
            String actionmethod ="① 허벅지 뒤가 등받이에 의해 완벽하게지지되고 등받이가 완전히지지되도록 필요한 조정을 한뒤 손잡이를 잡고 지지하십시. 패드를 발목 바로 위에 올려 놓은 상태에서 발의 발 아래에 발을 걸어 놓습니다. ② 다리가 완전히 펼쳐질 때까지 다리를 똑 바르게하여 레버를 앞뒤로 들어 올리면서 숨을 내쉽니다. ③ 무릎을 구부려 레버를 시작 위치로 돌려 놓으며 숨을 들이쉽니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.legextention);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%A0%88%EA%B7%B8%EC%9D%B5%EC%8A%A4%ED%85%90%EC%85%98"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("레그 컬")) {
            String actionexplain = "허벅지 뒤쪽부터 종아리 부위 햄스트링 집중적으로 발달시키기 위한 운동입니다. 엉덩이가 벤치에서 떨어지지 않게 주의하십시";
            String actionmethod ="① 다리 컬 벤치에 엎드려서 레버 패드 아래에 다리를 위치 시키십시오. 무릎은 벤치 가장자리 바로 아래에 있어야하며 레버 패드는 발 뒤꿈치 바로 위에 있어야합니다. ② 무릎을 구부리고 레버가 허벅지 뒤쪽에 닿을 때까지 구부리십시오. ③ 레버를 시작 위치로 낮추면서 숨을 들이쉽니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.legcurl);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%A0%88%EA%B7%B8%EC%BB%AC"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("레그 프레스")) {
            String actionexplain = "허벅지 앞쪽 대퇴 사두근을 집중적으로 발달시키기 위한 운동입니다. 부상을 방지하기 위해 등받이 지지대 및 안전 받침대를 조정하십시오.";
            String actionmethod ="① 플랫폼에서 발을 엉덩이 너비로 벌립니다. 측면 손잡이를 잡고 다리를 펴십시오 ② 다리를 구부리고 무릎이 거의 완전히 구부러 질 때까지 플랫폼을 내리십시오. ③ 숨을 내쉬면서 플랫폼을 다시 시작 위치로 누릅니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.legpress);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%A0%88%EA%B7%B8+%ED%94%84%EB%A0%88%EC%8A%A4"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("런지")) {
            String actionexplain = "허벅지 앞쪽 대퇴 사두근을 집중적으로 발달시키기 위한 운동입니다. 덤벨을 드는 것으로 강도를 높일 수 있습니다.";
            String actionmethod ="① 양 손으로 똑바로 세우거나 엉덩이에 두십시오. ② 몸통을 똑바로 세운 채로 한쪽 다리로 큰 걸음을 내딛고 나가지 않은 다리의 무릎을 낮추며 거의 마루에 닿을 때까지 내려가십시오, 이때 숨을 들이쉽니다. ③ 내딪은 앞다리를 원래 위치로 돌리고 반대 다리로 런지를 반복합니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.lunge);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%9F%B0%EC%A7%80"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("덤벨 런지")) {
            String actionexplain = "엉덩이의 대둔근을 집중적으로 발달시키기 위한 운동입니다.";
            String actionmethod ="① 양 손에 아령을 들고 똑바로 섭니다. ② 숨을 내쉴 때 한쪽 다리로 큰 걸음을 내딛고 몸을 낮추십시오. 나가지 않은 다리의 무릎을 낮추며 거의 마루에 닿을 때까지 내려가십시오, 이때 숨을 들이쉽니다. ③ 상체를 펴며 내딪은 앞다리를 원래 위치로 돌리고 반대 다리로 런지를 반복합니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.dumbbelllunge);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%8D%A4%EB%B2%A8+%EB%9F%B0%EC%A7%80"));
                    startActivity(intent);
                }
            });

            //코어
        }else if(actionname.equals("플랭크")) {
            String actionexplain = "복근, 대퇴사두근을 집중적으로 발달시키기 위한 운동입니다. 척추 기립근과 팔 근육도 사용됩니다.";
            String actionmethod ="① 팔꿈치와 팔뚝을 바닥에 대고 엎드린 뒤 몸을 곧게 펴십시오. 발은 서로 가깝고 팔꿈치는 어깨 바로 아래 있어야하며 상체는 팔뚝과 발가락으로지지되어야합니다. ② 정상적으로 호흡하면서 원하는 시간 동안이 자세를 유지하십시오. ③ 천천히 시작 위치로 돌아갑니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.plank);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%ED%94%8C%EB%9E%AD%ED%81%AC"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("바이시클 크런치")) {
            String actionexplain = "하복부를 집중적으로 발달시키기 위한 운동입니다.";
            String actionmethod ="① 누워서 손을 머리 뒤에 두고 허리는 땅에서 떨어지지 않게 합니다. ② 사이클링 동작으로 천천히 가슴쪽으로 한 번에 하나씩 무릎을 올리십시오. ③ 왼쪽 무릎을 올릴때 오른쪽 팔꿈치를 왼쪽 무릎에 닿도록 상체를 굽히고 오른쪽 무릎을 올릴때 왼쪽 팔꿈치가 오른쪽 무릎에 닿도록 상체를 굽힙니다. ";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.bicyclecrunch);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%B0%94%EC%9D%B4%EC%8B%9C%ED%81%B4+%ED%81%AC%EB%9F%B0%EC%B9%98"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("디클라인 크런치")) {
            String actionexplain = "복근을 집중적으로 발달시키기 위한 운동입니다. ";
            String actionmethod ="① 경사진 벤치에 등을 대고 (누운 자세) 누워 발을 걸어 놓습니다. 가슴이나 머리 또는 목 뒤에 손을 배치하십시오.② 목에 중립을 유지하고 허리를 벤치에 대고 누르면서 복부를 구부려서 머리와 어깨를 벤치에서 들어 올립니다. ③ 숨을 들이쉬며 머리와 어깨를 낮추고 시작 자세로 돌아가 복부를 이완시킵니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.declinecrunch);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%94%94%ED%81%B4%EB%9D%BC%EC%9D%B8+%ED%81%AC%EB%9F%B0%EC%B9%98"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("슈퍼맨 푸쉬업")) {
            String actionexplain = "대흉근과 복근을 집중적으로 발달시키기 위한 운동입니다. ";
            String actionmethod ="① 팔을 앞쪽으로 펼치고 손바닥을 바닥에 평평하게 놓고 바닥에 엎드립니다. ② 손바닥을 아래로 밀면 몸을 바닥에서 들어 올릴 수 있습니다. ③ 숨을 들이쉬면 천천히 몸을 바닥으로 내립니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.superman);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EC%8A%88%ED%8D%BC%EB%A7%A8+%ED%91%B8%EC%89%AC%EC%97%85"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("사이드 플랭크")) {
            String actionexplain = "측면 복근을 집중적으로 발달시키기 위한 운동입니다. 이 동작은 너무 무리하면 척추에 좋지 않을 수 있습니다.";
            String actionmethod ="① 바닥이나 매트 위에 눕고 팔꿈치와 팔뚝의 상체를 지탱하십시오. 팔뚝은 몸에 수직으로 놓아야하며, 팔꿈치는 어깨 바로 아래에 있어야하며 발은 서로 겹쳐 져야합니다. ② 숨을 내쉴 때 척추를 옆으로 구부리고 발로 내려 가면서 최대한 바닥에서 엉덩이를 들어 올립니다. ③ 엉덩이를 바닥에 내려 놓으면 숨을 들이쉽니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.sideplank);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EC%82%AC%EC%9D%B4%EB%93%9C%ED%94%8C%EB%9E%AD%ED%81%AC"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("덤벨 사이드 밴드")) {
            String actionexplain = "측면 복근을 집중적으로 발달시키기 위한 운동입니다. ";
            String actionmethod ="① 한손에 덤벨을 들고 선 뒤, 자유로운 손을 머리 뒤로 위치시킵니다. ② 측복근에서 가벼운 당겨짐을 느낄 때까지 허리를 옆으로 구부리며 숨을 들이쉽니다. ③ 덤벨을 올리기 위해 허리를 펴며 숨을 내쉽니다. ";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.dumbbellsidebend);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%8D%A4%EB%B2%A8+%EC%82%AC%EC%9D%B4%EB%93%9C+%EB%B0%B4%EB%93%9C+"));
                    startActivity(intent);
                }
           });

         //가슴운동
        }else if(actionname.equals("벤치 프레스")) {
            String actionexplain = "대흉근을 집중적으로 발달시키기 위한 운동입니다. 이두와 삼두에도 도움을 주며 무리한 무게는 떨어질 때 부상을 유발할 수 있어 보조자와 함께하는 것이 좋습니다.";
            String actionmethod ="① 평평한 벤치에 등을 대고 다리를 벌리고 바닥에 발을 평평하게하십시오. 어깨 너비의 그립을 사용하여 바벨을 들어올립니다. ② 팔꿈치를 몸에 가깝게 유지하고, 바벨을 가슴에 대고 숨을 들이쉽니다. ③ 바벨을 다시 시작 위치로 되돌릴 때 숨을 내쉽니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.benchpress);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%B2%A4%EC%B9%98+%ED%94%84%EB%A0%88%EC%8A%A4"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("딥스")) {
            String actionexplain = "대흉근 하부를 집중적으로 발달시키기 위한 운동입니다. 팔꿈치에 무리를 줄 수 있는 동작으로 운동 방법을 정확히 숙지해 주십시오." ;
            String actionmethod ="① 딥 머신의 무릎 패드 위로 무릎을 꿇고 어깨 너비 막대를 잡으십시오. ② 몸통을 똑바로 유지하고 팔꿈치를 몸에 집어 넣고 어깨에 가벼운 스트레칭을 느낄 때까지 몸을 낮추십시오. ③ 가슴과 팔에 힘을 팔을 펴며 상체를 원래 위치로 올립니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.dipth);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%94%A5%EC%8A%A4"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("인클라인 벤치 프레스")) {
            String actionexplain = "대흉근 상부를 집중적으로 발달시키기 위한 운동입니다.";
            String actionmethod ="① 45도 기울어 진 벤치에 누워 아령을 가슴 옆에 위치시킵니다. ② 아령을 상향으로 그리고 안쪽으로 누르면 팔이 거의 완전히 펴지고 아령이 거의 닿을 때까지 숨을 내쉽니다. ③ 가슴에 가벼운 당겨짐을 느낄 때 아령을 시작위치로 내리며 숨을 들이쉽니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.inclinebenchpress);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EC%9D%B8%ED%81%B4%EB%9D%BC%EC%9D%B8+%EB%B2%A4%EC%B9%98%ED%94%84%EB%A0%88%EC%8A%A4"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("디클라인 벤치 프레스")) {
            String actionexplain = "대흉근 하부를 집중적으로 발달시키기 위한 운동입니다.";
            String actionmethod ="① 거꾸로 높인 벤치에 누워 아령을 가슴 옆에 위치시킵니다. ② 아령을 상향으로 그리고 안쪽으로 누르면 팔이 거의 완전히 펴지고 아령이 거의 닿을 때까지 숨을 내쉽니다. ③ 어깨 또는 가슴에 가벼운 당겨짐을 느낄 때 아령을 시작 위치로 내립니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.declinebenchpress);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%94%94%ED%81%B4%EB%9D%BC%EC%9D%B8+%EB%B2%A4%EC%B9%98%ED%94%84%EB%A0%88%EC%8A%A4"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("다이아몬드 푸쉬업")) {
            String actionexplain = "대흉근과 삼두를 집중적으로 발달시키기 위한 운동입니다.";
            String actionmethod ="① 엄지 손가락과 검지 손가락으로 다이아몬드 모양으로 만드어 바닥에 대고 엎드립니다. ② 팔꿈치를 몸과 몸에 똑바로 두고 가슴을 바닥에 내리면서 숨을 들이쉽니다. ③ 숨을 내쉴 때 팔꿈치를 펴고 몸을 다시 시작 위치로 올립니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.diamondpushup);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%8B%A4%EC%9D%B4%EC%95%84%EB%AA%AC%EB%93%9C+%ED%91%B8%EC%89%AC%EC%97%85"));
                    startActivity(intent);
                }
            });
        }else if(actionname.equals("플라이")) {
            String actionexplain = "대흉근을 집중적으로 발달시키기 위한 운동입니다. 더 큰 효과를 위해 동작을 천천히하고 근육의 고립상태를 유지하십시오. ";
            String actionmethod ="① 등 받침에 단단히 밀착해 앉으십시오. 손목, 팔꿈치 및 어깨가 수평이되도록 하여 레버를 잡습니다. ② 팔꿈치를 약간 구부린 상태에서 레버를 누르면서 숨을 내쉽니다. ③ 가슴이나 어깨에 가벼운 당겨짐을 느낄 때까지 기다리고 움직임을 되돌릴 때 숨을 들이쉽니다.";
            actionexplainview.setText(actionexplain);
            actionmethodview.setText(actionmethod);
            actionimage.setImageResource(R.drawable.meachinefly);
            connectinternet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/results?search_query=%EB%A8%B8%EC%8B%A0+%ED%94%8C%EB%9D%BC%EC%9D%B4"));
                    startActivity(intent);
                }
            });
        }








    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
