package com.autohome.iotrcontrol.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autohome.iotrcontrol.R;
import com.autohome.iotrcontrol.adapter.shouyeGNAdapter;
import com.autohome.iotrcontrol.data.gongnengBean;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.util.LogUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeItemFragment extends Fragment {
    View mainView;
    private String mParam;
    private Activity mActivity;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private zhutiBean mCurrentZhuti;
    private ArrayList<gongnengBean> mCurrentGongnengs;

    private shouyeGNAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity) context;
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            mCurrentZhuti = (zhutiBean) bundle.getSerializable("data");
            if(mCurrentZhuti != null) {
                mCurrentGongnengs = mCurrentZhuti.getGongnengBeans();
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_homepage_main, container, false);
        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = mainView.findViewById(R.id.fragment_shouye_recyclerview);
        if(mCurrentGongnengs != null && mCurrentGongnengs.size() > 0){
            mAdapter = new shouyeGNAdapter(mContext,mCurrentGongnengs);
        }else{
            mAdapter = new shouyeGNAdapter(mContext);
        }

        //创建线性布局
        mLayoutManager = new LinearLayoutManager(mContext);
        //垂直方向
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //给RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
