package com.autohome.iotrcontrol.data;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class MQTTBean {
    public String ipAddress;
    public String port;
    public String clientId;
    public String userName;
    public String passWord;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("ipAddress", ipAddress);
            obj.put("port", port);
            obj.put("clientId", clientId);
            obj.put("userName", userName);
            obj.put("passWord", passWord);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    public static MQTTBean parseJsonObj(JSONObject o){
        MQTTBean parseBean = new MQTTBean();
        if(o == null) {
            return null;
        }else {
            try {
                JSONObject obj = o;
                parseBean.setIpAddress(obj.get("ipAddress").toString());
                parseBean.setPort(obj.get("port").toString());
                parseBean.setClientId(obj.get("clientId").toString());
                parseBean.setUserName(obj.get("userName").toString());
                parseBean.setPassWord(obj.get("passWord").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return parseBean;
        }
    }
}
