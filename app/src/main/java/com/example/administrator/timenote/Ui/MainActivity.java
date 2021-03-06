package com.example.administrator.timenote.Ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.UserManager.UserActivity;
import com.example.administrator.timenote.Manager.UserManager.UserLogin;
import com.example.administrator.timenote.Manager.UserManager.loginServe;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;
import com.example.administrator.timenote.Threadcontroal.MyHandler;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.example.administrator.timenote.Model.BeanUserInformation.currentLoginUser;
import static com.example.administrator.timenote.Model.BeanUserInformation.tryLoginUser;
import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {

    private EditText pwd, uesrid;
    public static String suesrid;
    private Button button1;
    public static Handler myHandler;
    //Message msg = new Message();
    //Message msg = myHandler.obtainMessage();
    //Log.("msg",msg);
    private String spwd;
    @Override
    //访问网络同时加入这个
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        //允许使用webervice同时启用网络访问
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        button1 = findViewById(R.id.loading_1);
        Button button2 = findViewById(R.id.sign_up_1);
        Button button3 = findViewById(R.id.pwd_change_1);
        pwd = (EditText) findViewById(R.id.pwd_1);
        uesrid = (EditText) findViewById(R.id.userid_1);

        //强制竖屏
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // 隐藏密码
        pwd.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        // 密码文本框监听回车
        pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                pwd.clearFocus();
                return false;
            }
        });

        // 用户名文本框监听回车
        uesrid.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                pwd.requestFocus();
                return false;
            }
        });
        Map<String, String> map = new loginServe().getSaveUserInfo(MainActivity.this);
        {
            if(map!=null){
                suesrid = map.get("useremail");
                spwd = map.get("password");
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
                        userLogin.getRemoteInfo(suesrid);
                        //myHandler.obtainMessage(1,u_rst).sendToTarget();
                        //msg.obj = u_rst;
                        //msg.what = 1;
                        //myHandler.sendMessage(msg);
                    }
                });
                t.start();
                try {
                    t.join(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //判断账号密码是否匹配，是则进入主界面，否则弹框提示
                AlertDialog.Builder alterDialog = new AlertDialog.Builder(MainActivity.this);
                if (((suesrid.equals(tryLoginUser.getUseremail())) && (spwd.equals(tryLoginUser.getUserpassword())) && tryLoginUser.getStopdate().equals("0001-01-01T00:00:00"))) {
                    Intent intent = new Intent(MainActivity.this, Calendar_View.class);
                    startActivity(intent);
                    currentLoginUser = tryLoginUser;
                    boolean result = new loginServe().saveUserInfo(MainActivity.this,suesrid,spwd);
                    finish();
                }
            }
        }
        // 登录按钮
        button1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            //获得文本框内容
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, uesrid.getText().toString(), Toast.LENGTH_SHORT).show();
//            }

            public void onClick(View v) {
                suesrid=uesrid.getText().toString();
                spwd = pwd.getText().toString();

//                myHandler = new Handler(){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                        switch(msg.what){
//                            case 1:tryLoginUser = (BeanUserInformation) msg.obj;break;
//                            case 0:Toast.makeText(MainActivity.this,"没有该用户",Toast.LENGTH_SHORT).show();break;
//                            case -1:Toast.makeText(MainActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();break;
//                        }
//
////                  myHandler=new MainActivity.MyHandler(Looper.myLooper());
//                    }
//                };
//               创建子线程并引用webservice层的LoadUser方法
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
                        userLogin.getRemoteInfo(suesrid);
                        //myHandler.obtainMessage(1,u_rst).sendToTarget();
                        //msg.obj = u_rst;
                        //msg.what = 1;
                        //myHandler.sendMessage(msg);
                    }
                });
                t.start();
                try {
                    t.join(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //判断账号密码是否匹配，是则进入主界面，否则弹框提示
                AlertDialog.Builder alterDialog = new AlertDialog.Builder(MainActivity.this);
                if (((suesrid.equals(tryLoginUser.getUseremail())) && (spwd.equals(tryLoginUser.getUserpassword())) && tryLoginUser.getStopdate().equals("0001-01-01T00:00:00"))) {
                    Intent intent = new Intent(MainActivity.this, Calendar_View.class);
                    startActivity(intent);
                    currentLoginUser = tryLoginUser;
                    boolean result = new loginServe().saveUserInfo(MainActivity.this,suesrid,spwd);
                    finish();
                }
                else if((suesrid.equals(""))){
                    alterDialog.setTitle("提示！");
                    alterDialog.setMessage("账号不能为空");
                    alterDialog.setCancelable(false);
                    alterDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "确认", Toast.LENGTH_SHORT).show();
                            uesrid.requestFocus();
                        }
                    });
                    alterDialog.show();
                }
                else if((spwd.equals(""))){
                    alterDialog.setTitle("提示！");
                    alterDialog.setMessage("密码不能为空");
                    alterDialog.setCancelable(false);
                    alterDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "确认", Toast.LENGTH_SHORT).show();
                            uesrid.requestFocus();
                        }
                    });
                    alterDialog.show();
                }
//                else if(tryLoginUser.getUseremail()==null){
//                    alterDialog.setTitle("提示！");
//                    alterDialog.setMessage("用户不存在");
//                    alterDialog.setCancelable(false);
//                    alterDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(MainActivity.this, "确认", Toast.LENGTH_SHORT).show();
//                            uesrid.requestFocus();
//                        }
//                    });
//                    alterDialog.show();
//                }
                else if(tryLoginUser.getUseremail()!=null&&!tryLoginUser.getStopdate().equals("0001-01-01T00:00:00")){
                    alterDialog.setTitle("您的账号还未激活");
                    alterDialog.setMessage("是否现在激活");
                    alterDialog.setCancelable(false);
                    alterDialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "尚未激活", Toast.LENGTH_SHORT).show();
                            uesrid.requestFocus();
                        }
                    });
                    alterDialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO: 2018/6/6 调用邮箱验证弹框
                            final Email_sure1 email_sure= new Email_sure1(MainActivity.this,R.style.dialog);
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
                                                userActivity.getRemoteInfo(suesrid);

                                            }
                                        });
                                        r.start();
                                        try {
                                            r.join(30000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        pwd.setText("");
                                        Toast.makeText(MainActivity.this, "用户激活成功", Toast.LENGTH_SHORT).show();
                                        uesrid.requestFocus();
                                    }
                                }
                            });
                        }
                    });

                    alterDialog.show();
                }
                else if((tryLoginUser.getUseremail()==null)||(!suesrid.equals(tryLoginUser.getUseremail())) || (!spwd.equals(tryLoginUser.getUserpassword())))
                {
                    alterDialog.setTitle("糟糕！");
                    alterDialog.setMessage("账号或密码错误");
                    alterDialog.setCancelable(false);
                    alterDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "确认", Toast.LENGTH_SHORT).show();
                            pwd.setText("");
                            uesrid.requestFocus();
                        }
                    });
                    alterDialog.show();
                }
            }
        });

        //注册按钮
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd.requestFocus();
                uesrid.requestFocus();
                Intent intent = new Intent(MainActivity.this, Sign_UpActivity.class);
                startActivity(intent);
                //转到注册界面

            }
        });

        //忘记密码按钮
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pwd.requestFocus();
                uesrid.requestFocus();
                Intent intent = new Intent(MainActivity.this, Forgot_pwd.class);
                startActivity(intent);
                //转到忘记密码界面

            }
        });
    }




//      class MyThread implements Runnable {
//          public void run() {
//              //从消息池中取出一个message
//              Message msg = myHandler.obtainMessage();
//              //Bundle是message中的数据
//              Bundle b = new Bundle();
//              b.putString("color", "我的");
//              msg.setData(b);
//              //传递数据
//              myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
//          }
//      }

}
