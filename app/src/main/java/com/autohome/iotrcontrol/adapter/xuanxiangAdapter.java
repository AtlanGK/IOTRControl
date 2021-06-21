package com.autohome.iotrcontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.R;
import com.autohome.iotrcontrol.data.xuanxiangBean;
import com.autohome.iotrcontrol.util.LogUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class xuanxiangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<xuanxiangBean> mDatas;

    public xuanxiangAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }
    public xuanxiangAdapter(Context context, ArrayList<xuanxiangBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    public ArrayList<xuanxiangBean> getmDatas() {
        return mDatas;
    }

    public void setmDatas(ArrayList<xuanxiangBean> datas) {
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new NormalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mTV.setText(mDatas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder{
        public TextView mTV;

        public NormalHolder(View itemView) {
            super(itemView);

            mTV = (TextView) itemView.findViewById(R.id.item_title_tv);
            mTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,mTV.getText(),Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}