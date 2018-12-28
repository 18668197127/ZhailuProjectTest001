package com.example.administrator.zhailuprojecttest001.walletItem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;

public class ConsumeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_consume);
        initFirst();
    }

    public void initFirst(){
        TextView textView=findViewById(R.id.layout_consume_title).findViewById(R.id.textview_title);
        textView.setText("消费明细");
    }
}
