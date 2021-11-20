package com.example.steadily;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    int iMinute=0, iSecond=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context mContext = getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ImageButton back = findViewById(R.id.imgbtnTimerBack);
        ImageButton cancel = findViewById(R.id.imgbtnTimerCancel);
        TextView title = findViewById(R.id.txtTimerList);
        TextView minuteTV = findViewById(R.id.txtTimerMinute);
        TextView secondTV = findViewById(R.id.txtTimerSecond);
        ImageButton startBtn = findViewById(R.id.imgbtnTimerPlay);
        ImageButton stopBtn = findViewById(R.id.imgbtnTimerStop);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Intent intent = getIntent();

        // 리스트 제목 타이머로 가져오기
        title.setText(intent.getStringExtra("title"));
        // 리스트 분 타이머로 가져오기
        //minuteTV.setText(intent.getStringExtra("minute"));
        minuteTV.setText("1");
        // 리스트 초 타이머로 가져오기
        /*second.setText(intent.getStringExtra("second"));*/
        // 임의의 데이터
        secondTV.setText("5");


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
                                    startBtn.setVisibility(View.GONE);
                                    stopBtn.setVisibility(View.VISIBLE);
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