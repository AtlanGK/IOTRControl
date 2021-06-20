package com.autohome.iotrcontrol.data;

public class xuanxiangBean implements recyclerListItemBean{
    String name;
    String udpMessage;
    String mqttTopic;
    String mqttMessage;

    public xuanxiangBean(String na){
        name = na;
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
