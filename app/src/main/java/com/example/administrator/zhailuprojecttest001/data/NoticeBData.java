package com.example.administrator.zhailuprojecttest001.data;


//活动通知的数据类
public class NoticeBData {
    private String  imageURL;
    private String text01;

    public NoticeBData(String  imageURL, String text01) {
        this.imageURL = imageURL;
        this.text01 = text01;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getText01() {
        return text01;
    }

    public void setText01(String text01) {
        this.text01 = text01;
    }
}
