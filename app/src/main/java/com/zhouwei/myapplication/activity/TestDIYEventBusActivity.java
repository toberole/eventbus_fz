package com.zhouwei.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhouwei.myapplication.R;
import com.zhouwei.myapplication.xxeventtbus.XXEventBus;

/**
 * Created by Charles on 2018/4/8.
 */

public class TestDIYEventBusActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.testeventbus_layout);

        XXEventBus.getInstance().register(this);

        Button button = findViewById(R.id.btn_testDIYEventBus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XXEventBus.getInstance().post("测试DIY EventBus");
            }
        });
    }

    public void onEvent(String event) {
        Log.i("AAAA", "event: " + event);
    }
}
