package com.autohome.iotrcontrol.data;

public class UDPBean {
    public String ipAddress;
    public String port;
    @Override
    public String toString(){
        return ipAddress+":"+port;
    }
}
