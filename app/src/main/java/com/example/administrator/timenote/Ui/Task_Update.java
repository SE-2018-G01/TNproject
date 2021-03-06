package com.example.administrator.timenote.Ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.administrator.timenote.Manager.TaskManager.DeleteEvent;
import com.example.administrator.timenote.Manager.TaskManager.LoadAllEvent;
import com.example.administrator.timenote.Manager.TaskManager.UpdateEvent;
import com.example.administrator.timenote.Model.BeanEventInformation;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Task_Update extends Dialog {

    private Button back;// 返回按钮
    private Context context;
    private MenuItem gMenuItem1, gMenuItem2;// list1的两个按钮
    private String getdate = null;// 获得的时间

    public boolean issure() {
        return issure;
    }

    private boolean issure = false;
    private Button level_update;// 优先级修改
    private Button list_spot_3;// 菜单按钮
    private TextView list_name_task;// 修改清单按钮
    private TimePickerView pvCustomTime,pvTime;// 时间选择
    private Button rering_setup;// 重复设置
    private Button ring_setup;// 提醒时间设置
    private Switch switch2;// 开启叶子计时按钮
    private Button save;// 保存按钮
    private Button time_setup;// 事务时间修改
    private EditText task_update_name;// 事务名称修改
    private EditText task_update_d;// 事务描述修改
    private Calendar selectedDate;

    //private int position = getIntent().getIntExtra("position",0);
    private static int eventid;
    private String oldname;
    private String oldnote;
    private String oldleaveseventsign;
    private Date eventdate;

    private int getpriority = -1;// 获得优先级
    private int getlistid = -1;// 获得清单id
    private int leaveseventsign = -1;// 获得叶子事务选择
    private String geteventname = "";// 获得事务名称
    private String geteventnote = "";// 获得事务备注

    public static int getEventid() {
        return eventid;
    }

    public Task_Update(){
        super(null);
    }

    public Task_Update(Context context) {
        super(context);
        this.context = context;
    }

    public Task_Update(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }
    public Task_Update(Context context, int theme,int eventid,String eventname,String eventnote,String leaveseventsign, Date eventdate) {
        super(context, theme);
        this.context = context;
        this.eventid = eventid;
        this.oldname = eventname;
        this.oldnote = eventnote;
        this.oldleaveseventsign = leaveseventsign;
        this.eventdate = eventdate;
    }

//    @Override
//    public void show() {
//        super.show();
//        /**
//         * 设置宽度全屏，要设置在show的后面
//         */
//        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.gravity = Gravity.BOTTOM;
//        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
//
//        getWindow().setAttributes(layoutParams);
//    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_task__update);

        Window dialogWindow = this.getWindow();

//        WindowManager m = context.getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() ); // 高度设置为屏幕
//        p.width = (int) (d.getWidth() ); // 宽度设置为屏幕
        dialogWindow.setAttributes(p);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //绑定按钮
        back =findViewById(R.id.back_8);
        level_update = findViewById(R.id.level_update);
        list_spot_3 = findViewById(R.id.list_spot_3);
        list_name_task = findViewById(R.id.list_name_task);
        rering_setup = findViewById(R.id.rering_setup);
        ring_setup = findViewById(R.id.ring_setup);
        save = findViewById(R.id.save_update);
        switch2 = findViewById(R.id.switch2);
        time_setup = findViewById(R.id.time_setup);
        task_update_name = findViewById(R.id.task_upda_name);
        task_update_d = findViewById(R.id.task_update_d);

        task_update_name.setText(oldname);
        if(oldnote != null){
            task_update_d.setText(oldnote);
        }

        if (oldleaveseventsign.equals("true"))
            switch2.setChecked(true);
        else
            switch2.setChecked(false);

        // 绑定监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 是否允许设置提醒
//        if (getdate == null){
//            ring_setup.setVisibility(View.INVISIBLE);
//            rering_setup.setVisibility(View.INVISIBLE);
//        }

        // 开启叶子计时
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getContext(),"开启叶子计时",Toast.LENGTH_SHORT).show();
                    leaveseventsign = 1;
                }else {
                    Toast.makeText(getContext(),"关闭叶子计时",Toast.LENGTH_SHORT).show();
                    leaveseventsign = 0;
                }
            }
        });

        // 优先级选择
        level_update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Level_select level_select = new Level_select(getContext(),R.style.dialog);
                level_select.show();
                level_select.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        initlevelupdate();
                    }
                });
            }
        });


        // 显示状态子菜单
        list_spot_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(list_spot_3);
            }
        });

        list_name_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List_select list_select = new List_select(getContext(),R.style.dialog);
                list_select.show();
                list_select.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
            }
        });

        // 提醒重复设置
        rering_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rering_setup rering_setup = new Rering_setup(getContext(),R.style.dialog);
                rering_setup.show();
                rering_setup.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
            }
        });

        // 提醒时间设置
        ring_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ringtime_setup ringtime_setup = new Ringtime_setup(getContext(),R.style.dialog);
                ringtime_setup.show();
                ringtime_setup.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
            }
        });


        // 保存按钮监听
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存信息
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        UpdateEvent updateEvent = new UpdateEvent();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateEvent.getRemoteInfo(eventid,geteventname,getpriority,getlistid,getdate,leaveseventsign,geteventnote);
                    }
                });
                t.start();
                try {
                    t.join(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                issure = true;
                dismiss();
            }
        });

        // 事务时间修改
        time_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCustomTimePicker();
                pvCustomTime.show();
                Toast.makeText(getContext(),getdate,Toast.LENGTH_SHORT).show();
                // 是否允许设置提醒
//                if (getdate != null || !eventdate.toString().equals("Sat Jan 01 00:00:00 GMT+00:00 1")){
//                    ring_setup.setVisibility(View.VISIBLE);
//                    rering_setup.setVisibility(View.VISIBLE);
//                }
            }
        });

        // 事务名称框监听
        task_update_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                }
                else{
                    geteventname = task_update_name.getText().toString();
                }
            }
        });
        // 事务描述框监听
        task_update_d.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                }
                else{
                    geteventnote = task_update_d.getText().toString();
                }
            }
        });
//        this.setCanceledOnTouchOutside(false);
//        this.setCancelable(true);
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.task_up, popupMenu.getMenu());
        gMenuItem1 = popupMenu.getMenu().findItem(R.id.save);
        gMenuItem2 = popupMenu.getMenu().findItem(R.id.delete);
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save:
                        Toast.makeText(getContext(),"保存",Toast.LENGTH_SHORT).show();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                UpdateEvent updateEvent = new UpdateEvent();
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                updateEvent.getRemoteInfo(eventid,geteventname,getpriority,getlistid,getdate,leaveseventsign,geteventnote);
                            }
                        });
                        t.start();
                        try {
                            t.join(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        issure = true;

//                        Thread u = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // TODO Auto-generated method stub
//                                for (int i = 0; i < 10; i++) {
//                                    Message msg = Message.obtain();
//                                    msg.what = 1;
//                                    msg.obj = System.currentTimeMillis() + "";
//                                    page1.handler1.sendMessage(msg);
//                                    Log.i("MThread", Thread.currentThread().getName() + "----发送了消息！" + msg.obj);
//                                    SystemClock.sleep(1000);
//                                }
//                            }
//                        });
//                        u.start();
//                        try {
//                            u.join(30000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        dismiss();
                        break;
                    case R.id.delete:
                        Toast.makeText(getContext(),"删除",Toast.LENGTH_SHORT).show();
//                        final AlertDialog.Builder alterDialog = new AlertDialog.Builder(getContext());
//                        alterDialog.setTitle("提示！");
//                        alterDialog.setTitleColor(Color.parseColor("#00c8aa"));
//                        alterDialog.setMessage("您确定要删除这个任务吗？");
//                        alterDialog.setCancelable(false);
                        final Delete delete = new Delete(getContext(), R.style.dialog);
                        delete.show();
                        delete.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                if (delete.getIssue()) {
                                    // TODO: 2018/6/26 事务删除
                                    Thread r = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            DeleteEvent deleteEvent = new DeleteEvent();
                                            try {
                                                Thread.sleep(300);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            deleteEvent.getRemoteInfo(eventid);
                                        }
                                    });
                                    r.start();
                                    try {
                                        r.join(30000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    issure = true;
                                    dismiss();
                                }
                            }

                        });

                        break;
                    default:break;
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

    private String getTime(Date date) { // 可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
    private void initCustomTimePicker() {//Dialog 模式下，在底部弹出
        selectedDate = Calendar.getInstance();
        pvCustomTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                getdate = getTime(date);
                selectedDate.setTime(date);
            }
        })
                .setDate(selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                                initCustomTimePicker2();
                                pvTime.show();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true)
                .build();
        Dialog mDialog = pvCustomTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvCustomTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.CENTER);//改成Bottom,底部显示
            }
        }
    }
    private void initCustomTimePicker2() {
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                getdate = getTime(date);
            }
        })
                .setDate(selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .isDialog(true)
                .build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);// 修改动画样式
                dialogWindow.setGravity(Gravity.CENTER); // 改成Bottom,底部显示
            }
        }
    }

    private void initlevelupdate() {
        switch(Level_select.getLevel())
        {
            case 0:
                level_update.setBackgroundResource(R.drawable.level0);
                getpriority = 0;
                break;
            case 1:
                level_update.setBackgroundResource(R.drawable.level1);
                getpriority = 1;
                break;
            case 2:
                level_update.setBackgroundResource(R.drawable.level2);
                getpriority = 2;
                break;
            case 3:
                level_update.setBackgroundResource(R.drawable.level3);
                getpriority = 3;
                break;
        }
    }

}
