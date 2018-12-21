package com.example.administrator.zhailuprojecttest001.data;


//活动通知的数据类
public class ActivityBData {
    private int imageId;
    private String text01;

    public ActivityBData(int imageId, String text01) {
        this.imageId = imageId;
        this.text01 = text01;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getText01() {
        return text01;
    }

    public void setText01(String text01) {
        this.text01 = text01;
    }
}
