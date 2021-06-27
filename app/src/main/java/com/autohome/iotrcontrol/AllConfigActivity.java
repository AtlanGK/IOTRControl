package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.MQTTBean;
import com.autohome.iotrcontrol.data.UDPBean;
import com.autohome.iotrcontrol.util.MQTTManager;

public class AllConfigActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout mBack;
    private TextView mUpdTv,mMqttTv,mEditTv;
    private TextView mConfirm,mCancel;
    private TextView mCurrentType;
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
        initDefaultConfig();
    }

    private void initView() {
        mBack = findViewById(R.id.config_top_header_back_rl);
        mUpdTv = findViewById(R.id.config_udp_tv);
        mMqttTv = findViewById(R.id.config_mqtt_tv);
        mEditTv = findViewById(R.id.config_jump_to_edit_tv);
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
        mCurrentType = findViewById(R.id.config_current_mode_tv);
        mBack.setOnClickListener(this);
        mUpdTv.setOnClickListener(this);
        mMqttTv.setOnClickListener(this);
        mEditTv.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        state = DataManager.getInstance().getType();
        if(state == 0){
            mUdpContainer.setVisibility(View.VISIBLE);
            mMqttContainer.setVisibility(View.GONE);
            mCurrentType.setText("当前UDP生效");
        }else{
            mUdpContainer.setVisibility(View.GONE);
            mMqttContainer.setVisibility(View.VISIBLE);
            mCurrentType.setText("当前MQTT生效");
        }
    }

    private void initDefaultConfig() {
        UDPBean currentUdpBean = DataManager.getInstance().getmUdpBean();
        if(currentUdpBean != null){
            mUdpIpAddressEd.setText(currentUdpBean.getIpAddress());
            mUdpPortEd.setText(currentUdpBean.getPort());
        }
        MQTTBean currentMqttBean = DataManager.getInstance().getmMqttBean();
        if(currentMqttBean != null){
            mMqttIpAddressEd.setText(currentMqttBean.getIpAddress());
            mMqttPortEd.setText(currentMqttBean.getPort());
            mMqttClientIDEd.setText(currentMqttBean.getClientId());
            mMqttUsernameEd.setText(currentMqttBean.getUserName());
            mMqttPasswordEd.setText(currentMqttBean.getPassWord());
        }
    }

    private void saveEditInfoConfirm() {
        if(state == 0) {
            UDPBean udpBean = new UDPBean();
            udpBean.ipAddress = mUdpIpAddressEd.getText().toString();
            udpBean.port = mUdpPortEd.getText().toString();
            if(!isCheckUdpValid(udpBean)){
                Toast.makeText(mContext,"UDP配置参数不合法"+udpBean.toString(),Toast.LENGTH_SHORT).show();
                return;
            }
            DataManager.getInstance().setUdpBean(udpBean);
            DataManager.getInstance().isInited = true;
            DataManager.getInstance().setType(0);
            Toast.makeText(mContext,"UDP配置保存成功，数据："+udpBean.toString(),Toast.LENGTH_SHORT).show();

            mCurrentType.setText("当前UDP生效");
            DataManager.getInstance().syncConfigInfos();

        }else if(state == 1){
            MQTTBean mqttBean = new MQTTBean();
            mqttBean.ipAddress = mMqttIpAddressEd.getText().toString();
            mqttBean.port = mMqttPortEd.getText().toString();
            mqttBean.clientId = mMqttClientIDEd.getText().toString();
            mqttBean.userName = mMqttUsernameEd.getText().toString();
            mqttBean.passWord = mMqttPasswordEd.getText().toString();
            if(!isCheckMqttValid(mqttBean)){
                Toast.makeText(mContext,"MQTT配置参数不合法"+mqttBean.toString(),Toast.LENGTH_SHORT).show();
                return;
            }

            DataManager.getInstance().setMqttBean(mqttBean);
            DataManager.getInstance().isInited = true;
            DataManager.getInstance().setType(1);
            Toast.makeText(mContext,"MQTT配置保存成功，数据："+mqttBean.toString(),Toast.LENGTH_SHORT).show();
            mCurrentType.setText("当前MQTT生效");
            DataManager.getInstance().syncConfigInfos();
            String serverIp = DataManager.getInstance().getmMqttBean().ipAddress;
            int serverPort = Integer.parseInt(DataManager.getInstance().getmMqttBean().port);
            String clientId = DataManager.getInstance().getmMqttBean().clientId;
            if(!TextUtils.isEmpty(serverIp) && serverPort > 0 && !TextUtils.isEmpty(clientId)) {
                MQTTManager.getInstance().setMqttIpPortAndClientId(serverIp, serverPort, clientId);
                new Thread() {
                    @Override
                    public void run() {
                        MQTTManager.getInstance().startSendMQTT();
                    }
                }.start();
            }
        }

    }

    private boolean isCheckUdpValid(UDPBean udpBean) {
        if(udpBean == null)
            return false;
        if(TextUtils.isEmpty(udpBean.ipAddress))
            return false;
        if(TextUtils.isEmpty(udpBean.port))
            return false;
        return true;
    }

    private boolean isCheckMqttValid(MQTTBean mqttBean) {
        if(mqttBean == null)
            return false;
        if(TextUtils.isEmpty(mqttBean.ipAddress))
            return false;
        if(TextUtils.isEmpty(mqttBean.port))
            return false;
        if(TextUtils.isEmpty(mqttBean.clientId))
            return false;

            return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.config_top_header_back_rl:
                this.setResult(-1);
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
            case R.id.config_jump_to_edit_tv:
                Intent intent = new Intent();
                intent.setClass(mContext,zhutiActivity.class);
                startActivity(intent);
                break;
            case R.id.config_bottom_confirm_tv:
                saveEditInfoConfirm();
                break;
            case R.id.config_bottom_cancel_tv:
                this.setResult(-1);
                this.finish();
//                Toast.makeText(mContext,"取消",Toast.LENGTH_SHORT).show();
                //do cancel operation
                break;
            default:
                break;
        }
    }

}
