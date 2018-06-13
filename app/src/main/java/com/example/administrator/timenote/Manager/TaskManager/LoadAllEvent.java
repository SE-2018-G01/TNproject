package com.example.administrator.timenote.Manager.TaskManager;

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
 * Created by XuanWem Chen on 2018/6/10.
 */

public class LoadAllEvent {
    // 命名空间
    String nameSpace = "http://tempuri.org/";
    // 调用的方法名称
    String methodName = "AllEvent";
    // EndPoint
    String endPoint = "http://39.108.124.121:5818/WebService1.asmx";
    // SOAP Action
    String soapAction = "http://tempuri.org//AllEvent/";

    // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);

    HttpTransportSE transport = new HttpTransportSE(endPoint,60000*5);

    //创建子线程并引用webservice层的LoadUser方法
    public List<BeanEventInformation> getRemoteInfo(int userid) {
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("usereid", userid);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);
        // 等价于envelope.bodyOut = rpc;   envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        //Message msg = MainActivity.myHandler.obtainMessage();
        List<BeanEventInformation> eventInformationList = new ArrayList<BeanEventInformation>();
        try {
            transport.call(soapAction, envelope);
            String a = transport.requestDump;
            System.out.println(a);
            String b =  transport.responseDump;
            System.out.println(b);
            SoapObject result = (SoapObject) envelope.bodyIn;
            SoapObject detail = (SoapObject) result.getProperty(methodName+"Result");
            for (int j = 0; j < detail.getPropertyCount(); j++) {
                BeanEventInformation e = new BeanEventInformation();
                SoapObject mstr = (SoapObject) detail.getProperty(j);
                e.setUserid(Integer.parseInt(mstr.getProperty(0).toString()));
                e.setEventid(Integer.parseInt(mstr.getProperty(1).toString()));
                e.setDreminid(Integer.parseInt(mstr.getProperty(2).toString()));
                e.setListid(Integer.parseInt(mstr.getProperty(3).toString()));
                e.setEventname(mstr.getProperty(4).toString());
                e.setEventdate(stringToDate(mstr.getProperty(5).toString(), "yyyy-MM-ddTHH:mm:ss"));
                e.setDreminddate(stringToDate(mstr.getProperty(6).toString(), "yyyy-MM-ddTHH:mm:ss"));
                e.setLeaveseventsign(mstr.getProperty(7).toString());
                if(mstr.getProperty(8)!=null) e.setEventnote(mstr.getProperty(8).toString());
                eventInformationList.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // msg.what = 0;
        }
        return eventInformationList;
    }

    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args){
        LoadAllEvent loadAllEvent = new LoadAllEvent();
        loadAllEvent.getRemoteInfo(133);
    }
}
