package com.example.administrator.zhailuprojecttest001.activity;

import android.app.Person;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.GlideApp;
import com.example.administrator.zhailuprojecttest001.MainActivity;
import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.dialog.OpenPhoneDialog;

import com.example.administrator.zhailuprojecttest001.gsonData6_7.Result6;
import com.example.administrator.zhailuprojecttest001.retrofit.Data7UserCenter;
import com.example.administrator.zhailuprojecttest001.util.GetSPData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserCenterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UserCenterActivity";
    private String responseString;
    private Result6 result6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter_c);
        initFirst();
        String userId=new GetSPData().getSPUserID(UserCenterActivity.this);
        retrofitV7(userId);
    }

    //用于更新title和底部图片
    public void initFirst(){
        TextView textView=findViewById(R.id.textview_title);
        textView.setText("个人中心");
        ImageButton imageButton=findViewById(R.id.imagebutton_homepage);
        imageButton.setBackgroundResource(R.drawable.icon_shouye_jianying_wenzi);
        ImageButton imageButton1=findViewById(R.id.imagebutton_me);
        imageButton1.setBackgroundResource(R.drawable.icon_wo_jianying_wenzi);
        LinearLayout linearLayout=findViewById(R.id.main_b_title);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white));

        imageButton.setOnClickListener(this);
        ImageButton imageButton2=findViewById(R.id.imagebutton_order);
        imageButton2.setOnClickListener(this);

        TextView textViewPhone=findViewById(R.id.text_open_phone);
        textViewPhone.setOnClickListener(this);

        LinearLayout ll_5=findViewById(R.id.ll_list_5);
        ll_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCenterActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout ll_1=findViewById(R.id.ll_list_1);
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCenterActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout ll_2=findViewById(R.id.ll_list_2);
        ll_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCenterActivity.this,WalletActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.imagebutton_homepage:
                Intent intent=new Intent(UserCenterActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.imagebutton_order:
                Intent intent2=new Intent(UserCenterActivity.this,OrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.text_open_phone:
                OpenPhoneDialog openPhoneDialog=new OpenPhoneDialog(UserCenterActivity.this);
                openPhoneDialog.setIcallPhone(new OpenPhoneDialog.IcallPhone() {
                    @Override
                    public void callPhone() {
                        Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + 4001790720l));
                        startActivity(intentPhone);
                    }
                });
                openPhoneDialog.show();
                break;
        }
    }

    //个人中心页面的数据获取,通过user_id获取头像,手机,昵称
    //例如:{"code":1,"msg":"success","data":{"id":"2","telephone":"18098211997","nickname":"18098211997","avatar":"url地址"}}
    public void retrofitV7(final String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.mouqukeji.com/api/v1/user/")
                .build();
        Data7UserCenter data7UserCenter = retrofit.create(Data7UserCenter.class);
        Call<ResponseBody> call = data7UserCenter.getData(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString = response.body().string();
                    Log.i(TAG, "onResponse测试: " + responseString);
                    JSONObject jsonObject = new JSONObject(responseString);
                    String code2 = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");
                    String data=jsonObject.getString("data");
                    Log.i(TAG, "onResponse: 测试json解析" + code2 + " " + msg +" "+data);
                    if (data==null){
                        //数据请求为空
                    }else if (!"success".equals(msg)){
                        //数据请求失败
                    }else {
                        //数据请求成功,解析data
                        Gson gson=new Gson();
                        result6=gson.fromJson(data,Result6.class);
                        Log.i(TAG, "onResponse: 验证data解析是否成功: "+result6.getId()+" "+result6.getTelephone()+" "+result6.getNickname()+" "+result6.getAvatar());
                        showResponseResult();
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
    //网络请求后的更新UI界面操作
    public void showResponseResult(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String telephone=result6.getTelephone();
                String nickName=result6.getNickname();
                String avatar=result6.getAvatar();
                TextView textViewNickName=findViewById(R.id.text_user_nickname);
                textViewNickName.setText(nickName);
                TextView textViewAccount=findViewById(R.id.text_user_account);
                textViewAccount.setText(telephone);
                CircleImageView circleImageView=findViewById(R.id.circle_head);
                GlideApp.with(UserCenterActivity.this).load(avatar).into(circleImageView);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //如果修改过个人信息则重新网络请求
        if (PersonalActivity.flagSave==1){
            String userId=new GetSPData().getSPUserID(UserCenterActivity.this);
            retrofitV7(userId);
            //重新置0
            PersonalActivity.flagSave=0;
        }
    }
}
