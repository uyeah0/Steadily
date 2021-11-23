package com.example.steadily;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    static String getToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        //Date 객체 사용
        Date date = new Date();
        String reult = simpleDateFormat.format(date);

        return reult;
    }

}
