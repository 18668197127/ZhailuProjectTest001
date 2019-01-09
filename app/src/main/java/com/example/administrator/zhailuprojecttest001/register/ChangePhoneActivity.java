package com.example.administrator.zhailuprojecttest001.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data10GetCode3;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data8ChangePw;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data9ChangePhone;
import com.example.administrator.zhailuprojecttest001.util.FormatVf;
import com.example.administrator.zhailuprojecttest001.util.GetSPData;
import com.example.administrator.zhailuprojecttest001.util.LoginQuit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChangePhoneActivity";
    private String responseString;
    private String responseString2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        initClick();
    }

    public void initClick() {
        LinearLayout linearLayout=findViewById(R.id.ll_cancel);
        linearLayout.setOnClickListener(this);
        Button buttonChange=findViewById(R.id.button_change_phone);
        buttonChange.setOnClickListener(this);
        Button buttonGetCode=findViewById(R.id.button_get_captcha);
        buttonGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ll_cancel:
                finish();
                break;
            case R.id.button_change_phone:
                FormatVf formatVf=new FormatVf();
                EditText editText1=findViewById(R.id.editText1);
                String editTextString1=editText1.getText().toString();
                EditText editText2=findViewById(R.id.editText2);
                String editTextString2=editText2.getText().toString();
                GetSPData getSPData=new GetSPData();
                String userId=getSPData.getSPUserID(ChangePhoneActivity.this);
                Log.i(TAG, "onClick: 输出旧密码: "+editTextString1+" 输出新密码: "+editTextString2+" "+userId);
                if (formatVf.isPhone(editTextString1)&formatVf.isVf(editTextString2)){
                    //格式验证正确
                    retrofitV9(userId,editTextString1,editTextString2);
                }else {
                    //格式验证错误
                    if (!formatVf.isPhone(editTextString1)){
                        Toast toast=Toast.makeText(ChangePhoneActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("请输入正确的手机号");
                        toast.show();
                    }else if (!formatVf.isVf(userId)){
                        Toast toast=Toast.makeText(ChangePhoneActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("请输入四位验证码");
                        toast.show();
                    }
                }
                break;
            case R.id.button_get_captcha:
                FormatVf formatVf2=new FormatVf();
                EditText editText3=findViewById(R.id.editText1);
                String editTextString3=editText3.getText().toString();
                if (formatVf2.isPhone(editTextString3)){
                    //格式验证正确
                    retrofitV10(editTextString3,"3");
                }else {
                    //格式验证错误
                    Toast toast=Toast.makeText(ChangePhoneActivity.this,"",Toast.LENGTH_SHORT);
                    toast.setText("请输入正确的手机号");
                    toast.show();
                }
                break;
        }
    }
    //修改手机号,参数为user_id,new_phone,code
    public void retrofitV9(final String userId,final String newPhone,final String code) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.mouqukeji.com/api/v1/user/")
                .build();
        Data9ChangePhone data9ChangePhone = retrofit.create(Data9ChangePhone.class);
        Call<ResponseBody> call = data9ChangePhone.postData(userId, newPhone, code);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString = response.body().string();
                    Log.i(TAG, "onResponse测试: " + responseString);
                    JSONObject jsonObject = new JSONObject(responseString);
                    String code2 = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");
                    Log.i(TAG, "onResponse: 测试json解析" + code2 + " " + msg );
                    if (!msg.equals("success")) {
                        Toast toast = Toast.makeText(ChangePhoneActivity.this, "", Toast.LENGTH_SHORT);
                        toast.setText("验证码或者手机号错误");
                        toast.show();
                        Log.i(TAG, "onResponse: 验证码或者手机号错误");
                    } else {
//                        String data = jsonObject.getString("data");
//                        Log.i(TAG, "onResponse: "+data);
                        Toast toast = Toast.makeText(ChangePhoneActivity.this, "", Toast.LENGTH_SHORT);
                        toast.setText("手机号修改成功");
                        toast.show();
                        int type=1;
                        new LoginQuit().loginQuit(ChangePhoneActivity.this,type);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onResponse: " + t.toString());
            }
        });
    }

    //修改手机号,新手机号的验证码发送请求
    public void retrofitV10(final String newPhone,final String type) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.mouqukeji.com/api/Login/")
                .build();
        Data10GetCode3 data10GetCode3 = retrofit.create(Data10GetCode3.class);
        Call<ResponseBody> call = data10GetCode3.postData(newPhone, type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString2 = response.body().string();
                    Log.i(TAG, "onResponse测试: " + responseString2);
                    JSONObject jsonObject = new JSONObject(responseString2);
                    String code2 = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");
                    Log.i(TAG, "onResponse: 测试json解析" + code2 + " " + msg );
                    if (!msg.equals("success")) {
                        Toast toast = Toast.makeText(ChangePhoneActivity.this, "", Toast.LENGTH_SHORT);
                        toast.setText("验证码发送失败");
                        toast.show();
                        Log.i(TAG, "onResponse: 验证码发送失败");
                    } else {
//                        String data = jsonObject.getString("data");
//                        Log.i(TAG, "onResponse: "+data);
                        Toast toast = Toast.makeText(ChangePhoneActivity.this, "", Toast.LENGTH_SHORT);
                        toast.setText("验证码已发送");
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
                Log.i(TAG, "onResponse: " + t.toString());
            }
        });
    }
}
