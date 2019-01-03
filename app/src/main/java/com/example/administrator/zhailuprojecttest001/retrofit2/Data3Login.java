package com.example.administrator.zhailuprojecttest001.retrofit2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//retrofit2的一个post接口
public interface Data3Login {
        //test1
        @FormUrlEncoded
        @POST("Login")
        Call<ResponseBody> postData(@Field("telephone") String telephone, @Field("password") String password);

}

