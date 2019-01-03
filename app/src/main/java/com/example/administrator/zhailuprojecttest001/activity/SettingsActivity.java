package com.example.administrator.zhailuprojecttest001.activity;

import android.content.Context;
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
                loginQuit();
                finish();
                Intent intent=new Intent(SettingsActivity.this,MainActivity.class);
                break;
        }
    }

    public void loginQuit(){
        SharedPreferences sharedPreferences=getSharedPreferences("zhailu",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        boolean b=editor.commit();
        Log.i(TAG, "loginQuit: 是否成功清空SP数据 "+b);
    }
}
