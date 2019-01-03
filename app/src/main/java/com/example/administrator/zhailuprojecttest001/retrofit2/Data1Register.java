package com.example.administrator.zhailuprojecttest001.retrofit2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//retrofit2的一个post接口
public interface Data1Register {
        //test1
        @FormUrlEncoded
        @POST("register")
        Call<ResponseBody> postData(@Field("telephone") String telephone, @Field("code") String code, @Field("password") String password);
}

