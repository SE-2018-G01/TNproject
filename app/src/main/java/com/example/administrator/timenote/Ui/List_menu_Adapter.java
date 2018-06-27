package com.example.administrator.timenote.Ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timenote.Manager.ListManager.DeleteList;
import com.example.administrator.timenote.Manager.ListManager.LoadAllList;
import com.example.administrator.timenote.Model.BeanListInformation;
import com.example.administrator.timenote.Model.BeanUserInformation;
import com.example.administrator.timenote.Model.List_menu;
import com.example.administrator.timenote.Model.task;
import com.example.administrator.timenote.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class List_menu_Adapter extends ArrayAdapter<List_menu> implements View.OnClickListener, View.OnLongClickListener {
    private int resourceId;
    private ListAdapter.InnerItemOnclickListener mListener;
    private int position;

    public List_menu_Adapter(Context context, int resource, List<List_menu> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        List_menu l = getItem(position);
        ViewHolder holder = new ViewHolder();
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null,false);
        holder.imageView = view.findViewById(R.id.imageView4);
        holder.button = view.findViewById(R.id.list_menu_name);
        holder.imageView.setImageBitmap(l.getImageView());
        holder.button.setText(l.getList_menu_name());

        holder.imageView.setOnClickListener(this);
        holder.imageView.setTag(position);
        holder.imageView.setOnLongClickListener(this);
        holder.button.setOnClickListener(this);
        holder.button.setTag(position);
        holder.button.setOnLongClickListener(this);

        return view;
    }

    @Override
    public boolean onLongClick(View v) {
        position = (Integer) v.getTag();
        if (!getItem(position).getList_menu_name().equals("收集箱")) {
            Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder alterDialog = new AlertDialog.Builder(getContext());
            alterDialog.setTitle("提示！");
            alterDialog.setMessage("您确定要删除这个清单吗？所属任务将会加入默认列表");
            alterDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO: 2018/6/26 清单删除
                            Thread r = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    DeleteList deleteList = new DeleteList();
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    deleteList.getRemoteInfo(BeanListInformation.allList.get(position).getListid());

                                }
                            });
                            r.start();
                            try {
                                r.join(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

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

                            page1.list_update.delete_update();
                        }

                    }
            );
            alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alterDialog.show();
        }
        return true;

    }

    interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(ListAdapter.InnerItemOnclickListener listener){
        this.mListener=listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
    static class ViewHolder{
        Button button;
        ImageView imageView;
    }
}