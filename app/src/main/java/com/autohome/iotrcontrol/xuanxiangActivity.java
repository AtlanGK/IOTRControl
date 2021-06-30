package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.adapter.xuanxiangAdapter;
import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.gongnengBean;
import com.autohome.iotrcontrol.data.recyclerListItemBean;
import com.autohome.iotrcontrol.data.xuanxiangBean;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.util.LogUtil;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class xuanxiangActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private RecyclerView mRecyclerView;
    private TextView mTitle,mBack,mConfirm,mCancel,mAdd;
    private ArrayList<xuanxiangBean> mXuanXiangBeans;
    private xuanxiangAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private zhutiBean mBelongZhuti;
    private gongnengBean mBelongGongneng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuanxiang);
        mContext = this;
        initView();
        initData();
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

    private void initData() {

        mBelongZhuti = (zhutiBean) getIntent().getSerializableExtra("zhuti");
        mBelongGongneng = (gongnengBean) getIntent().getSerializableExtra("gongneng");
        mTitle.setText(mBelongZhuti.getName()+"/"+mBelongGongneng.getName());
        mXuanXiangBeans = mBelongGongneng.getXuanxiangBeans();
        if(null != mXuanXiangBeans && mXuanXiangBeans.size() > 0) {
            mAdapter = new xuanxiangAdapter(mContext,mXuanXiangBeans);
        }else{
            mAdapter = new xuanxiangAdapter(mContext);
        }
        mAdapter.setmBelongZhuti(mBelongZhuti);
        mAdapter.setmBelongGongneng(mBelongGongneng);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //给RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_xuanxiang_top_back:
                finish();
                break;
            case R.id.activity_xuanxiang_bottom_confirm_tv:
                saveXuanxiangToBelongGongneng();
                Toast.makeText(mContext,"保存成功 数据长度=="+mAdapter.getmDatas().size(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_xuanxiang_bottom_cancel_tv:
                finish();
                break;
            case R.id.activity_xuanxiang_bottom_add_tv:
                addNewXuanXiangItem();
                break;
            default:
                break;
        }
    }

    private void saveXuanxiangToBelongGongneng() {
        int findMatchZhutiPos = -1;
        int findMatchGongnengPos = -1;
        try {
            findMatchZhutiPos = findMatchZhutiBeanPos();
            findMatchGongnengPos = findMatchGongnengBeanPos(DataManager.getInstance().getZhutiBeans().get(findMatchZhutiPos));
        }catch (Exception e){
            e.printStackTrace();
        }
        if(findMatchZhutiPos != -1 && findMatchGongnengPos != -1){
            //找到了匹配位置
            DataManager.getInstance().getZhutiBeans().get(findMatchZhutiPos).getGongnengBeans().get(findMatchGongnengPos).setXuanxiangBeans(mAdapter.getmDatas());
            DataManager.getInstance().syncLocalDatas();
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
}
