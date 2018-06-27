package com.example.administrator.timenote.Ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.timenote.Manager.ListManager.NewList;
import com.example.administrator.timenote.R;

/**
 * Created by XuanWem Chen on 2018/6/27.
 */

public class Delete extends Dialog {
    /**
     * 上下文对象 *
     */
    private Button back;
    private Context context;
    private boolean issue = false; // 是否验证成功
    private Button sure;// 确认按钮

    public boolean getIssue() {
        return issue;
    }

    public Delete (Context context) {
        super(context);
        this.context = context;
    }

    public Delete (Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.delete);

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
        back = findViewById(R.id.back_20);
        sure = findViewById(R.id.sure_delete);


        // 为按钮绑定点击事件监听器
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View ciew) {
                dismiss();
            }
        });

        // 确认按钮事件
        sure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                issue = true;

                dismiss();

            }
        });

    }
}