package com.zhouwei.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhouwei.myapplication.R;
import com.zhouwei.myapplication.xxeventbus.XXEventBus;
import com.zhouwei.myapplication.xxeventbus.Subscribe;
import com.zhouwei.myapplication.xxeventbus.ThreadMode;

/**
 * Created by Charles on 2018/4/8.
 */

public class TestDIYEventBusActivity1 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.testeventbus_layout);

        XXEventBus.getInstance().register(this);

        Button button = findViewById(R.id.btn_testDIYEventBus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XXEventBus.getInstance().post("主线程 测试 DIY EventBus");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        XXEventBus.getInstance().post("非主线程 测试 DIY EventBus");
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_unregister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XXEventBus.getInstance().unRegister(TestDIYEventBusActivity1.this);
            }
        });
    }

    @Subscribe(tm = ThreadMode.MAIN)
    public void onEvent(String event) {
        Log.i("AAAA", "event: " + event + " Thread name: " + Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        XXEventBus.getInstance().unRegister(this);
    }
}
