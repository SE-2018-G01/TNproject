package com.example.administrator.timenote.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.timenote.Model.BeanEventInformation;
import com.example.administrator.timenote.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeSet;

public class MyCustomAdapter3 extends BaseAdapter implements View.OnClickListener{

    public static ArrayList<BeanEventInformation> getData() {
        return data;
    }

    public static void setData(ArrayList<BeanEventInformation> data) {
        MyCustomAdapter3.data = data;
    }

    private static ArrayList<BeanEventInformation> data = new ArrayList<>();
    private LayoutInflater inflater;
    private ListAdapter.InnerItemOnclickListener mListener;
    //private TreeSet<Integer> set = new TreeSet<>();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
    private int index = 0;


    public MyCustomAdapter3(Context context) {
        data.clear();
        inflater = LayoutInflater.from(context);
    }

    public void addItem(BeanEventInformation item) {
        data.add(item);
    }

//    public void addSeparatorItem(BeanEventInformation item) {
//        data.add(item);
//        set.add(data.size() - 1);
//    }
//
//    public int getItemViewType(int position) {
//        return set.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
//    }

//    @Override
//    public int getViewTypeCount() {
//        return TYPE_MAX_COUNT;
//    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;


            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.task_secect_button, null);
            holder.taskname = convertView .findViewById(R.id.task_select_button_1);
            holder.t = data.get(position);
            holder.taskname.setText(holder.t.getEventname());
            holder.taskname.setOnClickListener(this);
            holder.taskname.setTag(position);


        return convertView;
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

    public static class ViewHolder {
        public TextView taskname;
        public TextView date1;
        public View view_line;
        public TextView taskdes;
        //public TextView taskp;
        public ImageView ImageId;
        private CheckBox checkBox;
        public SimpleDateFormat format;
        public  BeanEventInformation t;
    }
}