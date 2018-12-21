package com.example.administrator.zhailuprojecttest001;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.zhailuprojecttest001.activity.OrderActivity;
import com.example.administrator.zhailuprojecttest001.adapter.NoticeBAdapter;
import com.example.administrator.zhailuprojecttest001.adapter.AdvertAdapter;
import com.example.administrator.zhailuprojecttest001.data.ActivityBData;
import com.example.administrator.zhailuprojecttest001.gsonData1.Banner;
import com.example.administrator.zhailuprojecttest001.gsonData1.Categorie;
import com.example.administrator.zhailuprojecttest001.gsonData1.Data;
import com.example.administrator.zhailuprojecttest001.gsonData1.Notice;
import com.example.administrator.zhailuprojecttest001.gsonData1.Result;
import com.example.administrator.zhailuprojecttest001.retrofit.ZhailuData1;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;


//主页面activity
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<ActivityBData> activityBDataList=new ArrayList<>();
    private NoticeBAdapter noticeBAdapter;
    private ViewPager viewPager;
    private List<View> viewList=new ArrayList<>();

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;

    private ConstraintLayout mainLayout;

    private LinearLayout pointLl;
    private int curIndex = 0;

    private static final String TAG = "MainActivity";

    private LayoutInflater mInflater;
    private Result result;

    private ImageButton imageButtonOrder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_a);
        mInflater = LayoutInflater.from(this);
        mainLayout=findViewById(R.id.main_a_layout);
        pointLl=findViewById(R.id.main_a_point_ll);



        initActivityBDataList();
        noticeBAdapter =new NoticeBAdapter(activityBDataList);
        recyclerView=findViewById(R.id.main_a_recycler);
        manager=new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(noticeBAdapter);





        viewPager=findViewById(R.id.main_a_viewpager_1);
        initImageButtonList();
        AdvertAdapter advertAdapter=new AdvertAdapter(viewList);
        viewPager.setAdapter(advertAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(curIndex);
        setOvalLayout();

        retrofitGetData();

        imageButtonOrder=findViewById(R.id.imagebutton_order);
        imageButtonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initActivityBDataList(){
        activityBDataList.add(new ActivityBData(R.drawable.test_picture_001,"这是我们的第一个活动"));
        activityBDataList.add(new ActivityBData(R.drawable.test_picture_002,"这是我们的第二个活动"));
    }
    private void initImageButtonList(){
//        for (int i=0;i<result.getData().getBanners().size();i++){
//            View view1=  mInflater.inflate(R.layout.advert_01,null);
//            ImageButton imageButton=view1.findViewById(R.id.advert_imagebutton_01);
//            GlideApp.with(MainActivity.this).load(result.getData().getBanners().get(i).getBanner()).into(imageButton);
//            viewList.add(view1);
//        }
        View view1=  mInflater.inflate(R.layout.advert_01,null);
//        ImageButton imageButton=view1.findViewById(R.id.advert_imagebutton_01);
//        GlideApp.with(MainActivity.this).load(result.getData().getBanners().get(0).getBanner()).into(imageButton);
        View view2=  mInflater.inflate(R.layout.advert_02,null);
//        ImageButton imageButton2=view1.findViewById(R.id.advert_imagebutton_02);
//        GlideApp.with(MainActivity.this).load(result.getData().getBanners().get(1).getBanner()).into(imageButton2);
        viewList.add(view1);
        viewList.add(view2);
    }

    public void retrofitGetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://test.mouqukeji.com/")
                        .build();
                ZhailuData1 zhailuData1=retrofit.create(ZhailuData1.class);
                //test Path parameter
//                Call<ResponseBody> call=zhailuData1.getZhailuData("Index");
                //test no parameter
                Call<ResponseBody> call=zhailuData1.getZhailuData();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String s=response.body().string();
                            Log.i(TAG, "onResponse测试: "+s);
                            Gson gson=new Gson();
                            result=gson.fromJson(s,Result.class);
                            //test Categories
                            for (int i=0;i<4;i++){
                                Log.i(TAG, "onResponse测试: "+result.getData().getCategories().get(0).getCate_photo());
                            }
                            Log.i(TAG, "onResponse: 测试"+R.id.advert_imagebutton_01+" "+R.id.advert_imagebutton_02);
                            showResponseResult(result);
//                            initImageButtonList();
//                            setOvalLayout();
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

    public void showResponseResult(final Result result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Data data=result.getData();
                int s1=result.getData().getCategories().size();
                int s2=result.getData().getBanners().size();
                int s3=result.getData().getNotices().size();
                for (int i=0;i<s1;i++){
                    TextView t1=findViewById(R.id.main_a_item_imagebutton_1+i).findViewById(R.id.item_textview_a_01);
                    t1.setText(data.getCategories().get(i).getCate_name());
                    final ImageButton i1=findViewById(R.id.main_a_item_imagebutton_1+i).findViewById(R.id.item_imagebutton_a_01);

                    //glide
//                    GlideApp.with(MainActivity.this).load(data.getCategories().get(i).getCate_photo()).into(i1);
                    GlideApp.with(MainActivity.this).load(data.getCategories().get(i).getCate_photo()).into(i1);
//                    RequestOptions requestOptions=new RequestOptions();
                }
                for (int i=0;i<2;i++){
                    ImageButton imageButton=findViewById(R.id.advert_layout_01+i).findViewById(R.id.advert_imagebutton_01+i);
                    GlideApp.with(MainActivity.this).load(data.getBanners().get(i).getBanner()).into(imageButton);
                }
                for (int i=0;i<2;i++){
                    ImageButton imageButton=manager.findViewByPosition(i).findViewById(R.id.item_imagebutton_b_01);
                    GlideApp.with(MainActivity.this).load(data.getNotices().get(i).getThumb()).into(imageButton);
                    Button button=manager.findViewByPosition(i).findViewById(R.id.item_button_b_01);
                    button.setText(data.getNotices().get(i).getDescription());
                }
                activityBDataList.add(new ActivityBData(R.drawable.test_picture_001,"这是我们的第一个活动"));
                noticeBAdapter.notifyDataSetChanged();
                

            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

//        pointLl.getChildAt(curIndex).findViewById(R.id.advert_point_layout_01).getLayoutParams().width=21;
//        pointLl.getChildAt(curIndex).findViewById(R.id.advert_point_01).getLayoutParams().width=21;
//        Log.i(TAG, "onPageSelected: "+pointLl.getChildAt(curIndex).findViewById(R.id.advert_point_01).getWidth()+"  1"+" "+curIndex);
//        pointLl.getChildAt(curIndex).findViewById(R.id.advert_point_01).setLayoutParams(new LinearLayout.LayoutParams(21,12));
        pointLl.getChildAt(curIndex).findViewById(R.id.advert_point_01).setBackgroundResource(R.drawable.dot_normal);
//        pointLl.getChildAt(i).findViewById(R.id.advert_point_layout_01).getLayoutParams().width=39;
        pointLl.getChildAt(i).findViewById(R.id.advert_point_01).getLayoutParams().width=39;
//        Log.i(TAG, "onPageSelected: "+pointLl.getChildAt(i).findViewById(R.id.advert_point_01).getWidth()+"  2"+" "+i);
//        pointLl.getChildAt(i).findViewById(R.id.advert_point_01).setLayoutParams(new LinearLayout.LayoutParams(39,12,6));
        pointLl.getChildAt(i).findViewById(R.id.advert_point_01).setBackgroundResource(R.drawable.dot_selected);
////        pointLl.getChildAt(i).findViewById(R.id.advert_point_01).getLayoutParams().width=39;

        curIndex = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public void setOvalLayout() {
        for (int i = 0; i < viewList.size(); i++) {
            LinearLayout point= (LinearLayout) mInflater.inflate(R.layout.advert_point, pointLl,false);
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) point.findViewById(R.id.advert_point_01).getLayoutParams();
            pointLl.addView(point);
            params.leftMargin=12;
            params.width=21;
            params.height=12;
        }
//         默认显示第一页
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) pointLl.getChildAt(0).findViewById(R.id.advert_point_01).getLayoutParams();
        params.width=39;
        pointLl.getChildAt(0).findViewById(R.id.advert_point_01)
                .setBackgroundResource(R.drawable.dot_selected);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (pointLl.getChildAt(0)==null){
//            setOvalLayout();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
