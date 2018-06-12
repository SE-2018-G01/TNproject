package com.example.administrator.timenote.Ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.administrator.timenote.R;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Task_Update extends AppCompatActivity {

    private Button back;// 返回按钮
    private MenuItem gMenuItem1, gMenuItem2;// list1的两个按钮
    private String getdate;// 获得的时间
    private Button level_update;// 优先级修改
    private Button list_spot_3;// 菜单按钮
    private TextView list_name_task;// 修改清单按钮
    private TimePickerDialog pvCustomTime;// 时间选择
    private Button rering_setup;// 重复设置
    private Button ring_setup;// 提醒时间设置
    private Button save;// 保存按钮
    private Button time_setup;// 事务时间修改
    private EditText task_update_name;// 事务名称修改
    private EditText task_update_d;// 事务描述修改

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_task__update);
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        //绑定按钮
        back =findViewById(R.id.back_8);
        level_update = findViewById(R.id.level_update);
        list_spot_3 = findViewById(R.id.list_spot_3);
        list_name_task = findViewById(R.id.list_name_task);
        rering_setup = findViewById(R.id.rering_setup);
        ring_setup = findViewById(R.id.ring_setup);
        save = findViewById(R.id.save_update);
        time_setup = findViewById(R.id.time_setup);
        task_update_name = findViewById(R.id.task_upda_name);
        task_update_d = findViewById(R.id.task_update_d);

        // 绑定监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 优先级选择
        level_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Level_select level_select = new Level_select(Task_Update.this,R.style.dialog);
                level_select.show();
                level_select.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

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
                List_select list_select = new List_select(Task_Update.this,R.style.dialog);
                list_select.show();
                list_select.setOnDismissListener(new DialogInterface.OnDismissListener() {
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
                Rering_setup rering_setup = new Rering_setup(Task_Update.this,R.style.dialog);
                rering_setup.show();
                rering_setup.setOnDismissListener(new DialogInterface.OnDismissListener() {
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
                initCustomTimePicker();
                Toast.makeText(Task_Update.this,getdate,Toast.LENGTH_SHORT).show();
            }
        });

        // 保存按钮监听
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存信息
                finish();
            }
        });

        // 事务时间修改
        time_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCustomTimePicker();
                Toast.makeText(Task_Update.this,getdate,Toast.LENGTH_SHORT).show();
            }
        });

        // 事务名称框监听
        task_update_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        // 事务描述框监听
        task_update_d.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
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
                        Toast.makeText(Task_Update.this,"保存",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.delete:
                        Toast.makeText(Task_Update.this,"删除",Toast.LENGTH_SHORT).show();
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
    private void initCustomTimePicker() {//Dialog 模式下，在底部弹出
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        pvCustomTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                getdate = String.valueOf(hourOfDay+":"+minute);
            }
        },hour,minute,true);
        pvCustomTime.show();
    }
}
