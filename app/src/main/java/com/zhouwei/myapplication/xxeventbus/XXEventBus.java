package com.zhouwei.myapplication.xxeventbus;

import android.os.Looper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Charles on 2018/4/8.
 */

public class XXEventBus {
    // class 事件类 ；ArrayList<Subscription> 订阅了某一个事件的所有观察者的集合
    private HashMap<Class, ArrayList<Subscription>> subscriptionsWithEvent = new HashMap<>();

    private MainThreadHandler mainThreadHandler = new MainThreadHandler(this, Looper.getMainLooper());

    private AsyThreadHandler asyThreadHandler = new AsyThreadHandler(this);

    public void register(Object subscriber) {
        Class clazz = subscriber.getClass();
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if (null != annotation) {
                ThreadMode threadMode = annotation.tm();
                Class[] params = method.getParameterTypes();
                ArrayList<Subscription> subscriptions;
                if (subscriptionsWithEvent.containsKey(params[0])) {
                    subscriptions = subscriptionsWithEvent.get(params[0]);
                } else {
                    subscriptions = new ArrayList<>();
                }

                Subscription sub = null;
                if (threadMode == ThreadMode.MAIN) {
                    sub = new Subscription(subscriber, new SubscriberMethod(method, params[0], 1));
                } else if (threadMode == ThreadMode.BACKGROUND) {
                    sub = new Subscription(subscriber, new SubscriberMethod(method, params[0], 0));
                }

                subscriptions.add(sub);
                subscriptionsWithEvent.put(params[0], subscriptions);
            }
        }
    }

    public void unRegister(Object subscription) {
        synchronized (subscriptionsWithEvent) {
            for (Map.Entry<Class, ArrayList<Subscription>> en : subscriptionsWithEvent.entrySet()) {
                Class clazz = en.getKey();
                ArrayList<Subscription> subscriptions = en.getValue();

                ArrayList<Subscription> dels = new ArrayList<>();
                for (Subscription sub : subscriptions) {
                    if (sub.subscriber == subscription) {
                        dels.add(sub);
                    }
                }

                subscriptions.removeAll(dels);
            }
        }
    }

    public void post(Object event) {
        Class clazz = event.getClass();
        ArrayList<Subscription> subscriptions = subscriptionsWithEvent.get(clazz);
        if (null != subscriptions && subscriptions.size() > 0) {
            for (Subscription subscription : subscriptions) {
                if (subscription.subscriberMethod.flag == 0) {
                    asyThreadHandler.post(subscription, event);
                } else {
                    mainThreadHandler.post(subscription, event);
                }
            }
        }
    }

    public static XXEventBus getInstance() {
        return XXEventBusHolder.instance;
    }

    private XXEventBus() {
    }

    public void invoke(Subscription subscription, Method method, Object event) {
        try {
            method.invoke(subscription.subscriber, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class XXEventBusHolder {
        public static XXEventBus instance = new XXEventBus();
    }
}
