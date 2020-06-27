package com.example.startrace.util;

import android.util.Log;

public class URLProviderUtils {

    /**
     * 获取首页的url
     *
     * @param offset 数据偏移量
     * @param size   返回数据的条目个数
     * @return url
     */
    static String basepath = "http://192.168.1.100:8081/stu/";

    /**
     * 登陆
     * @return
     */
    public static String loginUrl(){
        String url = basepath+"login";
        Log.i("Login_url", url);
        return url;
    }

    /**
     *更新密码
     * @return
     */
    public static String updateInfor(){
        String url = basepath+"updateInfor";
        Log.i("updateInfor_url", url);
        return url;
    }

    /**
     * 首页课程
     * @return
     */
    public static String queryAllCourse(){
        String url = basepath+"queryAllCourse";
        Log.i("queryAllCourse", url);
        return url;
    }

    /**
     * 加入课程
     * @return url
     */
    public static String addCourse(){
        String url = basepath+"addCours";
        Log.i("addCourse", url);
        return url;
    }

    /**
     * 取消课程
     * @return
     */
    public static String cancelCourse(){
        String url = basepath+"cancelCourse";
        Log.i("cancelCourse", url);
        return url;
    }

    /**
     * 签到
     * @return
     */
    public static String sign(){
        String url = basepath+"sign";
        Log.i("sign", url);
        return url;
    }

    /**
     * 获取个人信息
     * @return
     */
    public static String getMyInfo(){
        String url = basepath+"getMyInfo";
        Log.i("getMyInfo", url);
        return url;
    }
    private static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    private static String getPhoneModel() {
        return android.os.Build.MODEL;
    }
}

