package com.ruan.udpsdk;

import android.util.Log;

import com.example.ruan.udp_sdk.UDP;
import com.example.ruan.udp_sdk.UDPListen;

/**
 * Created by Administrator on 2016/11/4.
 */
public class ScanUtil extends UDP implements UDPListen.UDPHandler {


    private BlinkIOTCall call = null;

    public void Scan(String sn, BlinkIOTCall call) {
        this.call = call;

        this.uSend("255.255.255.255", 9331, sn.getBytes(), 2);
        this.uReviced(0, this);
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
    public void Handler(int position, Object[] objects) {
        call.didBlinkCallSuccess(new String((byte[]) objects[0], 0, (int) objects[1]));
    }

    /**
     * 接收出错或者超时
     *
     * @param position
     * @param error    0就是超时(30秒)
     */
    @Override
    public void Error(int position, int error) {

    }
}
