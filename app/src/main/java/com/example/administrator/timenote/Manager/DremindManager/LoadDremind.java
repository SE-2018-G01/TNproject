package com.example.administrator.timenote.Manager.DremindManager;

import com.example.administrator.timenote.Manager.TaskManager.LoadAllEvent;
import com.example.administrator.timenote.Model.BeanDRemindInformation;
import com.example.administrator.timenote.Model.BeanEventInformation;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XuanWem Chen on 2018/6/20.
 */

public class LoadDremind {
    // 命名空间
    String nameSpace = "http://tempuri.org/";
    // 调用的方法名称
    String methodName = "LoadDefaultSet";
    // EndPoint
    String endPoint = "http://39.108.124.121:5818/WebService1.asmx";
    // SOAP Action
    String soapAction = "http://tempuri.org//LoadDefaultSet/";

    // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);

    HttpTransportSE transport = new HttpTransportSE(endPoint,60000*5);

    //创建子线程并引用webservice层的LoadUser方法
    public Boolean getRemoteInfo(int userid,int eventid) {
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("userid", userid);
        rpc.addProperty("eventid", eventid);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);
        // 等价于envelope.bodyOut = rpc;   envelope.setOutputSoapObject(rpc);
        transport.debug = false;
        try {
            transport.call(soapAction, envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            if (result == null)
                return false;
            BeanDRemindInformation dr = new BeanDRemindInformation();
            dr.setUserid(Integer.parseInt(result.getProperty("Userid").toString()));
            dr.setEventid(Integer.parseInt(result.getProperty("Eventid").toString()));
            dr.setDremindid(Integer.parseInt(result.getProperty("Dremindid").toString()));
            dr.setDremindring(Integer.parseInt(result.getProperty("Dremindring").toString()));
            dr.setDremindrepeat(Integer.parseInt(result.getProperty("Dremindrepeat").toString()));
            dr.setAheadtime(Integer.parseInt(result.getProperty("Aheadtime").toString()));
            dr.setPid(Integer.parseInt(result.getProperty("Pid").toString()));
            dr.setLeavestime(Integer.parseInt(result.getProperty("Leavestime").toString()));
            dr.setDefaulttime(result.getProperty("Defaulttime").toString());
            dr.setDremindvib(result.getProperty("Dremindvib").toString());
            BeanDRemindInformation.tsset = dr;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args){
        LoadDremind loadDremind = new LoadDremind();
        loadDremind.getRemoteInfo(9,-99);
    }
}
