package com.example.administrator.zhailuprojecttest001.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.data.ActivityBData;
import com.example.administrator.zhailuprojecttest001.gsonData2.Data2;
import com.example.administrator.zhailuprojecttest001.gsonData2.Result2;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private final Context context;
    private List<Data2> mData2List;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bImage;
        Button bText01;

        public ViewHolder(View view){
            super(view);
            bImage=view.findViewById(R.id.item_imagebutton_b_01);
            bText01=view.findViewById(R.id.item_button_b_01);
        }
    }
    public OrderListAdapter(Context context,List<Data2> data2List){
        this.context=context;
        mData2List=data2List;
    }

    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_imagebutton_b,viewGroup,false);
        OrderListAdapter.ViewHolder holder=new OrderListAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(OrderListAdapter.ViewHolder viewHolder, int i) {
        Data2 result2= mData2List.get(i);
//        viewHolder.bImage.setImageResource(result2.getImageId());
//        viewHolder.bImage.setBackgroundResource(activityBData.getImageId());
//        viewHolder.bText01.setText(result2.getText01());
    }

    @Override
    public int getItemCount() {
        return mData2List.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
