package com.example.ruan.udp_sdk;


import android.util.Log;

/**
 * Created by Ruanjiahui on 2016/7/15.
 * <p/>
 * 使用UDP直接继承这个类
 * 或者
 * 直接new 这个类就可以使用
 */
public class UDP implements UDPListen.UDPCallback {

    private UDPSource udpBase = null;
    private UDPListen.UDPHandler handler = null;
    private int count = 0;
    private String IP;
    private int PORT = 0;
    private byte[] buffer = null;


    /**
     * 实例化对象
     */
    public UDP() {
        udpBase = new UDPBase();
        //初始化链接
        udpBase.Connect();
    }

    /**
     * 外部调用的函数接口 接收数据
     *
     * @param position 接收数据的标识
     * @param handler  接收数据的接口
     */
    public void uReviced(int position, UDPListen.UDPHandler handler) {
        this.handler = handler;
        udpBase.Revice(position, this);
    }

    /**
     * 外部调用的函数接口    发送数据
     *
     * @param IP     发送IP
     * @param PORT   发送的远程端口
     * @param buffer 发送的内容
     * @param count  发送的次数
     */
    public void uSend(String IP, int PORT, byte[] buffer, int count) {
        this.IP = IP;
        this.PORT = PORT;
        this.count = count;
        this.buffer = buffer;
        udpBase.Send(IP, PORT, buffer);
    }

    /**
     * 关闭链接
     */
    public void unClose() {
        udpBase.unConnect();
    }

    /**
     * 处理接收消息的接口
     * //创建一个Object对象数组
     * //0 储存接收的数据
     * //1 储存接收数据的长度
     * //2 储存接收的地址
     * //3 储存接收的端口
     *
     * @param position 调用接口的表示
     * @param objects  返回的数组
     */
    @Override
    public void CallSuccess(int position, Object[] objects) {
        if (handler != null)
            handler.Handler(position, objects);
        else
            Log.e("Ruan", "UDPListen.UDPHandler is not null");
    }

    /**
     * 接收出错或者超时
     *
     * @param position
     * @param error    0就是超时(30秒)
     */
    @Override
    public void CallError(int position, int error) {
        count--;
        if (count > 0)
            udpBase.Send(IP, PORT, buffer);
        if (count <= 0) {
            if (handler != null)
                handler.Error(position, error);
            else
                Log.e("Ruan", "UDPListen.UDPHandler is not null");
        }
    }
}
