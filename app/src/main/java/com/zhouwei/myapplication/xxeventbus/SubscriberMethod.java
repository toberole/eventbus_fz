package com.zhouwei.myapplication.xxeventbus;

import java.lang.reflect.Method;

/**
 * Created by Charles on 2018/4/8.
 * <p>
 * 观察者里面的方法 包装类
 */

public class SubscriberMethod {
    public Method method;// 接收事件的方法
    public Class clazz;// 方法的参数[ 也即事件 ]
    public int flag;// 0 标识子线程；1 标识主线程

    public SubscriberMethod(Method method, Class clazz, int flag) {
        this.method = method;
        this.clazz = clazz;
        this.flag = flag;
    }
}
