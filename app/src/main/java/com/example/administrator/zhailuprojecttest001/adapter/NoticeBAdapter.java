package com.example.administrator.zhailuprojecttest001.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.data.ActivityBData;

import java.util.List;


//活动通知RecyclerView的适配器
public class NoticeBAdapter extends RecyclerView.Adapter<NoticeBAdapter.ViewHolder>{

    private List<ActivityBData> mActivityBDataList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bImage;
        Button bText01;

        public ViewHolder(View view){
            super(view);
            bImage=view.findViewById(R.id.item_imagebutton_b_01);
            bText01=view.findViewById(R.id.item_button_b_01);
        }
    }
    public NoticeBAdapter(List<ActivityBData> activityBDataList){
        mActivityBDataList = activityBDataList;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_imagebutton_b,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ActivityBData activityBData= mActivityBDataList.get(i);
        viewHolder.bImage.setImageResource(activityBData.getImageId());
//        viewHolder.bImage.setBackgroundResource(activityBData.getImageId());
        viewHolder.bText01.setText(activityBData.getText01());
    }

    @Override
    public int getItemCount() {
        return mActivityBDataList.size();
    }
}
