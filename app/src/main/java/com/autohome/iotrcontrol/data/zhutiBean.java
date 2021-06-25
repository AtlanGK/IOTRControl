package com.autohome.iotrcontrol.data;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class zhutiBean implements recyclerListItemBean, Serializable{
    String name;
    public String uid;
    ArrayList<gongnengBean> gongnengBeans;

    public zhutiBean() {
        gongnengBeans = new ArrayList<>();
        uid = UUID.randomUUID().toString();
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
            obj.put("gongnengs",""+gongnengToStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    private String gongnengToStr() {
        StringBuffer s = new StringBuffer();
        if(gongnengBeans == null || gongnengBeans.size() ==0){
            s.append("");
            return s.toString();
        }
        s.append("[");
        for(int i = 0 ;i <gongnengBeans.size();i++){
            s.append(gongnengBeans.get(i).toString());
            if(i != gongnengBeans.size() -1){
                //不是最后一位
                s.append(",");
            }
        }
        s.append("]");
        return s.toString();
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
            ArrayList<gongnengBean> parseGongnengs = new ArrayList<>();
            String parseBeanStr = obj.get("gongnengs").toString();
            parseGongnengs = convertStrToBeans(parseBeanStr);
            if(null != parseGongnengs && parseGongnengs.size() >0) {
                parseBean.setGongnengBeans(parseGongnengs);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseBean;
    }

    private static ArrayList<gongnengBean> convertStrToBeans(String parseBeanStr) {
        ArrayList<gongnengBean> results = new ArrayList<>();
        JSONArray arr = JSONArray.parseArray(parseBeanStr);
        if(arr == null || arr.size() == 0){
            return null;
        }
        for(int i = 0;i<arr.size();i++){
            JSONObject obj = arr.getJSONObject(i);
            gongnengBean itemBean = gongnengBean.parseJsonObj(obj);
            if(null != itemBean) {
                results.add(itemBean);
            }
        }
        return results;
    }

}
