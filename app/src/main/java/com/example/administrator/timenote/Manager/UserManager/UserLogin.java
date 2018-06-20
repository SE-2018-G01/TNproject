package com.example.administrator.timenote.Manager.UserManager;


import android.os.Message;
import android.util.Base64;
import android.util.Base64DataException;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.Ui.MainActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.Date;
import java.util.List;

import static com.example.administrator.timenote.Model.BeanUserInformation.tryLoginUser;

/**
 * Created by Sprou on 2018/5/26.
 */

public class UserLogin {
    // 命名空间
    String nameSpace = "http://tempuri.org/";
    // 调用的方法名称
    String methodName = "LoadUser";
    // EndPoint
    String endPoint = "http://39.108.124.121:5818/WebService1.asmx";
    // SOAP Action
    String soapAction = "http://tempuri.org//LoadUser/";

    // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);

    HttpTransportSE transport = new HttpTransportSE(endPoint);

    //创建子线程并引用webservice层的LoadUser方法
    public void getRemoteInfo(String useremail) {
        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId
        rpc.addProperty("useremail", useremail);
        envelope.bodyOut = rpc;
        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;
        (new MarshalBase64()).register(envelope);
        // 等价于envelope.bodyOut = rpc;   envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        //Message msg = MainActivity.myHandler.obtainMessage();
        BeanUserInformation u = new BeanUserInformation();
        try {
            transport.call(soapAction, envelope);
            String a = transport.requestDump;
            System.out.println(a);
            String b =  transport.responseDump;
            System.out.println(b);
            if (envelope.getResponse() == null) throw new Exception("没有该用户");
            SoapObject result = (SoapObject) envelope.getResponse();
            u.setUserid(Integer.parseInt(result.getProperty(0).toString()));
            u.setUsername(result.getProperty(1).toString());
            if(!result.getProperty(2).toString().equals("anyType{}")) u.setUserpassword(result.getProperty(2).toString());
            u.setUseremail(result.getProperty(3).toString());
            u.setCreatdate(result.getProperty(4).toString());
            u.setStopdate(result.getProperty(5).toString());
            u.setUsercalendar(result.getProperty(6).toString());
            u.setUsertypeface(result.getProperty(7).toString());
            u.setAchievement(Integer.parseInt(result.getProperty(8).toString()));
            u.setLeavesid(Integer.parseInt(result.getProperty(9).toString()));
            u.setAuthcode(result.getProperty(10).toString());
            u.setIcon(Base64.decode(result.getProperty(11).toString(), Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanUserInformation.tryLoginUser=u;
    }
    public static void main(String[] args){
        UserLogin userLogin = new UserLogin();
        userLogin.getRemoteInfo("972357450@qq.com");
        System.out.println(tryLoginUser.getUseremail());
        System.out.println(tryLoginUser.getUserpassword());
    }
}