package com.autohome.iotrcontrol.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.autohome.iotrcontrol.R;
import com.autohome.iotrcontrol.data.DataManager;
import com.autohome.iotrcontrol.data.gongnengBean;
import com.autohome.iotrcontrol.data.zhutiBean;
import com.autohome.iotrcontrol.xuanxiangActivity;
import com.autohome.iotrcontrol.xuanxiangConfigActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class gongnengAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<gongnengBean> mDatas;
    private zhutiBean mBelongZhuti;

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

    public zhutiBean getmBelongZhuti() {
        return mBelongZhuti;
    }

    public void setmBelongZhuti(zhutiBean mBelongZhuti) {
        this.mBelongZhuti = mBelongZhuti;
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
        public TextView mTVConfig;
        public EditText mTVEdit;
        public TextView mTVDele;
        public TextView mTVUp;
        public TextView mTVDown;
        public TextView mTVConfirm;
        public TextView mTVCancel;

        public gongnengViewHolder(View itemView) {
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
                    if(mTV.getTag() instanceof gongnengBean){
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
                    if(mTV.getTag() instanceof gongnengBean){
                        gongnengBean mItemData = (gongnengBean) mTV.getTag();
                        removeSelectedItemBean(mItemData);
                    }
                }
            });
            mTVConfig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof gongnengBean){
                        gongnengBean mItemData = (gongnengBean) mTV.getTag();
                        saveGongnengToBelongZhuti();
//                        Toast.makeText(mContext,mItemData.getName()+"**",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(mContext, xuanxiangActivity.class);
                        intent.setPackage("com.autohome.iotcontrol");
                        intent.putExtra("gongneng",mItemData);
                        intent.putExtra("zhuti",mBelongZhuti);
                        mContext.startActivity(intent);
                    }

                }
            });
            mTVUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof gongnengBean){
                        gongnengBean mItemData = (gongnengBean) mTV.getTag();
                        swapSelectedItemBean(mItemData,"up");
                    }
                }
            });
            mTVDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof gongnengBean){
                        gongnengBean mItemData = (gongnengBean) mTV.getTag();
                        swapSelectedItemBean(mItemData,"down");
                    }
                }
            });
            mTVConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mTV.getTag() instanceof gongnengBean){
                        mTV.setText(mTVEdit.getText());
                        gongnengBean mItemData = (gongnengBean) mTV.getTag();
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
                    if(mTV.getTag() instanceof gongnengBean){
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

    private void swapSelectedItemBean(gongnengBean itemData,String direc) {
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
                    swapGongnengBean(findPos, swapPos);
                }
            }else if(direc.equals("down")){
                swapPos = findPos+1;
                if (findPos == mDatas.size() -1) {
                    //向下交换，最后一个
                    Toast.makeText(mContext, "已经最后一个了", Toast.LENGTH_SHORT).show();
                } else {
                    swapGongnengBean(findPos, swapPos);
                }
            }
        }
    }

    private void swapGongnengBean(int findPos, int swapPos) {
        if(findPos < 0 || findPos > mDatas.size())
            return;
        if(swapPos < 0 || swapPos > mDatas.size())
            return;
        gongnengBean mTempBean = mDatas.get(findPos);
        mDatas.set(findPos,mDatas.get(swapPos));
        mDatas.set(swapPos,mTempBean);
        mTempBean = null;
        notifyDataSetChanged();
    }


    private void removeSelectedItemBean(gongnengBean itemData) {
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
    private void modifySelectedItemBeanName(gongnengBean itemData,String newName) {
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

    private void saveGongnengToBelongZhuti() {
        int findMatchPos = -1;
        findMatchPos = findMatchGongnengBeanPos();
        if(findMatchPos != -1) {
            //找到了match 主题数据，设置功能bean，并保存
            DataManager.getInstance().getZhutiBeans().get(findMatchPos).setGongnengBeans(getmDatas());
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