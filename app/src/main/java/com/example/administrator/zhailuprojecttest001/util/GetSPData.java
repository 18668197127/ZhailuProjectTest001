package com.example.administrator.zhailuprojecttest001.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.example.administrator.zhailuprojecttest001.staticData.LoginStaticData;

import static android.support.constraint.Constraints.TAG;

//这个是获取SP数据的工具类
public class GetSPData {
    private static final String TAG = "GetSPData";

    //该方法用于获取SP的userID
    public String getSPUserID(ContextWrapper contextWrapper){
        SharedPreferences sharedPreferences=contextWrapper.getSharedPreferences("zhailu",Context.MODE_PRIVATE);
        String userIdEncode=sharedPreferences.getString("userId",null);
        String userId="";
        if (userIdEncode==null){
            //这里是userId本地数据不存在的判断,可以加后续业务逻辑代码
            return null;
        }else {
            //userId解密
            String userIdDecode=new String (Base64.decode(userIdEncode.getBytes(),Base64.DEFAULT));
            userId=userIdDecode.substring(3,userIdDecode.length()-3);
            //验证,从sharedpreferences获取数据
            //验证,从静态变量获取数据
            Log.i(TAG, "getSPUserID: sp读取"+userId);
            return userId;
        }
    }
    //获取token
    public String getSPToken(ContextWrapper contextWrapper){
        SharedPreferences sharedPreferences=contextWrapper.getSharedPreferences("zhailu",Context.MODE_PRIVATE);
        return sharedPreferences.getString("tk",null);
    }

    //获取token
    public String getSPTelephone(ContextWrapper contextWrapper){
        SharedPreferences sharedPreferences=contextWrapper.getSharedPreferences("zhailu",Context.MODE_PRIVATE);
        return sharedPreferences.getString("telephone",null);
    }
}
