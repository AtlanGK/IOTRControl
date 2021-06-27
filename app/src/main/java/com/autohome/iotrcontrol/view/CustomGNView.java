package com.autohome.iotrcontrol.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autohome.iotrcontrol.R;
import com.autohome.iotrcontrol.data.gongnengBean;

public class CustomGNView extends RelativeLayout {
    private Context mContext;
    private TextView mTitle;
    private LinearLayout mContainer;
    private gongnengBean mData;
    public CustomGNView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_gn_item,this,true);
        mContext = context;
        mTitle = findViewById(R.id.view_gn_title_tv);
        mContainer = findViewById(R.id.view_gn_container);
    }

    public gongnengBean getmData() {
        return mData;
    }

    public void setmData(gongnengBean mData) {
        this.mData = mData;
        mContainer.removeAllViews();
        initRenderAndSetData();
    }

    private void initRenderAndSetData() {
        if(!TextUtils.isEmpty(mData.getName())){
            mTitle.setText(mData.getName());
        }
        if(mData.getXuanxiangBeans() == null || mData.getXuanxiangBeans().size() == 0)
            return;
        int xLength = mData.getXuanxiangBeans().size();
        if(xLength % 2 == 0){
            //偶数个
            int count = xLength / 2;
            AddViewAndSetData(count,0);
        }else{
            //奇数个
            int count = xLength / 2 ;
            AddViewAndSetData(count+1,1);
        }
    }

    private void AddViewAndSetData(int count,int type) {
        for(int i = 0;i<count;i++){
            if(i != count -1) {//非最后一排
                GNInnerLineItemView itemView = new GNInnerLineItemView(mContext, null);
                itemView.setmXX1(mData.getXuanxiangBeans().get(i * 2));
                itemView.setmXX2(mData.getXuanxiangBeans().get(i * 2 + 1));
                mContainer.addView(itemView);
            }else{
                //最后一排
                if(type == 0){
                    //偶数个最后一排
                    GNInnerLineItemView itemView = new GNInnerLineItemView(mContext, null);
                    itemView.setmXX1(mData.getXuanxiangBeans().get(i * 2));
                    itemView.setmXX2(mData.getXuanxiangBeans().get(i * 2 + 1));
                    mContainer.addView(itemView);
                }else{
                    //奇数个最后一排
                    GNInnerLineItemView itemView = new GNInnerLineItemView(mContext, null);
                    itemView.setmXX1(mData.getXuanxiangBeans().get(i * 2));
                    itemView.getmTV2().setVisibility(INVISIBLE);
                    mContainer.addView(itemView);
                }
            }

        }
    }


}
