package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.autohome.iotrcontrol.adapter.MyPagerAdapter;
import com.autohome.iotrcontrol.adapter.shouyeGNAdapter;
import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.gongnengBean;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.util.LogUtil;
import com.autohome.iotrcontrol.util.MQTTManager;
import com.autohome.iotrcontrol.util.ScreenUtil;
import com.autohome.iotrcontrol.util.UDPUtils;
import com.autohome.iotrcontrol.view.RatioLinearLayout;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class IOTRControlActivity extends FragmentActivity implements View.OnClickListener{

    private FrameLayout mSetting;
    private RatioLinearLayout mTopBg;
    private EditText mPassET;
    private Context mContext;
    private ArrayList<zhutiBean> mZhutis;
    private ArrayList<gongnengBean> mCurrentGongnengs;
    private zhutiBean mCurrentZhuti;
    private PagerSlidingTabStrip mTabs;
    private ViewPager mViewPager;
    private MyPagerAdapter mAdapter;
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
        mTopBg = findViewById(R.id.homepage_top_header_bg);
        mTabs = findViewById(R.id.homepage_mid_tabs);
        mViewPager = findViewById(R.id.homepage_main_pager);
        mPassET = findViewById(R.id.homepage_top_header_pass);
        mPassET.setVisibility(View.GONE);
        mPassET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("admin123456")){
                    Intent intent = new Intent();
                    intent.setClass(mContext, AllConfigActivity.class);
                    startActivityForResult(intent,1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        if(null != mZhutis && mZhutis.size() >0) {
            mCurrentZhuti= mZhutis.get(0);
            mCurrentGongnengs = mZhutis.get(0).getGongnengBeans();
        }
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(),mZhutis);
        mViewPager.setAdapter(mAdapter);
        mTabs.setIndicatorColor(getResources().getColor(R.color.SlateOrange));
        mTabs.setTextColor(getResources().getColor(R.color.SlateOrange));
        mTabs.setTextSize(40);
        mTabs.setShouldExpand(true);
        mTabs.setIndicatorHeight(ScreenUtil.dpToPxInt(mContext,3));
        mTabs.setViewPager(mViewPager);
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d("GKTEST","onPageSelected =="+position);
                mCurrentZhuti = mAdapter.getmDatas().get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateAdapterData() {
        if(mCurrentZhuti == null) {
            //首页为空，数据null
            if(DataManager.getInstance().getZhutiBeans().hashCode() != mZhutis.hashCode()) {
                mZhutis = DataManager.getInstance().getZhutiBeans();
                if(mZhutis != null && mZhutis.size() > 0) {
                    mCurrentZhuti = mZhutis.get(0);
//                    mAdapter.setmDatas(mZhutis,true);
//                    mAdapter.notifyDataSetChanged();
                }
            }
            return;
        }
        mZhutis = DataManager.getInstance().getZhutiBeans();
        if(mZhutis != null && mZhutis.size() > 0) {
            mAdapter.setmDatas(mZhutis);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(null != mPassET){
            mPassET.setText("");
            mPassET.setVisibility(View.GONE);
        }
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
                mPassET.setVisibility(View.VISIBLE);
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
