package com.zhouwei.myapplication.xxeventtbus;

/**
 * Created by Charles on 2018/4/8.
 */

public enum ThreadMode {
    MAIN(0), BACKGROUND(1);

    public int value;

    ThreadMode(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }
}
