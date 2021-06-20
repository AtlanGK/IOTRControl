package com.autohome.iotrcontrol.data;

public class DataManager {
    private static DataManager mInstance;

    private UDPBean mUdpBean;
    private MQTTBean mMqttBean;
    private int Type = 0;//0:udp,1:mqtt
    public boolean isInited = false;

    public DataManager(){}

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

}
