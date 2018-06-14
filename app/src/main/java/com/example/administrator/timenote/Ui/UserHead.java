package com.example.administrator.timenote.Ui;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;
import com.liuguangqiang.ipicker.IPicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserHead extends AppCompatActivity {

    private Button back;// 返回按钮
    private ImageView head;// 头像图片
    private Button list_spot_4;// 菜单
    private Button sure;// 确认按钮
    private RecyclerView recyclerView;
    private SelectedAdapter adapter;
    private ArrayList<String> selectPictures = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_head);
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // 绑定按钮
        back = findViewById(R.id.back_15);
        head = findViewById(R.id.head);
        list_spot_4 = findViewById(R.id.list_spot_4);
        sure = findViewById(R.id.save_head);

        // 返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 菜单按钮
        list_spot_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(head);
            }
        });

        // 保存按钮
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                head.setDrawingCacheEnabled(true);
                Bitmap bm = Bitmap.createBitmap(convertViewToBitmap(head));

                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bitmapByte = baos.toByteArray();
                if (bitmapByte.length != 0) {
                    bm = BitmapFactory.decodeByteArray(bitmapByte, 0,bitmapByte.length);
                } else {

                }
                if(BeanUserInformation.currentLoginUser!=null)
                {
                    BeanUserInformation.currentLoginUser.setIcon(bitmapByte);
                    User_information.uesr_image.setImageBitmap(bm);
                    page4.imageButton1.setImageBitmap(bm);
                    page1.person.setImageBitmap(bm);
                    finish();
                }
                else{
                    finish();
                }
                list_spot_4.setDrawingCacheEnabled(false);
            }
        });
    }
    private void showPopupMenu(View view) {

        IPicker.setLimit(1);
        IPicker.setCropEnable(true);
        IPicker.setOnSelectedListener(new IPicker.OnSelectedListener() {
            @Override
            public void onSelected(List<String> paths) {
                selectPictures.clear();
                selectPictures.addAll(paths);
                adapter.notifyDataSetChanged();
                Glide.with(UserHead.this).load(paths.get(0)).into(head);

            }
        });
        adapter = new SelectedAdapter(getApplicationContext(), selectPictures);
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.photo_menu, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.select:
                        IPicker.open(getApplicationContext(), selectPictures);
                        break;
                    default:
                }
                return true;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();

    }
    public Bitmap convertViewToBitmap(View view){
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
