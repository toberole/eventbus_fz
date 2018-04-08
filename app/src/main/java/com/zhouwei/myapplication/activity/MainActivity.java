package com.zhouwei.myapplication.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhouwei.myapplication.R;
import com.zhouwei.myapplication.Student;
import com.zhouwei.myapplication.UpdateInfo;
import com.zhouwei.myapplication.eventbus_.BaseActivity;
import com.zhouwei.myapplication.eventbus_.EventBusEngine;
import com.zhouwei.myapplication.eventbus_.EventCode;
import com.zhouwei.myapplication.eventbus_.EventELF;
import com.zhouwei.myapplication.eventbus_.EventThreadMode;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button button;
    private Button stu;

    @Override
    protected void setContent() {
        layout = R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        button = findViewById(R.id.btn_testEventBus);

        stu = findViewById(R.id.btn_testEventBusStu);

        button.setOnClickListener(this);
        stu.setOnClickListener(this);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onReceiveEvent(EventELF eventELF) {
        switch (eventELF.getEventCode()) {
            case EventCode.UPDATE_EVENT:
                UpdateInfo info = eventELF.getEventData();
                Log.i("AAAA", info.toString() + " Thread Name: " + Thread.currentThread().getName());
                break;
            case EventCode.STU_EVENT:
                Student student = eventELF.getEventData();
                Log.i("AAAA", student.toString() + " Thread Name: " + Thread.currentThread().getName());
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_testEventBus:
                UpdateInfo info = new UpdateInfo();
                info.desc = "hello update info";
                info.versionCode = 10;
                EventELF eventELF = new EventELF(EventCode.UPDATE_EVENT, info);
                eventELF.runEnv = EventThreadMode.MAIN_THREAD;
                EventBusEngine.postEvent(eventELF);
                break;
            case R.id.btn_testEventBusStu:
                Student student = new Student();
                student.age = 20;
                student.name = "xiaohong";
                EventELF eventELF1 = new EventELF(EventCode.STU_EVENT, student);
                EventBusEngine.postEvent(eventELF1);
                break;
            default:
                break;
        }

    }
}
