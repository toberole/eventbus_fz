package com.zhouwei.myapplication.activity;

import android.view.View;
import android.widget.Button;

import com.zhouwei.myapplication.R;
import com.zhouwei.myapplication.eventbus_.BaseActivity;
import com.zhouwei.myapplication.eventbus_.EventBusEngine;
import com.zhouwei.myapplication.eventbus_.EventCode;
import com.zhouwei.myapplication.eventbus_.EventELF;

/**
 * Created by Charles on 2018/4/8.
 */

public class SecondActivity extends BaseActivity {
    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void setContent() {
        layout = R.layout.second_layout;
    }

    @Override
    protected void initView() {
        super.initView();

        Button button = findViewById(R.id.btn_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusEngine.postEvent(new EventELF(EventCode.TEST_EVENT, "测试数据"));
            }
        });
    }
}
