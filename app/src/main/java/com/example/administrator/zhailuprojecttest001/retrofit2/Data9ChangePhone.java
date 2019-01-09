package com.example.administrator.zhailuprojecttest001.retrofit2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//retrofit2的一个接口
public interface Data9ChangePhone {


        //test1
        @FormUrlEncoded
        @POST("editMobile")
        Call<ResponseBody> postData(@Field("user_id") String userId, @Field("telephone") String newTelephone, @Field("code") String code);


}
