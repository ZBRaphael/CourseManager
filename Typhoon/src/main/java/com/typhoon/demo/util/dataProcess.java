package com.typhoon.demo.util;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class dataProcess {
    //切割json
    public static JSONObject Process_data(String data) {
        JSONObject ret = new JSONObject();
        String[] splitdata = data.split("&");
        for (String i : splitdata) {
            String[] temp_split_data = i.split("=");
            ret.put(temp_split_data[0], temp_split_data[1]);
        }
        return ret;
    }
    // 比较时间
    public static boolean compareDate(String CourseDate){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        Date date = new Date(System.currentTimeMillis());
        String nowDate = formatter.format(date);
        if(CourseDate.compareTo(nowDate) >= 0){
            return true;
        }
        return false;
    }
}
