package com.example.administrator.zhailuprojecttest001.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.activity.WalletActivity;
import com.example.administrator.zhailuprojecttest001.walletItem.ConsumeActivity;
import com.example.administrator.zhailuprojecttest001.walletItem.CouponActivity;
import com.example.administrator.zhailuprojecttest001.walletItem.RechargeActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initClick();
    }

    public void initClick() {
        TextView textView = findViewById(R.id.textview_sign_up);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.textview_sign_up:
                Intent intent1 = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent1);
                break;
        }
    }

}
