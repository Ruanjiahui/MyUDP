package com.example.ruan.udp_sdk.Thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.ruan.udp_sdk.UDPConfig;
import com.example.ruan.udp_sdk.UDPListen;

/**
 * Created by Administrator on 2016/7/15.
 */
public class UDPSend implements Runnable {
    private UDPListen.UDPSend uSend = null;
    private byte[] buffer = null;
    private int count = 0;

    /**
     * 这个方法是发送UDP调用的方法
     *
     * @param uSend 发送信息的接口
     */
    public UDPSend(UDPListen.UDPSend uSend, byte[] buffer, int count) {
        this.uSend = uSend;
        this.buffer = buffer;
        this.count = count;
    }

    /**
     * 这个方法是发送UDP调用的方法
     *
     * @param uSend 发送信息的接口
     */
    public UDPSend(UDPListen.UDPSend uSend, byte[] buffer) {
        this.uSend = uSend;
        this.buffer = buffer;
    }

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        //每隔1秒发送一个
        uSend.Send(buffer);

        Message msg = new Message();
        handler.sendMessage(msg);
    }


    private Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void dispatchMessage(Message msg) {
            uSend.SendReults();
        }
    };
}
