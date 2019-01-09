package com.example.administrator.zhailuprojecttest001.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.Toast;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data5GetCode2;
import com.example.administrator.zhailuprojecttest001.util.FormatVf;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetbackPw1Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GetbackPw1Activity";
    
    private String responseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getback_pw);
        initClick();
    }

    public void initClick() {
        Button buttonNext=findViewById(R.id.button_next);
        buttonNext.setOnClickListener(this);
        //这个是测试按钮,正式版请删除,layout中也要删除
        Button button2=findViewById(R.id.button_test);
        button2.setOnClickListener(this);
        LinearLayout linearLayout=findViewById(R.id.ll_cancel);
        linearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //初始化工具类,格式验证
        FormatVf formatVf = new FormatVf();

        EditText editText1=findViewById(R.id.editText1);
        String editText1String=editText1.getText().toString();

        int id = v.getId();
        switch (id) {
            case R.id.button_next:

                if (!formatVf.isPhone(editText1String)){
                    Toast toast=Toast.makeText(GetbackPw1Activity.this,"",Toast.LENGTH_SHORT);
                    toast.setText("请输入正确格式的手机号码");
                    toast.show();
                }else {
                    //格式验证通过,进行后续操作:网络请求验证码
                    retrofitV5(editText1String);
                }
                break;
            case R.id.button_test:
                //这里是测试按钮,正式版请删除
                Intent intent=new Intent(GetbackPw1Activity.this,GetbackPw2Activity.class);
                intent.putExtra("telephone",editText1String);
                startActivity(intent);
                break;
            case R.id.ll_cancel:
                finish();
                break;
        }
    }
    //登录接口post请求
    public void retrofitV5(final String telephone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/Login/")
                .build();
        Data5GetCode2 data5GetCode2 =retrofit.create(Data5GetCode2.class);
        Call<ResponseBody> call=data5GetCode2.postData(telephone);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString=response.body().string();
                    Log.i(TAG, "onResponse测试: "+responseString);
                    JSONObject jsonObject=new JSONObject(responseString);
                    String code=jsonObject.getString("code");
                    String msg=jsonObject.getString("msg");
                    String data=jsonObject.getString("data");
                    Log.i(TAG, "onResponse: 测试json解析"+code+" "+msg+" "+data);
                    if (!msg.equals("success")){
                        Toast toast=Toast.makeText(GetbackPw1Activity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("短信验证码请求发送异常");
                        toast.show();
                    }else{
                        Intent intent=new Intent(GetbackPw1Activity.this,GetbackPw2Activity.class);
                        intent.putExtra("telephone",telephone);
                        startActivity(intent);
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
}
