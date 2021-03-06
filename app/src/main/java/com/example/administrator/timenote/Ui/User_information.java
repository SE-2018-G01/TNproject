package com.example.administrator.timenote.Ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.timenote.Manager.UserManager.ChangeEmail;
import com.example.administrator.timenote.Manager.UserManager.ChangeName;
import com.example.administrator.timenote.Manager.UserManager.UserLogin;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;
import com.liuguangqiang.ipicker.IPicker;
import com.liuguangqiang.ipicker.events.IPickerEvent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class User_information extends AppCompatActivity {

    private Button back9;// 返回按钮
    private EditText email_update;// 邮箱
    private Button loadout;// 登出
    private EditText name_update;// 昵称
    public static ImageView uesr_image;// 用户头像
    private String semail;
    private String sname;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_information);
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // 控件绑定
        back9 = findViewById(R.id.back_9);
        email_update = findViewById(R.id.email_update);
        email_update.setText(BeanUserInformation.currentLoginUser.getUseremail());
        loadout = findViewById(R.id.load_out);
        name_update = findViewById(R.id.name_update);
        name_update.setText(BeanUserInformation.currentLoginUser.getUsername());
        uesr_image = findViewById(R.id.user_image);

        // 监听回车
        email_update.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                email_update.requestFocus();
                return false;
            }
        });
        name_update.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                name_update.clearFocus();
                return false;
            }
        });

        // 返回按钮
        back9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_update.requestFocus();
                email_update.requestFocus();
                name_update.requestFocus();
                finish();
            }
        });

        // 邮箱修改框
        email_update.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                semail = email_update.getText().toString();
                if(hasFocus)
                {

                }
                else
                {
                    if((semail.indexOf("@") > 0) && !semail.equals(BeanUserInformation.currentLoginUser.getUseremail()))
                    {
                        //发送验证码
                        final Email_sure email_sure= new Email_sure(User_information.this,R.style.dialog);
                        email_sure.show();
                        email_sure.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                if(email_sure.getIssue())
                                {
                                    //修改用户信息
                                    Thread f = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            ChangeEmail changeEmail = new ChangeEmail();
                                            try {
                                                Thread.sleep(300);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            changeEmail.getRemoteInfo(semail);

                                        }
                                    });
                                    f.start();
                                    try {
                                        f.join(30000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

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

                                        }
                                    });
                                    t.start();
                                    try {
                                        t.join(30000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    BeanUserInformation.currentLoginUser = BeanUserInformation.tryLoginUser;
                                }
                            }
                        });
                    }
                }
            }
        });

        // 登出
        loadout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_update.requestFocus();
                email_update.requestFocus();
                name_update.requestFocus();
                Intent intent = new Intent(User_information.this,MainActivity.class);
                startActivity(intent);
                File file = new File(User_information.this.getFilesDir(),"info.txt");
                file.delete();
                finish();
            }
        });

        // 昵称修改
        name_update.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                sname = name_update.getText().toString();
                if(hasFocus)
                {

                }
                else
                {
                    if (!sname.equals(BeanUserInformation.currentLoginUser.getUsername())) {
                        // 修改密码
                        Thread f = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                ChangeName changeName = new ChangeName();
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                changeName.getRemoteInfo(sname);

                            }
                        });
                        f.start();
                        try {
                            f.join(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

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
                                userLogin.getRemoteInfo(BeanUserInformation.currentLoginUser.getUseremail());

                            }
                        });
                        t.start();
                        try {
                            t.join(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        BeanUserInformation.currentLoginUser = BeanUserInformation.tryLoginUser;
                        page4.setUser_name_page4(sname);
                    }

                    }
            }
        });

        // 头像按钮
        if(BeanUserInformation.currentLoginUser.getIcon()!=null)
        {
            Bitmap bm;
            byte[] bitmapByte = BeanUserInformation.currentLoginUser.getIcon();
            if (bitmapByte.length != 0) {
                bm = BitmapFactory.decodeByteArray(bitmapByte, 0,bitmapByte.length);
                uesr_image.setImageBitmap(bm);
            } else {

            }
        }
        uesr_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_information.this,UserHead.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
