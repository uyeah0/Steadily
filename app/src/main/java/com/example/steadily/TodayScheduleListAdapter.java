package com.example.steadily;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class TodayScheduleListAdapter extends BaseAdapter {
    private Context mContext;
    private CheckBox todaySchedule_chk;
    private TextView todaySchedule_title;
    private TextView todaySchedule_time;
    private Button todaySchedule_modify_btn;
    private TextView todaySchedule_num;
    private TextView todaySchedule_numMessage;
    private int countList = 0;

    private ArrayList<TodayScheduleItem> listViewItemArrayTodayScheduleList = new ArrayList<>();

    public TodayScheduleListAdapter(){} // 기본 생성자

    // Adapter에 사용되는 리스트 개수 반환
    @Override
    public int getCount() { return listViewItemArrayTodayScheduleList.size(); }

    // 지정한 위치에 있는 아이템 반환
   @Override
    public Object getItem(int position) { return listViewItemArrayTodayScheduleList.get(position); }

    // 지정한 위치와 관련있는 아이템의 ID값 반환
    @Override
    public long getItemId(int position) { return position; }

    // postion에 있는 데이터를 화면에 출력할 리스트
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
