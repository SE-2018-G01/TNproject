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
        list.add("每周六");
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
        int position;
        position = (Integer) v.getTag();


                Toast.makeText(getContext(),"123",Toast.LENGTH_SHORT).show();


    }
}

