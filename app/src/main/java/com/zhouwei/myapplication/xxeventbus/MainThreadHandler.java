package com.zhouwei.myapplication.xxeventbus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Charles on 2018/4/8.
 */

public class MainThreadHandler extends Handler {
    public XXEventBus eventBus;

    public MainThreadHandler(XXEventBus eventBus) {
        super(Looper.getMainLooper());
        this.eventBus = eventBus;
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

        if (isMainThread()) {
            XXEventBus.getInstance().invoke(subscription, subscription.subscriberMethod.method, event);
        } else {
            sendEmptyMessage(0);
        }
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
