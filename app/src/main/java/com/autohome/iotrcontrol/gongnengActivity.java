package com.autohome.iotrcontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class gongnengActivity extends Activity implements View.OnClickListener{

    private Context mContext;
    private RecyclerView mRecyclerView;
    private TextView mTitle,mBack,mConfirm,mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongneng);
        mContext = this;
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.activity_gongneng_recyclerview);
        mTitle = findViewById(R.id.activity_gongneng_top_title);
        mBack = findViewById(R.id.activity_gongneng_top_back);
        mConfirm = findViewById(R.id.activity_gongneng_bottom_confirm_tv);
        mCancel = findViewById(R.id.activity_gongneng_bottom_cancel_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_gongneng_top_back:
                finish();
                break;
            case R.id.activity_gongneng_bottom_confirm_tv:
                break;
            case R.id.activity_gongneng_bottom_cancel_tv:
                break;
            default:
                break;
        }
    }
}
