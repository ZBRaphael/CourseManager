package com.typhoon.demo.util;

import com.alibaba.fastjson.JSONObject;

public class dataProcess {
    public static JSONObject Process_data(String data) {
        JSONObject ret = new JSONObject();
        String[] splitdata = data.split("&");
        for (String i : splitdata) {
            String[] temp_split_data = i.split("=");
            ret.put(temp_split_data[0], temp_split_data[1]);
        }
        return ret;
    }
}
