package com.example.administrator.timenote.Ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.ListManager.LoadAllList;
import com.example.administrator.timenote.Manager.ListManager.NewList;
import com.example.administrator.timenote.Manager.TaskManager.Complete;
import com.example.administrator.timenote.Manager.TaskManager.LoadAllEvent;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;

import static com.example.administrator.timenote.Model.BeanListInformation.allList;

public class List_select extends Dialog implements OnItemClickListener, ListAdapter.InnerItemOnclickListener {
    /**
     * 上下文对象 *
     */
    private Button back;// 取消按钮
    private Context context;
    private ListAdapter listAdapter;// 清单列表适配器
    private ListView listView;// 清单列表
    private ArrayList<String> listname;// 清单名称列表
    private New_List new_list;// 新建清单;
    private static int nlistid; // 获得清单id
    private static String nlistname="所有";// 当前选择的清单名称
    private Boolean issure = false;

    public Boolean getIssure() {
        return issure;
    }


//    public static int getListid() {
//        return listid;
//    }
//
//    private static int listid;

    public static int getNlistid() {
        return nlistid;
    }



    public static String getNlistname() {
        return nlistname;
    }



    public List_select(Context context) {
        super(context);
        this.context = context;
    }

    public List_select(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.list_select_1);


        /*
         * 获取验证码的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

//            WindowManager m = context.getWindowManager();
//            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//             p.height = (int) (d.getHeight() ); // 高度设置为屏幕
//            p.width = (int) (d.getWidth() ); // 宽度设置为屏幕
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        back=findViewById(R.id.back_4);
        listView = findViewById(R.id.listView3);

        initList();

        // 为按钮绑定点击事件监听器
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                dismiss();
            }
        });



        this.setCancelable(true);
    }


    // 清单选择后点击的事件回调
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.list_select_button:
                nlistname = listname.get(position);
                if(nlistname.equals("添加清单"))
                {
                    new_list = new New_List(getContext(),R.style.dialog);
                    new_list.show();
                    new_list.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        if(new_list.getIssue())
                        {
                            nlistname = new_list.getList_name();

                            // TODO 2018.6.17 添加清单nlistname
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    NewList newList = new NewList();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    newList.getRemoteInfo(nlistname);
                                }
                            });
                            t.start();
                            try {
                                t.join(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            issure = true;
                            initList();
                            dismiss();
                            Toast.makeText(context,nlistname,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                }
                else{
                    nlistid = allList.get(position).getListid();
                    issure = true;
                    dismiss();
                    Toast.makeText(context,nlistname,Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }
    @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(context,"列表 "+position+" 被点击了",Toast.LENGTH_SHORT).show();
    }

    private void initList(){
        listname=new ArrayList<String>();
//        listname.add("收集箱");
//        for(int i=0;i<10;i++)
//        {
//            listname.add("清单");
//        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                LoadAllList loadAllList = new LoadAllList();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadAllList.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid());
            }
        });
        t.start();
        try {
            t.join(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < allList.size(); i++){
            listname.add(allList.get(i).getListname());
        }
        listAdapter=new ListAdapter(context,R.layout.list_button,listname);
        listname.add("添加清单");
        nlistid = allList.get(allList.size()-1).getListid();
        listAdapter.setOnInnerItemOnClickListener(this);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

}
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        //通过view获取其内部的组件，进而进行操作
//        String text = (String) ((TextView)view.findViewById(R.id.text)).getText();
//        //大多数情况下，position和id相同，并且都从0开始
//        String showText = "点击第" + position + "项，文本内容为：" + text + "，ID为：" + id;
//        Toast.makeText(context, showText, Toast.LENGTH_LONG).show();
//    }



