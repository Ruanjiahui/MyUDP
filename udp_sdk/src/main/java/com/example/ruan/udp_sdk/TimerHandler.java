package com.example.ruan.udp_sdk;

import android.os.Message;

/**
 * Created by Administrator on 2016/3/18.
 */
public interface TimerHandler {

    public void timerHandler(Message msg);

    public Message timerRun();
}
