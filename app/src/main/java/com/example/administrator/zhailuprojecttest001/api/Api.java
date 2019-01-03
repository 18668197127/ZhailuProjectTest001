package com.example.administrator.zhailuprojecttest001.api;
//这是一个后端接口的汇总数据类
//PUrl表示后缀的部分,Url表示完整URL
public class Api {
    //主页
    private String mainUrl="http://test.mouqukeji.com/";
    private String shouyePUrl="api/v1/Index";
    //登录注册
    private String loginMainUrl="http://test.mouqukeji.com/api/Login/";
    private String getCodePUrl="getcode";
    private String registerPUrl="register";


    //get,set
    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getShouyePUrl() {
        return shouyePUrl;
    }

    public void setShouyePUrl(String shouyePUrl) {
        this.shouyePUrl = shouyePUrl;
    }

    public String getLoginMainUrl() {
        return loginMainUrl;
    }

    public void setLoginMainUrl(String loginMainUrl) {
        this.loginMainUrl = loginMainUrl;
    }

    public String getGetCodePUrl() {
        return getCodePUrl;
    }

    public void setGetCodePUrl(String getCodePUrl) {
        this.getCodePUrl = getCodePUrl;
    }

    public String getRegisterPUrl() {
        return registerPUrl;
    }

    public void setRegisterPUrl(String registerPUrl) {
        this.registerPUrl = registerPUrl;
    }
}
