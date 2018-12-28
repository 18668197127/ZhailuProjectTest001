package com.example.administrator.zhailuprojecttest001.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Data3Wallet {

    //获取wallet数据(根据userID得到余额的值)
    @GET("wallet")
    Call<ResponseBody> getWalletBalance(@Query("user_id")String id);
}
