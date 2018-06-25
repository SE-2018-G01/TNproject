package com.example.administrator.timenote.Ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.DremindManager.ChangeRepeat;
import com.example.administrator.timenote.Manager.DremindManager.LoadDremind;
import com.example.administrator.timenote.Manager.DremindManager.NewDRemind;
import com.example.administrator.timenote.Model.BeanDRemindInformation;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;

import java.util.ArrayList;
import java.util.List;


public class Rering_setup extends Dialog implements AdapterView.OnItemClickListener, ListAdapter.InnerItemOnclickListener{
    /**
     * 上下文对象 *
     */
    private Button back;
    private Context context;
    private ListView listView;// 选择列表
    private ArrayList<String> list = new ArrayList<>();
    private int position;
    private String show;

    private Boolean redx = true;

    public Rering_setup(Context context) {
        super(context);
        this.context = context;
    }

    public Rering_setup(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.rering_set);

        /*
         * 获取验证码的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

//            WindowManager m = context.getWindowManager();
//            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//             p.height = (int) (d.getHeight() ); // 高度设置为屏幕
//            p.width = (int) (d.getWidth() ); // 宽度设置为屏幕
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        back = findViewById(R.id.back_14);
        listView = findViewById(R.id.rering_setup_list);

        // 为按钮绑定点击事件监听器
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View ciew) {
                dismiss();
            }
        });

        list.add("从不");
        list.add("每天");
        list.add("每周工作日(周一至周五)");
        list.add("每周休息日");
        list.add("每月(当天)");
        list.add("每年(当天)");
        ListAdapter listAdapter = new ListAdapter(context,R.layout.list_button,list);
        listView.setAdapter(listAdapter);
        listAdapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);


        this.setCancelable(true);
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    public void itemClick(View v) {
        position = (Integer) v.getTag();
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
                redx = loadDremind.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),Task_Update.getEventid());
            }
        });
        t.start();
        try {
            t.join(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!redx){
            Thread s = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    NewDRemind newDRemind = new NewDRemind();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    newDRemind.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),Task_Update.getEventid());
                }
            });
            s.start();
            try {
                s.join(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread r = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    ChangeRepeat changeRepeat = new ChangeRepeat();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    changeRepeat.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),Task_Update.getEventid(),position);
                }
            });
            r.start();
            try {
                r.join(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            Thread r = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    ChangeRepeat changeRepeat = new ChangeRepeat();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    changeRepeat.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),Task_Update.getEventid(),position);
                }
            });
            r.start();
            try {
                r.join(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        switch (position){
            case 0:show = "从不";break;
            case 1:show = "每天";break;
            case 2:show = "每周工作日(周一至周五)";break;
            case 3:show = "每周休息日";break;
            case 4:show = "每月(当天)";break;
            case 5:show = "每年(当天)";break;
        }
        Toast.makeText(getContext(),show,Toast.LENGTH_SHORT).show();
        dismiss();
    }
}

