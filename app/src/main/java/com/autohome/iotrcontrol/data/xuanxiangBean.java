package com.autohome.iotrcontrol.data;

import java.util.UUID;

public class xuanxiangBean implements recyclerListItemBean{
    String name;
    String udpMessage;
    String mqttTopic;
    String mqttMessage;
    public String uid;

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

}
