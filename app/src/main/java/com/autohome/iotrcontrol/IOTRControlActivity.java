package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.util.MQTTManager;
import com.autohome.iotrcontrol.util.UDPUtils;

public class IOTRControlActivity extends Activity implements View.OnClickListener{

    private FrameLayout mSetting;
    private TextView mTv1Open;
    private TextView mTv1Close;
    private Context mContext;

    private UDPUtils udpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_main);
        mContext = this;
        initView();
        initdefaultConfig();
    }

    private void initView() {
        mSetting = findViewById(R.id.homepage_top_header_setting_fr);
        mTv1Open = findViewById(R.id.activity_iot_main_right_tv1);
        mTv1Close = findViewById(R.id.activity_iot_main_right_tv2);
        mSetting.setOnClickListener(this);
        mTv1Open.setOnClickListener(this);
        mTv1Close.setOnClickListener(this);
    }

    private void initdefaultConfig() {
        //从sp读出上次配置，并初始化mqtt client
    }

    private void clickResponse(String msg) {
        String toastMsg = "";
        if(DataManager.getInstance().getType() == 0){
            //udp 模式
            toastMsg = msg+" UDP"+"  "+DataManager.getInstance().getmUdpBean().toString();
        }else if(DataManager.getInstance().getType() == 1){
            //mqtt 模式
            toastMsg = msg+" MQTT"+"  "+DataManager.getInstance().getmMqttBean().toString();
        }
        if(!DataManager.getInstance().isInited){
            toastMsg = msg+"   暂未配置UDP或MQTT参数";
        }
        Toast.makeText(mContext,toastMsg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy(){
        MQTTManager.getInstance().closeMQTT();
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        String serverIp;
        int serverPort;
        String clientId;
        switch (v.getId()) {
            case R.id.homepage_top_header_setting_fr:
                Intent intent = new Intent();
                intent.setClass(mContext, ConfigActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_iot_main_right_tv1:
                if(DataManager.getInstance().getType() == 0) {
                    if(null == DataManager.getInstance().getmUdpBean()){
                        Toast.makeText(mContext, "需要先配置udp参数才能发送数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    serverIp = DataManager.getInstance().getmUdpBean().ipAddress;
                    serverPort = Integer.parseInt(DataManager.getInstance().getmUdpBean().port);
                    if (!TextUtils.isEmpty(serverIp) && serverPort > 0) {
                        udpUtils = new UDPUtils(serverIp, serverPort);
                        clickResponse("点击投影打开");
                        new Thread() {
                            @Override
                            public void run() {
                                if (udpUtils != null) {
                                    udpUtils.sendControInfo("_Projector_on_");
                                } else {
                                    //log message
                                }
                            }
                        }.start();
                    } else {
                        Toast.makeText(mContext, "需要先配置udp参数才能发送数据", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(null == DataManager.getInstance().getmMqttBean()){
                        Toast.makeText(mContext, "需要先配置MQTT参数才能发送数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MQTTManager.getInstance().sendMessage("Projector","on");
                }
                break;
            case R.id.activity_iot_main_right_tv2:
                if(DataManager.getInstance().getType() == 0) {
                    if(null == DataManager.getInstance().getmUdpBean()){
                        Toast.makeText(mContext, "需要先配置udp参数才能发送数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    serverIp = DataManager.getInstance().getmUdpBean().ipAddress;
                    serverPort = Integer.parseInt(DataManager.getInstance().getmUdpBean().port);
                    if (!TextUtils.isEmpty(serverIp) && serverPort > 0) {
                        udpUtils = new UDPUtils(serverIp, serverPort);
                        clickResponse("点击投影关闭");
                        new Thread() {
                            @Override
                            public void run() {
                                if (udpUtils != null) {
                                    udpUtils.sendControInfo("_Projector_off_");
                                } else {
                                    //log message
                                }
                            }
                        }.start();
                    } else {
                        Toast.makeText(mContext, "需要先配置udp参数才能发送数据", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(null == DataManager.getInstance().getmMqttBean()){
                        Toast.makeText(mContext, "需要先配置MQTT参数才能发送数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MQTTManager.getInstance().sendMessage("Projector","off");
                }
                break;
            default:
                break;
        }
    }

}
