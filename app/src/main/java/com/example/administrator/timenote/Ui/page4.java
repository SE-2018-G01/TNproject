package com.example.administrator.timenote.Ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.administrator.timenote.Manager.DremindManager.ChangeDRTime;
import com.example.administrator.timenote.Manager.DremindManager.ChangeLeavesTime;
import com.example.administrator.timenote.Manager.DremindManager.ChangeRepeat;
import com.example.administrator.timenote.Manager.DremindManager.ChangeRing;
import com.example.administrator.timenote.Manager.DremindManager.ChangeVib;
import com.example.administrator.timenote.Manager.DremindManager.LoadDremind;
import com.example.administrator.timenote.Model.BeanDRemindInformation;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class page4 extends Fragment {

    private ArrayAdapter adapter_time = null;// 时间下拉列表配置器
    private ArrayAdapter adapter_even = null;// 频率下拉列表配置器
    private ArrayAdapter adapter_ring =null;// 铃声下拉列表配置器
    public static ImageView imageButton1; // 头像按钮
    private TimePickerView pvTime;// 时间自定义界面
    private Switch switch1;// 振动按钮
    private Spinner time_select_1; // 提醒时间下拉列表
    private Spinner time_ring_1;// 提醒闹铃下拉列表
    private Spinner time_even_1;// 时间
    private TextView time_text;// 自定义时间提醒框
    private TextView time_even_text;// 自定义频率
    private static TextView user_name_page4;// 用户名显示
    private Button user_psonal_button;// 个人中心
    private TextView yezi_time;// 叶子时长
    private TextView yezi_2;// 设置叶子时长
    private yezi_time_select yezi_time_select1;//设置叶子时长界面
    private int time_p;// 定义时间位置
    private String time_get;// 获取时间

    public static void setUser_name_page4(String user_name_page4) {
        page4.user_name_page4.setText(user_name_page4);
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page4, container, false);

        // 对应控件
        imageButton1 = view.findViewById(R.id.imageButton); // 头像按钮
        switch1 = view.findViewById(R.id.switch1);// 振动开关
        time_select_1 = view.findViewById(R.id.time_select_1);// 时间选择
        time_ring_1 = view.findViewById(R.id.time_ring_1);// 闹铃选择
        time_even_1 = view.findViewById(R.id.time_even_1);// 重复选择
        time_text = view.findViewById(R.id.time_text_1);// 自定义时间显示
        //time_even_text = view.findViewById(R.id.retime_text_1);// 重复选择存储文本
        user_name_page4 = view.findViewById(R.id.user_name_page4);// 用户名文本
        user_psonal_button = view.findViewById(R.id.user_psonal_button);//
        yezi_time = view.findViewById(R.id.yezi_time);// 叶子时间选择
        yezi_2 = view.findViewById(R.id.yezi_2);// 叶子时间显示

        initloaddefault();

        // 初始叶子时长
        yezi_time.setText(String.valueOf(BeanDRemindInformation.defaultset.getLeavestime()) + "分钟");

        // 初始默认提醒时间
        switch (BeanDRemindInformation.defaultset.getDefaulttime()){
            case "0":time_p = 0;break;
            case "1":time_p = 1;break;
            case "2":time_p = 2;break;
            case "3":time_p = 3;break;
            case "4":time_p = 4;break;
            case "5":time_p = 5;break;
            case "6":time_p = 6;break;
            case "7":time_p = 7;break;
            case "8":time_p = 8;break;
            case "9":time_p = 9;break;
            case "10":time_p = 10;break;
            case "11":time_p = 11;break;
            default:time_p = 12;break;
        }

        // 初始震动状态
        if (BeanDRemindInformation.defaultset.getDremindvib().equals("true"))
            switch1.setChecked(true);
        else
            switch1.setChecked(false);

        // 显示用户名
        if(BeanUserInformation.currentLoginUser.getIcon()!=null)
        {
            Bitmap bm;
            byte[] bitmapByte = BeanUserInformation.currentLoginUser.getIcon();
            if (bitmapByte.length != 0) {
                bm = BitmapFactory.decodeByteArray(bitmapByte, 0,bitmapByte.length);
                imageButton1.setImageBitmap(bm);
            } else {

            }
        }
        user_name_page4.setText(BeanUserInformation.currentLoginUser.getUsername());

        // 头像按钮
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(),User_information.class);
                startActivity(intent1);
            }
        });

        //振动按钮
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getContext(),"开启振动",Toast.LENGTH_SHORT).show();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            ChangeVib changeVib = new ChangeVib();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            changeVib.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),-99,1);
                        }
                    });
                    t.start();
                    try {
                        t.join(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getContext(),"关闭振动",Toast.LENGTH_SHORT).show();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            ChangeVib changeVib = new ChangeVib();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            changeVib.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),-99,0);
                        }
                    });
                    t.start();
                    try {
                        t.join(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //叶子时长选择
        yezi_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yezi_time_select1 =new yezi_time_select(getContext(),R.style.dialog);
                yezi_time_select1.show();
                yezi_time_select1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(yezi_time_select1.getYezi_time()!=null) {
                            yezi_time.setText(yezi_time_select1.getYezi_time() + "分钟");
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    ChangeLeavesTime changeLeavesTime = new ChangeLeavesTime();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    changeLeavesTime.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),-99,Integer.parseInt(yezi_time_select1.getYezi_time()));
                                }
                            });
                            t.start();
                            try {
                                t.join(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            initloaddefault();
                            Yezi_start.setM(Integer.parseInt(yezi_time_select1.getYezi_time()));
                        }
                    }
                });
            }
        });
        yezi_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yezi_time_select1 =new yezi_time_select(getContext(),R.style.dialog);
                yezi_time_select1.show();
                yezi_time_select1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(yezi_time_select1.getYezi_time()!=null) {
                            yezi_time.setText(yezi_time_select1.getYezi_time() + "分钟");
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    ChangeLeavesTime changeLeavesTime = new ChangeLeavesTime();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    changeLeavesTime.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(), -99, Integer.parseInt(yezi_time_select1.getYezi_time()));
                                }
                            });
                            t.start();
                            try {
                                t.join(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            initloaddefault();
                            Yezi_start.setM(Integer.parseInt(yezi_time_select1.getYezi_time()));
                        }
                    }
                });
            }
        });

        // 进入个人中心
        user_psonal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Pesonal.class);
                startActivity(intent);
            }
        });

        // 重复选择列表
        adapter_even = ArrayAdapter.createFromResource(getContext(),R.array.time_even,android.R.layout.simple_spinner_item);
        adapter_even.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_even_1.setAdapter(adapter_even);
        // 初始重复
        time_even_1.setSelection(BeanDRemindInformation.defaultset.getDremindrepeat(),true);
        time_even_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                // 更新设置
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        ChangeRepeat changeRepeat = new ChangeRepeat();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        changeRepeat.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),-99, position);

                    }
                });
                t.start();
                try {
                    t.join(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initloaddefault();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 铃声选择列表
        adapter_ring = ArrayAdapter.createFromResource(getContext(),R.array.time_ring,android.R.layout.simple_spinner_item);
        adapter_ring.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_ring_1.setAdapter(adapter_ring);
        // 初始铃声
        time_ring_1.setSelection(BeanDRemindInformation.defaultset.getDremindring(),true);
        time_ring_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                    // 更新设置
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            ChangeRing changeRing = new ChangeRing();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            changeRing.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),-99, position);
                        }
                    });
                    t.start();
                    try {
                        t.join(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                initloaddefault();
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 时间选择列表
        adapter_time = ArrayAdapter.createFromResource(getContext(),R.array.time_select,android.R.layout.simple_spinner_item);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_select_1.setAdapter(adapter_time);
        // 初始默认提醒时间
        time_select_1.setSelection(time_p,true);
        if (time_p == 12){
            time_text.setText(BeanDRemindInformation.defaultset.getDefaulttime());
        }
        time_select_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                final String cityName = (String) adapter_time.getItem(position);
                if(cityName.equals("自定义"))
                {
                    initCustomTimePicker2();
                    pvTime.show();
                    pvTime.setOnDismissListener(new OnDismissListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onDismiss(Object o) {
                            if(time_text.getVisibility()==0)
                            {
                                // 更新设置
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        ChangeDRTime loadDremind = new ChangeDRTime();
                                        try {
                                            Thread.sleep(300);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        loadDremind.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),-99, time_get);

                                    }
                                });
                                t.start();
                                try {
                                    t.join(30000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                initloaddefault();
                            }
                        }
                    });
                }
                else
                {
                    time_text.setVisibility(View.INVISIBLE);
                    // 更新设置
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            ChangeDRTime loadDremind = new ChangeDRTime();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            loadDremind.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),-99, String.valueOf(position));
                        }
                    });
                    t.start();
                    try {
                        t.join(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    initloaddefault();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
    private void initCustomTimePicker2() {

        Calendar selectedDate = Calendar.getInstance();
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                time_get = getTime(date);
                time_text.setText(getTime(date));
                time_text.setVisibility(View.VISIBLE);
            }
        })
                .setDate(selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .isDialog(true)
                .build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.CENTER);//改成Bottom,底部显示
            }
        }
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    private void initloaddefault(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                LoadDremind loadDremind = new LoadDremind();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadDremind.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid(),-99);
                BeanDRemindInformation.defaultset = BeanDRemindInformation.tsset;
            }
        });
        t.start();
        try {
            t.join(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}