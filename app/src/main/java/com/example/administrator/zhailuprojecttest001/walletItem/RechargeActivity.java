package com.example.administrator.zhailuprojecttest001.walletItem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.MainActivity;
import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.adapter.NoticeBAdapter;
import com.example.administrator.zhailuprojecttest001.adapter.RechargeAdapter;
import com.example.administrator.zhailuprojecttest001.data.RechargeData;
import com.example.administrator.zhailuprojecttest001.gsonData3.Data3;
import com.example.administrator.zhailuprojecttest001.gsonData3.Result3;
import com.example.administrator.zhailuprojecttest001.retrofit.Data3Wallet;
import com.example.administrator.zhailuprojecttest001.retrofit.Data4Recharge;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RechargeActivity extends AppCompatActivity {

    private static final String TAG = "RechargeActivity";

    public List<RechargeData> rechargeDataList=new ArrayList<>();
    private String responseString;
    private Result3 result3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_recharge);
        initFirst();
        retrofitGetData();
    }

    public void initFirst(){
        TextView textView=findViewById(R.id.layout_recharge_title).findViewById(R.id.textview_title);
        textView.setText("充值明细");
    }

    public void retrofitGetData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://test.mouqukeji.com/api/v1/user_bill/")
                        .build();
                Data4Recharge data4Recharge=retrofit.create(Data4Recharge.class);
                Call<ResponseBody> call=data4Recharge.getRechargeBalance("1","1");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            responseString=response.body().string();
                            Log.i(TAG, "onResponse测试: "+responseString);

                            try {
                                JSONObject objectResult=new JSONObject(responseString);
                                JSONObject objectBalance=objectResult.getJSONObject("data");
                                Gson gson=new Gson();
                                result3=gson.fromJson(objectBalance.toString(),Result3.class);
                                Log.i(TAG, "onResponse测试: "+result3.getBalance().get(0).getCreate_time());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            initRechargeData();
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
        }).start();
    }

    public void initRechargeData(){
        for (int i=0;i<result3.getBalance().size();i++){
            Data3 data3=result3.getBalance().get(i);
            RechargeData rechargeDate=new RechargeData();
            rechargeDate.setCreateTime(data3.getCreate_time());
            rechargeDate.setMoney(data3.getMoney());
            if (data3.getPay_type().equals("1")){
                rechargeDate.setPayType("微信支付");
            }else if (data3.getPay_type().equals("2")){
                rechargeDate.setPayType("支付宝支付");
            }else if (data3.getPay_type().equals("3")){
                rechargeDate.setPayType("银行卡支付");
            }
            rechargeDataList.add(rechargeDate);
        }
        initRecyclerView();
    }
    public void initRecyclerView(){
        RecyclerView recyclerView=findViewById(R.id.recharge_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(RechargeActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RechargeAdapter rechargeAdapter=new RechargeAdapter(rechargeDataList);
        recyclerView.setAdapter(rechargeAdapter);
    }
}
