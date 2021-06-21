package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.autohome.iotrcontrol.adapter.xuanxiangAdapter;
import com.autohome.iotrcontrol.data.recyclerListItemBean;
import com.autohome.iotrcontrol.data.xuanxiangBean;
import com.autohome.iotrcontrol.util.LogUtil;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class xuanxiangActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private RecyclerView mRecyclerView;
    private TextView mTitle,mBack,mConfirm,mCancel,mAdd;
    private ArrayList<recyclerListItemBean> mXuanXiangBeans;
    private xuanxiangAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuanxiang);
        mContext = this;
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.activity_xuanxiang_recyclerview);
        mTitle = findViewById(R.id.activity_xuanxiang_top_title);
        mBack = findViewById(R.id.activity_xuanxiang_top_back);
        mConfirm = findViewById(R.id.activity_xuanxiang_bottom_confirm_tv);
        mCancel = findViewById(R.id.activity_xuanxiang_bottom_cancel_tv);
        mAdd = findViewById(R.id.activity_xuanxiang_bottom_add_tv);
        mBack.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mAdapter = new xuanxiangAdapter(mContext);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //给RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    public static int count = 0;
    private void addNewXuanXiangItem() {
        LogUtil.d("gktest","addNewXuanXiangItem");
        count++;
        xuanxiangBean itemBean = new xuanxiangBean("新增选项"+count);
        ArrayList<xuanxiangBean> mTempDatas = new ArrayList<>();
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
            case R.id.activity_xuanxiang_top_back:
                finish();
                break;
            case R.id.activity_xuanxiang_bottom_confirm_tv:

                break;
            case R.id.activity_xuanxiang_bottom_cancel_tv:

                break;
            case R.id.activity_xuanxiang_bottom_add_tv:
                addNewXuanXiangItem();
                break;
            default:
                break;
        }
    }
}
