package com.example.administrator.timenote.Ring;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.timenote.R;

import java.util.Calendar;

public class RingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private Vibrator mVibrator;  //声明一个振动器对象

    PowerManager.WakeLock mWakelock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //唤醒屏幕
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_ring);

        //铃声设置
        mediaPlayer = MediaPlayer.create(this, R.raw.one);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //振动设置
        mVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        mVibrator.vibrate(new long[]{1000, 1000, 1000, 1000}, 0);

        //唤醒屏幕
        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
        mWakelock.acquire();
    }

    //停止点击事件
    @SuppressLint("NewApi")
    public void stop(View view){
        if (mediaPlayer!=null){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
        if (mVibrator.hasVibrator())
        {
            mVibrator.cancel();
        }
        mWakelock.release();

        //设置下一次提醒
        int id = getIntent().getIntExtra("id",0);

        Calendar calendar1= Calendar.getInstance();
        Intent intent=new Intent();
        intent.setAction("com.liuqian.android_27alarm.RING");
        intent.putExtra("id",id);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(RingActivity.this,id,intent,0);
        //闹钟管理者 参数： 唤醒屏幕，
        AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent);

        finish();
    }

}
