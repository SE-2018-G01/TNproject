package com.example.administrator.timenote.Ui;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.timenote.Manager.ListManager.UpdateList;
import com.example.administrator.timenote.Model.List_menu;
import com.example.administrator.timenote.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.timenote.Model.BeanListInformation.allList;

public class List_Update extends AppCompatActivity implements AdapterView.OnItemClickListener, ListAdapter.InnerItemOnclickListener{

    private Button back;// 返回按钮
    private ListView listView;// 清单列表
    private List<List_menu> list;
    private List_update_dialog list_update_dialog;
    private static int position;

    public static int getPosition() {
        return position;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_list_update);
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // 绑定按钮
        back = findViewById(R.id.back_11);
        listView = findViewById(R.id.list_update_listview);

        // 返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 列表点击
        inindrawer();

    }
    public void inindrawer(){
        list = new ArrayList<>();
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_list);
        List_menu list_menu_item ;
        for(int i=0 ; i < allList.size(); i++){
            list_menu_item = new List_menu(bmp,allList.get(i).getListname());
            list.add(list_menu_item);
        }

        List_menu_Adapter list_menu_adapter = new List_menu_Adapter(this,R.layout.list_menu_button,list);
        listView.setAdapter(list_menu_adapter);
        list_menu_adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    String name;
    @Override
    public void itemClick(View v) {
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.imageView4:
            case R.id.list_menu_name:
                name = list.get(position).getList_menu_name();
                list_update_dialog = new List_update_dialog(this,R.style.dialog);
                list_update_dialog.show();
                list_update_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(list_update_dialog.getIssue())
                        {
                            name = list_update_dialog.getList_name();
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    UpdateList updateList = new UpdateList();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    updateList.getRemoteInfo(name,allList.get(position).getListid());
                                }
                            });
                            t.start();
                            try {
                                t.join(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            update();
                        }

                    }
                });

                break;
            default:
                break;

        }
    }
    public void update(){
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.menu_list);
        List_menu list_menu_item = new List_menu(bmp,name);
        list.set(position,list_menu_item);
        List_menu_Adapter list_menu_adapter = new List_menu_Adapter(this,R.layout.list_menu_button,list);
        listView.setAdapter(list_menu_adapter);
        list_menu_adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);
    }
}
