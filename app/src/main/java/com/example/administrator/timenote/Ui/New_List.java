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
import android.widget.TextView;

import com.example.administrator.timenote.Manager.TaskManager.NewList;
import com.example.administrator.timenote.Manager.UserManager.EmailResend;
import com.example.administrator.timenote.Manager.UserManager.UserLogin;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;

public class New_List extends Dialog {
    /**
     * 上下文对象 *
     */
    private Button back;
    private Context context;
    private boolean issue = false; // 是否验证成功
    private EditText list_new_name;// 清单名输入框
    public String getList_name() {
        return list_name;
    }
    private String list_name;// 读取列表名字
    private Button sure;// 确认按钮

    public boolean getIssue() {
        return issue;
    }

    public New_List(Context context) {
        super(context);
        this.context = context;
    }

    public New_List(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.list_new);

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
        back = findViewById(R.id.back_10);
        list_new_name = findViewById(R.id.list_new_1);
        sure = findViewById(R.id.sure_list_new);


        // 为按钮绑定点击事件监听器
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View ciew) {
                dismiss();
            }
        });

        // 确认按钮事件
        sure.setEnabled(Boolean.FALSE);
        sure.setTextColor(context.getResources().getColor(R.color.color_ok_2));
        sure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                issue = true;
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        NewList newList = new NewList();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        newList.getRemoteInfo(list_name);

                    }
                });
                t.start();
                try {
                    t.join(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //list_name;
                dismiss();

            }
        });

        list_new_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                sure.setTextColor(context.getResources().getColor(R.color.color_ok_2));
                sure.setEnabled(Boolean.FALSE);
            }

            // 如果没有输入则禁止确认
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list_name = list_new_name.getText().toString();

                if (TextUtils.isEmpty(list_new_name.getText()) | list_name.equals("准备做什么？")) {
                    sure.setTextColor(context.getResources().getColor(R.color.color_ok_2));
                    sure.setEnabled(Boolean.FALSE);
                } else {
                    sure.setTextColor(context.getResources().getColor(R.color.color_ok_1));
                    sure.setEnabled(Boolean.TRUE);
                }
            }
            public void afterTextChanged(Editable s) {

            }
        });

    }
}

