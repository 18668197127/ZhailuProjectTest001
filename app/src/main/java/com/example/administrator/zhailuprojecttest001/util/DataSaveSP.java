package com.example.administrator.zhailuprojecttest001.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

//这个是用户登录数据SP持久化的工具类
//第一个方法是数据持久化token和userId,第二个userId,第三个方法是一个userId的加密方法字符串拼接+Base64
public class DataSaveSP {
    private static final String TAG = "DataSaveSP";

    //token数据和user_id数据持久化
    public boolean dataSave(String token, String userId,String telephone, ContextWrapper contextWrapper){
        SharedPreferences sharedPreferences=contextWrapper.getSharedPreferences("zhailu",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("tk",token);
        //数据加密
        String userIdEncode=customEncode(userId);
        editor.putString("userId",userIdEncode);
        editor.putString("telephone",telephone);
        boolean b=editor.commit();
        Log.i(TAG, "dataSave: 数据持久化"+token+" "+userIdEncode+" "+telephone);
        return b;
    }

    //token数据和user_id数据持久化
    public boolean dataSave(String token, String userId, ContextWrapper contextWrapper){
        SharedPreferences sharedPreferences=contextWrapper.getSharedPreferences("zhailu",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("tk",token);
        //数据加密
        String userIdEncode=customEncode(userId);
        editor.putString("userId",userIdEncode);
        boolean b=editor.commit();
        Log.i(TAG, "dataSave: 数据持久化"+token+" "+userIdEncode+" ");
        return b;
    }
    //user_id数据持久化
    public boolean dataSave(String userId, ContextWrapper contextWrapper){
        SharedPreferences sharedPreferences=contextWrapper.getSharedPreferences("zhailu",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        //数据加密
        String userIdEncode=customEncode(userId);
        editor.putString("userId",userIdEncode);
        boolean b=editor.commit();
        Log.i(TAG, "dataSave: 数据持久化"+" "+userIdEncode);
        return b;
    }

    //这个是userId的字符串拼接+Base64加密
    public String  customEncode(String initString){
        initString="173"+initString+"604";
        byte[] encode=Base64.encode(initString.getBytes(),Base64.DEFAULT);
        String dataKey=new String (encode);
        Log.i(TAG, "encrypt:加密完成 "+dataKey);
        return dataKey;
    }
}
