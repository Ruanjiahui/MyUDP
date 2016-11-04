package com.ruan.udpsdk;

/**
 * Created by Administrator on 2016/11/4.
 */
public interface BlinkIOTCall {

    /**
     * 接口调用成功
     *
     * @param mac
     */
    public void didBlinkCallSuccess(String mac);

    /**
     * 接口调用失败
     *
     * @param err_no 错误代码(BlinkErrorNo)
     */
    public void didBlinkCallFailure(int err_no);
}
