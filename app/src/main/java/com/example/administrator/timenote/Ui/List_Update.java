package com.example.administrator.timenote.Ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.timenote.Manager.ListManager.UpdateList;
import com.example.administrator.timenote.Model.List_menu;
import com.example.administrator.timenote.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.timenote.Model.BeanListInformation.allList;

public class List_Update extends Dialog implements AdapterView.OnItemClickListener, ListAdapter.InnerItemOnclickListener{

    private Button back;// 返回按钮
    private Context context;
    private ListView listView;// 清单列表
    private List<List_menu> list;
    private List_update_dialog list_update_dialog;
    private static int position;

    public List_Update(){
        super(null);
    }

    public static int getPosition() {
        return position;
    }
    public List_Update(Context context) {
        super(context);
        this.context = context;
    }

    public List_Update(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_update);
        Window dialogWindow = this.getWindow();

//        WindowManager m = context.getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() ); // 高度设置为屏幕
//        p.width = (int) (d.getWidth() ); // 宽度设置为屏幕
        dialogWindow.setAttributes(p);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // 绑定按钮
        back = findViewById(R.id.back_11);
        listView = findViewById(R.id.list_update_listview);

        // 返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 列表点击
        inindrawer();

    }
    public void inindrawer(){
        list = new ArrayList<>();
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_list);
        List_menu list_menu_item ;
        for(int i=0 ; i < allList.size(); i++){
            list_menu_item = new List_menu(bmp,allList.get(i).getListname());
            list.add(list_menu_item);
        }

        List_menu_Adapter list_menu_adapter = new List_menu_Adapter(context,R.layout.list_menu_button,list);
        listView.setAdapter(list_menu_adapter);
        list_menu_adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    private String nameget;
    @Override
    public void itemClick(View v) {
        position = (Integer) v.getTag();
        if (!list.get(position).getList_menu_name().equals("收集箱")) {
            switch (v.getId()) {
                case R.id.imageView4:
                case R.id.list_menu_name:
                    nameget = list.get(position).getList_menu_name();
                    list_update_dialog = new List_update_dialog(context, R.style.dialog);
                    list_update_dialog.show();
                    list_update_dialog.setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (list_update_dialog.getIssue()) {
                                nameget = list_update_dialog.getList_name();
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
                                        updateList.getRemoteInfo(nameget, allList.get(position).getListid());
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
    }
    public void update(){
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_list);
        List_menu list_menu_item = new List_menu(bmp,nameget);
        list.set(position,list_menu_item);
        List_menu_Adapter list_menu_adapter = new List_menu_Adapter(context,R.layout.list_menu_button,list);
        listView.setAdapter(list_menu_adapter);
        list_menu_adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);
    }
    public void delete_update(){
        inindrawer();
        List_menu_Adapter list_menu_adapter = new List_menu_Adapter(context,R.layout.list_menu_button,list);
        listView.setAdapter(list_menu_adapter);
        list_menu_adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);
    }
}
