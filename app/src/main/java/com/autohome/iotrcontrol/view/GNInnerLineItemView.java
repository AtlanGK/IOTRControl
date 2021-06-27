package com.autohome.iotrcontrol.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.R;
import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.xuanxiangBean;
import com.autohome.iotrcontrol.util.MQTTManager;
import com.autohome.iotrcontrol.util.UDPUtils;

import androidx.annotation.Nullable;

public class GNInnerLineItemView extends LinearLayout {
    private TextView mTV1;
    private TextView mTV2;
    private xuanxiangBean mXX1,mXX2;
    private Context mContext;
    String serverIp;
    int serverPort;
    private UDPUtils udpUtils;
    private Handler mHandler;
    private String mUdpmessage;

    public GNInnerLineItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_gn_inner_line_item, this, true);
        mContext = context;
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case 1:
                        Toast.makeText(mContext, "发送udp参数 "+mUdpmessage, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }}};
        mTV1 = findViewById(R.id.view_gn_inner_line_tv1);
        mTV2 = findViewById(R.id.view_gn_inner_line_tv2);
        mTV1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mXX1 == null)
                    return;
                if (DataManager.getInstance().getType() == 0) {
                    if (null == DataManager.getInstance().getmUdpBean()) {
                        Toast.makeText(mContext, "需要先配置udp参数才能发送数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    serverIp = DataManager.getInstance().getmUdpBean().ipAddress;
                    serverPort = Integer.parseInt(DataManager.getInstance().getmUdpBean().port);
                    if (!TextUtils.isEmpty(serverIp) && serverPort > 0) {
                        udpUtils = new UDPUtils(serverIp, serverPort);
                        new Thread() {
                            @Override
                            public void run() {
                                if (udpUtils != null) {
                                    String udpmessage = mXX1.getUdpMessage();
                                    if(!TextUtils.isEmpty(udpmessage)) {
                                        //点击发送udp message
                                        udpUtils.sendControInfo(udpmessage);
                                        mUdpmessage = udpmessage;
                                        mHandler.sendEmptyMessage(1);
                                    }
                                } else {
                                    //log message
                                }
                            }
                        }.start();
                    } else {
                        Toast.makeText(mContext, "需要先配置udp参数才能发送数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (null == DataManager.getInstance().getmMqttBean()) {
                        Toast.makeText(mContext, "需要先配置MQTT参数才能发送数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String mqttTopic = mXX1.getMqttTopic();
                    String mqttMessage = mXX1.getMqttMessage();
                    if(!TextUtils.isEmpty(mqttTopic) && !TextUtils.isEmpty(mqttMessage)) {
                        MQTTManager.getInstance().sendMessage(mqttTopic, mqttMessage);
                        Toast.makeText(mContext, "发送mqtt参数 "+mqttTopic + " " +mqttMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mTV2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mXX2 == null)
                    return;
                if (DataManager.getInstance().getType() == 0) {
                    if (null == DataManager.getInstance().getmUdpBean()) {
                        Toast.makeText(mContext, "需要先配置udp参数才能发送数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    serverIp = DataManager.getInstance().getmUdpBean().ipAddress;
                    serverPort = Integer.parseInt(DataManager.getInstance().getmUdpBean().port);
                    if (!TextUtils.isEmpty(serverIp) && serverPort > 0) {
                        udpUtils = new UDPUtils(serverIp, serverPort);
                        new Thread() {
                            @Override
                            public void run() {
                                if (udpUtils != null) {
                                    String udpmessage = mXX2.getUdpMessage();
                                    if(!TextUtils.isEmpty(udpmessage)) {
                                        //点击发送udp message
                                        udpUtils.sendControInfo(udpmessage);
                                        mUdpmessage = udpmessage;
                                        mHandler.sendEmptyMessage(1);
                                    }
                                } else {
                                    //log message
                                }
                            }
                        }.start();
                    } else {
                        Toast.makeText(mContext, "需要先配置udp参数才能发送数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (null == DataManager.getInstance().getmMqttBean()) {
                        Toast.makeText(mContext, "需要先配置MQTT参数才能发送数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String mqttTopic = mXX2.getMqttTopic();
                    String mqttMessage = mXX2.getMqttMessage();
                    if(!TextUtils.isEmpty(mqttTopic) && !TextUtils.isEmpty(mqttMessage)) {
                        MQTTManager.getInstance().sendMessage(mqttTopic, mqttMessage);
                        Toast.makeText(mContext, "发送mqtt参数 "+mqttTopic + " " +mqttMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public TextView getmTV1() {
        return mTV1;
    }

    public void setmTV1(TextView mTV1) {
        this.mTV1 = mTV1;
    }

    public TextView getmTV2() {
        return mTV2;
    }

    public void setmTV2(TextView mTV2) {
        this.mTV2 = mTV2;
    }

    public xuanxiangBean getmXX1() {
        return mXX1;
    }

    public void setmXX1(xuanxiangBean mXX1) {
        this.mXX1 = mXX1;
        mTV1.setText(mXX1.getName());
    }

    public xuanxiangBean getmXX2() {
        return mXX2;
    }

    public void setmXX2(xuanxiangBean mXX2) {
        this.mXX2 = mXX2;
        mTV2.setText(mXX2.getName());
    }

}
