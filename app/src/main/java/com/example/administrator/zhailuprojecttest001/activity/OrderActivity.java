package com.example.administrator.zhailuprojecttest001.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.MainActivity;
import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.adapter.OrderAdapter;
import com.example.administrator.zhailuprojecttest001.adapter.OrderListAdapter;
import com.example.administrator.zhailuprojecttest001.fragment.OrderFragment;
import com.example.administrator.zhailuprojecttest001.gsonData1.Result;
import com.example.administrator.zhailuprojecttest001.gsonData2.Data2;
import com.example.administrator.zhailuprojecttest001.gsonData2.Result2;
import com.example.administrator.zhailuprojecttest001.retrofit.ZhailuData1;
import com.example.administrator.zhailuprojecttest001.retrofit.ZhailuData2;
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


//订单页面的activity
public class OrderActivity extends AppCompatActivity implements OrderFragment.OnFragmentInteractionListener {

    private static final String TAG = "OrderActivity";

    private OrderAdapter orderAdapter;
    private ArrayList<String> titleList =new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    private ViewPager viewPager;
    private TabLayout tabLayout1;

    private String responseString="6";
    public String getResponseString() {
        return responseString;
    }
    private Result2 result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_b);
        initFirst();
        initAdapter();
        setViewpager();
        GetSPData getSPData=new GetSPData();
        String userId=getSPData.getSPUserID(OrderActivity.this);
        retrofitGetData2(userId);
    }

    //用于更新title和底部图片,和底部图片设置点击事件
    public void initFirst(){
        TextView textView=findViewById(R.id.textview_title);
        textView.setText("订单");
        ImageButton imageButton=findViewById(R.id.imagebutton_order);
        imageButton.setBackgroundResource(R.drawable.icon_dingdan_jianying_wenzi);
        ImageButton imageButton1=findViewById(R.id.imagebutton_homepage);
        imageButton1.setBackgroundResource(R.drawable.icon_shouye_jianying_wenzi);
        LinearLayout linearLayout=findViewById(R.id.main_b_title);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white));

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton2=findViewById(R.id.imagebutton_me);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderActivity.this,UserCenterActivity.class);
                startActivity(intent);
            }
        });
    }

    //初始化Viewpager和TabLayout的tab标签和fragment
    public void initAdapter(){
        titleList.add("全部订单");
        titleList.add("待支付");
        titleList.add("待接单");
        titleList.add("进行中");
        titleList.add("已完成");
        titleList.add("待评价");
        titleList.add("已取消");
        OrderFragment f1=OrderFragment.newInstance(responseString,"0");
        OrderFragment f2=OrderFragment.newInstance(responseString,"1");
        OrderFragment f3=OrderFragment.newInstance(responseString,"2");
        OrderFragment f4=OrderFragment.newInstance("1","3+8");
        OrderFragment f5=OrderFragment.newInstance("1","4");
        OrderFragment f6=OrderFragment.newInstance("1","5");
        OrderFragment f7=OrderFragment.newInstance("1","6+7");
        fragmentList.add(f1);
        fragmentList.add(f2);
        fragmentList.add(f3);
        fragmentList.add(f4);
        fragmentList.add(f5);
        fragmentList.add(f6);
        fragmentList.add(f7);
    }

    //Viewpager的初始化和相关加载
    private void setViewpager(){
        orderAdapter=new OrderAdapter(getSupportFragmentManager(), titleList,fragmentList);
        viewPager= findViewById(R.id.viewpager_order_b);
        viewPager.setAdapter(orderAdapter);
        tabLayout1=findViewById(R.id.tabLayout_order_b);
//        tabLayout1
        tabLayout1.setupWithViewPager(viewPager);
//        tabLayout1.setTabsFromPagerAdapter(carTypeViewAdapter);
    }

    //这个是fragment的回调方法,可以自定义功能
    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println("这是我实现的fragment回调方法");
    }

    //retrofit获取数据Data2,之后gson解析到Result成员变量中
    public void retrofitGetData2(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/v1/task/")
                .build();
        ZhailuData2 zhailuData2=retrofit.create(ZhailuData2.class);
        //test Path parameter
//                Call<ResponseBody> call=zhailuData1.getZhailuData("Index");
        //test no parameter
        Call<ResponseBody> call=zhailuData2.getZhailuData("1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString=response.body().string();
                    Log.i(TAG, "onResponse测试: "+responseString);
                    Gson gson=new Gson();
                    result2=gson.fromJson(responseString,Result2.class);
                    Log.i(TAG, "onResponse: 测试:"+result2.getData().getTasks().isEmpty());
                    if (result2.getData().getTasks().isEmpty()){
                        //这里是数据访问出错
                    }else {
                        //这里是访问数据正常的逻辑
                        Log.i(TAG, "onResponse: 测试:"+result2.getData().getTasks().get(0).getDelivery_time());
                    }
//                            initRecyclerView();
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


//初始化RecyclerView,现在问题是不知道写在activity中还是fragment中,待考虑
//    public void initRecyclerView(){
//        OrderListAdapter orderListAdapter=new OrderListAdapter(OrderActivity.this,result2.getData());
//        RecyclerView recyclerView=findViewById(R.id.order_recycler);
//        LinearLayoutManager manager=new LinearLayoutManager(OrderActivity.this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(orderListAdapter);
//    }


}
