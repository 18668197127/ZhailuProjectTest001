package com.example.administrator.zhailuprojecttest001.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


//retrofit2的一个接口
public interface ZhailuData1 {
    //test Path parameter
    @GET("api/v1/{id}")
    Call<ResponseBody> getZhailuData(@Path("id") String  index);

    //test  no parameter
    @GET("api/v1/Index")
    Call<ResponseBody> getZhailuData();
}
