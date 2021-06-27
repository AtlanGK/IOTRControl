package com.autohome.iotrcontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autohome.iotrcontrol.R;
import com.autohome.iotrcontrol.data.gongnengBean;
import com.autohome.iotrcontrol.view.CustomGNView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class shouyeGNAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<gongnengBean> mDatas;
    public shouyeGNAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }
    public shouyeGNAdapter(Context context, ArrayList<gongnengBean> datas) {
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
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.shouye_item_gn, parent, false);
        return new shouyeGNAdapter.shouyeGNViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        shouyeGNAdapter.shouyeGNViewHolder normalHolder = (shouyeGNAdapter.shouyeGNViewHolder) holder;
        normalHolder.mCustomGNView.setTag(mDatas.get(position));
        normalHolder.mCustomGNView.setmData(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public class shouyeGNViewHolder extends RecyclerView.ViewHolder{
        public CustomGNView mCustomGNView;
        public shouyeGNViewHolder(View itemView) {
            super(itemView);
            mCustomGNView = itemView.findViewById(R.id.shouye_item_cust_gn);
        }
    }
}
