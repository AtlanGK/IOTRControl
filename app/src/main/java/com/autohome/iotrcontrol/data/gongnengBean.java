package com.autohome.iotrcontrol.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class gongnengBean implements recyclerListItemBean, Serializable {
    String name;
    ArrayList<xuanxiangBean> xuanxiangBeans;
    public String uid;

    public gongnengBean() {
        xuanxiangBeans = new ArrayList<>();
        uid = UUID.randomUUID().toString();
    }
    
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("uid", uid);
            obj.put("xuanxiangs",""+xuanxiangToStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    private String xuanxiangToStr() {
        StringBuffer s = new StringBuffer();
        if(xuanxiangBeans == null || xuanxiangBeans.size() ==0){
            s.append("");
            return s.toString();
        }
        s.append("[");
        for(int i = 0 ;i <xuanxiangBeans.size();i++){
            s.append(xuanxiangBeans.get(i).toString());
            if(i != xuanxiangBeans.size() -1){
                //不是最后一位
                s.append(",");
            }
        }
        s.append("]");
        return s.toString();
    }
    public static gongnengBean parseString(String inputStr){
        gongnengBean parseBean = new gongnengBean();
        try {
            JSONObject obj = JSONObject.parseObject(inputStr);
            parseBean.setName(obj.get("name").toString());
            parseBean.setUid(obj.get("uid").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseBean;
    }

    public static gongnengBean parseJsonObj(JSONObject o){
        gongnengBean parseBean = new gongnengBean();
        try {
            JSONObject obj = o;
            parseBean.setName(obj.get("name").toString());
            parseBean.setUid(obj.get("uid").toString());
            ArrayList<xuanxiangBean> parseXuanxiangs = new ArrayList<>();
            String parseBeanStr = obj.get("xuanxiangs").toString();
            parseXuanxiangs = convertStrToBeans(parseBeanStr);
            if(null != parseXuanxiangs && parseXuanxiangs.size() >0) {
                parseBean.setXuanxiangBeans(parseXuanxiangs);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseBean;
    }

    private static ArrayList<xuanxiangBean> convertStrToBeans(String parseBeanStr) {
        ArrayList<xuanxiangBean> results = new ArrayList<>();
        JSONArray arr = JSONArray.parseArray(parseBeanStr);
        if(arr == null || arr.size() == 0){
            return null;
        }
        for(int i = 0;i<arr.size();i++){
            JSONObject obj = arr.getJSONObject(i);
            xuanxiangBean itemBean = xuanxiangBean.parseJsonObj(obj);
            if(null != itemBean) {
                results.add(itemBean);
            }
        }
        return results;
    }
}
