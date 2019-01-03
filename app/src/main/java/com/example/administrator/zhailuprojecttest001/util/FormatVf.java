package com.example.administrator.zhailuprojecttest001.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//这个是格式验证的工具类
//现有功能,手机号格式验证,四位数字位验证码格式,密码格式(6-16位字母数字混合,不能全数字或字母,首位为字母)
public class FormatVf {
    private static final String TAG = "FormatVf";

    //判断输入的格式是否为手机号
    public boolean isPhone(String phone){
        String regex="^1[3456789]\\d{9}$";
        if (phone.length()!=11){
            Log.i(TAG, "isPhone: 手机位数不对");
            return false;
        }else {
            Pattern p=Pattern.compile(regex);
            Matcher m=p.matcher(phone);
            boolean isMatch=m.matches();
            Log.i(TAG, "isPhone: 是否手机正则匹配"+isMatch);
            return isMatch;
        }
    }
    //判断是否为密码格式
    public boolean isPassword(String password){
        String regex="^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(password);
        boolean isMatch=m.matches();
        Log.i(TAG, "isPassword: 是否密码正则匹配"+isMatch);
        return isMatch;
    }
    //判断是否为验证码格式
    public boolean isVf(String vf){
        String regex="^[0-9]{4}$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(vf);
        boolean isMatch=m.matches();
        Log.i(TAG, "isVf: 是否验证码正则匹配"+isMatch);
        return isMatch;
    }

}
