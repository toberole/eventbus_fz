package com.zhouwei.myapplication.eventbus_;

/**
 * Created by Charles on 2018/3/28.
 */

public class EventELF {

    public int runEnv = EventThreadMode.BACKGROUND_THREAD;

    private int eventCode;
    private Object eventData;

    public EventELF(int eventCode, Object eventData) {
        this.eventCode = eventCode;
        this.eventData = eventData;
    }

    public <E> E getEventData() {
        return (E) eventData;
    }

    public int getEventCode() {
        return eventCode;
    }
}
