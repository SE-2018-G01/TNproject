package com.example.administrator.timenote.Ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.DremindManager.LoadDremind;
import com.example.administrator.timenote.Manager.TaskManager.Complete;
import com.example.administrator.timenote.Manager.TaskManager.LoadAllEvent;
import com.example.administrator.timenote.Manager.TaskManager.LoadAllEventByPro;
import com.example.administrator.timenote.Manager.TaskManager.UnComplete;
import com.example.administrator.timenote.Manager.TaskManager.UpdatePid;
import com.example.administrator.timenote.Manager.TaskManager.UpdatePriority;
import com.example.administrator.timenote.Model.BeanDRemindInformation;
import com.example.administrator.timenote.Model.BeanEventInformation;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.Model.task;
import com.example.administrator.timenote.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.administrator.timenote.Model.BeanEventInformation.allEventList;

public class page2 extends Fragment implements OnDateSelectedListener, AdapterView.OnItemClickListener, ListAdapter.InnerItemOnclickListener{
    private Button calendar_mode; // 日历模式
    private Calendar calendar;// 获取当前时间用
    private LayoutInflater inflater1;
    private ListView listView;// 事务列表
    private Button list1; // 菜单按钮
    private Level_select level_select;// 优先级选择界面
    private MenuItem gMenuItem1, gMenuItem2;// list2的两个按钮
    private Button new_button;// 新建事务
    private static boolean stase1 = true, stase2 = false;// 列表显示状态
    private Button taday_1;// 回到今天按钮
    private MaterialCalendarView widget;// 日历视图接口
    private Task_Update task_update;// 修改事务
    private int position;
    private int ringpid;
    private int eventidget;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater1 = inflater;
        View view = inflater.inflate(R.layout.page2, container, false);
        widget=view.findViewById(R.id.calendarView);
        calendar_mode=view.findViewById(R.id.calendar_mode);
        new_button=view.findViewById(R.id.new_task1);
        listView = (ListView) view.findViewById(R.id.list_view_2);

        //日历设置为今天
        calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());


        //初始化事务列表适配器

        inittask(calendar.getTime());

        //顶层菜单栏
        //日历视图模式转换
        calendar_mode.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void onClick(View view) {
                    if(calendar_mode.getText().equals("月视图")) {
                        widget.state().edit()
//                        .setFirstDayOfWeek(Calendar.WEDNESDAY)//日历开始于周三
//                        .setMinimumDate(CalendarDay.from(2016, 4, 3))//日历开始
//                        .setMaximumDate(CalendarDay.from(2016, 5, 12))//日历结束
                                .setCalendarDisplayMode(CalendarMode.WEEKS)
                                .commit();
                        calendar_mode.setText("周视图");
                    }
                    else{
                        widget.state().edit()
                                .setCalendarDisplayMode(CalendarMode.MONTHS)
                                .commit();
                        calendar_mode.setText("月视图");

                    }

                }
            });

       widget.setOnDateChangedListener(this);

       //回到今天按钮
        taday_1=view.findViewById(R.id.taday_2);
        taday_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                widget.setSelectedDate(calendar.getTime());
                if(calendar_mode.getText().equals("周视图")) {
                    widget.state().edit()
                            .setCalendarDisplayMode(CalendarMode.WEEKS)
                            .commit();
                    calendar_mode.setText("周视图");
                    inittask(calendar.getTime());
                }
                else{
                    widget.state().edit()
                            .setCalendarDisplayMode(CalendarMode.MONTHS)
                            .commit();
                    calendar_mode.setText("月视图");
                    inittask(calendar.getTime());
                }
                //更新事务列表
            }
        });

        //模式菜单按钮
        list1 = view.findViewById(R.id.list_spot_2);
        list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(list1);
            }
        });

        //新建事务逻辑
        new_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 final New_task new_task= new New_task(inflater.getContext(), R.style.dialog, calendar);
                 new_task.show();
                new_task.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (new_task.issure()) {
                            page1.initReflesh();
                            inittask(calendar.getTime());
                            if (new_task.getdate != null) {
                                Date date = null;
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                try {
                                    date = formatter.parse(new_task.getdate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                int eventid = 0;
                                for (int i = 0; i < BeanEventInformation.allEventList.size(); i++) {
                                    if (eventid < BeanEventInformation.allEventList.get(i).getEventid())
                                        eventid = BeanEventInformation.allEventList.get(i).getEventid();
                                }

                                setAlarm(date, BeanDRemindInformation.defaultset.getDremindring(),
                                        Boolean.valueOf(BeanDRemindInformation.defaultset.getDremindvib()),
                                        BeanDRemindInformation.defaultset.getAheadtime(),
                                        BeanDRemindInformation.defaultset.getDremindrepeat(),
                                        eventid, new_task.scode, new_task.getdate);
                            }
                        }

                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        if(date == null) {
            //默认跳到今天
            calendar = Calendar.getInstance();
            widget.setSelectedDate(calendar.getTime());
            //更新任务列表
            page1.initReflesh();
            inittask(calendar.getTime());
        }
        else {
            //更新任务列表
            page1.initReflesh();
            calendar.setTime(date.getDate());
            inittask(date.getDate());
        }
    }
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this.inflater1.getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.showstyle, popupMenu.getMenu());
        gMenuItem1 = popupMenu.getMenu().findItem(R.id.complete_1);
        if (stase1 == true) {
            gMenuItem1.setTitle("显示已完成");
        }
        gMenuItem2 = popupMenu.getMenu().findItem(R.id.priorit_1);
        if (stase2 == false) {
            gMenuItem2.setTitle("按优先级排序");
        }
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.complete_1:
                        if (gMenuItem1.getTitle().equals("显示已完成")) {
                            stase1 = false;
                            //修改状态重新加载事务列表
                            inittask(calendar.getTime());
                        } else {
                            stase1 = true;
                            //修改状态重新加载事务列表
                            inittask(calendar.getTime());
                        }
                        break;
                    case R.id.priorit_1:
                        if (gMenuItem2.getTitle().equals("按时间排序")) {
                            stase2 = false;
                            //修改状态重新加载事务列表

                            inittask(calendar.getTime());
                        } else {
                            stase2 = true;
                            //修改状态重新加载事务列表

                            inittask(calendar.getTime());
                        }
                        break;
                    default:
                }
                return true;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getContext(),"列表 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClick(View v) {
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.taskdes:
            case R.id.data_1:
            case R.id.taskname:

                task_update = new Task_Update(getContext(),R.style.dialog1,
                        MyCustomAdapter.getData().get(position).getEventid(),
                        MyCustomAdapter.getData().get(position).getEventname(),
                        MyCustomAdapter.getData().get(position).getEventnote(),
                        MyCustomAdapter.getData().get(position).getLeaveseventsign(),
                        MyCustomAdapter.getData().get(position).getEventdate());
                task_update.show();
                task_update.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (task_update.issure())
                        {
                            int eventid = MyCustomAdapter.getData().get(position).getEventid();
                            page1.initReflesh();
                            inittask(calendar.getTime());
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    LoadDremind loadDremind = new LoadDremind();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    BeanDRemindInformation.tsset = loadDremind.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),MyCustomAdapter.getData().get(position).getEventid());
                                }
                            });
                            t.start();
                            try {
                                t.join(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            int pos = 0;
                            for (int i = 0; i < allEventList.size(); i++){
                                if (allEventList.get(i).getEventid() == eventid){
                                    pos = i;
                                    break;
                                }
                            }
                            if ( BeanDRemindInformation.tsset != null) {
                                updateAlarm(allEventList.get(pos).getEventdate(),
                                        BeanDRemindInformation.defaultset.getDremindring(),
                                        Boolean.valueOf(BeanDRemindInformation.tsset.getDremindvib()),
                                        BeanDRemindInformation.tsset.getAheadtime(),
                                        BeanDRemindInformation.tsset.getDremindrepeat(),
                                        eventid,
                                        allEventList.get(pos).getEventname(),
                                        allEventList.get(pos).getPid(),
                                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(allEventList.get(pos).getEventdate()));

                            }
                            else {
                                updateAlarm(allEventList.get(pos).getEventdate(),
                                        BeanDRemindInformation.defaultset.getDremindring(),
                                        Boolean.valueOf(BeanDRemindInformation.defaultset.getDremindvib()),
                                        BeanDRemindInformation.defaultset.getAheadtime(),
                                        BeanDRemindInformation.defaultset.getDremindrepeat(),
                                        eventid,
                                        allEventList.get(pos).getEventname(),
                                        allEventList.get(pos).getPid(),
                                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(allEventList.get(pos).getEventdate()));
                            }
                        }
                    }
                });
                //Toast.makeText(getContext(), "an", Toast.LENGTH_SHORT).show();
                break;
            // 修改优先级
            case R.id.image_level:
                level_select=new Level_select(getContext(),R.style.dialog);
                level_select.show();
                // 监听弹框消失
                level_select.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                UpdatePriority updatePriority = new UpdatePriority();
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                updatePriority.getRemoteInfo(MyCustomAdapter2.getData().get(position).getEventid(), Level_select.getLevel());
                            }
                        });
                        t.start();
                        try {
                            t.join(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 刷新
                        inittask(calendar.getTime());
                    }
                });
                break;
            case R.id.checkBox:
                // 设置已完成
                if(MyCustomAdapter.getData().get(position).getCheckBox()==1)
                {
                    // TODO: 2018/6/17   修改为未完成
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            UnComplete unComplete = new UnComplete();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            unComplete.getRemoteInfo(MyCustomAdapter.getData().get(position).getEventid());
                            int i = 0;
                            for (; i < allEventList.size(); i++){
                                if (allEventList.get(i).getEventid() == MyCustomAdapter.getData().get(position).getEventid())
                                    break;
                            }
                            allEventList.get(i).setCheckBox(0);

                        }
                    });
                    t.start();
                    try {
                        t.join(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 刷新
                    page1.initReflesh();
                    inittask(calendar.getTime());
                }
                else{
                    // TODO: 2018/6/17   修改为已完成
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
                            complete.getRemoteInfo(MyCustomAdapter.getData().get(position).getEventid());
                            int i = 0;
                            for (; i < allEventList.size(); i++){
                                if (allEventList.get(i).getEventid() == MyCustomAdapter.getData().get(position).getEventid())
                                    break;
                            }
                            allEventList.get(i).setCheckBox(1);

                        }
                    });
                    t.start();
                    try {
                        t.join(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    page1.initReflesh();
                    inittask(calendar.getTime());
                }
            default:
                break;
        }
    }

    // 列表装配
    private void inittask(Date date)
    {
        MyCustomAdapter2 adapter = new MyCustomAdapter2(this.inflater1.getContext());

        java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String datetype = formatter.format(date);//格式化数据

        int j = 0;
        if(j<allEventList.size()) {
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar1.setTime(date);
            calendar2.setTime(allEventList.get(j).getEventdate());

            listView.setAdapter(adapter);
            int index = 0;
            for (int i = 0; i < allEventList.size(); i++) {
                calendar2.setTime(allEventList.get(i).getEventdate());
                if (!((calendar1.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)) && (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) && (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))))
                    continue;
                if (stase1 && allEventList.get(i).getCheckBox() == 1)
                    continue;
                index++;
            }
            if (index != 0) {
                adapter.addSeparatorItem(new BeanEventInformation(datetype));
            }

            if (stase2) {
                List<BeanEventInformation> allEventBypro = new ArrayList<>();
                for (int n = 0; n < allEventList.size(); n++) {
                    calendar2.setTime(allEventList.get(n).getEventdate());
                    if ((calendar1.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)) && (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) && (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)))
                        allEventBypro.add(allEventList.get(n));
                }
                for (int l = 1; l < allEventBypro.size(); l++) {
                    BeanEventInformation eventchange;
                    int priority = allEventBypro.get(l - 1).getEventpriority();
                    int m = l - 1;
                    int k = l;
                    for (; k < allEventBypro.size(); k++) {
                        if (priority < allEventBypro.get(k).getEventpriority()) {
                            priority = allEventBypro.get(k).getEventpriority();
                            m = k;
                        }
                    }
                    eventchange = allEventBypro.get(l - 1);
                    allEventBypro.set(l - 1, allEventBypro.get(m));
                    allEventBypro.set(m, eventchange);
                }
                for (int k = 0; k < allEventBypro.size(); k++) {
                    if (stase1 && allEventBypro.get(k).getCheckBox() == 1)
                        continue;
                    adapter.addItem(allEventBypro.get(k));
                }
            } else {
                for (; j < allEventList.size(); j++) {
                    if (stase1 && allEventList.get(j).getCheckBox() == 1)
                        continue;
                    calendar2.setTime(allEventList.get(j).getEventdate());
                    if ((calendar1.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)) && (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) && (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)))
                        adapter.addItem(allEventList.get(j));
                }
            }
        }

        adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm(Date date, int ring, Boolean vib, long ahead, int repeat, int eventid, String eventname, String eventdate){
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTimeInMillis() > date.getTime()){
            return;
        }
        eventidget = eventid;
        calendar.setTime(date);
        long mils = 0;
        switch (BeanDRemindInformation.tsset.getAheadtime()) {
            case 0:
                break;
            case 1:
                mils = 1000 * 60 * 5;
                break;
            case 2:
                mils = 1000 * 60 * 30;
                break;
            case 3:
                mils = 1000 * 60 * 60;
                break;
            case 4:
                mils = 1000 * 60 * 60 * 24;
                break;
        }
        //跳转
        Intent intent=new Intent();
        ringpid = UUID.randomUUID().hashCode();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                UpdatePid updatePid = new UpdatePid();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updatePid.getRemoteInfo(eventidget, ringpid);
            }
        });
        t.start();
        try {
            t.join(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intent.setAction("com.liuqian.android_27alarm.RING");
        intent.putExtra("id", ringpid);
        intent.putExtra("ring", ring);
        intent.putExtra("vib", vib);
        intent.putExtra("repeat", repeat);
        intent.putExtra("eventid", eventid);
        intent.putExtra("eventname", eventname);
        intent.putExtra("eventdate", eventdate);
        //PendingIntent
        PendingIntent pendingIntent= PendingIntent.getBroadcast(getContext(),ringpid,intent,0);
        //闹钟管理者 参数： 唤醒屏幕，
        Calendar_View.alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - mils, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateAlarm(Date date, int ring, Boolean vib, int ahead, int repeat, int eventid, String eventname, int pid, String eventdate){
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTimeInMillis() > date.getTime()){
            return;
        }
        eventidget = eventid;
        calendar.setTime(date);
        long mils = 0;
        switch (ahead){
            case 0:break;
            case 1:mils = 1000*60*5;break;
            case 2:mils = 1000*60*30;break;
            case 3:mils = 1000*60*60;break;
            case 4:mils = 1000*60*60*24;break;
        }
        //跳转
        Intent intent=new Intent();
        if (pid == -99){
            ringpid = UUID.randomUUID().hashCode();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    UpdatePid updatePid = new UpdatePid();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updatePid.getRemoteInfo(eventidget, ringpid);
                }
            });
            t.start();
            try {
                t.join(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
            ringpid = pid;
        intent.setAction("com.liuqian.android_27alarm.RING");
        intent.putExtra("id", ringpid);
        intent.putExtra("ring", ring);
        intent.putExtra("vib", vib);
        intent.putExtra("ahead", ahead);
        intent.putExtra("repeat", repeat);
        intent.putExtra("eventid", eventid);
        intent.putExtra("eventname", eventname);
        intent.putExtra("eventdate", eventdate);
        //PendingIntent
        PendingIntent pendingIntent= PendingIntent.getBroadcast(getContext(),ringpid,intent,0);
        //闹钟管理者 参数： 唤醒屏幕，
        Calendar_View.alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - mils, pendingIntent);
    }

}