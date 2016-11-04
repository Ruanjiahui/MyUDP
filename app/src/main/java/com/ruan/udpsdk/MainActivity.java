package com.ruan.udpsdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ruan.udp_sdk.UDP;
import com.example.ruan.udp_sdk.UDPListen;

public class MainActivity extends AppCompatActivity implements BlinkIOTCall{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ScanUtil().Scan("IoT" , this);


    }

    /**
     * 接口调用成功
     *
     * @param mac
     */
    @Override
    public void didBlinkCallSuccess(String mac) {
        System.out.println(mac);
    }

    /**
     * 接口调用失败
     *
     * @param err_no 错误代码(BlinkErrorNo)
     */
    @Override
    public void didBlinkCallFailure(int err_no) {

    }
}
