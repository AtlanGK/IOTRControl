package com.autohome.iotrcontrol.data;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class zhutiBean implements recyclerListItemBean{
    String name;
    public String uid;
    ArrayList<gongnengBean> gongnengBeans;

    public zhutiBean() {
    }

    public zhutiBean(String na) {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<gongnengBean> getGongnengBeans() {
        return gongnengBeans;
    }

    public void setGongnengBeans(ArrayList<gongnengBean> gongnengBeans) {
        this.gongnengBeans = gongnengBeans;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    public static zhutiBean parseString(String inputStr){
        zhutiBean parseBean = new zhutiBean();
        try {
            JSONObject obj = JSONObject.parseObject(inputStr);
            parseBean.setName(obj.get("name").toString());
            parseBean.setUid(obj.get("uid").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseBean;
    }

    public static zhutiBean parseJsonObj(JSONObject o){
        zhutiBean parseBean = new zhutiBean();
        try {
            JSONObject obj = o;
            parseBean.setName(obj.get("name").toString());
            parseBean.setUid(obj.get("uid").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseBean;
    }

}
