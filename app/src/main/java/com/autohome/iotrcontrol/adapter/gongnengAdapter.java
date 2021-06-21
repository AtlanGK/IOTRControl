package com.autohome.iotrcontrol.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autohome.iotrcontrol.R;
import com.autohome.iotrcontrol.data.gongnengBean;
import com.autohome.iotrcontrol.xuanxiangActivity;
import com.autohome.iotrcontrol.xuanxiangConfigActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class gongnengAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<gongnengBean> mDatas;

    public gongnengAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }
    public gongnengAdapter(Context context, ArrayList<gongnengBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    public ArrayList<gongnengBean> getmDatas() {
        return mDatas;
    }

    public void setmDatas(ArrayList<gongnengBean> datas) {
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new gongnengViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        gongnengViewHolder normalHolder = (gongnengViewHolder) holder;
        normalHolder.mTV.setTag(mDatas.get(position));
        normalHolder.mTV.setText(mDatas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class gongnengViewHolder extends RecyclerView.ViewHolder{
        public TextView mTV;

        public gongnengViewHolder(View itemView) {
            super(itemView);

            mTV = (TextView) itemView.findViewById(R.id.item_title_tv);
            mTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof gongnengBean){
                        gongnengBean mItemData = (gongnengBean) mTV.getTag();
//                        Toast.makeText(mContext,mItemData.getName()+"**",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(mContext, xuanxiangActivity.class);
                        mContext.startActivity(intent);
                    }

                }
            });

        }
    }

}