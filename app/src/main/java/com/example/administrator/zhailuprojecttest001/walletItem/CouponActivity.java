package com.example.administrator.zhailuprojecttest001.walletItem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.adapter.CouponAdapter;
import com.example.administrator.zhailuprojecttest001.data.CouponData;
import com.example.administrator.zhailuprojecttest001.gsonData5.Data5;
import com.example.administrator.zhailuprojecttest001.gsonData5.Result5;
import com.example.administrator.zhailuprojecttest001.retrofit.Data6Coupon;
import com.example.administrator.zhailuprojecttest001.util.GetSPData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CouponActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CouponActivity";
    private String responseString;
    private Result5 result5;
    private List<CouponData> couponDataList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_coupon);
        initFirst();
        GetSPData getSPData=new GetSPData();
        String userId=getSPData.getSPUserID(CouponActivity.this);
        retrofitGetData(userId);
    }

    public void initFirst(){
        TextView textView=findViewById(R.id.layout_coupon_title).findViewById(R.id.textview_title);
        textView.setText("优惠券");
        LinearLayout llTitleBack=findViewById(R.id.layout_coupon_title).findViewById(R.id.ll_settings_back);
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
    public void retrofitGetData(String userId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/v1/coupon/")
                .build();
        Data6Coupon data6Coupon=retrofit.create(Data6Coupon.class);
        Call<ResponseBody> call=data6Coupon.getCouponData(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString=response.body().string();
                    Log.i(TAG, "onResponse测试: "+responseString);
                    Gson gson=new Gson();
                    result5=gson.fromJson(responseString,Result5.class);
                    Log.i(TAG, "onResponse: "+result5.getCode()+" "+result5.getMsg());
                    if (result5.getData().isEmpty()){
                        //获取数据为空或者失败
                    }else if (!result5.getCode().equals("10000")){
                        //获取数据为空或者失败
                    }else {
                        //这里是获取数据成功
                        Log.i(TAG, "onResponse测试: "+result5.getData().get(0).getStart_time());
                        initRechargeData();
                    }

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
    public void initRechargeData(){
        for (int i=0;i<result5.getData().size();i++){
            Data5 data5=result5.getData().get(i);
            CouponData couponData=new CouponData();
            couponData.setTypeName(data5.getCate_name());
            couponData.setTypeId(data5.getType());
            couponData.setNumPrice(data5.getNum());
            couponData.setStartTime(data5.getStart_time());
            couponData.setDuration(data5.getPrefix());
            couponData.setUseStatus(data5.getIs_use());
            couponData.setEndTime(data5.getEnd_time());
            couponDataList.add(couponData);
        }
        Log.i(TAG, "initRechargeData: "+couponDataList.size());
        initRecyclerView();
    }
    public void initRecyclerView(){
        RecyclerView recyclerView=findViewById(R.id.coupon_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CouponActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        CouponAdapter rechargeAdapter=new CouponAdapter(couponDataList);
        recyclerView.setAdapter(rechargeAdapter);
    }
}
