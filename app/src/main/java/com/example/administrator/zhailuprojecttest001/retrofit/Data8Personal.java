package com.example.administrator.zhailuprojecttest001.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Data8Personal {

    @GET("userPage")
    Call<ResponseBody> getData(@Query("user_id") String userId);
}
