package com.example.administrator.timenote.Ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;

public class Pesonal extends AppCompatActivity {

    private Button back;// 返回按钮
    private ImageView imageView3;// 头像按钮
    private TextView textView20;// 用户名

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pesonal);
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // 绑定按钮
        back = findViewById(R.id.back_9);
        imageView3 = findViewById(R.id.imageView3);
        textView20 = findViewById(R.id.textView20);

        if(BeanUserInformation.currentLoginUser.getIcon()!=null) {
            Bitmap bm;
            byte[] bitmapByte1 = BeanUserInformation.currentLoginUser.getIcon();
            if (bitmapByte1.length != 0) {
                bm = BitmapFactory.decodeByteArray(bitmapByte1, 0, bitmapByte1.length);
                imageView3.setImageBitmap(bm);
            }
            else {

            }
        }
        textView20.setText(BeanUserInformation.currentLoginUser.getUsername());

        // 返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 头像按钮
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pesonal.this,User_information.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
