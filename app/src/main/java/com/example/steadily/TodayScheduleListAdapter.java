package com.example.steadily;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TodayScheduleListAdapter extends BaseAdapter {
    private List<TodayScheduleItem> listViewItemArrayTodayScheduleList;

    public TodayScheduleListAdapter(List<TodayScheduleItem> scheduleList){
        this.listViewItemArrayTodayScheduleList = scheduleList;
    }

    // Adapter에 사용되는 리스트 개수 반환
    @Override
    public int getCount() {
        return listViewItemArrayTodayScheduleList.size();
    }

    // 지정한 위치에 있는 아이템 반환
    @Override
    public Object getItem(int position) {
        return listViewItemArrayTodayScheduleList.get(position);
    }

    // 지정한 위치와 관련있는 아이템의 ID값 반환
    @Override
    public long getItemId(int position) {
        return position;
    }

    // postion에 있는 데이터를 화면에 출력할 리스트
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // 리스트가 아무것도 없다면
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, parent, false);
        }


        RelativeLayout todaySchedule_RealativeLayout = convertView.findViewById(R.id.todaySchedule_RealativeLayout);
        CheckBox isDoneSchedule = convertView.findViewById(R.id.todaySchedule_chk);
        TextView title = convertView.findViewById(R.id.todaySchedule_title);
        TextView time = convertView.findViewById(R.id.todaySchedule_time);
        //ImageButton modify = convertView.findViewById(R.id.todaySchedule_modify_btn);
        LinearLayout todaySchedule_LinearLayout = convertView.findViewById(R.id.todaySchedule_LinearLayout);
        //TextView repeatCount = convertView.findViewById(R.id.todaySchedule_num);


        TodayScheduleItem item = listViewItemArrayTodayScheduleList.get(position);

        // 파이어베이스 연동 관련
        if(item.isChecked.equals("e")){
            isDoneSchedule.setChecked(false);
            Log.d("ischeck", item.isChecked);
        }else{
            isDoneSchedule.setChecked(true);
        }

        title.setText(item.title);
        time.setText(item.time);
        //repeatCount.setText(Integer.toString(item.repeatCount));

        /*타이머 창 띄우기*/

        /*버튼 범위 늘리기*/
        /*렐러티브 안 눌렀을 시 timer 화면 실행*/
        todaySchedule_RealativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimerActivity.class);
                intent.putExtra("title", item.title);
                intent.putExtra("minute",item.time);
                context.startActivity(intent);
            }
        });

        /*timer 화면 실행*/
        isDoneSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimerActivity.class);
                intent.putExtra("title", item.title);
                intent.putExtra("minute",item.time);
                context.startActivity(intent);
            }
        });

        todaySchedule_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimerActivity.class);
                intent.putExtra("title", item.title);
                intent.putExtra("minute",item.time);
                context.startActivity(intent);
            }
        });

        /*타이틀 눌렀을 시 timer 화면 실행*/
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimerActivity.class);
                intent.putExtra("title", item.title);
                intent.putExtra("minute",item.time);
                context.startActivity(intent);
            }
        });

        /*수정 버튼 눌렀을 시 수정 화면 실행*/
        /*현재 토스트로 구현*/
        /*modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,  "이미지 버튼이 눌려 졌습니다.", Toast.LENGTH_SHORT).show();
            }
        });
*/
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "이미지 버튼이 눌려졌습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }
}