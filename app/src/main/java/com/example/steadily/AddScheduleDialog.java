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

    static String clickedDate;
    private Context mContext;
    EditText editAddList;
    TimePicker timePicker;

    int cnt = 0;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
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

        editAddList = findViewById(R.id.etAddList);
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

    public void showDialog(String clickedDate, TodayListFragment.AddScheduleDialogClosedListener listener) {
        final Dialog dialog = new Dialog(mContext);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_schedule_add);

        dialog.show();

        final EditText editAddList = dialog.findViewById(R.id.etAddList);
        final ImageButton imgBtnCancel = dialog.findViewById(R.id.imgBtnCancel);
        final ImageButton imgBtnSave = dialog.findViewById(R.id.imgBtnScheduleSave);

        TimePicker timePicker = (TimePicker)dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        // 선택된 날짜 저장
        clickedDate = clickedDate;


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
        String finalClickedDate = clickedDate;
        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파이어베이스 저장 코드 추가
                String title = editAddList.getText().toString();
                int h = (timePicker.getCurrentHour()*60) + timePicker.getCurrentMinute();
                String time = Integer.toString(h);
                myRef.child(uid).child("date").child(time).child("schedule");

                FirebasePost f1 = new FirebasePost();
                String get_date = f1.getDate()+"";

                for(int i=0; i<5; i++){
                    String ii = i+"";

                    myRef.child(uid).child("date").child(finalClickedDate).child("schedule").child(ii).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String get_title = snapshot.child("title").getValue(String.class);

                            Log.d("add", ii + get_title + title + time);
                            if(get_title!= null && get_title.equals("e") && cnt != 1){
                                myRef.child(uid).child("date").child(finalClickedDate).child("schedule").child(ii).child("title").setValue(title);
                                myRef.child(uid).child("date").child(finalClickedDate).child("schedule").child(ii).child("time").setValue(time);
                                myRef.child(uid).child("date").child(finalClickedDate).child("schedule").child(ii).child("done").setValue("e");
                                cnt = 1;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                dialog.hide();
                listener.onClose();
            }
        });
    }

}