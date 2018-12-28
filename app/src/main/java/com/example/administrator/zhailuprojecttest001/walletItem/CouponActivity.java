package com.example.administrator.zhailuprojecttest001.walletItem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;

public class CouponActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_coupon);
        initFirst();
    }

    public void initFirst(){
        TextView textView=findViewById(R.id.layout_coupon_title).findViewById(R.id.textview_title);
        textView.setText("优惠券");
    }
}
