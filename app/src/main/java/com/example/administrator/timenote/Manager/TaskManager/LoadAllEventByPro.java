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
 * Created by XuanWem Chen on 2018/6/17.
 */

public class LoadAllEventByPro {
    // 命名空间
    String nameSpace = "http://tempuri.org/";
    // 调用的方法名称
    String methodName = "AllEventByPro";
    // EndPoint
    String endPoint = "http://39.108.124.121:5818/WebService1.asmx";
    // SOAP Action
    String soapAction = "http://tempuri.org//AllEventByPro/";

    // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);

    HttpTransportSE transport = new HttpTransportSE(endPoint,60000*5);

    //创建子线程并引用webservice层的LoadUser方法
    public Boolean getRemoteInfo(int userid) {
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("userid", userid);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);
        // 等价于envelope.bodyOut = rpc;   envelope.setOutputSoapObject(rpc);
        transport.debug = false;
        //List<BeanEventInformation> eventInformationList = new ArrayList<>();
        List<BeanEventInformation> allEventListByPro = new ArrayList<>();
        try {
            transport.call(soapAction, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            SoapObject detail = (SoapObject) result.getProperty(methodName+"Result");

            for (int j = 0; j < detail.getPropertyCount(); j++) {
                BeanEventInformation e = new BeanEventInformation();
                SoapObject mstr = (SoapObject) detail.getProperty(j);
                e.setUserid(Integer.parseInt(mstr.getProperty("Userid").toString()));
                e.setEventid(Integer.parseInt(mstr.getProperty("Eventid").toString()));
                e.setDreminid(Integer.parseInt(mstr.getProperty("Dreminid").toString()));
                e.setListid(Integer.parseInt(mstr.getProperty("Listid").toString()));
                e.setEventname(mstr.getProperty("Eventname").toString());
                e.setEventpriority(Integer.parseInt(mstr.getProperty("Eventpriority").toString()));
                e.setEventdate(stringToDate(mstr.getProperty("Eventdate").toString().replaceAll("T"," "), "yyyy-MM-dd HH:mm:ss"));
                e.setDreminddate(stringToDate(mstr.getProperty("Dreminddate").toString().replaceAll("T"," "), "yyyy-MM-dd HH:mm:ss"));
                e.setLeaveseventsign(mstr.getProperty("Leaveseventsign").toString());
                if(!mstr.getProperty("Eventnote").toString().equals("anyType{}")) e.setEventnote(mstr.getProperty("Eventnote").toString());
                e.setCheckBox(Integer.parseInt(mstr.getProperty("Complete").toString()));
                allEventListByPro.add(e);
            }
            BeanEventInformation.allEventList = allEventListByPro;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
        loadAllEvent.getRemoteInfo(9);
    }
}
