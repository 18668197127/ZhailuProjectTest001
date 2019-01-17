package com.example.administrator.zhailuprojecttest001.SettingItem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;

import org.w3c.dom.Text;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initFirst();
    }

    public void initFirst(){
        TextView textView=findViewById(R.id.textview_title);
        textView.setText("意见反馈");
        TextView textView1=findViewById(R.id.textView9);
        textView1.setFocusableInTouchMode(true);
        initClick();
    }

    public void initClick(){
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

    public void initRecyclerView(){
        RecyclerView recyclerView=findViewById(R.id.feedback_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(FeedBackActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}
