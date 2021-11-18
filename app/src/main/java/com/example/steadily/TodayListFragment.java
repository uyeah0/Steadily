package com.example.steadily;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

// 오늘 실천
public class TodayListFragment extends Fragment {
    Activity mActivity;

    ImageButton addScheduleButton;

    List<String> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mActivity = getActivity();

        View view = inflater.inflate(R.layout.fragment_todaylist, container, false);

        TextView ymTextView = view.findViewById(R.id.ymTextView); //0000년 00월 텍스트뷰
        TextView[] wDate = new TextView[7];
        wDate[0] = view.findViewById(R.id.SUN_num); //일 ~ 토 날짜표시 텍스트뷰
        wDate[1] = view.findViewById(R.id.MON_num);
        wDate[2] = view.findViewById(R.id.TUE_num);
        wDate[3] = view.findViewById(R.id.WED_num);
        wDate[4] = view.findViewById(R.id.THU_num);
        wDate[5] = view.findViewById(R.id.FRI_num);
        wDate[6] = view.findViewById(R.id.SAT_num);

        addScheduleButton = view.findViewById(R.id.imgbtnAddSchedule);
        addScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddScheduleDialog addScheduleDialog = new AddScheduleDialog(mActivity);
                addScheduleDialog.showDialog();
            }
        });



        return view;
    }

}
