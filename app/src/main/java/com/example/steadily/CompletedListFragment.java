package com.example.steadily;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.List;

// 전체 목록
public class CompletedListFragment extends Fragment {
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragement_completedlist, container, false);

        /*월간캘린더커스텀*/
        FlexibleCalendarView flexibleCalendarView = view.findViewById(R.id.calendar_view);
     /*   flexibleCalendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, int cellType) {
                //customize the date cells
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {

                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar3_date_cell_view, null);
                }
                if (cellType == BaseCellView.TODAY){
                    cellView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    cellView.setTextSize(15);
                } else {
                    cellView.setTextColor(getResources().getColor(android.R.color.white));
                    cellView.setTextSize(12);
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                //customize the weekday header cells
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar3_week_cell_view, null);
                    *//*cellView.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
                    cellView.setTextColor(getResources().getColor(android.R.color.holo_orange_light));*//*
                    cellView.setTextSize(18);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });
*/



        return view;
    }
}
