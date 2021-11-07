package com.example.steadily;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

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
        tabLayout.setupWithViewPager(viewPager); // 뷰페이저와 탭 레이아웃 연동

        // 탭 레이아웃 아이콘 넣기
        tabLayout.getTabAt(0).setIcon(R.drawable.down_fill_list);
        tabLayout.getTabAt(1).setIcon(R.drawable.down_today);
        tabLayout.getTabAt(2).setIcon(R.drawable.down_statistics);
    }
}