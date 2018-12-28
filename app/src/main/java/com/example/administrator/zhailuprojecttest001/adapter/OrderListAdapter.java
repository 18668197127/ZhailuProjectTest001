package com.example.administrator.zhailuprojecttest001.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.data.ActivityBData;
import com.example.administrator.zhailuprojecttest001.gsonData2.Data2;
import com.example.administrator.zhailuprojecttest001.gsonData2.Result2;

import java.util.List;

//订单RecyclerView的适配器(对应三种item布局order_item_1-3)
public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "OrderListAdapter";

    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;
    private final int TYPE_3 = 3;


    private final Context context;
    private List<Data2> mData2List;

    //第一种布局
    static class OneViewHolder extends RecyclerView.ViewHolder{

        TextView textId;
        TextView textType;
        TextView textPrice;
        TextView textTimescope;
        TextView textPaystate;
        Button buttonCancel;

        public OneViewHolder(View view){
            super(view);
            textId=view.findViewById(R.id.text_order_id);
            textType=view.findViewById(R.id.text_order_type);
            textPrice=view.findViewById(R.id.text_order_price);
            textTimescope=view.findViewById(R.id.text_order_timescope);
            textPaystate=view.findViewById(R.id.text_order_paystate);
            buttonCancel=view.findViewById(R.id.button_order_cancel);
        }
    }
    //第二种布局
    static class TwoViewHolder extends RecyclerView.ViewHolder{
        TextView textId;
        TextView textType;
        TextView textPrice;
        TextView textTimescope;
        TextView textPaystate;
        Button buttonCancel;
        Button buttonPay;

        public TwoViewHolder(View view){
            super(view);
            textId=view.findViewById(R.id.text_order_id);
            textType=view.findViewById(R.id.text_order_type);
            textPrice=view.findViewById(R.id.text_order_price);
            textTimescope=view.findViewById(R.id.text_order_timescope);
            textPaystate=view.findViewById(R.id.text_order_paystate);
            buttonCancel=view.findViewById(R.id.button_order_cancel);
            buttonPay=view.findViewById(R.id.button_order_pay);
        }
    }
    //第三种布局
    static class ThreeViewHolder extends RecyclerView.ViewHolder{
        TextView textId;
        TextView textType;
        TextView textPrice;
        TextView textTimescope;
        TextView textPaystate;
        TextView textState;
        Button buttonThree;

        public ThreeViewHolder(View view){
            super(view);
            textId=view.findViewById(R.id.text_order_id);
            textType=view.findViewById(R.id.text_order_type);
            textPrice=view.findViewById(R.id.text_order_price);
            textTimescope=view.findViewById(R.id.text_order_timescope);
            textPaystate=view.findViewById(R.id.text_order_paystate);
            textState=view.findViewById(R.id.text_order_state);
            buttonThree=view.findViewById(R.id.button_order_three);
        }
    }


    //有参构造方法
    public OrderListAdapter(Context context,List<Data2> data2List){
        this.context=context;
        mData2List=data2List;
    }


    //创建
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==TYPE_1){
            View view = LayoutInflater.from( viewGroup.getContext()).inflate(R.layout.order_item_1, viewGroup, false);
            OneViewHolder viewHolder=new OneViewHolder(view);
            return viewHolder;
        }else if (i==TYPE_2){
            View view = LayoutInflater.from( viewGroup.getContext()).inflate(R.layout.order_item_2, viewGroup, false);
            TwoViewHolder viewHolder=new TwoViewHolder(view);
            return viewHolder;
        }else if (i==TYPE_3){
            View view = LayoutInflater.from( viewGroup.getContext()).inflate(R.layout.order_item_3, viewGroup, false);
            ThreeViewHolder viewHolder=new ThreeViewHolder(view);
            return viewHolder;
        }else {
            System.out.println("测试:RecyclerView类型出错");
            return null;
        }
    }


    //绑定并且初始化控件
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        Data2 result2= mData2List.get(i);
        if (result2.getProgress().equals("1")){
            Log.i(TAG, "onBindViewHolder: "+"待支付,第"+i+"项");
            if (viewHolder instanceof TwoViewHolder){
                setList1((TwoViewHolder) viewHolder,result2);
            }
        }else if (result2.getProgress().equals("2")){
            Log.i(TAG, "onBindViewHolder: "+"待接单第"+i+"项");
            if (viewHolder instanceof OneViewHolder){
                setList2((OneViewHolder) viewHolder,result2);
            }
        }else if (result2.getProgress().equals("3")||result2.getProgress().equals("8")){
            Log.i(TAG, "onBindViewHolder: "+"进行中第"+i+"项");
            if (viewHolder instanceof ThreeViewHolder){
                setList3((ThreeViewHolder) viewHolder,result2);
            }
        }else if (result2.getProgress().equals("5")){
            Log.i(TAG, "onBindViewHolder: "+"已完成第"+i+"项");
            if (viewHolder instanceof ThreeViewHolder){
                setList5((ThreeViewHolder) viewHolder,result2);
            }
        }else if (result2.getProgress().equals("4")){
            Log.i(TAG, "onBindViewHolder: "+"待评价第"+i+"项");
            if (viewHolder instanceof ThreeViewHolder){
                setList4((ThreeViewHolder) viewHolder,result2);
            }
        }else if (result2.getProgress().equals("6")||result2.getProgress().equals("7")){
            Log.i(TAG, "onBindViewHolder: "+"已取消第"+i+"项");
            if (viewHolder instanceof ThreeViewHolder){
                setList6((ThreeViewHolder) viewHolder,result2);
            }
        }else {
            Log.i(TAG, "onBindViewHolder: "+"process类型不符");
        }
    }

    //获取子项数目
    @Override
    public int getItemCount() {
        return mData2List.size();
    }


    //获取类型
    @Override
    public int getItemViewType(int position) {
        if (mData2List.get(position).getProgress().equals("2")){
            return TYPE_1;
        }else if (mData2List.get(position).getProgress().equals("1")){
            return TYPE_2;
        }else {
            return TYPE_3;
        }
    }

    public void setList1(TwoViewHolder holder,Data2 data2){
        holder.textId.setText(data2.getOrder_sn());
        holder.textType.setText(data2.getCate_name());
        holder.textPrice.setText(data2.getTask_price());
        holder.textTimescope.setText(data2.getDelivery_time());
        holder.textPaystate.setText("已支付");
    }
    public void setList2(OneViewHolder holder,Data2 data2){
        holder.textId.setText(data2.getOrder_sn());
        holder.textType.setText(data2.getCate_name());
        holder.textPrice.setText(data2.getTask_price());
        holder.textTimescope.setText(data2.getDelivery_time());
        holder.textPaystate.setText("已支付");
    }
    public void setList3(ThreeViewHolder holder,Data2 data2){
        holder.textId.setText(data2.getOrder_sn());
        holder.textType.setText(data2.getCate_name());
        holder.textPrice.setText(data2.getTask_price());
        holder.textTimescope.setText(data2.getDelivery_time());
        holder.textPaystate.setText("已支付");
        if (data2.getProgress().equals("3")){
            holder.textState.setText("进行中...");
        }else if (data2.getProgress().equals("8")){
            holder.textState.setText("跑腿中...");
        }
    }
    public void setList4(ThreeViewHolder holder,Data2 data2){
        holder.textId.setText(data2.getOrder_sn());
        holder.textType.setText(data2.getCate_name());
        holder.textPrice.setText(data2.getTask_price());
        holder.textTimescope.setText(data2.getDelivery_time());
        holder.textPaystate.setText("已支付");
        holder.textState.setText("待评价...");
        holder.buttonThree.setText("去评价");
    }
    public void setList5(ThreeViewHolder holder,Data2 data2){
        holder.textId.setText(data2.getOrder_sn());
        holder.textType.setText(data2.getCate_name());
        holder.textPrice.setText(data2.getTask_price());
        holder.textTimescope.setText(data2.getDelivery_time());
        holder.textPaystate.setText("已支付");
        holder.textState.setText("已完成...");
        holder.buttonThree.setText("删除订单");
    }
    public void setList6(ThreeViewHolder holder,Data2 data2){
        holder.textId.setText(data2.getOrder_sn());
        holder.textType.setText(data2.getCate_name());
        holder.textPrice.setText(data2.getTask_price());
        holder.textTimescope.setText(data2.getDelivery_time());
        holder.textPaystate.setText("已支付");
        if (data2.getProgress().equals("6")){
            holder.textState.setText("已取消...");
        }else if (data2.getProgress().equals("7")){
            holder.textState.setText("已退款...");
        }
        holder.buttonThree.setText("删除订单");

    }
}
