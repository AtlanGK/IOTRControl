package com.autohome.iotrcontrol.data;

public class MQTTBean {
    public String ipAddress;
    public String port;
    public String clientId;
    public String userName;
    public String passWord;

    @Override
    public String toString(){
        return ipAddress+":"+port+"/clientid == "+clientId+"/username == "+userName+"/password == "+passWord;
    }
}
