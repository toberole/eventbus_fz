package com.zhouwei.myapplication.eventbus_;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Charles on 2018/3/28.
 */

public class EventBusEngine {
    public static void register(Object subScriber) {
        if (null != subScriber && !EventBus.getDefault().isRegistered(subScriber)) {
            EventBus.getDefault().register(subScriber);
        }
    }

    public static void unRegister(Object subScriber) {
        if (null != subScriber && EventBus.getDefault().isRegistered(subScriber)) {
            EventBus.getDefault().unregister(subScriber);
        }
    }

    public static void postEvent(EventELF eventELF) {
        if (null != eventELF) {
            EventBus.getDefault().post(eventELF);
        }
    }
}
