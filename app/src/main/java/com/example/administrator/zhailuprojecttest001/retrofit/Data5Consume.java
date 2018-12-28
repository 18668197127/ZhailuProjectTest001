package com.example.administrator.zhailuprojecttest001.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Data5Consume {

    @GET("buy")
    Call<ResponseBody> getConsumeData(@Query("user_id")String id, @Query("page")String page);
}
