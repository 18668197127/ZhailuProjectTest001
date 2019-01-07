package com.example.administrator.zhailuprojecttest001.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data5GetCode2;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data6CheckCode;
import com.example.administrator.zhailuprojecttest001.util.FormatVf;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetbackPw2Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GetbackPw2Activity";

    private String responseString;
    //这个是验证码失败次数
    private int time=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getback_pw2);
        initEdit();
    }

    public void initEdit(){
        final EditText editText1=findViewById(R.id.editText1);
        final EditText editText2=findViewById(R.id.editText2);
        final EditText editText3=findViewById(R.id.editText3);
        final EditText editText4=findViewById(R.id.editText4);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==1){
                    editText2.requestFocus();
                    int e2=editText2.getText().toString().length();
                    int e3=editText3.getText().toString().length();
                    int e4=editText4.getText().toString().length();
                    if (e2==1&&e3==1&&e4==1){
                        Intent intent=getIntent();
                        String telephone=intent.getStringExtra("telephone");
                        String code="";
                        code=editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString();
                        Log.i(TAG, "afterTextChanged: "+telephone+" "+code);
                        retrofitV6(telephone,code);

                    }
                }
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==1){

                    editText3.requestFocus();
                    int e1=editText1.getText().toString().length();
                    int e3=editText3.getText().toString().length();
                    int e4=editText4.getText().toString().length();
                    if (e1==1&&e3==1&&e4==1){
                        Intent intent=getIntent();
                        String telephone=intent.getStringExtra("telephone");
                        String code="";
                        code=editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString();
                        Log.i(TAG, "afterTextChanged: "+telephone+" "+code);
                        retrofitV6(telephone,code);

                    }
                }
            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==1){

                    editText4.requestFocus();
                    int e1=editText1.getText().toString().length();
                    int e2=editText2.getText().toString().length();
                    int e4=editText4.getText().toString().length();
                    if (e1==1&&e2==1&&e4==1){
                        Intent intent=getIntent();
                        String telephone=intent.getStringExtra("telephone");
                        String code="";
                        code=editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString();
                        Log.i(TAG, "afterTextChanged: "+telephone+" "+code);
                        retrofitV6(telephone,code);

                    }
                }
            }
        });
        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //这里做网络请求的判断
                if (s.length()==1){

                    int e1=editText1.getText().toString().length();
                    int e2=editText2.getText().toString().length();
                    int e3=editText3.getText().toString().length();
                    if (e1==1&&e2==1&&e3==1){
                        Intent intent=getIntent();
                        String telephone=intent.getStringExtra("telephone");
                        String code="";
                        code=editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString();
                        Log.i(TAG, "afterTextChanged: "+telephone+" "+code);
                        retrofitV6(telephone,code);

                    }
                }
            }
        });
    }

    public void initClick() {
        Button button=findViewById(R.id.button_again);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_again:
                break;


        }
    }

    //修改密码的验证码和手机号合法性请求
    public void retrofitV6(final String telephone,final String code) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/Login/")
                .build();
        Data6CheckCode data6CheckCodea =retrofit.create(Data6CheckCode.class);
        Call<ResponseBody> call=data6CheckCodea.postData(telephone,code);
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
                        time=time+1;
                        System.out.println(time);
                        if (time>8){
                            Toast toast=Toast.makeText(GetbackPw2Activity.this,"",Toast.LENGTH_SHORT);
                            toast.setText("验证码失败次数过多");
                            toast.show();
                            finish();
                        }else {
                            Toast toast=Toast.makeText(GetbackPw2Activity.this,"",Toast.LENGTH_SHORT);
                            toast.setText("验证码输入有误");
                            toast.show();
                        }
                    }else{
                        Intent intent=new Intent(GetbackPw2Activity.this,GetbackPw3Activity.class);
                        intent.putExtra("telephone",telephone);
                        intent.putExtra("code",code);
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: "+time);
    }
}
