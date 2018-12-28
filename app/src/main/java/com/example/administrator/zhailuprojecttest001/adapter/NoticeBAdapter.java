package com.example.administrator.zhailuprojecttest001.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.zhailuprojecttest001.GlideApp;
import com.example.administrator.zhailuprojecttest001.MainActivity;
import com.example.administrator.zhailuprojecttest001.R;
import com.example.administrator.zhailuprojecttest001.data.NoticeBData;

import java.util.List;


//活动通知RecyclerView的适配器
public class NoticeBAdapter extends RecyclerView.Adapter<NoticeBAdapter.ViewHolder>{

    private List<NoticeBData> mNoticeBDataList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bImage;
        Button bText01;

        public ViewHolder(View view){
            super(view);
            bImage=view.findViewById(R.id.item_imagebutton_b_01);
            bText01=view.findViewById(R.id.item_button_b_01);
        }
    }
    public NoticeBAdapter(List<NoticeBData> noticeBDataList,Context context){
        mNoticeBDataList = noticeBDataList;
        mContext=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_imagebutton_b,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        NoticeBData noticeBData = mNoticeBDataList.get(i);
        GlideApp.with(mContext).load(noticeBData.getImageURL()).into(viewHolder.bImage);
//        viewHolder.bImage.setBackgroundResource(noticeBData.getImageId());
        viewHolder.bText01.setText(noticeBData.getText01());
    }

    @Override
    public int getItemCount() {
        return mNoticeBDataList.size();
    }
}
