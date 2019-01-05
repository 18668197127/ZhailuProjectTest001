package com.example.administrator.zhailuprojecttest001.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhailuprojecttest001.MainActivity;
import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data3Login;
import com.example.administrator.zhailuprojecttest001.staticData.LoginStaticData;
import com.example.administrator.zhailuprojecttest001.util.DataSaveSP;
import com.example.administrator.zhailuprojecttest001.util.FormatVf;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignInActivity";
    private String responseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initClick();
    }

    public void initClick() {
        TextView textView = findViewById(R.id.textview_sign_up);
        textView.setOnClickListener(this);
        Button buttonLogin=findViewById(R.id.button_sign_in);
        buttonLogin.setOnClickListener(this);
        TextView textviewForgot=findViewById(R.id.textview_forget);
        textviewForgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //初始化工具类,格式验证
        FormatVf formatVf=new FormatVf();

        int id = v.getId();
        switch (id) {
            case R.id.textview_sign_up:
                Intent intent1 = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent1);
                break;
            case R.id.textview_forget:
                Intent intent2=new Intent(SignInActivity.this,GetbackPw1Activity.class);
                startActivity(intent2);
                break;
            case R.id.button_sign_in:
                EditText editText1=findViewById(R.id.editText1);
                String editText1String=editText1.getText().toString();
                EditText editText2=findViewById(R.id.editText2);
                String editText2String=editText2.getText().toString();
                if (!formatVf.isPhone(editText1String)){
                    Toast toast=Toast.makeText(SignInActivity.this,"",Toast.LENGTH_SHORT);
                    toast.setText("请输入正确格式的手机号码");
                    toast.show();
                }else if (!formatVf.isPassword(editText2String)){
                    Toast toast=Toast.makeText(SignInActivity.this,"",Toast.LENGTH_SHORT);
                    toast.setText("请输入6-16位字母数字混合密码,首位不为数字");
                    toast.show();
                }else {
                    //格式验证通过,进行后续操作:网络请求
                    retrofitV4(editText1String,editText2String);
                }
                break;
        }
    }

    //登录接口post请求
    public void retrofitV4(final String telephone, final String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/Login/")
                .build();
        Data3Login data3Login =retrofit.create(Data3Login.class);
        Call<ResponseBody> call=data3Login.postData(telephone,password);
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
                    if (data.charAt(0)!='{'){
                        //这里是登录失败,返回data无效的判断,可以后续添加业务逻辑
                        Toast toast=Toast.makeText(SignInActivity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("账户或密码错误");
                        toast.show();
                    }else {
                        //这里是登录成功,获取data数据,进行后续业务逻辑代码:登录数据持久化
                        JSONObject jsonObject2=jsonObject.getJSONObject("data");
                        String userId=jsonObject2.getString("user_id");
                        String token=jsonObject2.getString("token");
                        //静态数据存储
                        LoginStaticData.userId=userId;
                        LoginStaticData.token=token;
                        Log.i(TAG, "onResponse: JSon解析测试:"+userId+" "+token);

                        //这里是进行数据SP持久化+页面跳转
                        DataSaveSP dataSaveSP=new DataSaveSP();
                        boolean b=dataSaveSP.dataSave(token,userId,SignInActivity.this);
                        //注册完成之后,在存储完token和userId之后则表示登录成功,跳转到主页面
                        if (b){
                            Intent intent=new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast toast=Toast.makeText(SignInActivity.this,"",Toast.LENGTH_SHORT);
                            toast.setText("系统错误,登录无效");
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

}
