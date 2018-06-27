package com.example.administrator.timenote.Ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.DremindManager.LoadDremind;
import com.example.administrator.timenote.Manager.ListManager.LoadAllList;
import com.example.administrator.timenote.Manager.TaskManager.Complete;
import com.example.administrator.timenote.Manager.TaskManager.LoadAllEvent;
import com.example.administrator.timenote.Manager.TaskManager.LoadAllEventByPro;
import com.example.administrator.timenote.Manager.TaskManager.UnComplete;
import com.example.administrator.timenote.Manager.TaskManager.UpdatePid;
import com.example.administrator.timenote.Manager.TaskManager.UpdatePriority;
import com.example.administrator.timenote.Manager.UserManager.GetHead;
import com.example.administrator.timenote.Manager.UserManager.UserLogin;
import com.example.administrator.timenote.Model.BeanDRemindInformation;
import com.example.administrator.timenote.Model.BeanEventInformation;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.Model.List_menu;
import com.example.administrator.timenote.Model.task;
import com.example.administrator.timenote.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.administrator.timenote.Model.BeanEventInformation.allEventList;
import static com.example.administrator.timenote.Model.BeanListInformation.allList;
import static com.example.administrator.timenote.Model.BeanUserInformation.tryLoginUser;

public class page1  extends Fragment implements AdapterView.OnItemClickListener, ListAdapter.InnerItemOnclickListener{

    public static DrawerLayout drawerLayout;// 侧滑菜单
    private MenuItem gMenuItem1, gMenuItem2;// list1的两个按钮
    private LayoutInflater inflater;// 接口
    private Button list1, taday_1, all_1, list2;// 任务列表（page1）的按钮从右到左
    private TextView list_name_1;// 清单名称
    private ListView listView;// 事务列表
    private ListView list_menu;// 清单列表
    public static List_Update list_update;
    private List<List_menu> list;
    private Level_select level_select;// 优先级选择界面
    private New_List new_list;// 新建清单
    public static NavigationView navigationView;
    private Button new_button;// 新建事务
    public static ImageView person;// 侧滑菜单头像
    private int position; // 点击位置
    private TextView name;// 侧滑菜单名字
    private static boolean stase1 = true, stase2 = false, stase3 = true;// 列表显示状态
    private int ringpid;
    private Task_Update task_update;
    public static Handler handler1;
    private int eventidget;


    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.page1, container, false);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.page1);
        navigationView = (NavigationView)view.findViewById(R.id.nav);
        list_menu = view.findViewById(R.id.list_menu_list);
        inindrawer();
        person = view.findViewById(R.id.person_head);
        name = view.findViewById(R.id.name);
//        View headerView = navigationView .inflateHeaderView()getHeaderView(R.layout.head);// 获取头布局
        new_button=view.findViewById(R.id.new_task2);

        // 载入默认提醒设置
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
                BeanDRemindInformation.tsset = loadDremind.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),-99);
                BeanDRemindInformation.defaultset = BeanDRemindInformation.tsset;
            }
        });
        t.start();
        try {
            t.join(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 侧滑菜单
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); // 禁用手势侧滑操作
        list2 = view.findViewById(R.id.list_1);
        list2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.list_1:// 点击菜单，跳出侧滑菜单
                        if (drawerLayout.isDrawerOpen(navigationView)){
                            drawerLayout.closeDrawer(navigationView);
                        }else{
                            drawerLayout.openDrawer(navigationView);
                        }
                        break;
                }
            }
        });
        // 显示用户名
        if(BeanUserInformation.currentLoginUser.getIcon()!=null)
        {
            Bitmap bm;
            byte[] bitmapByte = BeanUserInformation.currentLoginUser.getIcon();
            if (bitmapByte.length != 0) {
                bm = BitmapFactory.decodeByteArray(bitmapByte, 0,bitmapByte.length);
                person.setImageBitmap(bm);
            } else {

            }
        }
        else{
            Thread r = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    GetHead getHead = new GetHead();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getHead.getRemoteInfo();

                }
            });
            r.start();
            try {
                r.join(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(BeanUserInformation.currentLoginUser.getIcon()!=null){
                Bitmap bm;
                byte[] bitmapByte = BeanUserInformation.currentLoginUser.getIcon();
                if (bitmapByte.length != 0) {
                    bm = BitmapFactory.decodeByteArray(bitmapByte, 0,bitmapByte.length);
                    person.setImageBitmap(bm);
                }
            }
        }
        name.setText(BeanUserInformation.currentLoginUser.getUsername());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                //item.setChecked(true);
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
        //page1按钮定义

        //顶层菜单栏
        list1 = view.findViewById(R.id.list_spot_1);
        taday_1 = view.findViewById(R.id.taday_1);
        all_1 = view.findViewById(R.id.all_1);

        list_name_1 = view.findViewById(R.id.list_name_1);
        //显示状态子菜单
        list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(list1);
            }
        });

        //回到今天按钮
        taday_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //事务列表加载到今天
                list_name_1.setText("今天");
                stase3 = false;
                initReflesh();
                inittoday();
            }
        });

        //回到所有按钮
        all_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //事务列表加载到所有
                list_name_1.setText("所有");
                stase3 = true;
                initReflesh();
                initadapter();
            }
        });

        //打开清单菜单

        initReflesh(); // 重新从数据库读取

        listView = (ListView) view.findViewById(R.id.list_view);
        initadapter();


        //新建事务逻辑
        new_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final New_task new_task= new New_task(inflater.getContext(),R.style.dialog);
                new_task.show();
                new_task.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (new_task.issure()) {
                            initReflesh();
                            if(stase3){
                                initadapter();
                            }
                            else {
                                inittoday();
                            }
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

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this.inflater.getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.showstyle, popupMenu.getMenu());
        gMenuItem1 = popupMenu.getMenu().findItem(R.id.complete_1);
        if (stase1) {
            gMenuItem1.setTitle("显示已完成");
        }
        gMenuItem2 = popupMenu.getMenu().findItem(R.id.priorit_1);
        if (!stase2) {
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
                            if(stase3){
                                initadapter();
                            }
                            else {
                                inittoday();
                            }
                        } else {
                            //修改状态重新加载事务列表
                            stase1 = true;
                            if(stase3){
                                initadapter();
                            }
                            else {
                                inittoday();
                            }
                        }
                        break;
                    case R.id.priorit_1:
                        if (gMenuItem2.getTitle().equals("按时间排序")) {
                            stase2 = false;
                            //修改状态重新加载事务列表
                            initadapter();
                        } else {
                            //修改状态重新加载事务列表
                            stase2 = true;
                            initadapter();
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
        position = (Integer) v.getTag() ;
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
                            initReflesh();
                            if(stase3){
                                initadapter();
                            }
                            else {
                                inittoday();
                            }

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

                break;
            // 修改优先级
            case R.id.image_level:
                level_select=new Level_select(getContext(),R.style.dialog);
                level_select.show();
                // 监听弹框消失
                level_select.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if ((Level_select.getLevel() != -1) && (Level_select.getLevel() != MyCustomAdapter.getData().get(position).getEventpriority())){
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
                                    updatePriority.getRemoteInfo(MyCustomAdapter.getData().get(position).getEventid(), Level_select.getLevel());
                                }
                            });
                            t.start();
                            try {
                                t.join(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            initReflesh();
                            if(stase3){
                                initadapter();
                            }
                            else {
                                inittoday();
                            }
                        }
                    }
                });
                break;
            // 修改完成状态
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
                    initReflesh();
                    if(stase3){
                        initadapter();
                    }
                    else {
                        inittoday();
                    }
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
                    initReflesh();
                    if(stase3){
                        initadapter();
                    }
                    else {
                        inittoday();
                    }
                }
                break;

            case R.id.list_menu_name:
            case R.id.imageView4:
                if (drawerLayout.isDrawerOpen(navigationView))
                drawerLayout.closeDrawer(navigationView);
                if (position == 0) {
                    list_name_1.setText("所有");
                    initadapter();
                }
                else if (position == 1)
                    inittoday();
                else if (position == 2)
                    initrecentlythree();
                else if ((position > 2) && (position < (allList.size() + 3)))
                    initlistinfo();

                String name = list.get(position).getList_menu_name();
                if(name.equals("新建清单"))
                {
                    new_list = new New_List(getContext(),R.style.dialog);
                    new_list.show();
                    new_list.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        public void onDismiss(DialogInterface dialog) {
                            if (new_list.getIssue()) {
                                inindrawer();
                                Toast.makeText(getContext(), new_list.getList_name(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else if(name.equals("设置清单")){
                    list_update = new List_Update(getContext(),R.style.dialog1);
                    list_update.show();
                    list_update.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            inindrawer();
                        }
                    });
                }
//                else {
//                    inindrawer();
//                    if(stase3){
//                        initadapter();
//                    }
//                    else {
//                        inittoday();
//                    }
//                }

                break;
            default:
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm(Date date, int ring, Boolean vib, int ahead, int repeat, int eventid, String eventname, String eventdate){
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

        private void inindrawer(){
        list = new ArrayList<>();
        Bitmap bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.menu_list);
        List_menu list_menu_item = new List_menu(bmp,"所有");
        list.add(list_menu_item);
        list_menu_item = new List_menu(bmp,"今天");
        list.add(list_menu_item);
        list_menu_item = new List_menu(bmp,"近三天");
        list.add(list_menu_item);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                LoadAllList loadAllList = new LoadAllList();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadAllList.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid());
            }
        });
        t.start();
        try {
            t.join(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.list_1);
        for(int i=0 ; i < allList.size(); i++){
            list_menu_item = new List_menu(bmp,allList.get(i).getListname());
            list.add(list_menu_item);
        }

        bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.plus);
        list_menu_item = new List_menu(bmp,"新建清单");
        list.add(list_menu_item);
        bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.setup1);
        list_menu_item = new List_menu(bmp,"设置清单");
        list.add(list_menu_item);

        List_menu_Adapter list_menu_adapter = new List_menu_Adapter(this.getContext(),R.layout.list_menu_button,list);
        list_menu.setAdapter(list_menu_adapter);
        list_menu_adapter.setOnInnerItemOnClickListener(this);
        list_menu.setOnItemClickListener(this);
    }

    // 装载今天
    private void inittoday(){
        MyCustomAdapter adapter = new MyCustomAdapter(this.inflater.getContext());
        listView.setAdapter(adapter);

        int j = 0;
        if(j<allEventList.size()) {
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(allEventList.get(j).getEventdate());



            //adapter.addSeparatorItem(new BeanEventInformation("今天"));
            list_name_1.setText("今天");

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

    // 装载近三天
    private void initrecentlythree(){
        MyCustomAdapter adapter = new MyCustomAdapter(this.inflater.getContext());
        listView.setAdapter(adapter);

        //adapter.addSeparatorItem(new BeanEventInformation("最近三天"));
        list_name_1.setText("最近三天");

        long millis;
        if (stase2 == true){
            List<BeanEventInformation> allEventBypro = new ArrayList<>();
            for (int n = 0; n<allEventList.size(); n++){
                millis = allEventList.get(n).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                if ((millis >= 0) && (millis <= 3 * (1000 * 60 * 60 * 24))  && (!allEventList.get(n).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1")))
                    allEventBypro.add(allEventList.get(n));
            }
            for (int l = 1; l < allEventBypro.size(); l++) {
                BeanEventInformation eventchange;
                int priority = allEventBypro.get(l-1).getEventpriority();
                int m = l - 1;
                int k = l;
                for (; k < allEventBypro.size(); k++) {
                    if (priority < allEventBypro.get(k).getEventpriority()) {
                        priority = allEventBypro.get(k).getEventpriority();
                        m = k;
                    }
                }
                eventchange = allEventBypro.get(l-1);
                allEventBypro.set(l-1, allEventBypro.get(m));
                allEventBypro.set(m, eventchange);
            }
            for(int k = 0; k < allEventBypro.size(); k++){
                if(stase1 == true && allEventBypro.get(k).getCheckBox() == 1)
                    continue;
                adapter.addItem(allEventBypro.get(k));
            }
        }
        else {
            for (int i = 0; i < allEventList.size(); i++) {
                millis = allEventList.get(i).getEventdate().getTime() - new Date(System.currentTimeMillis()).getTime();
                if ((millis >= 0) && (millis <= 3 * (1000 * 60 * 60 * 24))) {
                    if (stase1 == true && allEventList.get(i).getCheckBox() == 1) continue;
                    adapter.addItem(allEventList.get(i));
                }

            }
        }

        adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    // 装载指定清单列表
    private void initlistinfo(){
        MyCustomAdapter adapter = new MyCustomAdapter(this.inflater.getContext());
        listView.setAdapter(adapter);

       // adapter.addSeparatorItem(new BeanEventInformation(allList.get(position - 3).getListname());
        list_name_1.setText(allList.get(position - 3).getListname());

        if (stase2){
            List<BeanEventInformation> allEventBypro = new ArrayList<>();
            for (int n = 0; n<allEventList.size(); n++){
                if (position - 3 == allEventList.get(n).getListid())
                    allEventBypro.add(allEventList.get(n));
            }
            for (int l = 1; l < allEventBypro.size(); l++) {
                BeanEventInformation eventchange;
                int priority = allEventBypro.get(l-1).getEventpriority();
                int m = l - 1;
                int k = l;
                for (; k < allEventBypro.size(); k++) {
                    if (priority < allEventBypro.get(k).getEventpriority()) {
                        priority = allEventBypro.get(k).getEventpriority();
                        m = k;
                    }
                }
                eventchange = allEventBypro.get(l-1);
                allEventBypro.set(l-1, allEventBypro.get(m));
                allEventBypro.set(m, eventchange);
            }
            for(int k = 0; k < allEventBypro.size(); k++){
                if(stase1 && allEventBypro.get(k).getCheckBox() == 1)
                    continue;
                adapter.addItem(allEventBypro.get(k));
            }
        }
        else {
            for (int i = 0; i < allEventList.size(); i++) {
                if ((allList.get(position - 3).getListid()) == allEventList.get(i).getListid()) {
                    if (stase1 && allEventList.get(i).getCheckBox() == 1) continue;
                    adapter.addItem(allEventList.get(i));
                }

            }
        }

        adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    // 装载所有
    public void initadapter(){
        MyCustomAdapter adapter = new MyCustomAdapter(this.inflater.getContext());
        listView.setAdapter(adapter);


        int i = 0;
        int j = 0;
        if(i < allEventList.size()){
            if (allEventList.get(i).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1")) {
                int index = 0;
                for (; j< allEventList.size();j++) {
                    if (!allEventList.get(j).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1"))
                        break;
                    if(stase1 == true && allEventList.get(j).getCheckBox() == 1)
                        continue;
                    index++;
                }
                if (index != 0){
                    adapter.addSeparatorItem(new BeanEventInformation("未设置时间"));
                }

                if (stase2 == true){
                    List<BeanEventInformation> allEventBypro = new ArrayList<>();
                    for (int n = 0; n<allEventList.size(); n++){
                        if (allEventList.get(n).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1"))
                            allEventBypro.add(allEventList.get(n));
                    }
                    for (int l = 1; l < allEventBypro.size(); l++) {
                        BeanEventInformation eventchange;
                        int priority = allEventBypro.get(l-1).getEventpriority();
                        int m = l - 1;
                        int k = l;
                        for (; k < allEventBypro.size(); k++) {
                            if (priority < allEventBypro.get(k).getEventpriority()) {
                                priority = allEventBypro.get(k).getEventpriority();
                                m = k;
                            }
                        }
                        eventchange = allEventBypro.get(l-1);
                        allEventBypro.set(l-1, allEventBypro.get(m));
                        allEventBypro.set(m, eventchange);
                    }
                    for(int k = 0; k < allEventBypro.size(); k++){
                        if(stase1 == true && allEventBypro.get(k).getCheckBox() == 1)
                            continue;
                        adapter.addItem(allEventBypro.get(k));
                    }
                    for (; i < allEventList.size(); i++) {
                        if (!allEventList.get(i).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1"))
                            break;
                    }
                }
                else{
                    for (; i < allEventList.size(); i++) {
                        if (!allEventList.get(i).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1"))
                            break;
                        if(stase1 == true && allEventList.get(i).getCheckBox() == 1)
                            continue;
                        adapter.addItem(allEventList.get(i));
                    }
                }

            }
        }

        long millis;
        if(i < allEventList.size()){
            millis = allEventList.get(i).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
            if (millis < 0) {
                int index = 0;
                for (; j< allEventList.size();j++) {
                    millis = allEventList.get(j).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                    if (millis >= 0)
                        break;
                    if(stase1 == true && allEventList.get(j).getCheckBox() == 1)
                        continue;
                    index++;
                }
                if (index != 0) {
                    adapter.addSeparatorItem(new BeanEventInformation("过去"));
                }

                if (stase2 == true){
                    List<BeanEventInformation> allEventBypro = new ArrayList<>();
                    for (int n = 0; n<allEventList.size(); n++){
                        millis = allEventList.get(n).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                        if ((millis < 0) && (!allEventList.get(n).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1")))
                            allEventBypro.add(allEventList.get(n));
                    }
                    for (int l = 1; l < allEventBypro.size(); l++) {
                        BeanEventInformation eventchange;
                        int priority = allEventBypro.get(l-1).getEventpriority();
                        int m = l - 1;
                        int k = l;
                        for (; k < allEventBypro.size(); k++) {
                            if (priority < allEventBypro.get(k).getEventpriority()) {
                                priority = allEventBypro.get(k).getEventpriority();
                                m = k;
                            }
                        }
                        eventchange = allEventBypro.get(l-1);
                        allEventBypro.set(l-1, allEventBypro.get(m));
                        allEventBypro.set(m, eventchange);
                    }
                    for(int k = 0; k < allEventBypro.size(); k++){
                        if(stase1 == true && allEventBypro.get(k).getCheckBox() == 1)
                            continue;
                        adapter.addItem(allEventBypro.get(k));
                    }
                    for (; i< allEventList.size();i++) {
                        millis = allEventList.get(i).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                        if (millis >= 0)
                            break;
                    }
                }
                else {
                    for (; i< allEventList.size();i++) {
                        millis = allEventList.get(i).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                        if (millis >= 0)
                            break;
                        if(stase1 == true && allEventList.get(i).getCheckBox() == 1)
                            continue;
                        adapter.addItem(allEventList.get(i));
                    }
                }

            }
        }

        if(i<allEventList.size()){
            millis = allEventList.get(i).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
            if ((millis >= 0) && (millis <= (1000 * 60 * 60 * 24))) {
                int index = 0;
                for (; j< allEventList.size();j++) {
                    millis = allEventList.get(j).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                    if (millis > (1000 * 60 * 60 * 24))
                        break;
                    if(stase1 == true && allEventList.get(j).getCheckBox() == 1)
                        continue;
                    index++;
                }
                if (index != 0) {
                    adapter.addSeparatorItem(new BeanEventInformation("最近一天"));
                }

                if (stase2 == true){
                    List<BeanEventInformation> allEventBypro = new ArrayList<>();
                    for (int n = 0; n<allEventList.size(); n++){
                        millis = allEventList.get(n).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                        if ((millis >= 0) && (millis <= (1000 * 60 * 60 * 24)) && (!allEventList.get(n).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1")))
                            allEventBypro.add(allEventList.get(n));
                    }
                    for (int l = 1; l < allEventBypro.size(); l++) {
                        BeanEventInformation eventchange;
                        int priority = allEventBypro.get(l-1).getEventpriority();
                        int m = l - 1;
                        int k = l;
                        for (; k < allEventBypro.size(); k++) {
                            if (priority < allEventBypro.get(k).getEventpriority()) {
                                priority = allEventBypro.get(k).getEventpriority();
                                m = k;
                            }
                        }
                        eventchange = allEventBypro.get(l-1);
                        allEventBypro.set(l-1, allEventBypro.get(m));
                        allEventBypro.set(m, eventchange);
                    }
                    for(int k = 0; k < allEventBypro.size(); k++){
                        if(stase1 == true && allEventBypro.get(k).getCheckBox() == 1)
                            continue;
                        adapter.addItem(allEventBypro.get(k));
                    }
                    for (; i < allEventList.size(); i++) {
                        millis = allEventList.get(i).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                        if (millis > (1000 * 60 * 60 * 24))
                            break;
                    }
                }
                else {
                    for (; i < allEventList.size(); i++) {
                        millis = allEventList.get(i).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                        if (millis > (1000 * 60 * 60 * 24))
                            break;
                        if (stase1 == true && allEventList.get(i).getCheckBox() == 1)
                            continue;
                        adapter.addItem(allEventList.get(i));
                    }
                }
            }
        }

        if(i<allEventList.size()){
            millis = allEventList.get(i).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
            if ((millis > (1000 * 60 * 60 * 24)) && (millis <= 7 * (1000 * 60 * 60 * 24))) {
                int index = 0;
                for (; j< allEventList.size();j++) {
                    millis = allEventList.get(j).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                    if (millis > 7 * (1000 * 60 * 60 * 24))
                        break;
                    if(stase1 == true && allEventList.get(j).getCheckBox() == 1)
                        continue;
                    index++;
                }
                if (index != 0) {
                    adapter.addSeparatorItem(new BeanEventInformation("最近七天"));
                }

                if (stase2 == true){
                    List<BeanEventInformation> allEventBypro = new ArrayList<>();
                    for (int n = 0; n<allEventList.size(); n++){
                        millis = allEventList.get(n).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                        if ((millis > (1000 * 60 * 60 * 24)) && (millis <= 7 * (1000 * 60 * 60 * 24))  && (!allEventList.get(n).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1")))
                            allEventBypro.add(allEventList.get(n));
                    }
                    for (int l = 1; l < allEventBypro.size(); l++) {
                        BeanEventInformation eventchange;
                        int priority = allEventBypro.get(l-1).getEventpriority();
                        int m = l - 1;
                        int k = l;
                        for (; k < allEventBypro.size(); k++) {
                            if (priority < allEventBypro.get(k).getEventpriority()) {
                                priority = allEventBypro.get(k).getEventpriority();
                                m = k;
                            }
                        }
                        eventchange = allEventBypro.get(l-1);
                        allEventBypro.set(l-1, allEventBypro.get(m));
                        allEventBypro.set(m, eventchange);
                    }
                    for(int k = 0; k < allEventBypro.size(); k++){
                        if(stase1 == true && allEventBypro.get(k).getCheckBox() == 1)
                            continue;
                        adapter.addItem(allEventBypro.get(k));
                    }
                    for (; i < allEventList.size(); i++) {
                        millis = allEventList.get(i).getEventdate().getTime() - new Date(System.currentTimeMillis()).getTime();
                        if (millis > 7 * (1000 * 60 * 60 * 24)) break;
                    }
                }
                else {
                    for (; i < allEventList.size(); i++) {
                        millis = allEventList.get(i).getEventdate().getTime() - new Date(System.currentTimeMillis()).getTime();
                        if (millis > 7 * (1000 * 60 * 60 * 24)) break;
                        if (stase1 == true && allEventList.get(i).getCheckBox() == 1) continue;
                        adapter.addItem(allEventList.get(i));
                    }
                }
            }
        }

        if(i<allEventList.size()){
            millis = allEventList.get(i).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
            if (millis > 7 * (1000 * 60 * 60 * 24)){
                int index = 0;
                for (; j< allEventList.size();j++) {
                    if(stase1 == true && allEventList.get(j).getCheckBox() == 1)
                        continue;
                    index++;
                }
                if (index != 0) {
                    adapter.addSeparatorItem(new BeanEventInformation("更远"));
                }

                if (stase2 == true){
                    List<BeanEventInformation> allEventBypro = new ArrayList<>();
                    for (int n = 0; n<allEventList.size(); n++){
                        millis = allEventList.get(n).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                        if ((millis > 7 * (1000 * 60 * 60 * 24))  && (!allEventList.get(n).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1")))
                            allEventBypro.add(allEventList.get(n));
                    }
                    for (int l = 1; l < allEventBypro.size(); l++) {
                        BeanEventInformation eventchange;
                        int priority = allEventBypro.get(l-1).getEventpriority();
                        int m = l - 1;
                        int k = l;
                        for (; k < allEventBypro.size(); k++) {
                            if (priority < allEventBypro.get(k).getEventpriority()) {
                                priority = allEventBypro.get(k).getEventpriority();
                                m = k;
                            }
                        }
                        eventchange = allEventBypro.get(l-1);
                        allEventBypro.set(l-1, allEventBypro.get(m));
                        allEventBypro.set(m, eventchange);
                    }
                    for(int k = 0; k < allEventBypro.size(); k++){
                        if(stase1 == true && allEventBypro.get(k).getCheckBox() == 1)
                            continue;
                        adapter.addItem(allEventBypro.get(k));
                    }
                }
                else {
                    for (; i< allEventList.size();i++) {
                        if(stase1 == true && allEventList.get(i).getCheckBox() == 1) continue;
                        adapter.addItem(allEventList.get(i));
                    }
                }

            }
        }

        adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    public static void initReflesh() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                LoadAllEvent loadAllEvent = new LoadAllEvent();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadAllEvent.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid());

            }
        });
        t.start();
        try {
            t.join(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

