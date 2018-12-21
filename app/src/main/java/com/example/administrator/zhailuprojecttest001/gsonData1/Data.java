package com.example.administrator.zhailuprojecttest001.gsonData1;

import java.util.List;

public class Data {
    private List<Categorie> categories;
    private List<Banner> banners;
    private List<Notice> notices;

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }
}
