package com.example.administrator.zhailuprojecttest001.retrofit2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//retrofit2的一个接口
public interface Data8ChangePw {


        //test1
        @FormUrlEncoded
        @POST("editPassword")
        Call<ResponseBody> postData(@Field("user_id") String userId, @Field("password") String password, @Field("new_password") String newPassword);


}
