package com.autohome.iotrcontrol.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.autohome.iotrcontrol.R;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.gongnengActivity;
import com.autohome.iotrcontrol.xuanxiangConfigActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class zhutiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<zhutiBean> mDatas;

    public zhutiAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }
    public zhutiAdapter(Context context, ArrayList<zhutiBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    public ArrayList<zhutiBean> getmDatas() {
        return mDatas;
    }

    public void setmDatas(ArrayList<zhutiBean> datas) {
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new zhutiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        zhutiViewHolder normalHolder = (zhutiViewHolder) holder;
        normalHolder.mTV.setTag(mDatas.get(position));
        normalHolder.mTV.setText(mDatas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class zhutiViewHolder extends RecyclerView.ViewHolder{
        public TextView mTV;
        public TextView mTVConfig;
        public EditText mTVEdit;
        public TextView mTVDele;
        public TextView mTVUp;
        public TextView mTVDown;
        public TextView mTVConfirm;
        public TextView mTVCancel;

        public zhutiViewHolder(View itemView) {
            super(itemView);

            mTV =        (TextView) itemView.findViewById(R.id.item_title_tv);
            mTVConfig =  (TextView) itemView.findViewById(R.id.item_func_config_tv);
            mTVEdit =    (EditText) itemView.findViewById(R.id.item_edit_tv);
            mTVUp =      (TextView) itemView.findViewById(R.id.item_func_up_tv);
            mTVDown =    (TextView) itemView.findViewById(R.id.item_func_down_tv);
            mTVDele =    (TextView) itemView.findViewById(R.id.item_func_dele_tv);
            mTVConfirm = (TextView) itemView.findViewById(R.id.item_confirm_tv);
            mTVCancel =  (TextView) itemView.findViewById(R.id.item_cancel_tv);

            mTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof zhutiBean){
                        mTVEdit.setText(mTV.getText());
                        mTVEdit.setVisibility   (View.VISIBLE);
                        mTVConfirm.setVisibility(View.VISIBLE);
                        mTVCancel.setVisibility (View.VISIBLE);
                        mTV.setVisibility       (View.INVISIBLE);
                        mTVUp.setVisibility     (View.INVISIBLE);
                        mTVDown.setVisibility   (View.INVISIBLE);
                        mTVConfig.setVisibility (View.INVISIBLE);
                        mTVDele.setVisibility   (View.INVISIBLE);
                        mTVEdit.setSelectAllOnFocus(true);
                        mTVEdit.requestFocus();
                    }
                }
            });
            mTVDele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof zhutiBean){
                        zhutiBean mItemData = (zhutiBean) mTV.getTag();
                        removeSelectedItemBean(mItemData);
                    }
                }
            });

            mTVConfig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof zhutiBean){
                        zhutiBean mItemData = (zhutiBean) mTV.getTag();
                        Toast.makeText(mContext,mItemData.getName()+"**",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setPackage("com.autohome.iotcontrol");
                        intent.putExtra("zhuti",mItemData);
                        intent.setClass(mContext, gongnengActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
            mTVUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof zhutiBean){
                        zhutiBean mItemData = (zhutiBean) mTV.getTag();
                        swapSelectedItemBean(mItemData,"up");
                    }
                }
            });
            mTVDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof zhutiBean){
                        zhutiBean mItemData = (zhutiBean) mTV.getTag();
                        swapSelectedItemBean(mItemData,"down");
                    }
                }
            });
            mTVConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof zhutiBean){
                        mTV.setText(mTVEdit.getText());
                        zhutiBean mItemData = (zhutiBean) mTV.getTag();
                        modifySelectedItemBeanName(mItemData,mTVEdit.getText().toString());
                        mTVEdit.setVisibility   (View.INVISIBLE);
                        mTVConfirm.setVisibility(View.INVISIBLE);
                        mTVCancel.setVisibility (View.INVISIBLE);
                        mTV.setVisibility       (View.VISIBLE);
                        mTVUp.setVisibility     (View.VISIBLE);
                        mTVDown.setVisibility   (View.VISIBLE);
                        mTVConfig.setVisibility (View.VISIBLE);
                        mTVDele.setVisibility   (View.VISIBLE);
                    }
                }
            });
            mTVCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof zhutiBean){
                        //mTVEdit.setText(mTV.getText());
                        mTVEdit.setVisibility   (View.INVISIBLE);
                        mTVConfirm.setVisibility(View.INVISIBLE);
                        mTVCancel.setVisibility (View.INVISIBLE);
                        mTV.setVisibility       (View.VISIBLE);
                        mTVUp.setVisibility     (View.VISIBLE);
                        mTVDown.setVisibility   (View.VISIBLE);
                        mTVConfig.setVisibility (View.VISIBLE);
                        mTVDele.setVisibility   (View.VISIBLE);
                    }
                }
            });

        }
    }

    private void swapSelectedItemBean(zhutiBean itemData,String direc) {
        if(itemData == null)
            return;
        int findPos = -1;
        int swapPos = -1;
        for(int i = 0 ;i < mDatas.size();i++){
            if(mDatas.get(i).uid.equals(itemData.uid)){
                findPos = i;
            }
        }
        if(findPos != -1){
            //找到了匹配位置
            if(direc.equals("up")) {
                swapPos = findPos-1;
                if (findPos == 0) {
                    //向上交换，第一个
                    Toast.makeText(mContext, "已经是第一个了", Toast.LENGTH_SHORT).show();
                } else {
                    swapZhuTiBean(findPos, swapPos);
                }
            }else if(direc.equals("down")){
                swapPos = findPos+1;
                if (findPos == mDatas.size() -1) {
                    //向下交换，最后一个
                    Toast.makeText(mContext, "已经最后一个了", Toast.LENGTH_SHORT).show();
                } else {
                    swapZhuTiBean(findPos, swapPos);
                }
            }
        }
    }

    private void swapZhuTiBean(int findPos, int swapPos) {
        if(findPos < 0 || findPos > mDatas.size())
            return;
        if(swapPos < 0 || swapPos > mDatas.size())
            return;
        zhutiBean mTempBean = mDatas.get(findPos);
        mDatas.set(findPos,mDatas.get(swapPos));
        mDatas.set(swapPos,mTempBean);
        mTempBean = null;
        notifyDataSetChanged();
    }

    private void removeSelectedItemBean(zhutiBean itemData) {
        if(itemData == null)
            return;
        for(int i = 0 ;i < mDatas.size();i++){
            if(mDatas.get(i).uid.equals(itemData.uid)){
                //删除选中的item
                mDatas.remove(i);
                notifyDataSetChanged();
            }
        }
    }

    private void modifySelectedItemBeanName(zhutiBean itemData,String newName) {
        if(itemData == null)
            return;
        for(int i = 0 ;i < mDatas.size();i++){
            if(mDatas.get(i).uid.equals(itemData.uid)){
                //确认修改名字，找到对应的item，改变name
                mDatas.get(i).setName(newName);
                notifyDataSetChanged();
            }
        }
    }


}