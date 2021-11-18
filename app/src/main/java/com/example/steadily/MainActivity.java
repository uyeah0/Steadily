package com.example.steadily;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        adapter = new FragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter); // 뷰페이저와 어댑터 연결
        tabLayout.setupWithViewPager(viewPager); // 뷰페이저와 탭레이아웃 연동

        tabLayout.getTabAt(0).setText(R.string.tab_menu_completed);
        tabLayout.getTabAt(1).setText(R.string.tab_menu_today);
        tabLayout.getTabAt(2).setText(R.string.tab_menu_statistics);

        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentTime);

        FirebasePost f = new FirebasePost(date_text);
    }
}