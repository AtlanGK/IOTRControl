package com.autohome.iotrcontrol.data;

import java.util.ArrayList;

public class zhutiBean implements recyclerListItemBean{
    String name;
    ArrayList<gongnengBean> gongnengBeans;
    public zhutiBean(String na){
        name = na;
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
