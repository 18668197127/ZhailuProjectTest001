package com.example.administrator.zhailuprojecttest001.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.MainActivity;
import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.dialog.OpenPhoneDialog;

public class UserCenterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UserCenterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter_c);
        initFirst();
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
}
