package com.example.administrator.zhailuprojecttest001.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.data.CouponData;
import com.example.administrator.zhailuprojecttest001.data.RechargeData;

import java.util.List;


//充值列表RecyclerView的适配器
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder>{

    private static final String TAG = "RechargeAdapter";

    private List<CouponData> mCouponDataList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        TextView textView2;
        TextView textView3;

        public ViewHolder(View view){
            super(view);
            textView1=view.findViewById(R.id.textview_coupon_typename);
            textView2=view.findViewById(R.id.textview_coupon_time);
            textView3=view.findViewById(R.id.textview_coupon_price);
        }
    }
    public CouponAdapter(List<CouponData> couponDataList){
        mCouponDataList = couponDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_couponlist,viewGroup,false);
        CouponAdapter.ViewHolder holder=new CouponAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CouponData couponData=mCouponDataList.get(i);
        viewHolder.textView1.setText("宅鹿—"+couponData.getTypeName()+"通用券");
        viewHolder.textView2.setText("有效期："+formatChange(couponData.getStartTime())+"—"+formatChange(couponData.getEndTime()));
        viewHolder.textView3.setText(couponData.getNumPrice());
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: "+mCouponDataList.size());
        return mCouponDataList.size();
    }

    public String formatChange(String first){
        String second=first.replace("-",".");
        return second;
    }
}
