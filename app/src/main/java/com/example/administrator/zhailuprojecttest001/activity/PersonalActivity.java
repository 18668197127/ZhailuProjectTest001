package com.example.administrator.zhailuprojecttest001.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_d);
        initFirst();
    }

    public void initFirst(){
        TextView title=findViewById(R.id.layout_personal_title).findViewById(R.id.textview_title);
        title.setText("我的资料");
        LinearLayout llSave=findViewById(R.id.layout_personal_title).findViewById(R.id.ll_settings_save);
        llSave.setVisibility(View.VISIBLE);
        initFirstClick();
    }

    public void initFirstClick(){
        LinearLayout llTitleBack=findViewById(R.id.layout_personal_title).findViewById(R.id.ll_settings_back);
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
