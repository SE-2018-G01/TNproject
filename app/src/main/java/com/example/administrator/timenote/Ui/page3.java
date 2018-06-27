package com.example.administrator.timenote.Ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.LeavesManager.LoadLeaves;
import com.example.administrator.timenote.Model.BeanLeavesStatistics;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;

import java.util.Calendar;

public class page3 extends Fragment {

    private Button tasklist;//事务选择列表
    private Button start;// 开始专注按钮
    private TextView tasktext1;// 提示文本大字
    private TextView tasktext2;// 提示文本小字
    private Task_select_list task_select_list;// 事务选择
    private String tishi;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page3, container, false);
        tasklist=view.findViewById(R.id.task_list_yezi);
        start=view.findViewById(R.id.start_task);
        tasktext1=view.findViewById(R.id.tasktext1);
        tasktext2=view.findViewById(R.id.tasktext2);

        // 初始化
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                LoadLeaves loadLeaves = new LoadLeaves();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadLeaves.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid());
                BeanLeavesStatistics.todayleaves = BeanLeavesStatistics.leavesStatistics;
            }
        });
        t.start();
        try {
            t.join(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) < 4 || calendar.get(Calendar.HOUR_OF_DAY) >= 22)
            tishi = "夜深了，早点休息";
        else if (calendar.get(Calendar.HOUR_OF_DAY) < 7)
            tishi = "早上好";
        else if (calendar.get(Calendar.HOUR_OF_DAY) < 11)
            tishi = "上午好";
        else if (calendar.get(Calendar.HOUR_OF_DAY) < 13)
            tishi = "中午了，休息一下";
        else if (calendar.get(Calendar.HOUR_OF_DAY) < 17)
            tishi = "下午也要加油";
        else if (calendar.get(Calendar.HOUR_OF_DAY) < 19)
            tishi = "是时候放松一下了";
        else if (calendar.get(Calendar.HOUR_OF_DAY) < 22)
            tishi = "晚上好";
        tasktext1.setText(tishi);
        if (BeanLeavesStatistics.todayleaves.getLeavesamount() != 0)
            tasktext2.setText("今天您已收获"+String.valueOf(BeanLeavesStatistics.todayleaves.getLeavesamount())+"叶子");

        //事务选择列表按钮监听
        tasklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task_select_list=new Task_select_list(inflater.getContext(),R.style.dialog);
                task_select_list.show();
                task_select_list.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if(task_select_list.isIssure())
                        {
                            Intent intent = new Intent(getContext(),Yezi_start.class);
                            intent.putExtra("p",task_select_list.getNposition());
                            startActivity(intent);
                        }

                    }
                });
            }
        });

        //开始按钮监听
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Yezi_start.class);
                startActivity(intent);
            }
        });

        return view;
    }
}