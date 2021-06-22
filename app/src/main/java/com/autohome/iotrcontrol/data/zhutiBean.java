package com.autohome.iotrcontrol.data;

import java.util.ArrayList;
import java.util.UUID;

public class zhutiBean implements recyclerListItemBean{
    String name;
    public String uid;
    ArrayList<gongnengBean> gongnengBeans;
    public zhutiBean(String na){
        name = na;
        gongnengBeans = new ArrayList<>();
        uid = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<gongnengBean> getGongnengBeans() {
        return gongnengBeans;
    }

    public void setGongnengBeans(ArrayList<gongnengBean> gongnengBeans) {
        this.gongnengBeans = gongnengBeans;
    }

}
