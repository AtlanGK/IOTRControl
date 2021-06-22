package com.autohome.iotrcontrol.data;

import java.util.ArrayList;
import java.util.UUID;

public class gongnengBean implements recyclerListItemBean{
    String name;
    ArrayList<xuanxiangBean> xuanxiangBeans;
    public String uid;

    public gongnengBean(String na){
        name = na;
        xuanxiangBeans = new ArrayList<>();
        uid = UUID.randomUUID().toString();
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
