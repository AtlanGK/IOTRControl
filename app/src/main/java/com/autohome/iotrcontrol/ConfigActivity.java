package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.MQTTBean;
import com.autohome.iotrcontrol.data.UDPBean;

public class ConfigActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout mBack;
    private TextView mUpdTv,mMqttTv;
    private TextView mConfirm,mCancel;
    private LinearLayout mUdpContainer;
    private LinearLayout mMqttContainer;
    private EditText mUdpIpAddressEd,mUdpPortEd;
    private EditText mMqttIpAddressEd,mMqttPortEd,mMqttClientIDEd,mMqttUsernameEd,mMqttPasswordEd;
    int state;
    //0: UDP ,1: MQTT

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_config);
        mContext = this;
        initView();
    }

    private void initView() {
        mBack = findViewById(R.id.config_top_header_back_rl);
        mUpdTv = findViewById(R.id.config_udp_tv);
        mMqttTv = findViewById(R.id.config_mqtt_tv);
        mUdpContainer = findViewById(R.id.config_mid_udp_ll);
        mMqttContainer = findViewById(R.id.config_mid_mqtt_ll);
        mConfirm = findViewById(R.id.config_bottom_confirm_tv);
        mCancel = findViewById(R.id.config_bottom_cancel_tv);
        mUdpIpAddressEd = findViewById(R.id.config_udp_ipaddr_ed);
        mUdpPortEd = findViewById(R.id.config_udp_port_ed);
        mMqttIpAddressEd = findViewById(R.id.config_mqtt_ipaddr_ed);
        mMqttPortEd = findViewById(R.id.config_mqtt_port_ed);
        mMqttClientIDEd = findViewById(R.id.config_mqtt_clientid_ed);
        mMqttUsernameEd = findViewById(R.id.config_mqtt_username_ed);
        mMqttPasswordEd = findViewById(R.id.config_mqtt_password_ed);
        mBack.setOnClickListener(this);
        mUpdTv.setOnClickListener(this);
        mMqttTv.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mUdpContainer.setVisibility(View.VISIBLE);
        mMqttContainer.setVisibility(View.GONE);
        state = 0;
    }

    private void saveEditInfoConfirm() {
        if(state == 0) {
            UDPBean udpBean = new UDPBean();
            udpBean.ipAddress = mUdpIpAddressEd.getText().toString();
            udpBean.port = mUdpPortEd.getText().toString();
            DataManager.getInstance().setUdpBean(udpBean);
            DataManager.getInstance().isInited = true;
            DataManager.getInstance().setType(0);
            Toast.makeText(mContext,"UDP配置保存成功，数据："+udpBean.toString(),Toast.LENGTH_SHORT).show();

        }else if(state == 1){
            MQTTBean mqttBean = new MQTTBean();
            mqttBean.ipAddress = mMqttIpAddressEd.getText().toString();
            mqttBean.port = mMqttPortEd.getText().toString();
            mqttBean.clientId = mMqttClientIDEd.getText().toString();
            mqttBean.userName = mMqttUsernameEd.getText().toString();
            mqttBean.passWord = mMqttPasswordEd.getText().toString();

            DataManager.getInstance().setMqttBean(mqttBean);
            DataManager.getInstance().isInited = true;
            DataManager.getInstance().setType(1);
            Toast.makeText(mContext,"MQTT配置保存成功，数据："+mqttBean.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.config_top_header_back_rl:
                this.finish();
                break;
            case R.id.config_udp_tv:
                Toast.makeText(mContext,"设置模式为UDP",Toast.LENGTH_SHORT).show();
                state = 0;
                mUdpContainer.setVisibility(View.VISIBLE);
                mMqttContainer.setVisibility(View.GONE);
                break;
            case R.id.config_mqtt_tv:

                Toast.makeText(mContext,"设置模式为MQTT",Toast.LENGTH_SHORT).show();
                state = 1;
                mUdpContainer.setVisibility(View.GONE);
                mMqttContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.config_bottom_confirm_tv:
                saveEditInfoConfirm();
                break;
            case R.id.config_bottom_cancel_tv:

                Toast.makeText(mContext,"取消",Toast.LENGTH_SHORT).show();
                //do cancel operation
                break;
            default:
                break;
        }
    }

}