package com.example.ruan.udp_sdk;

import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/15.
 */
public interface UDPListen {

    public interface UDPSend {

        /**
         * 发送消息的接口
         */
        public void Send(byte[] buffer);


        public void SendReults();
    }

    public interface UDPReviced {
        /**
         * 接收消息的接口
         *
         * @return
         */
        public Object[] Reviced();
    }

    public interface UDPHandler {

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
        public void Handler(int position, Object[] objects);

        /**
         * 接收出错或者超时
         *
         * @param position
         * @param error    0就是超时(30秒)
         */
        public void Error(int position, int error);
    }


    public interface UDPCallback {

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
        public void CallSuccess(int position, Object[] objects);

        /**
         * 接收出错或者超时
         *
         * @param position
         * @param error    0就是超时(30秒)
         */
        public void CallError(int position, int error);


    }

}
