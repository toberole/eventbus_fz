package com.zhouwei.myapplication.xxeventbus;

import java.lang.reflect.Method;

/**
 * Created by Charles on 2018/4/8.
 * <p>
 * 观察者里面被回调的方法 包装类
 */

public class SubscriberMethod {
    public Method method; // 接收事件的方法
    public Class clazz; // 方法的参数class[ 也即事件 ]
    public ThreadMode flag; // 表示该方法需要在子线程中运行或者主线程中运行

    public SubscriberMethod(Method method, Class clazz, ThreadMode flag) {
        this.method = method;
        this.clazz = clazz;
        this.flag = flag;
    }
}
