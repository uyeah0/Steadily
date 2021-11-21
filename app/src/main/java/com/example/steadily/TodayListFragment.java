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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// 오늘 실천
public class TodayListFragment extends Fragment {

    // firebase
    static FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();


    Activity mActivity;

    /*오늘 실천 추가 버튼*/
    ImageButton addScheduleButton;
    ListView mScheduleListView;

    /*오늘 실천 리스트*/
    List<TodayScheduleItem> scheduleItems = new ArrayList<TodayScheduleItem>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mActivity = getActivity();

        View view = inflater.inflate(R.layout.fragment_todaylist, container, false);


        //주간캘린더 - 각 요일별 날짜 카드뷰
        CardView[] wCalender = new CardView[7];
        wCalender[0] = view.findViewById(R.id.wCalender_sun); //일요일
        wCalender[1] = view.findViewById(R.id.wCalender_mon); //월요일
        wCalender[2] = view.findViewById(R.id.wCalender_tue); //화요일
        wCalender[3] = view.findViewById(R.id.wCalender_wed); //수요일
        wCalender[4] = view.findViewById(R.id.wCalender_thu); //목요일
        wCalender[5] = view.findViewById(R.id.wCalender_fri); //금요일
        wCalender[6] = view.findViewById(R.id.wCalender_sat); //토요일

        //주간 캘린더 - 각 날짜에 맞도록 텍스트 주마다 변경
        TextView ymTextView = view.findViewById(R.id.ymTextView); //0000년 00월 텍스트뷰
        TextView[] wDate = new TextView[7];
        wDate[0] = view.findViewById(R.id.SUN_num); //일 ~ 토 날짜표시 텍스트뷰
        wDate[1] = view.findViewById(R.id.MON_num);
        wDate[2] = view.findViewById(R.id.TUE_num);
        wDate[3] = view.findViewById(R.id.WED_num);
        wDate[4] = view.findViewById(R.id.THU_num);
        wDate[5] = view.findViewById(R.id.FRI_num);
        wDate[6] = view.findViewById(R.id.SAT_num);

        WeekCalendar weekCalendar = new WeekCalendar();
        Date todayDate = new Date();
        //점찍기
        weekCalendar.setWeekCalenderDate(view,todayDate,ymTextView,wDate);
        //오늘날짜 색깔지정 (클릭한 날짜 색깔지정)
        WeekCalendar.setCardColor(todayDate.getDay(),wCalender);

        //ListView
        ListView listView = (ListView)view.findViewById(R.id.todaylistView);
        //오늘로 기본 리스트 보여짐
        WeekCalendar.createDataListToday(ymTextView,wDate,listView);

        //각 날짜를 클릭했을 때 날짜와 일치하는 데이터 불러오기
        wCalender[0].setOnClickListener(v -> { //일요일
            Log.d("myapp","일요일 눌림");
            WeekCalendar.setCardColor(0,wCalender);
        });
        wCalender[1].setOnClickListener(v -> {
            Log.d("myapp","월요일 눌림");
            WeekCalendar.setCardColor(1,wCalender);
        });
        wCalender[2].setOnClickListener(v -> {
            Log.d("myapp","화요일 눌림");
            WeekCalendar.setCardColor(2,wCalender);
        });
        wCalender[3].setOnClickListener(v -> {
            Log.d("myapp","수요일 눌림");
            WeekCalendar.setCardColor(3,wCalender);
        });
        wCalender[4].setOnClickListener(v -> {
            Log.d("myapp","목요일 눌림");
            WeekCalendar.setCardColor(4,wCalender);
        });
        wCalender[5].setOnClickListener(v -> {
            Log.d("myapp","금요일 눌림");
            WeekCalendar.setCardColor(5,wCalender);
        });
        wCalender[6].setOnClickListener(v -> {
            Log.d("myapp","토요일 눌림");
            WeekCalendar.setCardColor(6,wCalender);
        });


        /*스케줄 추가 이벤트*/
        addScheduleButton = view.findViewById(R.id.imgBtnAddSchedule);
        addScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddScheduleDialog addScheduleDialog = new AddScheduleDialog(mActivity);
                addScheduleDialog.showDialog();
            }
        });

        mScheduleListView = view.findViewById(R.id.todaylistView);


        /*임의의 데이터 생성*/
        /*파이어베이스로 데이터 연결*/
        TodayScheduleItem item1 = new TodayScheduleItem();
        item1.isChecked = true;
        item1.title= "[운동]걸어서 등교하기!";
        item1.time = "20";
        item1.repeatCount =5;

        TodayScheduleItem item2 = new TodayScheduleItem();
        item2.isChecked = false;
        item2.title= "[생활습관]일찍 일어나기!";
        item2.time = "10";
        item2.repeatCount =5;

        TodayScheduleItem item3 = new TodayScheduleItem();
        item3.isChecked = true;
        item3.title= "[공부]프로젝트하기!";
        item3.time = "10";
        item3.repeatCount =5;

        TodayScheduleItem item4 = new TodayScheduleItem();
        item4.isChecked = true;
        item4.title= "[공부]프로젝트하기!";
        item4.time = "40";
        item4.repeatCount = 5;

        scheduleItems.add(item1);
        scheduleItems.add(item2);
        scheduleItems.add(item3);
        scheduleItems.add(item4);

        /*어댑터로 연결*/
        TodayScheduleListAdapter adapter = new TodayScheduleListAdapter(scheduleItems);

        mScheduleListView.setAdapter(adapter);




        return view;
    }

    static class WeekCalendar{
        //  날짜 변경 메서드
        static void createDataListToday(TextView ymTextView, TextView[] wDate, ListView listView){
            Calendar calendar = Calendar.getInstance(Locale.KOREA);
        }

        static void setCardColor(int index, CardView[] wCalender){
            wCalender[index].setCardBackgroundColor(Color.parseColor("#FFFF00"));
            for(int i=0;i<7;i++){
                if(i==index) continue;
                wCalender[i].setCardBackgroundColor(Color.parseColor("#0a000000"));
            }
        }

        @SuppressLint("SetTextI18n")
        void setWeekCalenderDate(View view, Date date, TextView ymTextView, TextView[] wDate){ //주간캘린더 날짜변경 메소드
            firebaseAuth =  FirebaseAuth.getInstance();


            //날짜 형식 지정
            SimpleDateFormat todaySdf = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA); //한국 기준 시간 사용
            todaySdf.format(date); //한국 시간 적용

            //시작 날짜를 일요일로 고정
            Calendar cal = Calendar.getInstance(Locale.KOREA);
            cal.setFirstDayOfWeek(Calendar.SUNDAY);

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1)));

            //0000년 00월 텍스트 적용
            ymTextView.setText(todaySdf.format(cal.getTime()).substring(0,4)+"년 "+todaySdf.format(cal.getTime()).substring(5,7)+"월");

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

}
