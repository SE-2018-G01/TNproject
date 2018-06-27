package com.example.administrator.timenote.Ring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/02/14.
 */

public class RingReceived extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", 1);
        int ring = intent.getIntExtra("ring", 0);
        Boolean vib = intent.getBooleanExtra("vib", false);
        int ahead = intent.getIntExtra("ahead" , 0);
        int repeat = intent.getIntExtra("repeat", 0);
        int eventid = intent.getIntExtra("eventid", 0);
        String eventname = intent.getStringExtra("eventname");
        String eventdate = intent.getStringExtra("eventdate");

        System.out.print(id);
        //跳转到activity  播放闹钟
        Intent intent1=new Intent(context,RingActivity.class);
        //给INtentm设置一个标志位
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("id", id);
        intent1.putExtra("ring", ring);
        intent1.putExtra("vib", vib);
        intent1.putExtra("ahead", ahead);
        intent1.putExtra("repeat", repeat);
        intent1.putExtra("eventid", eventid);
        intent1.putExtra("eventname", eventname);
        intent1.putExtra("eventdate", eventdate);
        context.startActivity(intent1);
        Calendar calendar1= Calendar.getInstance();
    }
}
