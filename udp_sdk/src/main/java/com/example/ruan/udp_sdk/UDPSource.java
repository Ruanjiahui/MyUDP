package com.example.ruan.udp_sdk;

/**
 * Created by Administrator on 2016/7/16.
 *
 * 这个是UDP传输协议的抽象类
 */
public abstract class UDPSource{

    /**
     * 这个方法是初始化udp链接
     */
    protected abstract void Connect();

    /**
     * 这个方法是发送信息
     * @param IP            发送的IP
     * @param PORT          发送的端口
     * @param buffer        发送的数据
     */
    protected abstract void Send(String IP , int PORT , byte[] buffer);


    /**
     * 这个方法是接收信息
     *
     * @param position  请求的标示
     * @param callback   返回数据进行更新
     */
    protected abstract void Revice(int position , UDPListen.UDPCallback callback);


    /**
     * 退出链接
     */
    protected abstract void unConnect();
}
