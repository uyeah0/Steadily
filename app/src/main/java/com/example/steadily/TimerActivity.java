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

    private TextView mTitleTextView;
    private TextView mMinuteTextView;
    private TextView mSecondTextView;
    private ImageButton mStartImageButton;
    private ImageButton mStopImageButton;
    private int iMinute = 0;
    private int iSecond = 0;

    private Timer mTimer;
    private boolean mStared = false; //측정 시작 여부

    DatabaseReference mMyRef = FirebaseDatabase.getInstance().getReference().child("users");
    private String mUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Activity mActivity = this;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTitleTextView = findViewById(R.id.txtTimerList);
        mMinuteTextView = findViewById(R.id.txtTimerMinute);
        mSecondTextView = findViewById(R.id.txtTimerSecond);
        mStartImageButton = findViewById(R.id.imgbtnTimerPlay);
        mStopImageButton = findViewById(R.id.imgbtnTimerStopBtn);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        mUid = user.getUid();

        Intent intent = getIntent();

        // 리스트 제목 타이머로 가져오기
        mTitleTextView.setText(intent.getStringExtra("title"));
        String selectedDate = intent.getStringExtra("date");

        //showtime.setText("주어진 시간만큼 일정을 실천해주세요!");
        // 리스트 분 타이머로 가져오기
        mMinuteTextView.setText(intent.getStringExtra("minute"));
        // 리스트 초 타이머로 가져오기
        /*mScond.setText(intent.getStringExtra("second"));*/
        // 임의의 데이터
        mSecondTextView.setText("0");


        mStartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStared)  {
                    return;
                }

                iMinute = Integer.parseInt(mMinuteTextView.getText().toString());
                iSecond = Integer.parseInt(mSecondTextView.getText().toString());

                if (mTimer == null) {
                    mTimer = new Timer();
                }


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
                                    mSecondTextView.setText("0" + iSecond);
                                } else {
                                    mSecondTextView.setText(Integer.toString(iSecond));
                                }

                                if (iMinute <= 9) {
                                    mMinuteTextView.setText("0" + iMinute);
                                } else {
                                    mMinuteTextView.setText(Integer.toString(iMinute));
                                }

                                // 타이머를 종료한다..
                                if (iMinute == 0 && iSecond == 0) {
                                    mTimer.cancel();//타이머 종료

                                    saveData(selectedDate);
                                    mActivity.finish();
                                }
                            }
                        });

                    }

                };

                mTimer.schedule(timerTask, 0, 1000);

                mStared = true;
            }
        });

        mStopImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimer.cancel();
                saveData(selectedDate);
                mActivity.finish();
            }
        });
    }

    private void saveData(String selectedDate) {
        for (int i = 0; i < 5; i++) {
            String ii = i + "";

            mMyRef.child(mUid).child("date").child(selectedDate).child("schedule").child(ii).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String get_title = snapshot.child("title").getValue(String.class);
                    if(get_title.equals(mTitleTextView.getText().toString())){
                        mMyRef.child(mUid).child("date").child(selectedDate).child("schedule").child(ii).child("done").setValue("true");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}