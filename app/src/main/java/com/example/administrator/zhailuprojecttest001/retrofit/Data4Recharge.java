package com.example.administrator.zhailuprojecttest001.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Data4Recharge {
    //获取recharge数据(根据userID和pageID得到充值记录)
    @GET("recharge")
    Call<ResponseBody> getRechargeBalance(@Query("user_id")String userId,@Query("page")String page);
}
