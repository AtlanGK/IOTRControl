package com.autohome.iotrcontrol.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UDPUtils implements Runnable {
    public boolean keepRunning = true;//线程开始标志
    public static final String TAG = "TEST";
    //发送目的主机IP和端口
    private static String SERVER_IP;
    private static int SERVER_PORT;
    //本机监听的端口
    private static int LOCAL_PORT = 8929;

    //发送的消息
    private String message = "test";

    //服务器接收的消息
    private String receive;
    //Handler传递的数据
    private Message msg;
    //Message传递的Buddle参数
    private Bundle bundle;

    //wifi名和密码
    private String SSID, password;

    public UDPUtils() {

    }


    public UDPUtils(String Server_IP, int Server_Port) {
        SERVER_IP = Server_IP;
        SERVER_PORT = Server_Port;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 线程停止标志
     *
     * @param keepRunning
     */
    public void setKeepRunning(boolean keepRunning) {
        this.keepRunning = keepRunning;
    }

    public boolean getKeepRunning() {
        return this.keepRunning;
    }

    /**
     * 服务端监听程序
     */
    public void StartListen() {
        keepRunning = getKeepRunning();
        DatagramSocket socket = null;
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        try {
            socket = new DatagramSocket(LOCAL_PORT);
            socket.setBroadcast(true);
            Log.i(TAG, "socket");
//          socket.setSoTimeout(200);  
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        while (keepRunning) {
            try {
//等待客户机连接  
                packet.setData(data);
                Log.e(TAG, "receive0");
                socket.receive(packet);
                receive = new String(packet.getData(), 0, packet.getLength());
                msg = new Message();
                bundle = new Bundle();

//把数据放到buddle中
                bundle.putString("receive", receive);
//把buddle传递到message
                msg.setData(bundle);
                myHandler.sendMessage(msg);
            } catch (Exception e) {
                continue;
            }
        }

        if (socket != null) {
            socket.close();
            socket = null;
        }
    }

    //利用Handler将接收的数据实时打印出来
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = new Bundle();
//从传过来的message数据中取出传过来的绑定数据的bundle对象
            bundle = msg.getData();
            receive = bundle.getString("receive");
            setMessage(receive);
        }
    };

    public void sendControInfo(String message) {

        Log.d("UDPUtils",""+message);
        try {
            DatagramSocket sendSocket = new DatagramSocket();

            byte[] configInfo = message.getBytes();

            InetAddress ip = InetAddress.getByName(SERVER_IP); //即目的IP
            DatagramPacket sendPacket = new DatagramPacket(configInfo, configInfo.length, ip, SERVER_PORT);// 创建发送类型的数据报：  

            sendSocket.send(sendPacket); // 通过套接字发送数据：             

            sendSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        StartListen();
    }
}