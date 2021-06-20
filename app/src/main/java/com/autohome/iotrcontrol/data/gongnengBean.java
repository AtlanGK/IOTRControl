package com.autohome.iotrcontrol.data;

import java.util.ArrayList;

public class gongnengBean implements recyclerListItemBean{
    String name;
    ArrayList<xuanxiangBean> xuanxiangBeans;

    public gongnengBean(String na){
        name = na;
        xuanxiangBeans = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<xuanxiangBean> getXuanxiangBeans() {
        return xuanxiangBeans;
    }

    public void setXuanxiangBeans(ArrayList<xuanxiangBean> xuanxiangBeans) {
        this.xuanxiangBeans = xuanxiangBeans;
    }
}
