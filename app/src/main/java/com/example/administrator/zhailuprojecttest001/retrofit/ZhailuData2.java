package com.example.administrator.zhailuprojecttest001.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ZhailuData2 {

    //test Path parameter
    @GET("taskList")
    Call<ResponseBody> getZhailuData(@Query("user_id")String id,@Query("progress")String progress);

    //test Path parameter
    @GET("taskList")
    Call<ResponseBody> getZhailuData(@Query("user_id")String id);
}
