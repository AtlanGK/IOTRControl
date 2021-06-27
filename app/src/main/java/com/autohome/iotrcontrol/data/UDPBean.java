package com.autohome.iotrcontrol.data;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class UDPBean {

    public String ipAddress;
    public String port;

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

    @Override
    public String toString(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("ipAddress", ipAddress);
            obj.put("port", port);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    public static UDPBean parseJsonObj(JSONObject o) {
        UDPBean parseBean = new UDPBean();
        if (o == null) {
            return null;
        } else {
            try {
                JSONObject obj = o;
                parseBean.setIpAddress(obj.get("ipAddress").toString());
                parseBean.setPort(obj.get("port").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return parseBean;
        }
    }
}
