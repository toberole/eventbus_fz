package com.zhouwei.myapplication.diy_eventtbus;

/**
 * Created by Charles on 2018/4/8.
 */

public class AsyThreadHandler {
    public XXEventBus eventBus;

    public AsyThreadHandler(XXEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void post(final Subscription subscription, final Object event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                eventBus.invoke(subscription, subscription.subscriberMethod.method, event);
            }
        }).start();
    }
}
