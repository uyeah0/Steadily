package com.example.steadily;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TodayScheduleListAdapter extends BaseAdapter {
    private Context mContext;
    private CheckBox todaySchedule_chk;
    private TextView todaySchedule_title;
    private TextView todaySchedule_time;
    private Button todaySchedule_modify_btn;
    private TextView todaySchedule_num;
    private TextView todaySchedule_numMessage;
    private int countList = 0;

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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, parent, false);
        }

        CheckBox isDoneSchedule = convertView.findViewById(R.id.todaySchedule_chk);
        TextView title = convertView.findViewById(R.id.todaySchedule_title);
        TextView time = convertView.findViewById(R.id.todaySchedule_time);
        ImageButton modify = convertView.findViewById(R.id.todaySchedule_modify_btn);
        TextView repeatCount = convertView.findViewById(R.id.todaySchedule_num);


        TodayScheduleItem item = listViewItemArrayTodayScheduleList.get(position);

        isDoneSchedule.setChecked(item.isChecked);
        title.setText(item.title);
        time.setText(item.time);
        repeatCount.setText(Integer.toString(item.repeatCount));

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimerActivity.class);
                intent.putExtra("title", item.title);
                context.startActivity(intent);
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,  "이미지 버튼이 눌려 졌습니다.", Toast.LENGTH_SHORT).show();
            }
        });



        return convertView;
    }
}
