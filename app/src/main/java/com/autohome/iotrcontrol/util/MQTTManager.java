package com.autohome.iotrcontrol.util;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.eclipse.paho.client.mqttv3.MqttConnectOptions.MQTT_VERSION_3_1_1;

public class MQTTManager {

    private String broker = "";//serverURI
    private String secretKey = "";//固定配置
    private String acessKey = "";//固定配置

    private String topic = "";//自己定义
    private String groupId = "";//自己定义
    private String clientId = "";//自己定义

    private MqttClient mqttClient;
    private volatile static MQTTManager manager;

    private int[] qos = {0, 0};//订阅个数就是数组的长度

    private ScheduledExecutorService reconnectPool;//重连线程池

    public static MQTTManager getInstance() {
        if (manager == null) {
            synchronized (MQTTManager.class) {
                if (manager == null)
                    manager = new MQTTManager();
            }
        }
        return manager;
    }

    public MQTTManager() {

    }

    public void setMqttIpPortAndClientId(String Ip,int port,String clientid){
        this.broker = "tcp://"+Ip+":"+ port;
        this.clientId = clientid;
    }
    /**
     * 发送信息
     * @param msg
     */
    public void sendMessage(String topicmsg,String msg) {
        topic = topicmsg;
        MqttMessage message = new MqttMessage(msg.getBytes());
        message.setQos(0);
        LogUtil.d("MQTTManager","msg ="+msg);
        try {
            if (mqttClient != null)
                mqttClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
            LogUtil.i("MqttException-sendMQTT-" + e);
        }
    }

    /**
     * 开启MQTT连接和订阅
     */
    public void startSendMQTT() {
        try {
            closeMQTT();//断开和关闭连接的操作，由于我们需求需要，切换用户要重新创建新的连接，一般应用中基本都会始终订阅一条
            MemoryPersistence persistence = new MemoryPersistence();
            mqttClient = new MqttClient(broker, clientId, persistence);
            final MqttConnectOptions connOpts = new MqttConnectOptions();
//            String sign = MacSignature.macSignature(clientId.split("@@@")[0], secretKey);
//            connOpts.setUserName(acessKey);
            connOpts.setServerURIs(new String[]{broker});
//            connOpts.setPassword(sign.toCharArray());
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(20);
            connOpts.setConnectionTimeout(10);
            connOpts.setMqttVersion(MQTT_VERSION_3_1_1);
            connOpts.setAutomaticReconnect(false);//禁用自带重连机制，用于TV端会出现不稳定性，所以自己写了重连
            mqttClient.setCallback(new MqttCallbackExtended() {
                public void connectComplete(boolean reconnect, String serverURI) {
                    LogUtil.i("Send connect success" + topic);
                    closeReconnectTask();
                    subscribeFilter();//mqtt每次连接成功都得订阅Topic
                }

                public void connectionLost(Throwable throwable) {
                    LogUtil.i("mqtt connection lost");
                    startReconnectTask();
                }

                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    LogUtil.i("messageArrived:" + topic + "------" + new String(mqttMessage.getPayload()));
                }

                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    try {
                        LogUtil.i("deliveryComplete:" + iMqttDeliveryToken.getMessage().toString());
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            });
            mqttClient.connect(connOpts);
        } catch (Exception me) {
            me.printStackTrace();
        }
    }

    /**
     * 订阅Topic
     */
    private void subscribeFilter() {
        String registerTopic = "Test1";//自定义
        String controlTopic = "Test2";//自定义，作为示例订阅了两个
        String[] topicFilters = new String[]{registerTopic, controlTopic};

        try {
            mqttClient.subscribe(topicFilters, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private synchronized void startReconnectTask() {//开启重连任务
        if (reconnectPool != null) return;
        reconnectPool = Executors.newScheduledThreadPool(1);
        reconnectPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mqttClient == null || mqttClient.isConnected()) return;
                    mqttClient.reconnect();
                    LogUtil.d("reconnectSendMQTT" + topic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5 * 1000, TimeUnit.MILLISECONDS);
    }

    public synchronized void closeReconnectTask() {//程序销毁的时候也记得关闭
        if (reconnectPool != null) {
            reconnectPool.shutdownNow();
            reconnectPool = null;
        }
    }

    public void closeMQTT() {//在程序销毁的时候也记得调用
        try {
            closeReconnectTask();
            if (mqttClient != null) {
                mqttClient.disconnect();
                mqttClient.close();
                mqttClient = null;
                LogUtil.d("closeSendMQTT" + topic);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
