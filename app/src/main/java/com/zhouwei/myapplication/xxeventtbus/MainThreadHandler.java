package com.zhouwei.myapplication.xxeventtbus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Charles on 2018/4/8.
 */

public class MainThreadHandler extends Handler {
    public XXEventBus eventBus;
    public Looper looper;

    public MainThreadHandler(XXEventBus eventBus, Looper looper) {
        this.eventBus = eventBus;
        this.looper = looper;
    }

    @Override
    public void handleMessage(Message msg) {
        XXEventBus.getInstance().invoke(subscription, subscription.subscriberMethod.method, event);
    }

    private Subscription subscription;
    private Object event;

    public void post(Subscription subscription, Object event) {
        this.subscription = subscription;
        this.event = event;

        sendEmptyMessage(0);
    }
}
