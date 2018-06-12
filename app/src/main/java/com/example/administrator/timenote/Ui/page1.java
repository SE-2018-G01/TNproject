package com.example.administrator.timenote.Ui;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timenote.Model.List_menu;
import com.example.administrator.timenote.Model.task;
import com.example.administrator.timenote.R;

import java.util.ArrayList;
import java.util.List;

public class page1  extends Fragment implements AdapterView.OnItemClickListener, ListAdapter.InnerItemOnclickListener{

    public static DrawerLayout drawerLayout;// 侧滑菜单
    private MenuItem gMenuItem1, gMenuItem2;// list1的两个按钮
    private LayoutInflater inflater;// 接口
    private Button list1, taday_1, all_1, list2;// 任务列表（page1）的按钮从右到左
    private TextView list_name_1;// 清单名称
    private ListView list_menu;// 清单列表
    private List<List_menu> list;
    private Level_select level_select;// 优先级选择界面
    private New_List new_list;// 新建清单
    public static NavigationView navigationView;
    private Button new_button;// 新建事务
    private static boolean stase1 = true, stase2 = false;// 列表显示状态


    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.page1, container, false);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.page1);
        navigationView = (NavigationView)view.findViewById(R.id.nav);
        list_menu = view.findViewById(R.id.list_menu_list);
        inindrawer("lzc");
        View headerView = navigationView.getHeaderView(0);//获取头布局
        new_button=view.findViewById(R.id.new_task2);

        list2 = view.findViewById(R.id.list_1);
        list2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.list_1://点击菜单，跳出侧滑菜单
                        if (drawerLayout.isDrawerOpen(navigationView)){
                            drawerLayout.closeDrawer(navigationView);
                        }else{
                            drawerLayout.openDrawer(navigationView);
                        }
                        break;
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                //item.setChecked(true);
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
        //page1按钮定义

        //顶层菜单栏
        list1 = view.findViewById(R.id.list_spot_1);
        taday_1 = view.findViewById(R.id.taday_1);
        all_1 = view.findViewById(R.id.all_1);

        list_name_1 = view.findViewById(R.id.list_name_1);
        //显示状态子菜单
        list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(list1);
            }
        });

        //回到今天按钮
        taday_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //事务列表加载到今天
                list_name_1.setText("今天");
            }
        });

        //回到所有按钮
        all_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //事务列表加载到所有
                list_name_1.setText("所有");
            }
        });

        //打开清单菜单


        MyCustomAdapter adapter = new MyCustomAdapter(this.inflater.getContext());
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        for (int i = 0; i < 10; i++) {
            adapter.addSeparatorItem(new task(new java.sql.Timestamp(System.currentTimeMillis())));
            for (int j = 0; j < 10; j++) {
                task a = new task("打人"+j, new java.sql.Timestamp(System.currentTimeMillis()), "没事", "1", R.drawable.level3, R.drawable.line);
                adapter.addItem(a);
                task b = new task("打人", new java.sql.Timestamp(System.currentTimeMillis()), "没事", "1", R.drawable.level3, R.drawable.line);
                adapter.addItem(b);
            }
        }
        adapter.setOnInnerItemOnClickListener(this);
        listView.setOnItemClickListener(this);

        //新建事务逻辑
        new_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                New_task new_task= new New_task(inflater.getContext(),R.style.dialog);
                new_task.show();
            }
        });
        return view;
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this.inflater.getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.showstyle, popupMenu.getMenu());
        gMenuItem1 = popupMenu.getMenu().findItem(R.id.complete_1);
        if (stase1 == false) {
            gMenuItem1.setTitle("显示已完成");
        }
        gMenuItem2 = popupMenu.getMenu().findItem(R.id.priorit_1);
        if (stase2 == false) {
            gMenuItem2.setTitle("按时间排序");
        }
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.complete_1:
                        if (gMenuItem1.getTitle().equals("隐藏已完成")) {
                            stase1 = false;
                            //修改状态重新加载事务列表
                        } else {
                            stase1 = true;
                            //修改状态重新加载事务列表
                        }
                        break;
                    case R.id.priorit_1:
                        if (gMenuItem2.getTitle().equals("按优先级排序")) {
                            stase2 = false;
                            //修改状态重新加载事务列表
                        } else {
                            stase2 = true;
                            //修改状态重新加载事务列表
                        }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(),"列表 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.taskdes:
            case R.id.data_1:
            case R.id.taskname:
                Intent intent = new Intent(getContext(), Task_Update.class);
                intent.putExtra("taskname", "打人");
                getContext().startActivity(intent);
                Toast.makeText(getContext(), "an", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_level:
                level_select=new Level_select(getContext(),R.style.dialog);
                level_select.show();
                // 监听弹框消失
                level_select.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
                break;
            case R.id.list_menu_name:
            case R.id.imageView4:
                String name = list.get(position).getList_menu_name();
                if(name.equals("添加清单"))
                {
                    new_list = new New_List(getContext(),R.style.dialog);
                    new_list.show();
                    new_list.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        public void onDismiss(DialogInterface dialog) {
                            if (new_list.getIssue()) {
                                Toast.makeText(getContext(), new_list.getList_name(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else if(name.equals("设置清单")){
                    Intent intent1 = new Intent(getContext(),List_Update.class);
                    startActivity(intent1);
                }
                else {
                    inindrawer("lml");
                }

                break;
            default:
                break;
        }
    }
    public void inindrawer(String name){
        list = new ArrayList<>();
        Bitmap bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.menu_list);
        List_menu list_menu_item = new List_menu(bmp,"所有");
        list.add(list_menu_item);
        list_menu_item = new List_menu(bmp,"今天");
        list.add(list_menu_item);
        list_menu_item = new List_menu(bmp,"近三天");
        list.add(list_menu_item);
        list_menu_item = new List_menu(bmp,"收集箱");
        list.add(list_menu_item);
        for(int i=0;i<10;i++){
            list_menu_item = new List_menu(bmp,name+i);
            list.add(list_menu_item);
        }
        bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.plus);
        list_menu_item = new List_menu(bmp,"新建清单");
        list.add(list_menu_item);
        bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.setup1);
        list_menu_item = new List_menu(bmp,"设置清单");
        list.add(list_menu_item);

        List_menu_Adapter list_menu_adapter = new List_menu_Adapter(this.getContext(),R.layout.list_menu_button,list);
        list_menu.setAdapter(list_menu_adapter);
        list_menu_adapter.setOnInnerItemOnClickListener(this);
        list_menu.setOnItemClickListener(this);
    }
}

