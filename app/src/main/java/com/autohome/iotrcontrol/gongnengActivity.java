package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.autohome.iotrcontrol.adapter.gongnengAdapter;
import com.autohome.iotrcontrol.adapter.zhutiAdapter;
import com.autohome.iotrcontrol.data.gongnengBean;
import com.autohome.iotrcontrol.data.recyclerListItemBean;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.util.LogUtil;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class gongnengActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private RecyclerView mRecyclerView;
    private zhutiBean mBelongZhuti;
    private TextView mTitle,mBack,mConfirm,mCancel,mAdd;
    private ArrayList<recyclerListItemBean> mGongNengBeans;
    private gongnengAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongneng);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.activity_gongneng_recyclerview);
        mTitle = findViewById(R.id.activity_gongneng_top_title);
        mBack = findViewById(R.id.activity_gongneng_top_back);
        mConfirm = findViewById(R.id.activity_gongneng_bottom_confirm_tv);
        mCancel = findViewById(R.id.activity_gongneng_bottom_cancel_tv);
        mAdd = findViewById(R.id.activity_gongneng_bottom_add_tv);

        mBack.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mAdd.setOnClickListener(this);

        mAdapter = new gongnengAdapter(mContext);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //给RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mBelongZhuti = (zhutiBean) getIntent().getSerializableExtra("zhuti");
        mAdapter.setmBelongZhuti(mBelongZhuti);
        LogUtil.d("gktest","传入的主题bean ="+mBelongZhuti.toString());
    }

    public static int count = 0;
    private void addNewGongNengItem() {
        LogUtil.d("gktest","addNewzhutiItem");
        count++;
        gongnengBean itemBean = new gongnengBean("新增功能"+count);
        ArrayList<gongnengBean> mTempDatas = new ArrayList<>();
        if(mAdapter.getmDatas() == null || mAdapter.getmDatas().size() == 0){
            //第一次添加

        }else{
            //已有数据
            mTempDatas = mAdapter.getmDatas();
        }
        mTempDatas.add(itemBean);
        mAdapter.setmDatas(mTempDatas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_gongneng_top_back:
                finish();
                break;
            case R.id.activity_gongneng_bottom_confirm_tv:
                break;
            case R.id.activity_gongneng_bottom_cancel_tv:
                break;
            case R.id.activity_gongneng_bottom_add_tv:
                addNewGongNengItem();
                break;
            default:
                break;
        }
    }
}
