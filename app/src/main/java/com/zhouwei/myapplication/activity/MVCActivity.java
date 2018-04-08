package com.zhouwei.myapplication.activity;

import android.widget.EditText;
import android.widget.TextView;

import com.zhouwei.myapplication.R;
import com.zhouwei.myapplication.eventbus_.BaseActivity;

/**
 * Created by Charles on 2018/3/28.
 */

public class MVCActivity extends BaseActivity {
    private EditText et_name;
    private TextView tv_show;

    @Override
    protected void setContent() {
        layout = R.layout.mvc_layout;
    }

    @Override
    protected void initView() {
        super.initView();

        et_name = findViewById(R.id.et_name);
        tv_show = findViewById(R.id.tv_show);
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
