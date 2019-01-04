package com.example.administrator.zhailuprojecttest001.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.activity.OrderActivity;
import com.example.administrator.zhailuprojecttest001.adapter.OrderListAdapter;
import com.example.administrator.zhailuprojecttest001.gsonData2.Data2;
import com.example.administrator.zhailuprojecttest001.gsonData2.Result2;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    private static final String TAG = "OrderFragment";
    
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    //从activity传Data2放案暂时不可行,ARG_PARAM1暂时没用到
    private static final String ARG_PARAM1 = "Data2";
    private static final String ARG_PARAM2 = "processId";

    // TODO: Rename and change types of parameters

   //这是两个从activity传过来的参数
    private String mdata2;
    private String mProcessId;

    //这个是订单列表的子项数据
    private List<Data2> data2List=new ArrayList<>();

    //这个是fragment中网络请求的字符串
    private String responseString="6";

    //这个是回调接口声明
    private OnFragmentInteractionListener mListener;

    //这个是无参构造
    public OrderFragment() {
        // Required empty public constructor
    }

    //这个是工厂类创建fragment实例,可以传入两个参数
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mdata2 = getArguments().getString(ARG_PARAM1);
            mProcessId = getArguments().getString(ARG_PARAM2);
        }
//        System.out.println("Fragment1获取数据:"+data2);
        //直接activity请求传过来的问题在于请求耗时,这样加载fragment会卡顿
        //不知道网络请求放在哪个回调方法中好?待考虑
//        retrofitGetData2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (responseString.equals("6")){
            GetSPData getSPData=new GetSPData();
            String userId=getSPData.getSPUserID(getActivity());
            retrofitGetData2(userId);
            Log.i(TAG, "onStart: 调用了网络请求"+mProcessId);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        responseString="6";
        data2List.clear();
    }

    //这个是回调方法的执行
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    //这个是内部回调接口
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        Call<ResponseBody> call=zhailuData2.getZhailuData(userId,mProcessId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    responseString=response.body().string();
                    Log.i(TAG, "onResponse测试: "+responseString);
                    Gson gson=new Gson();
                    Result2 result2=gson.fromJson(responseString,Result2.class);
                    if (result2.getData().isEmpty()){
                        //数据读取为空或者数据访问异常
                    }else {
                        Log.i(TAG, "onResponse: 测试:"+result2.getData().get(0).getDelivery_time());
                        initRecyclerData2();
                        initRecycler();
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

    public void initRecyclerData2(){

        Gson gson=new Gson();
        Result2 result2=gson.fromJson(responseString,Result2.class);
        List<Data2> getdata2List=result2.getData();

        //根据不同订单类型加载不同数据
        //前端的progress验证,防止后台数据出错,二次保证显示对应progress的子项
        if (mProcessId.equals("0")){
            data2List=getdata2List;
        }else if (mProcessId.equals("1")){
            for (int i=0;i<getdata2List.size();i++){
                if (getdata2List.get(i).getProgress().equals("1")){
                    data2List.add(getdata2List.get(i));
                }
            }
        }else if (mProcessId.equals("2")){
            for (int i=0;i<getdata2List.size();i++){
                if (getdata2List.get(i).getProgress().equals("2")){
                    data2List.add(getdata2List.get(i));
                }
            }
        }else if (mProcessId.equals("3+8")){
            for (int i=0;i<getdata2List.size();i++){
                if (getdata2List.get(i).getProgress().equals("3")||getdata2List.get(i).getProgress().equals("8")){
                    data2List.add(getdata2List.get(i));
                }
            }
        }else if (mProcessId.equals("4")){
            for (int i=0;i<getdata2List.size();i++){
                if (getdata2List.get(i).getProgress().equals("4")){
                    data2List.add(getdata2List.get(i));
                }
            }
        }else if (mProcessId.equals("5")){
            for (int i=0;i<getdata2List.size();i++){
                if (getdata2List.get(i).getProgress().equals("5")){
                    data2List.add(getdata2List.get(i));
                }
            }
        }else if (mProcessId.equals("6+7")){
            for (int i=0;i<getdata2List.size();i++){
                if (getdata2List.get(i).getProgress().equals("6")||getdata2List.get(i).getProgress().equals("7")){
                    data2List.add(getdata2List.get(i));
                }
            }
        }
    }

    public void initRecycler(){
        RecyclerView recyclerView=getView().findViewById(R.id.order_recycler);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        OrderListAdapter orderListAdapter=new OrderListAdapter(getActivity(),data2List);
        recyclerView.setAdapter(orderListAdapter);

        //设置RecyclerView的显示和原先ll_no_order的隐藏
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayout linearLayout=getView().findViewById(R.id.ll_no_order);
        linearLayout.setVisibility(View.GONE);
    }
}
