package com.autohome.iotrcontrol.data;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.UUID;

public class xuanxiangBean implements recyclerListItemBean, Serializable {
    String name;
    public String uid;
    String udpMessage;
    String mqttTopic;
    String mqttMessage;

    public xuanxiangBean(){
        uid = UUID.randomUUID().toString();
    }


    public xuanxiangBean(String na){
        name = na;
        uid = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUdpMessage() {
        return udpMessage;
    }

    public void setUdpMessage(String udpMessage) {
        this.udpMessage = udpMessage;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }

    public void setMqttTopic(String mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    public String getMqttMessage() {
        return mqttMessage;
    }

    public void setMqttMessage(String mqttMessage) {
        this.mqttMessage = mqttMessage;
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
            obj.put("udpMessage",udpMessage);
            obj.put("mqttTopic",mqttTopic);
            obj.put("mqttMessage",mqttMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    public static xuanxiangBean parseString(String inputStr){
        xuanxiangBean parseBean = new xuanxiangBean();
        try {
            JSONObject obj = JSONObject.parseObject(inputStr);
            parseBean.setName(obj.get("name").toString());
            parseBean.setUid(obj.get("uid").toString());
            parseBean.setUdpMessage(obj.get("udpMessage").toString());
            parseBean.setMqttTopic(obj.get("mqttTopic").toString());
            parseBean.setMqttMessage(obj.get("mqttMessage").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseBean;
    }

    public static xuanxiangBean parseJsonObj(JSONObject o){
        xuanxiangBean parseBean = new xuanxiangBean();
        try {
            JSONObject obj = o;
            parseBean.setName(obj.get("name").toString());
            parseBean.setUid(obj.get("uid").toString());
            parseBean.setUdpMessage(obj.get("udpMessage").toString());
            parseBean.setMqttTopic(obj.get("mqttTopic").toString());
            parseBean.setMqttMessage(obj.get("mqttMessage").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseBean;
    }

}
