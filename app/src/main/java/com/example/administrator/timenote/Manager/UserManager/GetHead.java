package com.example.administrator.timenote.Manager.UserManager;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by XuanWem Chen on 2018/6/13.
 */

public class GetHead {
    // 命名空间
    String nameSpace = "http://tempuri.org/";
    // 调用的方法名称
    String methodName = "GetHead";
    // EndPoint
    String endPoint = "http://39.108.124.121:5818/WebService1.asmx";
    // SOAP Action
    String soapAction = "http://tempuri.org//GetHead/";

    // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);

    HttpTransportSE transport = new HttpTransportSE(endPoint);

    //创建子线程并引用webservice层的LoadUser方法
    public byte[] getRemoteInfo() {
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        //rpc.addProperty("head", head);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);
        // 等价于envelope.bodyOut = rpc;   envelope.setOutputSoapObject(rpc);
        transport.debug = false;
        byte[] result = new byte[1024];
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse()!=null) {
                result = envelope.getResponse().toString().getBytes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
