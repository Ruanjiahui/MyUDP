package com.example.ruan.udp_sdk.Thread;

import android.os.Handler;
import android.os.Message;


import com.example.ruan.udp_sdk.TimerHandler;

import java.util.TimerTask;

/**
 * Created by Administrator on 2016/2/29.
 */
public class MyTimerTask extends TimerTask {

    private TimerHandler timerHandler = null;

    public MyTimerTask(TimerHandler timerHandler) {
        this.timerHandler = timerHandler;
    }

    public MyTimerTask() {
    }


    @Override
    public void run() {
//        Looper.prepare();
        if (timerHandler.timerRun() != null)
            handler.sendMessage(timerHandler.timerRun());
//        Looper.loop();
    }

    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            timerHandler.timerHandler(msg);
        }
    };
}
