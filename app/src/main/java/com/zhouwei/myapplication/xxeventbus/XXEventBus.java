package com.zhouwei.myapplication.xxeventbus;

import android.os.Looper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Charles on 2018/4/8.
 */

public class XXEventBus {
    // class 事件类 ；ArrayList<Subscription> 订阅了某一个事件的所有观察者的集合
    private Map<Class, ArrayList<Subscription>> subscriptionsWithEvent = new ConcurrentHashMap<>();

    private MainThreadHandler mainThreadHandler = new MainThreadHandler(this, Looper.getMainLooper());

    private AsyThreadHandler asyThreadHandler = new AsyThreadHandler(this);

    public void register(Object subscriber) {
        if (!isRegistered(subscriber)) {
            Class clazz = subscriber.getClass();
            Method[] methods = clazz.getMethods();

            for (Method method : methods) {
                int modifiers = method.getModifiers();
                // 判断修饰符 被回调的方法必须是Public，不能是 static, abstract
                if ((modifiers & Modifier.ABSTRACT) == 0 && (modifiers & Modifier.STATIC) == 0) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    // 参数只能是一个 被注册的事件
                    if (parameterTypes != null && parameterTypes.length == 1) {
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
                                sub = new Subscription(subscriber, new SubscriberMethod(method, params[0], ThreadMode.MAIN));
                            } else if (threadMode == ThreadMode.BACKGROUND) {
                                sub = new Subscription(subscriber, new SubscriberMethod(method, params[0], ThreadMode.BACKGROUND));
                            }

                            subscriptions.add(sub);
                            subscriptionsWithEvent.put(params[0], subscriptions);
                        }
                    }
                }
            }
        }
    }

    public void unRegister(Object subscriber) {
        for (Map.Entry<Class, ArrayList<Subscription>> en : subscriptionsWithEvent.entrySet()) {
            ArrayList<Subscription> subscriptions = en.getValue();

            ArrayList<Subscription> dels = new ArrayList<>();
            for (Subscription sub : subscriptions) {
                if (sub.subscriber == subscriber) {
                    dels.add(sub);
                }
            }

            subscriptions.removeAll(dels);
        }
    }

    public boolean isRegistered(Object subscriber) {
        boolean isRegistered = false;
        for (Map.Entry<Class, ArrayList<Subscription>> en : subscriptionsWithEvent.entrySet()) {
            ArrayList<Subscription> subscriptions = en.getValue();

            for (Subscription sub : subscriptions) {
                if (sub.subscriber == subscriber) {
                    isRegistered = true;
                }
            }

            if (isRegistered) return isRegistered;
        }

        return isRegistered;
    }

    public void post(Object event) {
        Class clazz = event.getClass();
        ArrayList<Subscription> subscriptions = subscriptionsWithEvent.get(clazz);
        if (null != subscriptions && subscriptions.size() > 0) {
            for (Subscription subscription : subscriptions) {
                if (subscription.subscriberMethod.flag == ThreadMode.BACKGROUND) {
                    asyThreadHandler.post(subscription, event);
                } else {
                    mainThreadHandler.post(subscription, event);
                }
            }
        }
    }

    public void invoke(Subscription subscription, Method method, Object event) {
        try {
            method.invoke(subscription.subscriber, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static XXEventBus getInstance() {
        return XXEventBusHolder.instance;
    }

    private XXEventBus() {
    }

    private static class XXEventBusHolder {
        public static XXEventBus instance = new XXEventBus();
    }
}
