package com.example.administrator.zhailuprojecttest001.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Data9PersonalSave {

    @FormUrlEncoded
    @POST("updateInfo")
    Call<ResponseBody> postData(@Field("user_id") String userId, @Field("nickname") String nickName,@Field("gender") String gender, @Field("age") String age,@Field("school_name") String schoolName);
}
