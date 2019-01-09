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
import com.example.administrator.zhailuprojecttest001.activity.SettingsActivity;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data7ResetPw;
import com.example.administrator.zhailuprojecttest001.util.FormatVf;
import com.example.administrator.zhailuprojecttest001.util.LoginQuit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetbackPw3Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GetbackPw3Activity";
    private String telephone;
    private String code;

    private String responseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getback_pw3);

        Intent intent=getIntent();
        telephone=intent.getStringExtra("telephone");
        code=intent.getStringExtra("code");
        Log.i(TAG, "onCreate: "+telephone+" "+code);

        EditText editText=findViewById(R.id.editText1);
        initClick();

    }


    public void initClick(){
        Button button=findViewById(R.id.button_sign_in);
        button.setOnClickListener(this);
        LinearLayout linearLayout=findViewById(R.id.ll_cancel);
        linearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        FormatVf formatVf=new FormatVf();
        EditText editText=findViewById(R.id.editText1);
        String password=editText.getText().toString();
        int id=v.getId();
        switch (id){
            case R.id.button_sign_in:
                if (formatVf.isPassword(password)){
                    retrofitV7(telephone,code,password);
                }else {
                    Toast toast=Toast.makeText(GetbackPw3Activity.this,"",Toast.LENGTH_SHORT);
                    toast.setText("请输入6-16位字母数字混合密码,首位不为数字");
                    toast.show();
                }
                break;
            case R.id.ll_cancel:
                finish();
                break;
        }
    }

    //修改密码的验证码和手机号合法性请求
    public void retrofitV7(final String telephone,final String code,final String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/Login/")
                .build();
        Data7ResetPw data7ResetPw =retrofit.create(Data7ResetPw.class);
        Call<ResponseBody> call= data7ResetPw.postData(telephone,code,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString=response.body().string();
                    Log.i(TAG, "onResponse测试: "+responseString);
                    JSONObject jsonObject=new JSONObject(responseString);
                    String code2=jsonObject.getString("code");
                    String msg=jsonObject.getString("msg");
                    String data=jsonObject.getString("data");
                    Log.i(TAG, "onResponse: 测试json解析"+code2+" "+msg+" "+data);
                    if (!msg.equals("success")){

                        Toast toast=Toast.makeText(GetbackPw3Activity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("密码重置错误");
                        toast.show();
                        Log.i(TAG, "onResponse: 密码重置错误");
                    }else{
                        Toast toast=Toast.makeText(GetbackPw3Activity.this,"",Toast.LENGTH_SHORT);
                        toast.setText("密码重置成功");
                        toast.show();
                        int type=0;
                        new LoginQuit().loginQuit(GetbackPw3Activity.this,type);
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
