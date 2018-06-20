package com.example.administrator.timenote.Ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.timenote.Manager.UserManager.EmailResend;
import com.example.administrator.timenote.Manager.UserManager.Email_ck;
import com.example.administrator.timenote.Manager.UserManager.UserLogin;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;

import static com.example.administrator.timenote.Model.BeanUserInformation.tryLoginUser;

public class Forgot_pwd extends AppCompatActivity {
    private int recLen=60;
    private EditText email3,verification_code_3;
    private Button back1,send,resend2,sure_Verification;
    private TextView email3_error,Verification_error;
    public static String semail3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forgot_pwd_1);
        TextView sign_up_title_1=findViewById(R.id.sign_up_title_1);
        sign_up_title_1.setText("忘记密码");
        {setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);}
        back1=findViewById(R.id.back_1);
        send=findViewById(R.id.send_Verification_code_2);
        resend2=findViewById(R.id.resent_Verification_code_3);
        sure_Verification=findViewById(R.id.sure_Verification_code_2);

        email3=findViewById(R.id.email_3);
        verification_code_3=findViewById(R.id.Verification_code_3);

        email3_error=findViewById(R.id.email_error_2);
        Verification_error=findViewById(R.id.Verification_code_error_2);

        if (MainActivity.suesrid != null){
            email3.setText(MainActivity.suesrid);
            verification_code_3.requestFocus();
        }

        back1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                finish();
                //转到登录界面
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                semail3=email3.getText().toString();
                if(semail3.indexOf("@")>0)
                {
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
                            userLogin.getRemoteInfo(semail3);
                        }
                    });
                    t.start();
                    try {
                        t.join(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(BeanUserInformation.tryLoginUser != null)
                    {
                        send.setVisibility(View.INVISIBLE);
                        resend2.setVisibility(View.VISIBLE);
                        recLen = 60;
                        cdt.start();
                        Thread f = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                EmailResend emailResend = new EmailResend();
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                emailResend.getRemoteInfo(semail3);
                            }
                        });
                        f.start();
                        try {
                            f.join(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Thread r = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                UserLogin userLogin = new UserLogin();
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                userLogin.getRemoteInfo(semail3);
                            }
                        });
                        r.start();
                        try {
                            r.join(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        email3_error.setVisibility(View.VISIBLE);
                        email3_error.setText("邮箱不存在，请输入正确的邮箱");
                    }
                }
                else
                {
                    email3_error.setVisibility(View.VISIBLE);
                    email3_error.setText("邮箱格式错误，请输入正确的邮箱");
                }
            }
        });

        sure_Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scode=verification_code_3.getText().toString();
                if(scode.equals(BeanUserInformation.tryLoginUser.getAuthcode()))
                {
                    //修改密码
                    Intent intent=new Intent(Forgot_pwd.this,Change_pwd.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Verification_error.setVisibility(View.VISIBLE);
                }
            }
        });

        email3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    email3_error.setVisibility(View.INVISIBLE);
                }
                else{
                    if(!Email_ck.isEmail(email3.getText().toString())){
                        email3_error.setVisibility(View.VISIBLE);
                        email3_error.setText("邮箱格式错误，请输入正确的邮箱");
                    }
                }
            }
        });

        verification_code_3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    Verification_error.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    CountDownTimer cdt = new CountDownTimer(60000, 1000) {

        public void onTick(long millisUntilFinished) {
            recLen--;
            resend2.setText("重新发送"+recLen+"s");
        }
        @Override
        public void onFinish() {
            resend2.setVisibility(View.INVISIBLE);
            send.setVisibility(View.VISIBLE);
        }
    };
}
