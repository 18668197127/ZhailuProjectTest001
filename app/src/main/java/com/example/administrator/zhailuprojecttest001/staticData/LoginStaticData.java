package com.example.administrator.zhailuprojecttest001.staticData;

//用户登录信息的静态存储,避免每次都访问SP文件获取文件信息
public class LoginStaticData {
    //初始赋值均为空字符串
    //Android使用静态变量小心进程被杀死重新初始化静态变量,在使用静态变量的时候要判断该值是否为初始值
    public static String token="";
    public static String userId="";
    public static String telephone="";
}
