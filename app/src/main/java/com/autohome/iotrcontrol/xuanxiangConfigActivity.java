package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.gongnengBean;
import com.autohome.iotrcontrol.data.xuanxiangBean;
import com.autohome.iotrcontrol.data.zhutiBean;

public class xuanxiangConfigActivity extends Activity implements View.OnClickListener{
    private Context mContext;
    private TextView mTitle,mBack,mConfirm,mCancel;
    private EditText mUdpMessage,mMqttTopic,mMqttMessage;

    private zhutiBean mBelongZhuti;
    private gongnengBean mBelongGongneng;
    private xuanxiangBean mBelongXuanxiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuanxiang_config);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {
        mBelongZhuti = (zhutiBean) getIntent().getSerializableExtra("zhuti");
        mBelongGongneng = (gongnengBean) getIntent().getSerializableExtra("gongneng");
        mBelongXuanxiang = (xuanxiangBean) getIntent().getSerializableExtra("xuanxiang");

        mUdpMessage.setText(mBelongXuanxiang.getUdpMessage());
        mMqttTopic.setText(mBelongXuanxiang.getMqttTopic());
        mMqttMessage.setText(mBelongXuanxiang.getMqttMessage());

    }

    private void initView() {
        mTitle = findViewById(R.id.activity_xuanxiang_config_top_title);
        mBack = findViewById(R.id.activity_xuanxiang_config_top_back);
        mConfirm = findViewById(R.id.activity_xuanxiang_config_bottom_confirm_tv);
        mCancel = findViewById(R.id.activity_xuanxiang_config_bottom_cancel_tv);

        mUdpMessage = findViewById(R.id.activity_xuanxiang_config_udp_msg);
        mMqttTopic = findViewById(R.id.activity_xuanxiang_config_mqtt_topic);
        mMqttMessage = findViewById(R.id.activity_xuanxiang_config_mqtt_msg);

        mBack.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_xuanxiang_config_top_back:
                finish();
                break;
            case R.id.activity_xuanxiang_config_bottom_confirm_tv:
                saveCongfigToBelongXuanXiang();
                //todo
                break;
            case R.id.activity_xuanxiang_config_bottom_cancel_tv:
                finish();
                break;
            default:
                break;
        }
    }

    private void saveCongfigToBelongXuanXiang() {
        String strUdpMessage = mUdpMessage.getText().toString();
        String strMqttTopic = mMqttTopic.getText().toString();
        String strMqttMessage = mMqttMessage.getText().toString();

        Toast.makeText(mContext,"saveCongfigToBelongXuanXiang  "+strUdpMessage+"  "+strMqttTopic+"  "+strMqttMessage,Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(strUdpMessage)){
            Toast.makeText(mContext,"udpMessage不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(strMqttTopic)){
            Toast.makeText(mContext,"MqttTopic不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(strMqttTopic)){
            Toast.makeText(mContext,"MqttMessage不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        int findMatchZhutiPos = -1;
        int findMatchGongnengPos = -1;
        int findMatchXuanxiangPos = -1;

        try {
            findMatchZhutiPos = findMatchZhutiBeanPos();
            findMatchGongnengPos = findMatchGongnengBeanPos(DataManager.getInstance().getZhutiBeans().get(findMatchZhutiPos));
            findMatchXuanxiangPos = findMatchXuanxiangBeanPos(DataManager.getInstance().getZhutiBeans().get(findMatchZhutiPos).getGongnengBeans().get(findMatchGongnengPos));
        }catch (Exception e){
            e.printStackTrace();
        }
        if(findMatchZhutiPos != -1 && findMatchGongnengPos != -1 && findMatchXuanxiangPos != -1){
            //找到了匹配位置
            DataManager.getInstance().getZhutiBeans().get(findMatchZhutiPos).getGongnengBeans().get(findMatchGongnengPos).getXuanxiangBeans().get(findMatchXuanxiangPos).setUdpMessage(strUdpMessage);
            DataManager.getInstance().getZhutiBeans().get(findMatchZhutiPos).getGongnengBeans().get(findMatchGongnengPos).getXuanxiangBeans().get(findMatchXuanxiangPos).setMqttTopic(strMqttTopic);
            DataManager.getInstance().getZhutiBeans().get(findMatchZhutiPos).getGongnengBeans().get(findMatchGongnengPos).getXuanxiangBeans().get(findMatchXuanxiangPos).setMqttMessage(strMqttMessage);
            DataManager.getInstance().syncLocalDatas();
            Toast.makeText(mContext,"保存成功",Toast.LENGTH_SHORT).show();
        }

    }

    private int findMatchZhutiBeanPos() {
        int findMatchPos = -1;
        int spZhutiLength = DataManager.getInstance().getZhutiBeans().size();
        for(int i = 0;i < spZhutiLength;i++){
            String spItemUid = DataManager.getInstance().getZhutiBeans().get(i).getUid();
            if(spItemUid.equals(mBelongZhuti.getUid())){
                findMatchPos = i;
            }
        }
        return findMatchPos;
    }
    private int findMatchGongnengBeanPos(zhutiBean mZhutiData) {
        int findMatchPos = -1;
        int spGongnengLength = mZhutiData.getGongnengBeans().size();
        for(int i = 0;i < spGongnengLength;i++){
            String spItemUid = mZhutiData.getGongnengBeans().get(i).getUid();
            if(spItemUid.equals(mBelongGongneng.getUid())){
                findMatchPos = i;
            }
        }
        return findMatchPos;
    }
    private int findMatchXuanxiangBeanPos(gongnengBean mGongnengData) {
        int findMatchPos = -1;
        int spXuanxiangLength = mGongnengData.getXuanxiangBeans().size();
        for(int i = 0;i < spXuanxiangLength;i++){
            String spItemUid = mGongnengData.getXuanxiangBeans().get(i).getUid();
            if(spItemUid.equals(mBelongXuanxiang.getUid())){
                findMatchPos = i;
            }
        }
        return findMatchPos;
    }
}
