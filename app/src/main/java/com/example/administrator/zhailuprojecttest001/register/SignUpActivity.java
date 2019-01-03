package com.example.administrator.zhailuprojecttest001.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data1Register;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data2GetCode;

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
        int id = v.getId();
        switch (id) {
            case R.id.button_get_captcha:
                Log.i(TAG, "onClick: "+editText1String+" "+editText1String.length());
                if (isPhone(editText1String)){
                    retrofitV1(editText1String);
                }else {
                    Log.i(TAG, "onClick: 请输入正确格式的手机号码");
                    Toast toast=Toast.makeText(SignUpActivity.this,null,Toast.LENGTH_SHORT);
                    toast.setText("请输入正确格式的手机号码");
                    toast.show();
                }
                break;
            case R.id.button_sign_up:
                Log.i(TAG, "onClick: "+editText3String+" "+editText3String.length());
                if (isPassword(editText3String)&&isVf(editText2String)&&isPhone(editText1String)){
                    Log.i(TAG, "onClick注册: 三个文本输入格式正确");
                    retrofitV2(editText1String,editText2String,editText3String);
                }else {
                    if (!isPassword(editText3String)){
                        Toast toast=Toast.makeText(SignUpActivity.this,null,Toast.LENGTH_SHORT);
                        toast.setText("请输入6-16位字母数字混合密码,首位不为数字");
                        toast.show();
                    }else if (!isVf(editText2String)){
                        Toast toast=Toast.makeText(SignUpActivity.this,null,Toast.LENGTH_SHORT);
                        toast.setText("请输入四位验证码");
                        toast.show();
                    }else if (!isPhone(editText1String)){
                        Toast toast=Toast.makeText(SignUpActivity.this,null,Toast.LENGTH_SHORT);
                        toast.setText("请输入正确格式的手机号码");
                        toast.show();
                    }else {
                        Log.i(TAG, "onClick: 注册未知错误");
                        Toast toast=Toast.makeText(SignUpActivity.this,null,Toast.LENGTH_SHORT);
                        toast.setText("注册未知错误");
                        toast.show();
                    }
                }
                break;
        }
    }

    //获取验证码的网络请求
    public void retrofitV1(final String phone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                                Toast toast=Toast.makeText(SignUpActivity.this,null,Toast.LENGTH_SHORT);
                                toast.setText("短信验证码请求发送异常");
                                toast.show();
                            }else{
                                Toast toast=Toast.makeText(SignUpActivity.this,null,Toast.LENGTH_SHORT);
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
        }).start();
    }

    //注册的网络请求返回登录成功的数据
    public void retrofitV2(final String phone , final String vf , final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                            JSONObject jsonObject2=new JSONObject(data);
                            String userId=jsonObject2.getString("user_id");
                            String tkString=jsonObject2.getString("token");
                            Log.i(TAG, "onResponse: JSon解析测试:"+userId+" "+tkString);
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
        }).start();
    }

    //判断输入的格式是否为手机号
    public boolean isPhone(String phone){
        String regex="^1[3456789]\\d{9}$";
        if (phone.length()!=11){
            Log.i(TAG, "isPhone: 手机位数不对");
            return false;
        }else {
            Pattern p=Pattern.compile(regex);
            Matcher m=p.matcher(phone);
            boolean isMatch=m.matches();
            Log.i(TAG, "isPhone: 是否手机正则匹配"+isMatch);
            return isMatch;
        }
    }
    //判断是否为密码格式
    public boolean isPassword(String password){
        String regex="^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(password);
        boolean isMatch=m.matches();
        Log.i(TAG, "isPassword: 是否密码正则匹配"+isMatch);
        return isMatch;
    }
    //判断是否为验证码格式
    public boolean isVf(String vf){
        String regex="^[0-9]{4}$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(vf);
        boolean isMatch=m.matches();
        Log.i(TAG, "isVf: 是否验证码正则匹配"+isMatch);
        return isMatch;
    }
}
