package com.example.administrator.timenote.Ring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/02/14.
 */

public class RingReceived extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id",1);
        System.out.print(id);
        //跳转到activity  播放闹钟
            Intent intent1=new Intent(context,RingActivity.class);
            //给INtentm设置一个标志位
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra("id",id);
            context.startActivity(intent1);
        Calendar calendar1= Calendar.getInstance();
    }
}
