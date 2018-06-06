package com.example.administrator.timenote.Ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.UserManager.UserActivity;
import com.example.administrator.timenote.Manager.UserManager.UserCreate;
import com.example.administrator.timenote.Manager.UserManager.UserLogin;
import com.example.administrator.timenote.R;

import static com.example.administrator.timenote.Model.BeanUserInformation.tryLoginUser;


public class Sign_UpActivity extends AppCompatActivity {
    private EditText email,name,pwd,pwd2;
    private TextView email_error_1,email_remind_1,email_error_2,pwd_error_1,pwd_error_2;
    public static  String semail;
    private String sname,spwd;
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
                String spwd2=pwd2.getText().toString();

                if(semail.indexOf("@")>0&&spwd.length()>=8&&spwd.equals(spwd2)==true&&tryLoginUser.getUseremail()==null){
                // 开启子线程进行用户信息预插入
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
                        judge = userCreate.getRemoteInfo(semail,spwd,sname);
                    }
                });
                j.start();
                try {
                    j.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 判断预插入成功与否
                if(judge.equals("false")){
                    // 注册失败提示
                    Toast.makeText(Sign_UpActivity.this, "注册失败,请重试", Toast.LENGTH_SHORT).show();
                    finish();
                }

                    final Email_sure email_sure= new Email_sure(Sign_UpActivity.this,R.style.dialog);
                    email_sure.show();
                    email_sure.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if(email_sure.getIssue())
                            {
                                // 激活
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
                    // 注册账号，返回注册结果
//                    Intent intent =new Intent(Sign_UpActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
                }
               else
                {
                    email.requestFocus();
                    pwd.requestFocus();
                    pwd2.requestFocus();
                    name.requestFocus();
                }

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
                    if(semail.indexOf("@")<0)
                    {
                        email_error_1.setVisibility(View.VISIBLE);
                    }
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
                                    if(tryLoginUser.getUseremail()!=null)
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
    }
}
