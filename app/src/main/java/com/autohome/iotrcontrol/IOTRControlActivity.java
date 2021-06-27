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

import com.autohome.iotrcontrol.adapter.shouyeGNAdapter;
import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.gongnengBean;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.util.MQTTManager;
import com.autohome.iotrcontrol.util.ScreenUtil;
import com.autohome.iotrcontrol.util.UDPUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IOTRControlActivity extends Activity implements View.OnClickListener{

    private FrameLayout mSetting;
    private Context mContext;
    private LinearLayout mLeftLL;
    private RecyclerView mRecyclerView;
    private shouyeGNAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<zhutiBean> mZhutis;
    private ArrayList<gongnengBean> mCurrentGongnengs;
    private zhutiBean mCurrentZhuti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_main);
        mContext = this;
        initView();
        initData();
        initdefaultConfig();
    }

    private void initView() {
        mSetting = findViewById(R.id.homepage_top_header_setting_fr);
        mSetting.setOnClickListener(this);
        mLeftLL = findViewById(R.id.activity_iot_main_left_ll);
        mRecyclerView = findViewById(R.id.activity_shouye_recyclerview);
    }

    private void initdefaultConfig() {
        //从sp读出上次配置，并初始化mqtt client
        if(DataManager.getInstance().getType() == 1) {
            //如果保存的上次是mqtt配置，启动连接
            String serverIp = DataManager.getInstance().getmMqttBean().ipAddress;
            int serverPort = Integer.parseInt(DataManager.getInstance().getmMqttBean().port);
            String clientId = DataManager.getInstance().getmMqttBean().clientId;
            if (!TextUtils.isEmpty(serverIp) && serverPort > 0 && !TextUtils.isEmpty(clientId)) {
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

    private void initData() {
        mZhutis = DataManager.getInstance().getZhutiBeans();
        renderLeftZhutiTvs();
        if(null != mZhutis && mZhutis.size() >0) {
            mCurrentZhuti= mZhutis.get(0);
            mCurrentGongnengs = mZhutis.get(0).getGongnengBeans();
        }
        if(mCurrentGongnengs != null && mCurrentGongnengs.size() > 0){
            mAdapter = new shouyeGNAdapter(mContext,mCurrentGongnengs);
        }else{
            mAdapter = new shouyeGNAdapter(mContext);
        }
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //给RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void renderLeftZhutiTvs() {
        mLeftLL.removeAllViews();
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
            mCurrentZhuti = mItemZhutiBean;
//            Toast.makeText(mContext,""+mItemZhutiBean.getName(),Toast.LENGTH_SHORT).show();
            if(mAdapter != null){
                mAdapter.setmDatas(mItemZhutiBean.getGongnengBeans());
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateAdapterData() {
        if(mCurrentZhuti == null) {
            //首页为空，数据null
            if(DataManager.getInstance().getZhutiBeans().hashCode() != mZhutis.hashCode()) {
                mZhutis = DataManager.getInstance().getZhutiBeans();
                renderLeftZhutiTvs();
                if(mZhutis != null && mZhutis.size() > 0) {
                    mCurrentZhuti = mZhutis.get(0);
                    mAdapter.setmDatas(mCurrentZhuti.getGongnengBeans());
                    mAdapter.notifyDataSetChanged();
                }
            }
            return;
        }
//        if(DataManager.getInstance().getZhutiBeans().hashCode() != mZhutis.hashCode()) {
            //数据有变动,重新渲染左侧
            mZhutis = DataManager.getInstance().getZhutiBeans();
            renderLeftZhutiTvs();
            //遍历寻找当前的主题数据，并刷新
            for(int i = 0 ; i < DataManager.getInstance().getZhutiBeans().size();i++){
                zhutiBean searchItem = DataManager.getInstance().getZhutiBeans().get(i);
                if(mCurrentZhuti.getUid().equals(searchItem.getUid())){
                    //找到匹配
                    mCurrentZhuti = searchItem;
                    mAdapter.setmDatas(mCurrentZhuti.getGongnengBeans());
                    mAdapter.notifyDataSetChanged();
                }
            }
//        }

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
                startActivityForResult(intent,1);
                break;

            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                updateAdapterData();
            }
        }
    }

}
