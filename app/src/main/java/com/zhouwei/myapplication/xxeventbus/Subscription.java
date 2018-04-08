package com.zhouwei.myapplication.xxeventbus;

/**
 * Created by Charles on 2018/4/8.
 */

/**
 * 事件订阅者 包装类
 */
public class Subscription {
    public Object subscriber;
    public SubscriberMethod subscriberMethod;

    public Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }
}
