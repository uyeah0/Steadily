package com.example.steadily;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddScheduleDialog extends AppCompatActivity implements TimePicker.OnTimeChangedListener{

    private Context mContext;
    EditText editAddList;
    TimePicker timePicker;

    int cnt = 0;
    FirebaseAuth firebaseAuth;
    SharedPreferences preferences;

    //날짜 및 시간 형식 지정
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    //Date 객체 사용
    Date date = new Date();
    String time = simpleDateFormat.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //투명배경
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //전체화면 모드(상태바 제거)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_schedule_add);

        editAddList = findViewById(R.id.editAddList);
        timePicker = findViewById(R.id.timePicker);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Log.d("timePicker", timePicker.getCurrentHour()+" : "+timePicker.getCurrentMinute());
            }
        });
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute){

    }
    private NumberPicker getMinuteSpinner(TimePicker t){
        try{
            Field f = t.getClass().getDeclaredField("mMinuteSpinner");
            f.setAccessible(true);
            return (NumberPicker) f.get(t);
        }catch (Exception e){
            Log.e("timepicker","check grepcode");
            return null;
        }
    }

    public AddScheduleDialog(Context context) {
        this.mContext = context;
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mContext);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_schedule_add);

        dialog.show();

        final ImageButton imgBtnCancel = dialog.findViewById(R.id.imgbtnCancel);
        final ImageButton imgBtnSave = dialog.findViewById(R.id.imgbtnScheduleSave);

        TimePicker timePicker = (TimePicker)dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users");

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        //다이얼로그 닫기 
        imgBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        
        //저장버튼 클릭 리스너
        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파이어베이스 저장 코드 추가
                String title = editAddList.getText().toString();
                String time = timePicker.getCurrentHour().toString() + timePicker.getCurrentMinute().toString();
                myRef.child(uid).child("date").child(time).child("schedule");

                FirebasePost f1 = new FirebasePost();
                String get_date = f1.getDate()+"";

                for(int i=0; i<5; i++){
                    String ii = i+"";
                    myRef.child(uid).child("date").child(get_date).child("schedule").child(String.valueOf(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String get_title = snapshot.child("title").getValue(String.class);
                            if(get_title != "e" && cnt != 1){
                                myRef.child(uid).child("date").child(get_date).child("schedule").child(String.valueOf(ii)).child("title").setValue(title);
                                myRef.child(uid).child("date").child(get_date).child("schedule").child(String.valueOf(ii)).child("time").setValue(time);
                                cnt = 1;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                dialog.hide();
            }
        });



    }
}