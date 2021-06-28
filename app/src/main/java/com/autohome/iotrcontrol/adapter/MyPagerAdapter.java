package com.autohome.iotrcontrol.adapter;

import android.os.Bundle;

import com.autohome.iotrcontrol.R;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.fragment.HomeItemFragment;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter
{
    private ArrayList<Fragment> mFragmentList;
    private ArrayList<zhutiBean> mZhutis;

    public MyPagerAdapter(FragmentManager fm,ArrayList<zhutiBean> datas) {
        super(fm);
        mFragmentList = new ArrayList<>();
        if(datas != null && datas.size() >0) {
            mZhutis = datas;
        }else{
            mZhutis = new ArrayList<>();
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragmentList.size() > position){
            //有已经创建好的Fragment
            return mFragmentList.get(position);
        }else {
            //createFragmentAndReturn();
            Fragment pageFragment = new HomeItemFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", mZhutis.get(position));
            pageFragment.setArguments(bundle);
            mFragmentList.add(pageFragment);
            return pageFragment;
        }
    }

    private void addFragment(int position) {
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mZhutis.get(position).getName();
    }
    @Override
    public int getCount() {
        return mZhutis.size();
    }

    public ArrayList<zhutiBean> getmDatas() {
        return mZhutis;
    }

    public void setmDatas(ArrayList<zhutiBean> mZhutis) {
        this.mZhutis = mZhutis;
        mFragmentList.clear();
        reCreateFragments();
    }

    private void reCreateFragments() {
        for(int i = 0;i<mZhutis.size();i++){
            Fragment pageFragment = new HomeItemFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", mZhutis.get(i));
            pageFragment.setArguments(bundle);
            mFragmentList.add(pageFragment);
        }
    }

}