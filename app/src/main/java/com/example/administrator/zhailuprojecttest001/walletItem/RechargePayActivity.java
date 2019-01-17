package com.example.administrator.zhailuprojecttest001.walletItem;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;

import org.w3c.dom.Text;

public class RechargePayActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RechargePayActivity";
    public int selectAmount=0;
    public int selectPayType=0;

    private LinearLayout llChoice1;
    private LinearLayout llChoice2;
    private LinearLayout llChoice3;
    private LinearLayout llChoice4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_pay);
        initClick();
        RadioGroup radioGroup=findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.radio_recharge_1){
                    selectPayType=1;
                }else if (checkedId==R.id.radio_recharge_2){
                    selectPayType=2;
                }
            }
        });
    }

    public void initClick(){
        llChoice1=findViewById(R.id.ll_choice_1);
        llChoice2=findViewById(R.id.ll_choice_2);
        llChoice3=findViewById(R.id.ll_choice_3);
        llChoice4=findViewById(R.id.ll_choice_4);
        llChoice1.setOnClickListener(this);
        llChoice2.setOnClickListener(this);
        llChoice3.setOnClickListener(this);
        llChoice4.setOnClickListener(this);
        View viewClickBack=findViewById(R.id.view_click_rgcf_back);
        viewClickBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ll_choice_1:
                if (selectAmount==1){
                    selectAmount=0;
                    unSelectChange(1);
                }else if (selectAmount==2||selectAmount==3||selectAmount==4){
                    selectChange(1);
                    unSelectChange(selectAmount);
                    selectAmount=1;
                }else if (selectAmount==0){
                    selectChange(1);
                    selectAmount=1;
                }
                break;
            case R.id.ll_choice_2:
                if (selectAmount==2){
                    selectAmount=0;
                    unSelectChange(2);
                }else if (selectAmount==1||selectAmount==3||selectAmount==4){
                    selectChange(2);
                    unSelectChange(selectAmount);
                    selectAmount=2;
                }else if (selectAmount==0){
                    selectChange(2);
                    selectAmount=2;
                }
                break;
            case R.id.ll_choice_3:
                if (selectAmount==3){
                    selectAmount=0;
                    unSelectChange(3);
                }else if (selectAmount==2||selectAmount==1||selectAmount==4){
                    selectChange(3);
                    unSelectChange(selectAmount);
                    selectAmount=3;
                }else if (selectAmount==0){
                    selectChange(3);
                    selectAmount=3;
                }
                break;
            case R.id.ll_choice_4:
                if (selectAmount==4){
                    selectAmount=0;
                    unSelectChange(4);
                }else if (selectAmount==2||selectAmount==3||selectAmount==1){
                    selectChange(4);
                    unSelectChange(selectAmount);
                    selectAmount=4;
                }else if (selectAmount==0){
                    selectChange(4);
                    selectAmount=4;
                }
                break;
            case R.id.view_click_rgcf_back:
                finish();
                break;
        }
    }

    private void selectChange(int id){
        if (id==1){
            llChoice1.setBackgroundResource(R.drawable.ll_shape003);
            TextView textView1=findViewById(R.id.text_choice_1);
            textView1.setTextColor(getResources().getColor(R.color.blue_recharge));
            TextView amountText=findViewById(R.id.text_confirm_number);
            amountText.setText("¥100");
        }else if (id==2){
            llChoice2.setBackgroundResource(R.drawable.ll_shape003);
            TextView textView2=findViewById(R.id.text_choice_2);
            textView2.setTextColor(getResources().getColor(R.color.blue_recharge));
            TextView amountText=findViewById(R.id.text_confirm_number);
            amountText.setText("¥200");
        }else if (id==3){
            llChoice3.setBackgroundResource(R.drawable.ll_shape003);
            TextView textView3=findViewById(R.id.text_choice_3);
            textView3.setTextColor(getResources().getColor(R.color.blue_recharge));
            TextView amountText=findViewById(R.id.text_confirm_number);
            amountText.setText("¥500");
        }else if (id==4){
            llChoice4.setBackgroundResource(R.drawable.ll_shape003);
            TextView textView4=findViewById(R.id.text_choice_4);
            textView4.setTextColor(getResources().getColor(R.color.blue_recharge));
            TextView amountText=findViewById(R.id.text_confirm_number);
            amountText.setText("¥1000");
        }
    }
    private void unSelectChange(int id){
        if (id==1){
            llChoice1.setBackgroundResource(R.drawable.ll_shape001);
            TextView textView1=findViewById(R.id.text_choice_1);
            textView1.setTextColor(getResources().getColor(R.color.black));
        }else if (id==2){
            llChoice2.setBackgroundResource(R.drawable.ll_shape001);
            TextView textView2=findViewById(R.id.text_choice_2);
            textView2.setTextColor(getResources().getColor(R.color.black));
        }else if (id==3){
            llChoice3.setBackgroundResource(R.drawable.ll_shape001);
            TextView textView3=findViewById(R.id.text_choice_3);
            textView3.setTextColor(getResources().getColor(R.color.black));
        }else if (id==4){
            llChoice4.setBackgroundResource(R.drawable.ll_shape001);
            TextView textView4=findViewById(R.id.text_choice_4);
            textView4.setTextColor(getResources().getColor(R.color.black));
        }
    }
}
