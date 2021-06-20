package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class xuanxiangConfigActivity extends Activity implements View.OnClickListener{
    private Context mContext;
    private TextView mTitle,mBack,mConfirm,mCancel;
    private EditText mUdpMessage,mMqttTopic,mMqttMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuanxiang_config);
        mContext = this;
        initView();
    }

    private void initView() {
        mTitle = findViewById(R.id.activity_xuanxiang_config_top_title);
        mBack = findViewById(R.id.activity_xuanxiang_config_top_back);
        mConfirm = findViewById(R.id.activity_xuanxiang_config_bottom_confirm_tv);
        mCancel = findViewById(R.id.activity_xuanxiang_config_bottom_cancel_tv);

        mUdpMessage = findViewById(R.id.activity_xuanxiang_config_udp_msg);
        mMqttTopic = findViewById(R.id.activity_xuanxiang_config_mqtt_topic);
        mMqttMessage = findViewById(R.id.activity_xuanxiang_config_mqtt_msg);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_xuanxiang_config_bottom_confirm_tv:
                //todo
                break;
            default:
                break;
        }
    }
}
