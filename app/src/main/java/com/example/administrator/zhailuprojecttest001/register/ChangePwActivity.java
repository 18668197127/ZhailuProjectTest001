package com.example.administrator.zhailuprojecttest001.register;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.administrator.zhailuprojecttest001.retrofit2.Data8ChangePw;
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

public class ChangePwActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ChangePwActivity";

    private String responseString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);
        initClick();
    }

    public void initClick() {
        LinearLayout linearLayout=findViewById(R.id.ll_cancel);
        linearLayout.setOnClickListener(this);
        Button buttonChange=findViewById(R.id.button_change);
        buttonChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ll_cancel:
                finish();
                break;
            case R.id.button_change:
                FormatVf formatVf=new FormatVf();
                EditText editText1=findViewById(R.id.editText1);
                String editTextString1=editText1.getText().toString();
                EditText editText2=findViewById(R.id.editText2);
                String editTextString2=editText2.getText().toString();
                GetSPData getSPData=new GetSPData();
                String userId=getSPData.getSPUserID(ChangePwActivity.this);
                Log.i(TAG, "onClick: 输出旧密码: "+editTextString1+" 输出新密码: "+editTextString2+" "+userId);
                if (formatVf.isPassword(editTextString1)&formatVf.isPassword(editTextString2)){
                    //密码格式正确
                    retrofitV8(userId,editTextString1,editTextString2);
                }else {
                    Toast toast=Toast.makeText(ChangePwActivity.this,"",Toast.LENGTH_SHORT);
                    toast.setText("请输入6-16位字母数字混合密码,首位不为数字");
                    toast.show();
                }
                break;
        }
    }

    //修改密码,参数为user_id,password,new_password
    public void retrofitV8(final String userId,final String password,final String newPassword) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.mouqukeji.com/api/v1/user/")
                .build();
        Data8ChangePw data8ChangePw = retrofit.create(Data8ChangePw.class);
        Call<ResponseBody> call = data8ChangePw.postData(userId, password, newPassword);
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
                        Toast toast = Toast.makeText(ChangePwActivity.this, "", Toast.LENGTH_SHORT);
                        toast.setText("旧密码输入错误");
                        toast.show();
                        Log.i(TAG, "onResponse: 密码修改错误");
                    } else {
//                        String data = jsonObject.getString("data");
//                        Log.i(TAG, "onResponse: "+data);
                        Toast toast = Toast.makeText(ChangePwActivity.this, "", Toast.LENGTH_SHORT);
                        toast.setText("密码修改成功");
                        toast.show();
                        int type=1;
                        new LoginQuit().loginQuit(ChangePwActivity.this,type);
                    }
                    Log.i(TAG, "onResponse: 是否执行完成");
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
