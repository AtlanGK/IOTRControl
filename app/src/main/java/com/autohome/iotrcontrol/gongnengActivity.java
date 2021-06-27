package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.adapter.gongnengAdapter;
import com.autohome.iotrcontrol.adapter.zhutiAdapter;
import com.autohome.iotrcontrol.data.DataManager;
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
    private ArrayList<gongnengBean> mGongNengBeans;
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


    }

    private void initData() {
        mBelongZhuti = (zhutiBean) getIntent().getSerializableExtra("zhuti");
        LogUtil.d("gktest","传入的主题bean ="+mBelongZhuti.toString());
        mGongNengBeans = findMatchGongnengBeans();
        if(null != mGongNengBeans && mGongNengBeans.size() >0) {
            mAdapter = new gongnengAdapter(mContext,mGongNengBeans);
        }else{
            mAdapter = new gongnengAdapter(mContext);
        }
        mAdapter.setmBelongZhuti(mBelongZhuti);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //给RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<gongnengBean> findMatchGongnengBeans() {
        int findMatchPos = findMatchGongnengBeanPos();
        if(findMatchPos != -1){
            return DataManager.getInstance().getZhutiBeans().get(findMatchPos).getGongnengBeans();
        }else{
            return null;
        }
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
                saveGongnengToBelongZhuti();
                Toast.makeText(mContext,"保存成功 数据长度=="+mAdapter.getmDatas().size(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_gongneng_bottom_cancel_tv:
                finish();
                break;
            case R.id.activity_gongneng_bottom_add_tv:
                addNewGongNengItem();
                break;
            default:
                break;
        }
    }

    private void saveGongnengToBelongZhuti() {
        int findMatchPos = -1;
        findMatchPos = findMatchGongnengBeanPos();
        if(findMatchPos != -1) {
            //找到了match 主题数据，设置功能bean，并保存
            DataManager.getInstance().getZhutiBeans().get(findMatchPos).setGongnengBeans(mAdapter.getmDatas());
            DataManager.getInstance().syncLocalDatas();
        }
    }

    private int findMatchGongnengBeanPos() {
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
}
