package com.example.ruan.udp_sdk.Thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.ruan.udp_sdk.UDPListen;


/**
 * Created by Administrator on 2016/7/15.
 */
public class UDPReviced implements Runnable {
    private UDPListen.UDPReviced uReviced = null;
    private UDPListen.UDPCallback callback = null;
    private int position = 0;

    /**
     * 这个方法是接收信息调用的方法
     *
     * @param uReviced 接收信息的接口
     * @param callback 处理接收信息的接口
     */
    public UDPReviced(int position, UDPListen.UDPReviced uReviced, UDPListen.UDPCallback callback) {
        this.position = position;
        this.uReviced = uReviced;
        this.callback = callback;
    }

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        while (true) {
            Message message = new Message();
            Object[] data = uReviced.Reviced();
            if (data != null) {
                message.obj = data;
                handler.sendMessage(message);
            }
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            callback.CallSuccess(position, (Object[]) msg.obj);
        }
    };
}

