package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.util.MQTTManager;
import com.autohome.iotrcontrol.util.ScreenUtil;
import com.autohome.iotrcontrol.util.UDPUtils;

import java.util.ArrayList;

public class IOTRControlActivity extends Activity implements View.OnClickListener{

    private FrameLayout mSetting;
    private Context mContext;
    private LinearLayout mLeftLL,mRightContainer;
    private ArrayList<zhutiBean> mZhutis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_main);
        mContext = this;
        initView();
        initdefaultConfig();
        initData();
    }

    private void initView() {
        mSetting = findViewById(R.id.homepage_top_header_setting_fr);
        mSetting.setOnClickListener(this);
        mLeftLL = findViewById(R.id.activity_iot_main_left_ll);
        mRightContainer = findViewById(R.id.activity_iot_main_right_ll);

    }

    private void initdefaultConfig() {
        //从sp读出上次配置，并初始化mqtt client
    }

    private void initData() {
        mZhutis = DataManager.getInstance().getZhutiBeans();
        renderLeftZhutiTvs();
    }

    private void renderLeftZhutiTvs() {
        for(int i = 0;i < mZhutis.size();i++){
            TextView tv = new TextView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ScreenUtil.dpToPxInt(mContext,50));
            tv.setLayoutParams(params);
            params.setMargins(10,10,10,10);
            tv.setText(mZhutis.get(i).getName());
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(getResources().getColor(R.color.color_660e1029));
            tv.setTag(mZhutis.get(i));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickLeftTv(v.getTag());
                }
            });
            mLeftLL.addView(tv);
        }
    }

    private void clickLeftTv(Object tag) {
        if(tag instanceof zhutiBean){
            zhutiBean mItemZhutiBean = (zhutiBean) tag;
            Toast.makeText(mContext,""+mItemZhutiBean.getName(),Toast.LENGTH_SHORT).show();
        }
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
        switch (v.getId()) {
            case R.id.homepage_top_header_setting_fr:
                Intent intent = new Intent();
                intent.setClass(mContext, AllConfigActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

}
