package com.example.administrator.timenote.Ring;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.timenote.Manager.TaskManager.Complete;
import com.example.administrator.timenote.Model.BeanDRemindInformation;
import com.example.administrator.timenote.R;
import com.example.administrator.timenote.Ui.MyCustomAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.example.administrator.timenote.Model.BeanEventInformation.allEventList;

public class RingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private Vibrator mVibrator;  //声明一个振动器对象

    private Ringtone rt;
    private TextView eventname_text;
    private String eventname;
    private TextView eventdate_text;
    private String eventdate;
    private Button complete;
    private Button putoff;
    private int eventid;
    private int ahead;
    private int repeat;
    private Calendar calendar;
    private Boolean vib;
    private int ring;

    PowerManager.WakeLock mWakelock;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_ring);

        eventname_text = findViewById(R.id.task_name);
        eventdate_text = findViewById(R.id.task_time);
        complete = findViewById(R.id.complete_2);
        putoff = findViewById(R.id.putoff);

        eventname = getIntent().getStringExtra("eventname");
        eventname_text.setText(eventname);

        eventdate = getIntent().getStringExtra("eventdate");
        eventdate_text.setText(eventdate);

        eventid = getIntent().getIntExtra("eventid", 0);
        calendar = Calendar.getInstance();

        ahead = getIntent().getIntExtra("ahead", 0);
        repeat = getIntent().getIntExtra("repeat", 0);

        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = formatter.parse(eventdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);

//        long mils = 0;
//        switch (ahead){
//            case 0:break;
//            case 1:mils = 1000*60*5;break;
//            case 2:mils = 1000*60*30;break;
//            case 3:mils = 1000*60*60;break;
//            case 4:mils = 1000*60*60*24;break;
//        }


        if (repeat == 0){
            init();
        }else {
            int id = UUID.randomUUID().hashCode();
            Calendar calendar1 = Calendar.getInstance();
            Intent intent = new Intent();
            intent.setAction("com.liuqian.android_27alarm.RING");
            intent.putExtra("id", id);
            intent.putExtra("ring", ring);
            intent.putExtra("vib", vib);
            intent.putExtra("ahead", ahead);
            intent.putExtra("repeat", repeat);
            intent.putExtra("eventid", eventid);
            intent.putExtra("eventname", eventname);
            intent.putExtra("eventdate", eventdate);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(RingActivity.this, id, intent, 0);
            //闹钟管理者 参数： 唤醒屏幕，
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis() + 1000 * 60 * 60 * 24, pendingIntent);

            switch (repeat) {
                case 1:
                    init();
                    break;
                case 2:
                    if (calendar.get(Calendar.DAY_OF_WEEK) !=1 && calendar.get(Calendar.DAY_OF_WEEK) !=7)
                        init();
                    else
                        finish();
                    break;
                case 3:
                    if (calendar.get(Calendar.DAY_OF_WEEK) ==1 || calendar.get(Calendar.DAY_OF_WEEK) ==7)
                        init();
                    else
                        finish();
                    break;
                case 4:
                    if (calendar.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))
                        init();
                    else
                        finish();
                    break;
                case 5:
                    if (calendar.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR))
                        init();
                    else
                        finish();
                    break;
                default:finish();
                    break;

            }
        }


    }

    private void init(){
        //唤醒屏幕
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        cdt.start();

        // 铃声设置
        ring = getIntent().getIntExtra("ring",0);
        switch (ring){
            case 0:break;
            case 1:
                mediaPlayer = MediaPlayer.create(this, R.raw.one);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(this, R.raw.two);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(this, R.raw.three);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                break;
            case 4:
                mediaPlayer = MediaPlayer.create(this, R.raw.four);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                break;
            case 5:
                mediaPlayer = MediaPlayer.create(this, R.raw.five);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                break;
            case 6:
                mediaPlayer = MediaPlayer.create(this, R.raw.six);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                break;
            default:break;
        }

        // 振动设置
        vib = getIntent().getBooleanExtra("vib",false);
        mVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        if (vib) {
            mVibrator.vibrate(new long[]{1000, 1000, 1000, 1000}, 0);
        }

        //唤醒屏幕
        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
        mWakelock.acquire();

        // 完成点击监听
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Complete complete = new Complete();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        complete.getRemoteInfo(eventid);
                    }
                });
                t.start();
                try {
                    t.join(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stop(view);
            }
        });

        // 推迟点击监听
        putoff.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int id = UUID.randomUUID().hashCode();;
                Calendar calendar1= Calendar.getInstance();
                Intent intent=new Intent();
                intent.setAction("com.liuqian.android_27alarm.RING");
                intent.putExtra("id",id);
                intent.putExtra("ring", ring);
                intent.putExtra("vib", vib);
                intent.putExtra("ahead", ahead);
                intent.putExtra("repeat", repeat);
                intent.putExtra("eventid", eventid);
                intent.putExtra("eventname", eventname);
                intent.putExtra("eventdate", eventdate);
                PendingIntent pendingIntent= PendingIntent.getBroadcast(RingActivity.this,id,intent,0);
                //闹钟管理者 参数： 唤醒屏幕，
                AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis() + 1000 * 60 * 5, pendingIntent);
                stop(view);
            }
        });
    }


    // 停止点击事件
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

//        //设置下一次提醒
//        int id = getIntent().getIntExtra("id",0);
//
//        Calendar calendar1= Calendar.getInstance();
//        Intent intent=new Intent();
//        intent.setAction("com.liuqian.android_27alarm.RING");
//        intent.putExtra("id",id);
//        PendingIntent pendingIntent= PendingIntent.getBroadcast(RingActivity.this,id,intent,0);
//        //闹钟管理者 参数： 唤醒屏幕，
//        AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent);
        cdt.cancel();
        finish();

    }

    CountDownTimer cdt = new CountDownTimer(60000, 5000) {

        @SuppressLint({"ResourceAsColor", "SetTextI18n"})
        public void onTick(long millisUntilFinished) {
        }
        @SuppressLint("ResourceAsColor")
        @Override
        public void onFinish() {
            //实例化通知管理器
            NotificationManager notificationManager=  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //实例化通知
            NotificationCompat.Builder builder=new NotificationCompat.Builder(RingActivity.this);
            //设置值
            builder.setContentTitle("错过闹钟");
            builder.setContentText(eventname);
            builder.setDefaults(NotificationCompat.DEFAULT_ALL);
            builder.setAutoCancel(true);
            builder.setSmallIcon(android.R.drawable.ic_media_rew);

            //发送通知
            Notification notification=builder.build();
            notificationManager.notify(0x101,notification);
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
            finish();
        }
    };

}
