package com.example.administrator.zhailuprojecttest001.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhailuprojecttest001.MainActivity;
import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data1Register;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data2GetCode;
import com.example.administrator.zhailuprojecttest001.staticData.LoginStaticData;
import com.example.administrator.zhailuprojecttest001.util.DataSaveSP;
import com.example.administrator.zhailuprojecttest001.util.FormatVf;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUpActivity";
    private String responseString1;
    private String responseString2;

    private String replaceInit="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initClick();
    }
    //初始化点击事件
    public void initClick() {
        Button getVfButton=findViewById(R.id.button_get_captcha);
        getVfButton.setOnClickListener(this);
        Button registerButton=findViewById(R.id.button_sign_up);
        registerButton.setOnClickListener(this);
        //这个透明的view用于密码显示隐藏的点击效果实现
        View viewClickReplace=findViewById(R.id.view_click_replace);
        viewClickReplace.setOnClickListener(this);
    }
    //点击事件的具体实现
    @Override
    public void onClick(View v) {
        EditText editText1=findViewById(R.id.editText1);
        String editText1String=editText1.getText().toString();
        EditText editText2=findViewById(R.id.editText2);
        String editText2String=editText2.getText().toString();
        EditText editText3=findViewById(R.id.editText3);
        String editText3String=editText3.getText().toString();
        //工具类初始化
        FormatVf formatVf=new FormatVf();

        int id = v.getId();
        switch (id) {
            case R.id.button_get_captcha:
                Log.i(TAG, "onClick: "+editText1String+" "+editText1String.length());
                if (formatVf.isPhone(editText1String)){
                    retrofitV1(editText1String);
                }else {
                    Log.i(TAG, "onClick: 请输入正确格式的手机号码");
                    Toast toast=Toast.makeText(SignUpActivity.this,"",Toast.LENGTH_SHORT);
                    toast.setText("请输入正确格式的手机号码");
                    toast.show();
                }
                break;
            case R.id.button_sign_up:
                if (editText3String.equals("")){
                    ;
                }else if (editText3String.charAt(0)=='*'){
                    editText3String=replaceInit;
                }
                Log.i(TAG, "onClick: "+editText3String+" "+editText3String.length());
                if (formatVf.isPassword(editText3String)&&formatVf.isVf(editText2String)&&formatVf.isPhone(editText1String)){
                    Log.i(TAG, "onClick注册: 三个文本输入格式正确");
                    retrofitV2(editText1String,editText2String,editText3String);
                }else {
                    if (!formatVf.isPassword(editText3String)){
                        Toast toast=Toast.makeText(SignUpActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("请输入6-16位字母数字混合密码,首位不为数字");
                        toast.show();
                    }else if (!formatVf.isVf(editText2String)){
                        Toast toast=Toast.makeText(SignUpActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("请输入四位验证码");
                        toast.show();
                    }else if (!formatVf.isPhone(editText1String)){
                        Toast toast=Toast.makeText(SignUpActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("请输入正确格式的手机号码");
                        toast.show();
                    }else {
                        Log.i(TAG, "onClick: 注册未知错误");
                        Toast toast=Toast.makeText(SignUpActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("注册未知错误");
                        toast.show();
                    }
                }
                break;
            case R.id.view_click_replace:
                if (editText3String.equals("")||editText3String.charAt(0)=='*'){
                    editText3.setText(replaceInit);
                    ImageView imageViewZhengkai2=findViewById(R.id.imageView_zhengkai);
                    imageViewZhengkai2.setVisibility(View.VISIBLE);
                }else {
                    replaceInit=editText3String;
                    int length2=editText3String.length();
                    StringBuffer stringBuffer2=new StringBuffer();
                    for (int i=0;i<length2;i++){
                        stringBuffer2.append("*");
                    }
                    editText3.setText(stringBuffer2.toString());
                    ImageView imageViewZhengkai2=findViewById(R.id.imageView_zhengkai);
                    imageViewZhengkai2.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    //获取验证码的网络请求
    public void retrofitV1(final String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/Login/")
                .build();
        Data2GetCode getCodePost=retrofit.create(Data2GetCode.class);
        Call<ResponseBody> call=getCodePost.postData(phone);
//                Call<ResponseBody> call=getCodePost.postData2("18668197127");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString1=response.body().string();
                    Log.i(TAG, "onResponse测试: 验证码信息"+responseString1);
                    JSONObject jsonObject=new JSONObject(responseString1);
                    String code=jsonObject.getString("code");
                    String msg=jsonObject.getString("msg");
                    Log.i(TAG, "onResponse: 测试json解析"+code+" "+msg);
                    if (!msg.equals("success")){
                        Toast toast=Toast.makeText(SignUpActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("短信验证码请求发送异常");
                        toast.show();
                    }else{
                        Toast toast=Toast.makeText(SignUpActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("成功发送短信验证码");
                        toast.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onResponse: "+t.toString());
                Log.i(TAG, "onClick: 验证码post请求失败");
            }
        });

    }

    //注册的网络请求返回登录成功的数据
    public void retrofitV2(final String phone , final String vf , final String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/Login/")
                .build();
        Data1Register data1Register=retrofit.create(Data1Register.class);
        Call<ResponseBody> call=data1Register.postData(phone,vf,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString2=response.body().string();
                    Log.i(TAG, "onResponse测试: 注册信息"+responseString2);
                    JSONObject jsonObject=new JSONObject(responseString2);
                    String code=jsonObject.getString("code");
                    String msg=jsonObject.getString("msg");
                    String data=jsonObject.getString("data");
                    if (data.charAt(0)!='{'){
                        //这里是注册失败,返回data无效的判断,可以后续添加业务逻辑
                    }else{
                        //注册成功,进行json解析和数据持久化,数据静态存储的操作
                        JSONObject jsonObject2=jsonObject.getJSONObject("data");
                        String userId=jsonObject2.getString("user_id");
                        String tkString=jsonObject2.getString("token");
                        LoginStaticData.token=tkString;
                        LoginStaticData.userId=userId;
//                            Log.i(TAG, "onResponse: JSon解析测试:"+data);
                        Log.i(TAG, "onResponse: JSon解析测试:"+userId+" "+tkString);

                        //这里是进行数据SP持久化+页面跳转
                        DataSaveSP dataSaveSP=new DataSaveSP();
                        boolean b=dataSaveSP.dataSave(tkString,userId,SignUpActivity.this);
                        //注册完成之后,在存储完token和userId之后则表示登录成功,跳转到主页面
                        if (b){
                            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast toast=Toast.makeText(SignUpActivity.this,"",Toast.LENGTH_SHORT);
                            toast.setText("系统错误,注册无效");
                            toast.show();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onResponse: "+t.toString());
            }
        });

    }





    @Override
    protected void onPause() {
        super.onPause();
        //验证一下静态变量的数据(数据持久化确认存入,没有验证必要)
        Log.i(TAG, "onPause: 静态变量数据"+LoginStaticData.token+" "+LoginStaticData.userId);
    }
}
