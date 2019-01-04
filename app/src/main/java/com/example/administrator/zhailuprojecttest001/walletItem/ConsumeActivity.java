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
import com.example.administrator.zhailuprojecttest001.adapter.ConsumeAdapter;
import com.example.administrator.zhailuprojecttest001.adapter.RechargeAdapter;
import com.example.administrator.zhailuprojecttest001.data.ConsumeData;
import com.example.administrator.zhailuprojecttest001.data.ConsumeData2;
import com.example.administrator.zhailuprojecttest001.data.RechargeData;
import com.example.administrator.zhailuprojecttest001.gsonData3.Data3;
import com.example.administrator.zhailuprojecttest001.gsonData3.Result3;
import com.example.administrator.zhailuprojecttest001.gsonData4.Data4;
import com.example.administrator.zhailuprojecttest001.gsonData4.Result4;
import com.example.administrator.zhailuprojecttest001.retrofit.Data4Recharge;
import com.example.administrator.zhailuprojecttest001.retrofit.Data5Consume;
import com.example.administrator.zhailuprojecttest001.util.GetSPData;
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

public class ConsumeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ConsumeActivity";
    private String responseString;
    private Result4 result4;
    private List<ConsumeData> consumeDataList=new ArrayList<>();
    private ConsumeData2 consumeData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_consume);
        Log.i(TAG, "onCreate: ");
        initFirst();
        GetSPData getSPData=new GetSPData();
        String userId=getSPData.getSPUserID(ConsumeActivity.this);
        retrofitGetData(userId);
    }

    public void initFirst(){
        TextView textView=findViewById(R.id.layout_consume_title).findViewById(R.id.textview_title);
        textView.setText("消费明细");
        LinearLayout llTitleBack=findViewById(R.id.layout_consume_title).findViewById(R.id.ll_settings_back);
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
                .baseUrl("http://test.mouqukeji.com/api/v1/user_bill/")
                .build();
        Data5Consume data5Consume=retrofit.create(Data5Consume.class);
        Call<ResponseBody> call=data5Consume.getConsumeData(userId,"1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString=response.body().string();
                    Log.i(TAG, "onResponse测试: "+responseString);
                    JSONObject objectResult=new JSONObject(responseString);
                    JSONObject objectConsume=objectResult.getJSONObject("data");
                    Gson gson=new Gson();
                    result4=gson.fromJson(objectConsume.toString(),Result4.class);
                    Log.i(TAG, "onResponse: 数据测试"+result4.getTotal()+" "+result4.getConsume().isEmpty());
                    if (result4.getConsume().isEmpty()){
                        //这里是消费数据为空或者数据获取失败
                    }else {
                        Log.i(TAG, "onResponse测试: "+result4.getConsume().get(0).getMoney());
                        initConsumeData();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onResponse: "+t.toString());
            }
        });

    }
    public void initConsumeData(){
        consumeData2=new ConsumeData2();
        consumeData2.setTotal(result4.getTotal());
        for (int i=0;i<result4.getConsume().size();i++){
            Data4 data4=result4.getConsume().get(i);
            ConsumeData consumeData=new ConsumeData();
            consumeData.setType(data4.getType());
            consumeData.setCreateTime(data4.getCreate_time());
            consumeData.setMoney(data4.getMoney());
            consumeDataList.add(consumeData);
        }
        initRecyclerView();
    }
    public void initRecyclerView(){
        RecyclerView recyclerView=findViewById(R.id.consume_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ConsumeActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConsumeAdapter consumeAdapter=new ConsumeAdapter(consumeDataList);
        recyclerView.setAdapter(consumeAdapter);
        TextView total=findViewById(R.id.textview_consume_total);
        total.setText(consumeData2.getTotal());
    }
}
