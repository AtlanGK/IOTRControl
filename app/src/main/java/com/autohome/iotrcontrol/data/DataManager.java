package com.autohome.iotrcontrol.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autohome.iotrcontrol.util.LogUtil;
import com.autohome.iotrcontrol.util.SpHelper;


import java.util.ArrayList;

public class DataManager {
    private static DataManager mInstance;

    private UDPBean mUdpBean;
    private MQTTBean mMqttBean;
    private int Type = 0;//0:udp,1:mqtt
    public boolean isInited = false;
    private ArrayList<zhutiBean> mUserSavedData;

    private DataManager(){
        mUserSavedData = new ArrayList<>();
    }

    public static DataManager getInstance(){
        if(mInstance == null){
            synchronized (DataManager.class){
                if(mInstance == null){
                    mInstance = new DataManager();
                }
            }
        }
        return mInstance;
    }

    public void setUdpBean(UDPBean bean){
        this.mUdpBean = bean;
    }

    public void setMqttBean(MQTTBean bean){
        this.mMqttBean = bean;
    }

    public UDPBean getmUdpBean() {
        return mUdpBean;
    }

    public MQTTBean getmMqttBean() {
        return mMqttBean;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public void commitZhutiBeans(ArrayList<zhutiBean> data){
        mUserSavedData = data;
        //commit之后持久化存储
        SpHelper.commitString(SpHelper.ACTIVITY_ZHUTIBEANS,""+zhutiBeanToStr());
    }

    public ArrayList<zhutiBean> getZhutiBeans() {
        return mUserSavedData;
    }

    public String zhutiBeanToStr(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for(int i = 0;i<mUserSavedData.size();i++){
            stringBuffer.append(mUserSavedData.get(i).toString());
            if(i != mUserSavedData.size() -1) {
                //不是最后一位
                stringBuffer.append(",");
            }
        }
        stringBuffer.append("]");
        LogUtil.d("gktest zhutiBeanToStr ==",stringBuffer.toString());
        return stringBuffer.toString();
    }

    public void getSpZhutiBeanData(){
        String s = SpHelper.getString(SpHelper.ACTIVITY_ZHUTIBEANS,"");
        if(TextUtils.isEmpty(s)){
            //启动读取sp为空，return
            return;
        }
        //不为空，解析存储在sp的数据
        JSONArray array = JSONArray.parseArray(s);
        mUserSavedData.clear();
        ArrayList<zhutiBean> tempSpBeans = new ArrayList<>();
        for(int i = 0;i<array.size();i++){
            JSONObject item = array.getJSONObject(i);
            zhutiBean itemBean = zhutiBean.parseJsonObj(item);
            tempSpBeans.add(itemBean);
        }
        mUserSavedData.addAll(tempSpBeans);
    }


}
