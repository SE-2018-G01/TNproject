package com.example.administrator.timenote.Ui;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.UserManager.ChangePassword;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;

import static com.example.administrator.timenote.Model.BeanUserInformation.tryLoginUser;

public class Change_pwd extends AppCompatActivity {
    private EditText newpwd1,newpwd2;
    private TextView pwd1_error,pwd2_error;
    private Button sure;
    private String spwd1;
    private String result;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_pwd_1);
        TextView sign_up_title_1=findViewById(R.id.sign_up_title_1);
        sign_up_title_1.setText("修改密码");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button back1=findViewById(R.id.back_1);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newpwd1=findViewById(R.id.new_pwd_1);
        newpwd2=findViewById(R.id.new_pwd_2);

        pwd1_error=findViewById(R.id.pwd_1_error);
        pwd2_error=findViewById(R.id.pwd_2_error);

        // 密码隐藏
        newpwd1.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        newpwd2.setTransformationMethod(PasswordTransformationMethod
                .getInstance());

        // 两个密码文本框监听回车
        newpwd2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                newpwd2.clearFocus();
                return false;
            }
        });
        newpwd1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                newpwd2.requestFocus();
                return false;
            }
        });

        sure=findViewById(R.id.sure_pwd_1);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spwd1=newpwd1.getText().toString();
                String spwd2=newpwd2.getText().toString();
                if (spwd1.equals(BeanUserInformation.tryLoginUser.getUserpassword())){
                    Toast.makeText(Change_pwd.this,"请勿设置与原密码相同的密码",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (spwd1.length() >= 8 && (spwd1.equals(spwd2))) {
                        //修改密码
                        Thread f = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                ChangePassword changePassword = new ChangePassword();
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                result = changePassword.getRemoteInfo(spwd1);
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        // 更新UI
                                        if (result.equals("true"))
                                            Toast.makeText(Change_pwd.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(Change_pwd.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                                    }

                                });

                            }
                        });
                        f.start();
                        try {
                            f.join(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        finish();
                    } else if (spwd1.length() < 8) {
                        pwd1_error.setVisibility(View.VISIBLE);
                    } else if (spwd1.equals(spwd2) != true) {
                        pwd2_error.setVisibility(View.VISIBLE);
                    } else {
                        pwd1_error.setVisibility(View.VISIBLE);
                        pwd2_error.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        newpwd1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    pwd1_error.setVisibility(View.INVISIBLE);
                }
                else
                {
                    String spwd1=newpwd1.getText().toString();
                    if(spwd1.length()<8|spwd1.length()>20)
                    {
                        pwd1_error.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        newpwd2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    pwd2_error.setVisibility(View.INVISIBLE);
                }
                else
                {
                    String spwd2=newpwd2.getText().toString();
                    String spwd1=newpwd1.getText().toString();
                    if(spwd2.equals(spwd1)!=true)
                    {
                        pwd1_error.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
