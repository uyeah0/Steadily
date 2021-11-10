package com.example.steadily;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class AddScheduleDialog {
    private Context mContext;


    public AddScheduleDialog(Context context) {
        this.mContext = context;
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mContext);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_schedule_add);

        dialog.show();


        final ImageButton imgBtnCancel = dialog.findViewById(R.id.imgbtnCancel);
        final ImageButton imgBtnSave = dialog.findViewById(R.id.imgbtnScheduleSave);

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
            }
        });



    }
}