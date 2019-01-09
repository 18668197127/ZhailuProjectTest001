package com.example.administrator.zhailuprojecttest001.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;

public class ZhaiLuAboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhailu_about);
        initFirst();
    }

    //用于更新title和底部图片
    public void initFirst(){
        TextView textView=findViewById(R.id.textview_title);
        textView.setText("关于宅鹿");
        LinearLayout llTitleBack=findViewById(R.id.layout_settings_title).findViewById(R.id.ll_settings_back);
        llTitleBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ll_settings_back:
                finish();
                break;
        }
    }
}
