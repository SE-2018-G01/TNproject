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

import com.example.administrator.timenote.Manager.DremindManager.ChangeAhead;
import com.example.administrator.timenote.Manager.DremindManager.LoadDremind;
import com.example.administrator.timenote.Manager.DremindManager.NewDRemind;
import com.example.administrator.timenote.Model.BeanDRemindInformation;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;

import java.util.ArrayList;

/**
 * Created by XuanWem Chen on 2018/6/18.
 */

public class Ringtime_setup extends Dialog implements AdapterView.OnItemClickListener, ListAdapter.InnerItemOnclickListener{
    /**
     * 上下文对象 *
     */
    private Button back;
    private Context context;
    private ListView listView;// 选择列表
    private ArrayList<String> list = new ArrayList<>();
    private int position;
    private String show;
//    private Boolean aheaddx = true;

    public Ringtime_setup(Context context) {
        super(context);
        this.context = context;
    }

    public Ringtime_setup(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.ringtime_setup);

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
        back = findViewById(R.id.back_18);
        listView = findViewById(R.id.ringtime_setup_list);

        // 为按钮绑定点击事件监听器
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View ciew) {
                dismiss();
            }
        });

        list.add("准时");
        list.add("提前 5 分钟");
        list.add("提前 30 分钟");
        list.add("提前 1 小时");
        list.add("提前 1 天");
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
                BeanDRemindInformation.tsset = loadDremind.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),Task_Update.getEventid());
            }
        });
        t.start();
        try {
            t.join(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (BeanDRemindInformation.tsset == null){
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
                    ChangeAhead changeAhead = new ChangeAhead();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    changeAhead.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),Task_Update.getEventid(),position);
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
                    ChangeAhead changeAhead = new ChangeAhead();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    changeAhead.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),Task_Update.getEventid(),position);
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
            case 0:show = "准时";break;
            case 1:show = "提前 5 分钟";break;
            case 2:show = "提前 30 分钟";break;
            case 3:show = "提前 1 小时";break;
            case 4:show = "提前 1 天";break;
        }
        Toast.makeText(getContext(),show,Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
