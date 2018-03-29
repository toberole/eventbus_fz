package com.zhouwei.myapplication;

/**
 * Created by Charles on 2018/3/28.
 */

public class UpdateInfo {
    public int versionCode;
    public String desc;

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "versionCode=" + versionCode +
                ", desc='" + desc + '\'' +
                '}';
    }
}
