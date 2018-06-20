package com.example.administrator.timenote.Ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.TaskManager.Complete;
import com.example.administrator.timenote.Manager.TaskManager.UpdatePriority;
import com.example.administrator.timenote.R;

import static com.example.administrator.timenote.Model.BeanEventInformation.allEventList;

/**
 * Created by XuanWem Chen on 2018/6/19.
 */

public class Yezi_start extends AppCompatActivity{
    private Button back;// 退出按钮
    private TextView task_name;// 当前事物名称
    private TextView time;// 时间
    private int M = 0;// 分钟
    private int s = 3;// 秒
    private int p;
    private Task_select_list task_select_list;// 事务选择

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yezi_start);
        // 对应按钮
        back = findViewById(R.id.quit_task);
        task_name = findViewById(R.id.task_name_yezi);
        p = getIntent().getIntExtra("p",-1);
        if (p != -1) {
            task_name.setText(MyCustomAdapter3.getData().get(p).getEventname());
            task_name.setVisibility(View.VISIBLE);
        }
        else {
            task_name.setVisibility(View.INVISIBLE);
        }
        time = findViewById(R.id.yezi_jishi);
        cdt.start();

        // 退出按钮监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initquit();
            }
        });

    }

    CountDownTimer cdt = new CountDownTimer((M*60+s)*1000, 1000) {

        @SuppressLint({"ResourceAsColor", "SetTextI18n"})
        public void onTick(long millisUntilFinished) {
            if (s <= 0) {
                s = 60;
                M--;
            }
            s--;
            time.setText(M + ":" + s);

        }


        @SuppressLint("ResourceAsColor")
        public void onFinish() {
            // TODO: 2018/6/19 退出逻辑
            initquit();
        }
    };

    private void initquit(){
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(Yezi_start.this);
        if (p != -1) {
            alterDialog.setTitle("您完成当前任务了吗？");
            alterDialog.setMessage(MyCustomAdapter3.getData().get(p).getEventname());
            alterDialog.setCancelable(true);
            alterDialog.setPositiveButton("完成", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Yezi_start.this, "完成", Toast.LENGTH_SHORT).show();
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
                            complete.getRemoteInfo(MyCustomAdapter3.getData().get(p).getEventid());
                            int i = 0;
                            for (; i < allEventList.size(); i++){
                                if (allEventList.get(i).getEventid() == MyCustomAdapter3.getData().get(p).getEventid())
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
                }
            });
            alterDialog.setNegativeButton("未完成", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Yezi_start.this, "未完成", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            alterDialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Yezi_start.this, "取消", Toast.LENGTH_SHORT).show();
                }
            });
            alterDialog.show();
        }
        else {
            alterDialog.setTitle("您未选择任何任务");
            alterDialog.setMessage("需要选择您刚才做的任务吗？");
            alterDialog.setCancelable(true);
            alterDialog.setPositiveButton("选择", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Yezi_start.this, "选择", Toast.LENGTH_SHORT).show();
                    task_select_list=new Task_select_list(Yezi_start.this,R.style.dialog);
                    task_select_list.show();
                    task_select_list.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if(task_select_list.isIssure())
                            {
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
                                        complete.getRemoteInfo(MyCustomAdapter3.getData().get(task_select_list.getNposition()).getEventid());
                                        int i = 0;
                                        for (; i < allEventList.size(); i++){
                                            if (allEventList.get(i).getEventid() == MyCustomAdapter3.getData().get(task_select_list.getNposition()).getEventid())
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

                            }

                        }
                    });
                }
            });
            alterDialog.setNegativeButton("算了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Yezi_start.this, "算了", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            alterDialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Yezi_start.this, "取消", Toast.LENGTH_SHORT).show();
                }
            });
            alterDialog.show();
        }
    }
}