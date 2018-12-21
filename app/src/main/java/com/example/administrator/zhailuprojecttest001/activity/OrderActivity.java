package com.example.administrator.zhailuprojecttest001.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.adapter.OrderAdapter;
import com.example.administrator.zhailuprojecttest001.fragment.OrderFragment;

import java.util.ArrayList;


//订单页面的activity
public class OrderActivity extends AppCompatActivity implements OrderFragment.OnFragmentInteractionListener {

    private OrderAdapter orderAdapter;
    private ArrayList<String> titleList =new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    private ViewPager viewPager;
    private TabLayout tabLayout1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_b);
        initFirst();
        initAdapter();
        setViewpager();
    }

    //用于更新title和底部图片
    public void initFirst(){
        TextView textView=findViewById(R.id.textview_title);
        textView.setText("订单");
        ImageButton imageButton=findViewById(R.id.imagebutton_order);
        imageButton.setBackgroundResource(R.drawable.icon_dingdan_jianying_wenzi);
        ImageButton imageButton1=findViewById(R.id.imagebutton_homepage);
        imageButton1.setBackgroundResource(R.drawable.icon_shouye_jianying_wenzi);
        LinearLayout linearLayout=findViewById(R.id.main_b_title);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
    }
    public void initAdapter(){
        titleList.add("全部订单");
        titleList.add("待支付");
        titleList.add("待接单");
        titleList.add("进行中");
        titleList.add("已完成");
        titleList.add("待评价");
        titleList.add("已取消");
        OrderFragment f1=OrderFragment.newInstance("1","2");
        OrderFragment f2=OrderFragment.newInstance("1","2");
        OrderFragment f3=OrderFragment.newInstance("1","2");
        OrderFragment f4=OrderFragment.newInstance("1","2");
        OrderFragment f5=OrderFragment.newInstance("1","2");
        OrderFragment f6=OrderFragment.newInstance("1","2");
        OrderFragment f7=OrderFragment.newInstance("1","2");
        fragmentList.add(f1);
        fragmentList.add(f2);
        fragmentList.add(f3);
        fragmentList.add(f4);
        fragmentList.add(f5);
        fragmentList.add(f6);
        fragmentList.add(f7);
    }

    private void setViewpager(){
        orderAdapter=new OrderAdapter(getSupportFragmentManager(), titleList,fragmentList);
        viewPager= findViewById(R.id.viewpager_order_b);
        viewPager.setAdapter(orderAdapter);
        tabLayout1=findViewById(R.id.tabLayout_order_b);
//        tabLayout1
        tabLayout1.setupWithViewPager(viewPager);
//        tabLayout1.setTabsFromPagerAdapter(carTypeViewAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println("这是我实现的fragment回调方法");
    }
}
