package com.example.steadily;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// 오늘 실천
public class TodayListFragment extends Fragment {

    public interface AddScheduleDialogClosedListener {
        void onClose();
    }

    // firebase
    static FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
    static DatabaseReference mMyRef = FirebaseDatabase.getInstance().getReference().child("users");
    static String mUid = firebaseAuth.getCurrentUser().getUid();
    private String clickedDate;

    static String get_title, get_time, get_done;
    Activity mActivity;

    WeekCalendar mWeekCalendar;
    static TextView mYmTextView;

    /*오늘 실천 추가 버튼*/
    ImageButton addScheduleButton;
    static ListView mScheduleListView;

    /*오늘 실천 리스트*/
    static List<TodayScheduleItem> mScheduleItems;
    static TodayScheduleListAdapter mAdapter;

    CardView[] wCalender = new CardView[7];
    TextView[] wDate = new TextView[7];
    int mSelectedWeekIndex = 0;
    View.OnClickListener mOnClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.wCalender_sun:
                    mSelectedWeekIndex = 0;
                    break;
                case R.id.wCalender_mon:
                    mSelectedWeekIndex = 1;
                    break;
                case R.id.wCalender_tue:
                    mSelectedWeekIndex = 2;
                    break;
                case R.id.wCalender_wed:
                    mSelectedWeekIndex = 3;
                    break;
                case R.id.wCalender_thu:
                    mSelectedWeekIndex = 4;
                    break;
                case R.id.wCalender_fri:
                    mSelectedWeekIndex = 5;
                    break;
                case R.id.wCalender_sat:
                    mSelectedWeekIndex = 6;
                    break;

            }
            mScheduleItems.clear();
            mWeekCalendar.createDataList(wDate,mSelectedWeekIndex);
            saveDate(wDate,mSelectedWeekIndex);
            WeekCalendar.setCardColor(mSelectedWeekIndex, wCalender);
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mActivity = getActivity();

        View view = inflater.inflate(R.layout.fragment_todaylist, container, false);


        //주간캘린더 - 각 요일별 날짜 카드뷰

        wCalender[0] = view.findViewById(R.id.wCalender_sun); //일요일
        wCalender[1] = view.findViewById(R.id.wCalender_mon); //월요일
        wCalender[2] = view.findViewById(R.id.wCalender_tue); //화요일
        wCalender[3] = view.findViewById(R.id.wCalender_wed); //수요일
        wCalender[4] = view.findViewById(R.id.wCalender_thu); //목요일
        wCalender[5] = view.findViewById(R.id.wCalender_fri); //금요일
        wCalender[6] = view.findViewById(R.id.wCalender_sat); //토요일

        //주간 캘린더 - 각 날짜에 맞도록 텍스트 주마다 변경
        mYmTextView = view.findViewById(R.id.ymTextView); //0000년 00월 텍스트뷰
//        TextView[] wDate = new TextView[7];
        wDate[0] = view.findViewById(R.id.SUN_num); //일 ~ 토 날짜표시 텍스트뷰
        wDate[1] = view.findViewById(R.id.MON_num);
        wDate[2] = view.findViewById(R.id.TUE_num);
        wDate[3] = view.findViewById(R.id.WED_num);
        wDate[4] = view.findViewById(R.id.THU_num);
        wDate[5] = view.findViewById(R.id.FRI_num);
        wDate[6] = view.findViewById(R.id.SAT_num);

        mWeekCalendar = new WeekCalendar();
        Date todayDate = new Date();
        //점찍기
        mWeekCalendar.setWeekCalenderDate(todayDate, wDate);
        //오늘날짜 색깔지정 (클릭한 날짜 색깔지정)
        WeekCalendar.setCardColor(todayDate.getDay(),wCalender);

        mScheduleItems = new ArrayList<>();
        mAdapter = new TodayScheduleListAdapter(mScheduleItems);

        //ListView
        mScheduleListView = (ListView)view.findViewById(R.id.todaylistView);
        mScheduleListView.setAdapter(mAdapter);
        //오늘로 기본 리스트 보여짐
        WeekCalendar.createDataListToday(wDate);

        String textViewString = mYmTextView.getText().toString();
        clickedDate = textViewString.substring(0,4) + textViewString.substring(6,8) + todayDate.getDate();

        //각 날짜를 클릭했을 때 날짜와 일치하는 데이터 불러오기
        for (int i=0; i<7; i++) {
            wCalender[i].setOnClickListener(mOnClickListner);
        }

        /*스케줄 추가 이벤트*/
        addScheduleButton = view.findViewById(R.id.imgBtnAddSchedule);
        addScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddScheduleDialog addScheduleDialog = new AddScheduleDialog(mActivity);
                addScheduleDialog.showDialog(clickedDate, new AddScheduleDialogClosedListener() {
                    @Override
                    public void onClose() {
                        getRoutines(clickedDate);
                    }
                });
            }
        });


        return view;
    }


    private static void getRoutines(String clickedDate) {
        for(int i=0; i<5; i++){
            String ii = i+"";
            int it = i;

            mMyRef.child(mUid).child("date").child(clickedDate).child("schedule").child(ii).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    get_title = snapshot.child("title").getValue(String.class);
                    get_time = snapshot.child("time").getValue(String.class);
                    get_done = snapshot.child("done").getValue(String.class);

                    Log.d("get_Today", get_title+get_time+get_done);
                    if(get_title != null && !get_title.equals("e")) {
                        Log.d("get_if", get_done+get_time+get_title);
                        TodayScheduleItem item = new TodayScheduleItem();

                        item.date = clickedDate;
                        item.isChecked = get_done;
                        item.title = get_title;
                        item.time = get_time;

                        mScheduleItems.add(item);
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    static class WeekCalendar{
        //  날짜 변경 메서드
        static void createDataListToday(TextView[] wDate){
            Calendar calendar = Calendar.getInstance(Locale.KOREA);
            createDataList( wDate, calendar.get(Calendar.DAY_OF_WEEK)-1);
        }

        static void setCardColor(int index, CardView[] wCalender){
            wCalender[index].setCardBackgroundColor(Color.parseColor("#FFFF00"));
            for(int i=0;i<7;i++){
                if(i==index) continue;
                wCalender[i].setCardBackgroundColor(Color.parseColor("#0a000000"));
            }
        }

        static void createDataList( TextView[] wDate, int index) {
            //0000.00.00형식의 String 만들기
            String clickedDate = mYmTextView.getText().toString().substring(0,4)+""+mYmTextView.getText().toString().substring(6,8)+""+wDate[index].getText().toString();

            getRoutines(clickedDate);

        }


        @SuppressLint("SetTextI18n")
        void setWeekCalenderDate( Date date, TextView[] wDate){ //주간캘린더 날짜변경 메소드
            //날짜 형식 지정
            SimpleDateFormat todaySdf = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA); //한국 기준 시간 사용
            todaySdf.format(date); //한국 시간 적용

            //시작 날짜를 일요일로 고정
            Calendar cal = Calendar.getInstance(Locale.KOREA);
            cal.setFirstDayOfWeek(Calendar.SUNDAY);

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1)));

            //0000년 00월 텍스트 적용
            mYmTextView.setText(todaySdf.format(cal.getTime()).substring(0,4) + "년 "+todaySdf.format(cal.getTime()).substring(5,7)+"월");

            for ( int i = 0; i < 7; i++ ) {
                //00일 텍스트 적용
                wDate[i].setText(todaySdf.format(cal.getTime()).substring(8));
                cal.add(Calendar.DAY_OF_MONTH, 1);

                String monthValue;
                if(cal.get(Calendar.MONTH)<10) monthValue = "0"+cal.get(Calendar.MONTH);
                else monthValue = cal.get(Calendar.MONTH)+"";
                String dayValue;
                if(cal.get(Calendar.DAY_OF_MONTH)<10) dayValue = "0"+cal.get(Calendar.DAY_OF_MONTH);
                else dayValue = cal.get(Calendar.DAY_OF_MONTH)+"";
                //밑에 로그부분대로 파이어베이스에 넣으면 됩니다~ 특수문자없음
                Log.d("myapp","캘린더 : "+cal.get(Calendar.YEAR)+monthValue+dayValue);

            }
        }
    }


    // 클릭된 날짜 저장
    public void saveDate(TextView[] wDate, int index) {
        //0000.00.00형식의 String 만들기
        String textViewString = mYmTextView.getText().toString();
        clickedDate = textViewString.substring(0,4) + textViewString.substring(6,8) + wDate[index].getText().toString();
    }
}