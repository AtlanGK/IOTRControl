package com.autohome.iotrcontrol.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class RatioLinearLayout extends LinearLayout {

    private float mRatio = 1.63f;

    public RatioLinearLayout(Context context) {
        super(context);
    }

    public RatioLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RatioLinearLayout(Context context, AttributeSet attrs, int defStyleAttr,
                             int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        if(mRatio!=0){
            //根据宽度求高度
            int height= (int) (width/mRatio);
            //根据宽度求高度
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
