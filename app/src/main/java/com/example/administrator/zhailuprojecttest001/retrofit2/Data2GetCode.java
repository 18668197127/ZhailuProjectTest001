package com.example.administrator.zhailuprojecttest001.retrofit2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//retrofit2的一个接口
public interface Data2GetCode {


        //test1
        @FormUrlEncoded
        @POST("getcode")
        Call<ResponseBody> postData(@Field("telephone") String telephone);

}
