package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.adapter.zhutiAdapter;
import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.recyclerListItemBean;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.util.LogUtil;
import com.autohome.iotrcontrol.util.SpHelper;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class zhutiActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private RecyclerView mRecyclerView;
    private TextView mTitle,mBack,mConfirm,mCancel,mAdd;
    private ArrayList<recyclerListItemBean> mZhuTiBeans;
    private zhutiAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    ArrayList<zhutiBean> mZhutiBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuti);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.activity_zhuti_recyclerview);
        mTitle = findViewById(R.id.activity_zhuti_top_title);
        mBack = findViewById(R.id.activity_zhuti_top_back);
        mConfirm = findViewById(R.id.activity_zhuti_bottom_confirm_tv);
        mCancel = findViewById(R.id.activity_zhuti_bottom_cancel_tv);
        mAdd = findViewById(R.id.activity_zhuti_bottom_add_tv);
        mBack.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mAdd.setOnClickListener(this);
    }

    private void initData() {
        mZhutiBeans = DataManager.getInstance().getZhutiBeans();
        if(mZhutiBeans != null && mZhutiBeans.size() >0){
            mAdapter = new zhutiAdapter(mContext,mZhutiBeans);
        }else {
            mAdapter = new zhutiAdapter(mContext);
        }
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //给RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static int count = 0;
    private void addNewZhuTiItem() {
        LogUtil.d("gktest","addNewzhutiItem");
        count++;
        zhutiBean itemBean = new zhutiBean("新增主题"+count);
        ArrayList<zhutiBean> mTempDatas = new ArrayList<>();
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
            case R.id.activity_zhuti_top_back:
                finish();
                break;
            case R.id.activity_zhuti_bottom_confirm_tv:
                DataManager.getInstance().commitZhutiBeans(mAdapter.getmDatas());
                Toast.makeText(mContext,"保存成功 数据长度=="+mAdapter.getmDatas().size(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_zhuti_bottom_cancel_tv:
                break;
            case R.id.activity_zhuti_bottom_add_tv:
                addNewZhuTiItem();
                break;
            default:
                break;
        }
    }
}
