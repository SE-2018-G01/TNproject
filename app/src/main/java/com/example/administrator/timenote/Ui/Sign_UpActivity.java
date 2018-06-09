package com.example.administrator.timenote.Ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.UserManager.EmailResend;
import com.example.administrator.timenote.Manager.UserManager.UserActivity;
import com.example.administrator.timenote.Manager.UserManager.UserCreate;
import com.example.administrator.timenote.Manager.UserManager.UserLogin;
import com.example.administrator.timenote.Manager.UserManager.UserRollBack;
import com.example.administrator.timenote.R;

import static com.example.administrator.timenote.Model.BeanUserInformation.tryLoginUser;


public class Sign_UpActivity extends AppCompatActivity {
    private EditText email,name,pwd,pwd2;
    private TextView email_error_1,email_remind_1,email_error_2,pwd_error_1,pwd_error_2;
    private ProgressDialog logDialog;// 等待注册的加载框
    public static  String semail;// 邮箱提取
    private String sname,spwd;// 昵称和密码提取
    private String spwd2;// 确认密码提取
    private static String judge;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_up_1);

        {setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);}
        Button button1= findViewById(R.id.sign_up_2);//确认注册按钮
        Button button2= findViewById(R.id.sign_in_1);//已有账号按钮
        Button back1=findViewById(R.id.back_1);
        email=findViewById(R.id.e_mail_1);
        name=findViewById(R.id.name_1);
        pwd=findViewById(R.id.pwd_1);
        pwd2=findViewById(R.id.pwd_2);
        email_error_1=findViewById(R.id.emil_error_1);
        email_error_2=findViewById(R.id.emil_error_2);
        email_remind_1=findViewById(R.id.email_remind_1);
        pwd_error_1=findViewById(R.id.pwd_error_1);
        pwd_error_2=findViewById(R.id.pwd_error_2);

        // 密码隐藏
        pwd.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        pwd2.setTransformationMethod(PasswordTransformationMethod
                .getInstance());

        back1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                finish();
                // 转到登录界面
            }
        });

        // 确认注册按钮
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                semail=email.getText().toString();
                sname=name.getText().toString();
                spwd=pwd.getText().toString();
                spwd2=pwd2.getText().toString();
                showProgressDialog();



            }
        });

        // 已有账号按钮
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent =new Intent(Sign_UpActivity.this,MainActivity.class);
                startActivity(intent);
                // 转到登录界面
            }
        });


        // 邮箱焦点获得与失去
        email.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    email_error_1.setVisibility(View.INVISIBLE);
                    email_error_2.setVisibility(View.INVISIBLE);
                    email_remind_1.setVisibility(View.INVISIBLE);
                }
                else {
                    // 此处为失去焦点时的处理内容
                    semail=email.getText().toString();

                    // 开启子线程进行邮箱判重
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            UserLogin userLogin = new UserLogin();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            userLogin.getRemoteInfo(semail);
                            runOnUiThread(new Runnable(){

                                @Override
                                public void run() {
                                    // 更新UI
                                    // 提示邮箱是否已经注册
                                    if(semail.indexOf("@")<0)
                                    {
                                        email_error_1.setVisibility(View.VISIBLE);
                                    }
                                    else if(tryLoginUser.getUseremail()!=null&&tryLoginUser.getStopdate().equals("0001-01-01T00:00:00"))
                                        email_error_2.setVisibility(View.VISIBLE);
                                    else
                                        email_remind_1.setVisibility(View.VISIBLE);
                                }

                            });
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

        // 密码焦点获得与失去
        pwd.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    pwd_error_1.setVisibility(View.INVISIBLE);
                }
                else {
                    // 此处为失去焦点时的处理内容
                    String spwd=pwd.getText().toString();
                    if(spwd.length()<8|spwd.length()>20)
                    {
                        pwd_error_1.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        pwd2.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    pwd_error_2.setVisibility(View.INVISIBLE);
                }
                else {
                    // 此处为失去焦点时的处理内容
                    String spwd=pwd.getText().toString();
                    String spwd2=pwd2.getText().toString();
                    if(spwd.equals(spwd2)!=true)
                    {
                        pwd_error_2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //四个文本框监听回车
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                name.requestFocus();
                return false;
            }
        });

        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                pwd.requestFocus();
                return false;
            }
        });

        pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                pwd2.requestFocus();
                return false;
            }
        });

        pwd2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                pwd2.clearFocus();
                return false;
            }
        });
    }
    private void showProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setIcon(R.mipmap.ic_launcher);//
        // 设置提示的title的图标，默认是没有的，需注意的是如果没有设置title的话只设置Icon是不会显示图标的
        dialog.setTitle("Notice");
        // dismiss监听
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        // 监听Key事件被传递给dialog
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                return false;
            }
        });
        // 监听cancel事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        //设置可点击的按钮，最多有三个(默认情况下)
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Natrue",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialog.setMessage("Loging in ...");
        dialog.show();
        if(semail.indexOf("@")>0&&spwd.length()>=8&& spwd.equals(spwd2)) {
            Thread j = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    UserCreate userCreate = new UserCreate();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    judge = userCreate.getRemoteInfo(semail, spwd, sname);
                }
            });
            j.start();
            try {
                j.join(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 判断预插入成功与否
            if (judge.equals("false")) {
                // 注册失败提示
                dialog.dismiss();
                email.requestFocus();
                pwd.requestFocus();
                pwd2.requestFocus();
                name.requestFocus();
                Toast.makeText(Sign_UpActivity.this, "注册失败,请重试", Toast.LENGTH_SHORT).show();

                //                    Intent intent =new Intent(Sign_UpActivity.this,MainActivity.class);
                //                    startActivity(intent);
                //                    finish();
            }
            else {
                dialog.dismiss();
                final Email_sure email_sure = new Email_sure(Sign_UpActivity.this, R.style.dialog);
                email_sure.show();
                email_sure.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(final DialogInterface dialog) {
                        if (email_sure.getIssue()) {
                            // 激活用户
                            Thread r = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    UserActivity userActivity = new UserActivity();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    userActivity.getRemoteInfo(Sign_UpActivity.semail);

                                }
                            });
                            r.start();
                            try {
                                r.join(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            finish();
                            Toast.makeText(Sign_UpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        else
        {
            email.requestFocus();
            pwd.requestFocus();
            pwd2.requestFocus();
            name.requestFocus();
            dialog.dismiss();
        }
//        if(semail.indexOf("@")>0&&spwd.length()>=8&& spwd.equals(spwd2)) {
//            Thread j = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    UserCreate userCreate = new UserCreate();
//                    try {
//                        Thread.sleep(300);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    judge = userCreate.getRemoteInfo(semail, spwd, sname);
//                }
//            });
//            j.start();
//            try {
//                j.join(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            // 判断预插入成功与否
//            if (judge.equals("false")) {
//                // 注册失败提示
//                Toast.makeText(Sign_UpActivity.this, "注册失败,请重试", Toast.LENGTH_SHORT).show();
//
//
//                //                    Intent intent =new Intent(Sign_UpActivity.this,MainActivity.class);
//                //                    startActivity(intent);
//                //                    finish();
//            }
//            else {
//                final Email_sure email_sure = new Email_sure(Sign_UpActivity.this, R.style.dialog);
//                // TODO: 2018/6/6 换成登录提示错误的那种弹框
//                email_sure.show();
//                email_sure.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        if (email_sure.getIssue()) {
//                            // 激活用户
//                            Thread r = new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // TODO Auto-generated method stub
//                                    UserActivity userActivity = new UserActivity();
//                                    try {
//                                        Thread.sleep(300);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                    userActivity.getRemoteInfo(Sign_UpActivity.semail);
//
//                                }
//                            });
//                            r.start();
//                            try {
//                                r.join(30000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            finish();
//                            Toast.makeText(Sign_UpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            }

//        }
//        else
//        {
//            email.requestFocus();
//            pwd.requestFocus();
//            pwd2.requestFocus();
//            name.requestFocus();
//            dialog.dismiss();
//        }
    }
//    public void initSignUp(){
//        //判断是否是注册未激活用户
//        if(tryLoginUser==null) {
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    UserLogin userLogin = new UserLogin();
//                    try {
//                        Thread.sleep(300);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    userLogin.getRemoteInfo(semail);
//                }
//            });
//            t.start();
//            try {
//                t.join(30000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        else{
//            if(tryLoginUser.getUseremail()!=null&&tryLoginUser.getStopdate()!=null){
//                Thread j = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        EmailResend emailResend = new EmailResend();
//                        try {
//                            Thread.sleep(300);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        emailResend.getRemoteInfo(semail);
//                    }
//                });
//                j.start();
//                try {
//                    j.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            else {
//                // 注册账号，返回注册结果
//                // 开启子线程进行用户信息预插入
//                Thread j = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        UserCreate userCreate = new UserCreate();
//                        try {
//                            Thread.sleep(300);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        judge = userCreate.getRemoteInfo(semail,spwd,sname);
//                    }
//                });
//                j.start();
//                try {
//                    j.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                // 判断预插入成功与否
//                if(judge.equals("false")){
//                    // 注册失败提示
//                    Toast.makeText(Sign_UpActivity.this, "注册失败,请重试", Toast.LENGTH_SHORT).show();
//                    Thread s = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            UserRollBack userRollBack = new UserRollBack();
//                            try {
//                                Thread.sleep(300);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            userRollBack.getRemoteInfo(Sign_UpActivity.semail);
//
//                        }
//                    });
//                    s.start();
//                    try {
//                        s.join(30000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    finish();
//                }
//            }
//        }
//    }
}
