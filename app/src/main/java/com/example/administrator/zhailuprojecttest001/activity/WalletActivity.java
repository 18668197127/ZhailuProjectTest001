package com.example.administrator.zhailuprojecttest001.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.gsonData2.Result2;
import com.example.administrator.zhailuprojecttest001.retrofit.Data3Wallet;
import com.example.administrator.zhailuprojecttest001.retrofit.ZhailuData2;
import com.example.administrator.zhailuprojecttest001.staticData.LoginStaticData;
import com.example.administrator.zhailuprojecttest001.util.GetSPData;
import com.example.administrator.zhailuprojecttest001.walletItem.ConsumeActivity;
import com.example.administrator.zhailuprojecttest001.walletItem.CouponActivity;
import com.example.administrator.zhailuprojecttest001.walletItem.RechargeActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.administrator.zhailuprojecttest001.staticData.LoginStaticData.token;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "WalletActivity";

    private String responseString;
    private String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_d);
        initFirst();
        GetSPData getSPData=new GetSPData();
        String userID=getSPData.getSPUserID(WalletActivity.this);
        retrofitGetData2(userID);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void initFirst(){
        LinearLayout llTitle=findViewById(R.id.layout_wallet_title);
        llTitle.setBackgroundResource(R.color.default_gray);
        TextView title=llTitle.findViewById(R.id.textview_title);
        title.setText("我的钱包");
       initFirstClick();
    }

    public void initFirstClick(){
        LinearLayout ll_coupon=findViewById(R.id.ll_wallet_coupon);
        ll_coupon.setOnClickListener(this);
        CircleImageView circleImageView1=findViewById(R.id.circle_recharge);
        CircleImageView circleImageView2=findViewById(R.id.circle_consume);
        TextView textViewRecharge=findViewById(R.id.textview_wallet_recharge);
        TextView textViewConsume=findViewById(R.id.textview_wallet_consume);
        circleImageView1.setOnClickListener(this);
        circleImageView2.setOnClickListener(this);
        textViewRecharge.setOnClickListener(this);
        textViewConsume.setOnClickListener(this);
        LinearLayout llTitleBack=findViewById(R.id.layout_wallet_title).findViewById(R.id.ll_settings_back);
        llTitleBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ll_wallet_coupon:
                Intent intent1=new Intent(WalletActivity.this,CouponActivity.class);
                startActivity(intent1);
                break;
            case R.id.circle_recharge:
                Intent intent2=new Intent(WalletActivity.this,RechargeActivity.class);
                startActivity(intent2);
                break;
            case R.id.circle_consume:
                Intent intent3=new Intent(WalletActivity.this,ConsumeActivity.class);
                startActivity(intent3);
                break;
            case R.id.textview_wallet_recharge:
                Intent intent4=new Intent(WalletActivity.this,RechargeActivity.class);
                startActivity(intent4);
                break;
            case R.id.textview_wallet_consume:
                Intent intent5=new Intent(WalletActivity.this,ConsumeActivity.class);
                startActivity(intent5);
                break;
            case R.id.ll_settings_back:
                finish();
                break;
        }
    }

    //retrofit获取数据Data2,之后gson解析到Result成员变量中
    public void retrofitGetData2(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/v1/user_bill/")
                .build();
        Data3Wallet data3Wallet=retrofit.create(Data3Wallet.class);
        Call<ResponseBody> call=data3Wallet.getWalletBalance(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString=response.body().string();
                    Log.i(TAG, "onResponse测试: "+responseString);
                    try {
                        JSONObject objectResult=new JSONObject(responseString);
                        JSONObject objectBalance=objectResult.getJSONObject("data");
                        balance=objectBalance.getString("balance");
                        Log.i(TAG, "onResponse测试: "+objectBalance.getString("balance"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    updateAfterRetrofit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onResponse: "+t.toString());
            }
        });
    }


    public void updateAfterRetrofit(){
        TextView textViewBalance=findViewById(R.id.textview_wallet_balance);
        textViewBalance.setText(balance);
    }
}
