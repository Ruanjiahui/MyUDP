package com.example.ruan.udp_sdk;

import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;

import com.example.ruan.udp_sdk.Thread.MyTimerTask;
import com.example.ruan.udp_sdk.Thread.UDPReviced;
import com.example.ruan.udp_sdk.Thread.UDPSend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Timer;

/**
 * Created by Administrator on 2016/7/16.
 */
public class UDPBase extends UDPSource implements UDPListen.UDPReviced, UDPListen.UDPSend, TimerHandler {


    private DatagramSocket datagramSocket = null;
    private String IP = null;
    private int PORT = 0;
    private byte[] buffer = null;
    private int size = 5120;
    private Thread thread = null;
    private Timer timer = null;
    private UDPListen.UDPCallback callback = null;
    private int position = 0;
    private MyTimerTask myTimerTask = null;


    /**
     * 这个方法是初始化udp链接
     */
    @Override
    protected void Connect() {
        try {
            if (datagramSocket == null)
                datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个方法是发送信息
     *
     * @param IP     发送的IP
     * @param PORT   发送的端口
     * @param buffer 发送的数据
     */
    @Override
    protected void Send(String IP, int PORT, byte[] buffer) {
        this.IP = IP;
        this.PORT = PORT;
        System.out.println(new String(buffer, 0, buffer.length) + "--" + IP + "--" + PORT);


//        try {
//            DatagramPacket outdatagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(IP), PORT);
//            datagramSocket.send(outdatagramPacket);
//            timer = new Timer();
//            myTimerTask = new MyTimerTask(this);
//            timer.schedule(myTimerTask, UDPConfig.time, UDPConfig.time);
//        } catch (Exception e) {
//            CloseUDP();
//        }

        new Thread(new UDPSend(this, buffer)).start();
    }


    /**
     * 这个方法是接收信息
     */
    @Override
    protected void Revice(int position, UDPListen.UDPCallback callback) {
        this.position = position;
        this.callback = callback;
        thread = new Thread(new UDPReviced(position, this, callback));
        thread.start();
    }


    /**
     * 退出链接
     */
    @Override
    protected void unConnect() {
        if (datagramSocket != null) {
            this.datagramSocket.close();
            datagramSocket = null;
        }
    }

    /**
     * 接收消息的接口
     *
     * @return
     */
    @Override
    public Object[] Reviced() {
        //创建一个Object对象数组
        //0 储存接收的数据
        //1 储存接收数据的长度
        //2 储存接收的地址
        //3 储存接收的端口
        Object[] objects = new Object[4];
        if (datagramSocket != null) {
            try {
                buffer = new byte[size];
                DatagramPacket indatagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(indatagramPacket);
                Log.e("System.out", "接收结束");
                timeout = false;
                //马上关闭UDP
                CloseUDP();
                System.out.println(timeout + "---");
                System.out.println(indatagramPacket.getLength());
                //获取返回数据的长度
                objects[0] = buffer;
                objects[1] = indatagramPacket.getLength();
                objects[2] = indatagramPacket.getAddress().getHostAddress();
                objects[3] = indatagramPacket.getPort();
            } catch (IOException e) {
                CloseUDP();
            }
        }
        return objects;
    }

    /**
     * 发送消息的接口
     */
    @Override
    public void Send(byte[] buffer) {
        try {
            DatagramPacket outdatagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(IP), PORT);
            datagramSocket.send(outdatagramPacket);
        } catch (Exception e) {
            CloseUDP();
        }
    }

    @Override
    public void SendReults() {
        //timeout为true说明还没有接受到数据这个时候启动定时器
        if (timeout) {
            timer = new Timer();
            myTimerTask = new MyTimerTask(this);
            try {
                timer.schedule(myTimerTask, UDPConfig.time, UDPConfig.time);
            } catch (Exception e) {
                System.out.println("time was cancal");
            }
        }
    }

    private boolean timeout = true;

    @Override
    public void timerHandler(Message msg) {
        timeout = myTimerTask.cancel();
        Log.e("System.out", "++" + timeout);
        if (timeout) {
            CloseUDP();
            Log.e("System.out", "-------");
            //超时
            callback.CallError(position, 0);
        }
    }

    @Override
    public Message timerRun() {
//        try {
//            Thread.sleep(UDPConfig.time);
//        } catch (InterruptedException e) {
//        }
//        if (myTimerTask != null)
//            myTimerTask.cancel();
        return new Message();
    }


    private void CloseUDP() {
//        if (datagramSocket != null) {
//            datagramSocket.close();
//            datagramSocket = null;
//        }
        System.out.println(thread + "--" + timer + "--" + myTimerTask);
        if (thread != null)
            thread.interrupt();
        if (timer != null)
            timer.cancel();
        if (myTimerTask != null) {
            myTimerTask.cancel();
            Log.e("System.out", "--" + timeout);
        }
//        Log.e("System.out", "--" + myTimerTask.cancel());
    }
}
