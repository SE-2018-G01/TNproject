package com.example.administrator.timenote.Manager;

/**
 * Created by Sprou on 2018/6/6.
 */

import java.io.IOException;

import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;

/**
 * 继承了HttpTransportSE ,添加请求超时的操作
 *
 * @author wsx
 *
 */
public class MyAndroidHttpTransport extends HttpTransportSE {

    private int timeout = 30000; // 默认超时时间为30s

    public MyAndroidHttpTransport(String url) {
        super(url);
    }

    public MyAndroidHttpTransport(String url, int timeout) {
        super(url);
        this.timeout = timeout;
    }

    @Override
    public ServiceConnection getServiceConnection() throws IOException {
        ServiceConnectionSE serviceConnection = new ServiceConnectionSE(url);
        serviceConnection.setConnectionTimeOut(timeout);

        return serviceConnection;
    }
}