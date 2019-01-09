package com.example.administrator.zhailuprojecttest001.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.zhailuprojecttest001.R;


public class OpenPhoneDialog extends Dialog {
    private static final String TAG = "OpenPhoneDialog";
    private IcallPhone icallPhone;

    public void setIcallPhone(IcallPhone icallPhone) {
        this.icallPhone = icallPhone;
    }

    public OpenPhoneDialog(Context context) {
        super(context);
    }

    public OpenPhoneDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_item_phone);
        initView();
    }
    private void initView(){
        Button buttonCancel=findViewById(R.id.button_cancel_phone);
        Button buttonCall=findViewById(R.id.button_call_phone);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接调用回调方法跳转拨号页面无需请求权限
                //回调方法在调用dialog的activity中实现
                icallPhone.callPhone();
            }
        });
    }


    public interface IcallPhone{
        public void callPhone();
    }

    @Override
    public void show() {
        super.show();
    }
}
