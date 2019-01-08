package com.example.administrator.zhailuprojecttest001;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.activity.OrderActivity;
import com.example.administrator.zhailuprojecttest001.activity.UserCenterActivity;
import com.example.administrator.zhailuprojecttest001.adapter.NoticeBAdapter;
import com.example.administrator.zhailuprojecttest001.adapter.AdvertAdapter;
import com.example.administrator.zhailuprojecttest001.data.NoticeBData;
import com.example.administrator.zhailuprojecttest001.gsonData1.Data;
import com.example.administrator.zhailuprojecttest001.gsonData1.Result;
import com.example.administrator.zhailuprojecttest001.register.SignInActivity;
import com.example.administrator.zhailuprojecttest001.retrofit.ZhailuData1;
import com.example.administrator.zhailuprojecttest001.retrofit2.Data4TokenVf;
import com.example.administrator.zhailuprojecttest001.staticData.LoginStaticData;
import com.example.administrator.zhailuprojecttest001.util.DataSaveSP;
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


//主页面activity
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "MainActivity";

    private List<NoticeBData> noticeBDataList =new ArrayList<>();
    private NoticeBAdapter noticeBAdapter;
    private ViewPager viewPager;
    private List<View> viewList=new ArrayList<>();

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;

    private ConstraintLayout mainLayout;

    private LinearLayout pointLl;
    private int curIndex = 0;


    private LayoutInflater mInflater;
    private Result result;

    private ImageButton imageButtonOrder;
    private ImageButton imageButtonUserCenter;

    //该flag表示是否完成首页数据的网络请求,0表示没有完成网络请求,1表示已经成功请求到首页数据
    private int flag1 =0;
    //该flag表示是否处于登录状态,0表示未登录状态,1表示已经登录状态
    private int flag2 =0;

    private String responseTk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_a);
        mInflater = LayoutInflater.from(this);
        mainLayout=findViewById(R.id.main_a_layout);
        pointLl=findViewById(R.id.main_a_point_ll);



        initActivityBDataList();
        initNoticeRecycler();

        initAdvertViewpager();
//        setOvalLayout();

        retrofitGetData();



        //设置底部按钮的点击事件
        imageButtonOrder=findViewById(R.id.imagebutton_order);
        imageButtonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,OrderActivity.class);
                startActivity(intent1);
            }
        });
        imageButtonUserCenter=findViewById(R.id.imagebutton_me);
        imageButtonUserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this,UserCenterActivity.class);
                startActivity(intent2);
            }
        });
    }

    //初始化主页面的活动
    private void initActivityBDataList(){
//        noticeBDataList.add(new NoticeBData(R.drawable.test_picture_001,"这是我们的第一个活动"));
//        noticeBDataList.add(new NoticeBData(R.drawable.test_picture_002,"这是我们的第二个活动"));
    }

    public void initAdvertViewpager(){
        viewPager=findViewById(R.id.main_a_viewpager_1);
        initImageButtonList();
        AdvertAdapter advertAdapter=new AdvertAdapter(viewList);
        viewPager.setAdapter(advertAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(curIndex);
    }

    public void initNoticeRecycler(){
        noticeBAdapter =new NoticeBAdapter(noticeBDataList,MainActivity.this);
        recyclerView=findViewById(R.id.main_a_recycler);
        manager=new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(noticeBAdapter);
    }

    //初始化广告的子view
    private void initImageButtonList(){
//        for (int i=0;i<result.getData().getBanners().size();i++){
//            View view1=  mInflater.inflate(R.layout.advert_01,null);
//            ImageButton imageButton=view1.findViewById(R.id.advert_imagebutton_01);
//            GlideApp.with(MainActivity.this).load(result.getData().getBanners().get(i).getBanner()).into(imageButton);
//            viewList.add(view1);
//        }
//        View view1=  mInflater.inflate(R.layout.advert_01,null);
//        ImageButton imageButton=view1.findViewById(R.id.advert_imagebutton_01);
//        GlideApp.with(MainActivity.this).load(result.getData().getBanners().get(0).getBanner()).into(imageButton);
//        View view2=  mInflater.inflate(R.layout.advert_02,null);
//        ImageButton imageButton2=view1.findViewById(R.id.advert_imagebutton_02);
//        GlideApp.with(MainActivity.this).load(result.getData().getBanners().get(1).getBanner()).into(imageButton2);
//        viewList.add(view1);
//        viewList.add(view2);
    }

    //retrofit获取数据Data1,之后gson解析到Result成员变量中
    public void retrofitGetData() {
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
                    Log.i(TAG, "onResponse测试1: "+s);
                    Gson gson=new Gson();
                    result=gson.fromJson(s,Result.class);
                    //test Categories
                    for (int i=0;i<4;i++){
                        Log.i(TAG, "onResponse测试1: "+result.getData().getCategories().get(0).getCate_photo());
                    }
                    Log.i(TAG, "onResponse: 测试1"+R.id.advert_imagebutton_01+" "+R.id.advert_imagebutton_02);
                    flag1 =1;

                    //这里是不是考虑放在另外位置执行,是否会和验证登录状态的跳转冲突
                    //果然产生冲突,如果跳转和更新UI同时进行就error,这里选择在start()中开启一个子线程监听调用showResponseResult(result);
//                    showResponseResult(result);

//                            initImageButtonList();
//                            setOvalLayout();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onResponse1: "+t.toString());
            }
        });

    }

    //将解析到的数据加载到布局中,图片加载用的是Glide
    public void showResponseResult(final Result result){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Data data=result.getData();
                int s1=data.getCategories().size();
                int s2=data.getBanners().size();
                int s3=data.getNotices().size();
                for (int i=0;i<s1;i++){
                    TextView t1=findViewById(R.id.main_a_item_imagebutton_1+i).findViewById(R.id.item_textview_a_01);
                    t1.setText(data.getCategories().get(i).getCate_name());
                    final ImageButton i1=findViewById(R.id.main_a_item_imagebutton_1+i).findViewById(R.id.item_imagebutton_a_01);

                    //glide
//                    GlideApp.with(MainActivity.this).load(data.getCategories().get(i).getCate_photo()).into(i1);
                    GlideApp.with(MainActivity.this).load(data.getCategories().get(i).getCate_photo()).into(i1);
//                    RequestOptions requestOptions=new RequestOptions();
                }
                for (int i=0;i<s2;i++){
                    if (viewList.size()!=s2){
                        View view1=  mInflater.inflate(R.layout.advert_01,null);
                        viewList.add(view1);
//                        viewPager.notify();
                        ImageButton imageButton=view1.findViewById(R.id.advert_imagebutton_01);
                        GlideApp.with(MainActivity.this).load(data.getBanners().get(i).getBanner()).into(imageButton);
                    }
                    //废弃代码
//                    ImageButton imageButton=findViewById(R.id.advert_layout_01+i).findViewById(getResources().getIdentifier("advert_imagebutton_0"+(i+1), "id", getPackageName()));
//                    Log.i(TAG, "run: "+getResources().getIdentifier("advert_imagebutton_0"+(i+1), "id", getPackageName()));
//                    GlideApp.with(MainActivity.this).load(data.getBanners().get(i).getBanner()).into(imageButton);
                }
                AdvertAdapter advertAdapter=new AdvertAdapter(viewList);
                viewPager.setAdapter(advertAdapter);
                setOvalLayout();
                for (int i=0;i<s3;i++){
                    //废弃代码
//                    ImageButton imageButton=manager.findViewByPosition(i).findViewById(R.id.item_imagebutton_b_01);
//                    GlideApp.with(MainActivity.this).load(data.getNotices().get(i).getThumb()).into(imageButton);
//                    Button button=manager.findViewByPosition(i).findViewById(R.id.item_button_b_01);
//                    button.setText(data.getNotices().get(i).getDescription());
                    if (noticeBDataList.size()!=s3){
                        NoticeBData noticeBData =new NoticeBData(data.getNotices().get(i).getThumb(),data.getNotices().get(i).getDescription());
                        noticeBDataList.add(noticeBData);
                    }
                }
                noticeBAdapter.notifyDataSetChanged();
                

            }
        });
    }

    //这是广告Viewpager页面滑动的三个事件监听,设置了底下白色小点的动态效果
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

    //设置广告页面下方的白色小点
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

    //用于根据文件是否存在判断是否处在登录状态
    //用户的token信息保存的文件名为
    public void isLogin(){
        GetSPData getSPData=new GetSPData();
        String token=getSPData.getSPToken(MainActivity.this);
        String userId=getSPData.getSPUserID(MainActivity.this);
        String telephone=getSPData.getSPTelephone(MainActivity.this);
        //验证,从sharedpreferences获取数据
        //验证,从静态变量获取数据
        Log.i(TAG, "isLogin: 登录验证sp "+token+" "+userId+" "+telephone);
        if (LoginStaticData.token.equals(token)&&LoginStaticData.userId.equals(userId)&LoginStaticData.telephone.equals(telephone)){
            //静态数据正确
        }else{
            //静态变量赋值
            if (token!=null&&userId!=null&telephone!=null){
                LoginStaticData.token=token;
                LoginStaticData.userId=userId;
                LoginStaticData.telephone=telephone;
            }
            //验证一下静态信息
            Log.i(TAG, "isLogin: 登录验证静态数据(主页): "+LoginStaticData.token+" "+LoginStaticData.userId+" "+telephone);
        }
        if (token==null){
            //本地没有token,这里有第一个跳转
            finish();
            Intent intent=new Intent(MainActivity.this,SignInActivity.class);
            startActivity(intent);
        }else {
            //这里是未完待写的代码:根据本地token网络请求服务器,判断token是否合法,合法返回true则无需再次登录,不合法则还是返回false,为未登录状态,同时删除本地文件
            retrofitV3(token,userId);
        }
    }

    //token验证请求
    public void retrofitV3(final String token, final String userIdParam) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://test.mouqukeji.com/api/Login/")
                .build();
        Data4TokenVf data4TokenVf = retrofit.create(Data4TokenVf.class);
        Call<ResponseBody> call = data4TokenVf.postData(token);
//                Call<ResponseBody> call=getCodePost.postData2("18668197127");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseTk = response.body().string();
                    Log.i(TAG, "onResponse测试2: " + responseTk);
                    JSONObject jsonObject=new JSONObject(responseTk);
                    String code=jsonObject.getString("code");
                    String msg=jsonObject.getString("msg");
                    String data=jsonObject.getString("data");
                    if (data.charAt(0)!='{'){
                        //这里是token验证失败,返回data无效的判断,可以后续添加业务逻辑
                        finish();
                        Intent intent=new Intent(MainActivity.this,SignInActivity.class);
                        startActivity(intent);
                    }else if (!msg.equals("success")){
                        //这里也是token验证失败,返回msg不为success
                        finish();
                        Intent intent=new Intent(MainActivity.this,SignInActivity.class);
                        startActivity(intent);
                    }else {
                        //这里是token验证通过
                        flag2=1;
                        JSONObject jsonObject2=jsonObject.getJSONObject("data");
                        String userId=jsonObject2.getString("user_id");
                        //后续可以判断本地userId和该token的userId是否一致
                        if (userId.equals(userIdParam)){
                            Log.i(TAG, "onResponse2: userId数据验证正确");
                        }else {
                            Log.i(TAG, "onResponse2: userId数据验证错误: "+"本地token请求获取的userId和本地userId不相同");
                            //这里进行访问到的userId覆盖本地userId,防止别人直接修改文件userId的入侵行为
//                            SharedPreferences sharedPreferences=getSharedPreferences("zhailu",Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor=sharedPreferences.edit();
//                            editor.putString("userId",new DataSaveSP().customEncode(userId));
//                            boolean b=editor.commit();
                            DataSaveSP dataSaveSP=new DataSaveSP();
                            boolean b=dataSaveSP.dataSave(userId,MainActivity.this);
                            Log.i(TAG, "onResponse: userId覆盖结果"+b);
                            LoginStaticData.userId=userId;
                        }
                        Log.i(TAG, "onResponse2: token验证通过");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onResponse2: " + t.toString());
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
//        if (pointLl.getChildAt(0)==null){
//            setOvalLayout();
//        }
        isLogin();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (flag1 ==1&&flag2==1){
                        showResponseResult(result);
                        System.out.println("第二次重新更新");
                        break;
                    }else{
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
