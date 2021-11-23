package com.example.steadily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    int iMinute=0, iSecond=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context mContext = getApplicationContext();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // ImageButton back = findViewById(R.id.imgbtnTimerBack);
        //ImageButton cancel = findViewById(R.id.imgbtnTimerCancel);
        TextView title = findViewById(R.id.txtTimerList);
        TextView minuteTV = findViewById(R.id.txtTimerMinute);
        TextView secondTV = findViewById(R.id.txtTimerSecond);
        //TextView showtime = findViewById(R.id.txtCurrentTime);
        ImageButton startBtn = findViewById(R.id.imgbtnTimerPlay);
        //ImageButton stopBtnGone = findViewById(R.id.imgbtnTimerStop);
        ImageButton stopBtn = findViewById(R.id.imgbtnTimerStopBtn);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users");

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

      /*  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/

        Intent intent = getIntent();

        // 리스트 제목 타이머로 가져오기
        title.setText(intent.getStringExtra("title"));


        //날짜 및 시간 형식 지정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        //Date 객체 사용
        Date date = new Date();
        String time = simpleDateFormat.format(date);

        String[] fm = new String[1];
        for (int i = 0; i < 5; i++) {
            String ii = i + "";

            myRef.child(uid).child("date").child(time).child("schedule").child(ii).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String get_title = snapshot.child("title").getValue(String.class);
                    String get_time = snapshot.child("time").getValue(String.class);

                    Log.d("minuteTV", "a");
                    //Log.d("add", ii + get_title + title + time);
                    if (get_title != null && get_title.equals(title)) {
                        fm[0] = get_time;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        Log.d("minuteTV", "c");

        //showtime.setText("주어진 시간만큼 일정을 실천해주세요!");
        // 리스트 분 타이머로 가져오기
        minuteTV.setText(intent.getStringExtra("minute"));
        // 리스트 초 타이머로 가져오기
        /*second.setText(intent.getStringExtra("second"));*/
        // 임의의 데이터
        secondTV.setText("0");


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iMinute = Integer.parseInt(minuteTV.getText().toString());
                iSecond = Integer.parseInt(secondTV.getText().toString());

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 0초 이상이면
                                if (iSecond != 0) {
                                    //1초씩 감소
                                    iSecond--;

                                    // 0분 이상이면
                                } else if (iMinute != 0) {
                                    // 1분 = 60초
                                    iSecond = 60;
                                    iSecond--;
                                    iMinute--;
                                }

                                // 분, 초가 10이하(한자리수) 라면
                                // 숫자 앞에 0을 붙인다 ( 8 -> 08 )
                                if (iSecond <= 9) {
                                    secondTV.setText("0" + iSecond);
                                } else {
                                    secondTV.setText(Integer.toString(iSecond));
                                }

                                if (iMinute <= 9) {
                                    minuteTV.setText("0" + iMinute);
                                } else {
                                    minuteTV.setText(Integer.toString(iMinute));
                                }

                                // 타이머를 종료한다..
                                if (iMinute == 0 && iSecond == 0) {
                                    timer.cancel();//타이머 종료

                                    for (int i = 0; i < 5; i++) {
                                        String ii = i + "";

                                        myRef.child(uid).child("date").child(time).child("schedule").child(ii).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String get_title = snapshot.child("title").getValue(String.class);
                                                if(get_title.equals(title)){
                                                    myRef.child(uid).child("date").child(time).child("schedule").child(ii).child("done").setValue("ok");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }
                        });

                    }

                };

                timer.schedule(timerTask, 0, 1000);
            }
        });


    }
}