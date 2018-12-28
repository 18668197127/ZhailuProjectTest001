package com.example.administrator.zhailuprojecttest001.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.data.ActivityBData;
import com.example.administrator.zhailuprojecttest001.data.RechargeData;

import java.util.List;


//充值列表RecyclerView的适配器
public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.ViewHolder>{

    private List<RechargeData> mRechargeDataList;



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        TextView textView2;
        TextView textView3;

        public ViewHolder(View view){
            super(view);
            textView1=view.findViewById(R.id.textview_recharge_time);
            textView2=view.findViewById(R.id.textview_recharge_type);
            textView3=view.findViewById(R.id.textview_recharge_price);
        }
    }
    public RechargeAdapter(List<RechargeData> rechargeDataList){
        mRechargeDataList = rechargeDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rechargelist,viewGroup,false);
        RechargeAdapter.ViewHolder holder=new RechargeAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RechargeData rechargeData=mRechargeDataList.get(i);
        viewHolder.textView1.setText(rechargeData.getCreateTime());
        viewHolder.textView2.setText(rechargeData.getPayType());
        viewHolder.textView3.setText(rechargeData.getMoney());
    }

    @Override
    public int getItemCount() {
        return mRechargeDataList.size();
    }
}
