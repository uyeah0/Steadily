package com.example.steadily;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.Calendar;

public class AddScheduleDialog extends AppCompatActivity implements TimePicker.OnTimeChangedListener{

    private Context mContext;
    TimePicker timePicker;

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

                dialog.hide();
            }
        });



    }
}