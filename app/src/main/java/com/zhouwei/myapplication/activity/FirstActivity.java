package com.zhouwei.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhouwei.myapplication.R;
import com.zhouwei.myapplication.eventbus_.BaseActivity;
import com.zhouwei.myapplication.eventbus_.EventCode;
import com.zhouwei.myapplication.eventbus_.EventELF;

/**
 * Created by Charles on 2018/4/8.
 */

public class FirstActivity extends BaseActivity {

    private Button button;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onReceiveEvent(EventELF eventELF) {
        super.onReceiveEvent(eventELF);
        switch (eventELF.getEventCode()) {
            case EventCode.TEST_EVENT:
                Log.i("AAAA", "data: " + eventELF.getEventData());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("AAAA", "oncreate");
    }

    @Override
    protected void setContent() {
        layout = R.layout.first_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        button = findViewById(R.id.btn_jump);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                FirstActivity.this.startActivity(intent);

                // finish 之后就接收不到eventbus传递的事件了
                FirstActivity.this.finish();
            }
        });
    }
}
