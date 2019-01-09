package com.example.administrator.zhailuprojecttest001.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.MainActivity;
import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.register.ChangePhoneActivity;
import com.example.administrator.zhailuprojecttest001.register.ChangePwActivity;
import com.example.administrator.zhailuprojecttest001.register.SignInActivity;
import com.example.administrator.zhailuprojecttest001.util.LoginQuit;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_d);
        initFirst();
    }

    public void initFirst(){
        initFirstClick();
    }

    public void initFirstClick(){
        LinearLayout llTitleBack=findViewById(R.id.layout_settings_title).findViewById(R.id.ll_settings_back);
        llTitleBack.setOnClickListener(this);
        Button buttonQuit=findViewById(R.id.button_settings_quit);
        buttonQuit.setOnClickListener(this);
        LinearLayout llChangePw=findViewById(R.id.ll_settings_list_1);
        llChangePw.setOnClickListener(this);
        LinearLayout llChangePhone=findViewById(R.id.ll_settings_list_2);
        llChangePhone.setOnClickListener(this);
        LinearLayout llAbout=findViewById(R.id.ll_settings_list_5);
        llAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ll_settings_back:
                finish();
                break;
            case R.id.button_settings_quit:
                //这里是退出登录的操作
                int type=1;
                new LoginQuit().loginQuit(SettingsActivity.this,type);
                break;
            case R.id.ll_settings_list_1:
                Intent intent2=new Intent(SettingsActivity.this,ChangePwActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_settings_list_2:
                Intent intent3=new Intent(SettingsActivity.this,ChangePhoneActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_settings_list_5:
                Intent intent4=new Intent(SettingsActivity.this,ZhaiLuAboutActivity.class);
                startActivity(intent4);
                break;
        }
    }


}
