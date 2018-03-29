package com.zhouwei.myapplication.eventbus_;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Charles on 2018/3/28.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected int layout = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent();
        if (isRegisterEventBus()) {
            EventBusEngine.register(this);
        }

        if (layout != -1) {
            setContentView(layout);

            initView();
            initData();
        }
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBus(final EventELF eventELF) {
        if (null != eventELF) {
            if (eventELF.runEnv == EventThreadMode.MAIN_THREAD) {
                BaseActivity.this.getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        onReceiveEvent(eventELF);
                    }
                });
            } else {
                onReceiveEvent(eventELF);
            }

        }
    }

    protected void onReceiveEvent(EventELF eventELF) {

    }

    protected abstract void setContent();

    protected void initData() {
    }

    protected void initView() {
    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBusEngine.unRegister(this);
    }
}
