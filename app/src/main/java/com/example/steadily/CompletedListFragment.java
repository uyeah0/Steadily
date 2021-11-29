package com.example.steadily;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// 전체 목록
public class CompletedListFragment extends Fragment {
    private Context mContext;
    private FlexibleCalendarView mCalendarView;
    private TextView mCurrentDate;
    private ListView mCompletedList;
    private Button mChangeRoutine;

    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    String mMyUId = mFirebaseAuth.getUid();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("users");

    List<String> mRoutines = new ArrayList<>();
    CompletedListAdapter mCompletedListAdapter;
    String mSelectedDate;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragement_completedlist, container, false);

         //월간캘린더커스텀
        mCalendarView = view.findViewById(R.id.calendar_view);
        mCalendarView.setDisableAutoDateSelection(true);
        /*
        mCalendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, int cellType) {
                //customize the date cells
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar_date_cell_view, null);
                }
                if (cellType == BaseCellView.TODAY){
                    cellView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    cellView.setBackground(mContext.getResources().getDrawable(R.drawable.cell_yellow_background));
                    cellView.setTextSize(15);
                }
                else if (cellType == BaseCellView.SELECTED) {
                    cellView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    cellView.setBackground(mContext.getResources().getDrawable(R.drawable.cell_green_circular_background));
                    cellView.setTextSize(15);
                }
                else {
                    cellView.setTextColor(getResources().getColor(R.color.black));
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
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar_week_cell_view, null);

                    cellView.setTextColor(getResources().getColor(android.R.color.black));
                    cellView.setTextSize(18);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return String.valueOf(defaultValue.charAt(0));
            }
        });
        */
        mCalendarView = view.findViewById(R.id.calendar_view);
        mCurrentDate = view.findViewById(R.id.tv_selected_date);
        mCompletedList = view.findViewById(R.id.completedlist);
        mChangeRoutine = view.findViewById(R.id.btn_change);

        SelectedDateItem selectedDateItem = mCalendarView.getSelectedDateItem();
        if (selectedDateItem != null) {
            String displaySelectedDate = String.format("%d.%d", selectedDateItem.getMonth() + 1, selectedDateItem.getDay());
            mCurrentDate.setText(displaySelectedDate);
        }
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");

            //Date 객체 사용
            Date date = new Date();
            String today = simpleDateFormat.format(date);

            String[] dates = today.split("/");

            String displaySelectedDate = String.format("%s.%s", dates[0], dates[1]);
            mCurrentDate.setText(displaySelectedDate);
        }

        mCompletedListAdapter = new CompletedListAdapter(mRoutines);
        mCompletedList.setAdapter(mCompletedListAdapter);

        if (selectedDateItem != null){
            mSelectedDate = String.format("%d%d%d",  selectedDateItem.getYear(), selectedDateItem.getMonth() + 1, selectedDateItem.getDay());
        }
        else {
            mSelectedDate = Utils.getToday();
        }


        mCalendarView.setOnDateClickListener(new FlexibleCalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day) {
                mRoutines.clear();
                mCompletedListAdapter.notifyDataSetChanged();
                String displaySelectedDate = String.format("%d.%d", month + 1, day);
                mCurrentDate.setText(displaySelectedDate);
                mSelectedDate = String.format("%d%d%d", year, month + 1, day);

                getRoutines();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getRoutines();

    }

    // 전체 목록에 날짜별 루틴 가져오기
    private void getRoutines() {
        for(int i=0; i<5; i++) {
            mRootRef.child(mMyUId).child("date").child(mSelectedDate).child("schedule").child(Integer.toString(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String title = snapshot.child("title").getValue(String.class);
                    if (!TextUtils.isEmpty(title) && !title.equals("e")) {
                        mRoutines.add(title);
                        mCompletedListAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
