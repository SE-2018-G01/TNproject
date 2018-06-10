package com.example.administrator.timenote.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.timenote.Model.task;
import com.example.administrator.timenote.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeSet;

public class MyCustomAdapter extends BaseAdapter implements View.OnClickListener{

    private ArrayList<task> data = new ArrayList<>();
    private LayoutInflater inflater;
    private ListAdapter.InnerItemOnclickListener mListener;
    private TreeSet<Integer> set = new TreeSet<Integer>();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;


    public MyCustomAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void addItem(task item) {
        data.add(item);
    }

    public void addSeparatorItem(task item) {
        data.add(item);
        set.add(data.size() - 1);
    }

    public int getItemViewType(int position) {
        return set.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

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
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case TYPE_SEPARATOR:
                    convertView = inflater.inflate(R.layout.item2, null);
                    holder.date1 = convertView .findViewById(R.id.item_2);
                    holder.t = data.get(position);
//                    holder.format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:SS");
//                    holder.date1.setText(holder.format.format(holder.t.getDate_1()));
                    break;

                case TYPE_ITEM:
                    convertView = inflater.inflate(R.layout.arrary_button, null);
                    holder.taskname = convertView .findViewById(R.id.taskname);
                    holder.date1 = convertView .findViewById(R.id.data_1);
                    holder.view_line = convertView .findViewById(R.id.view_color_1);
                    holder.taskdes = convertView .findViewById(R.id.taskdes);
                    holder.ImageId = convertView .findViewById(R.id.image_level);
                    break;

            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.t = data.get(position);
        switch (type) {
            case TYPE_SEPARATOR:
                holder.format = new SimpleDateFormat("yyyy年MM月dd日");
                holder.date1.setText(holder.format.format(holder.t.getDate_1()));
                break;
            case TYPE_ITEM:
                holder.taskname.setText(holder.t.getTaskname());
                holder.taskname.setOnClickListener(this);
                holder.taskname.setTag(position);

                holder.format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:SS");
                holder.date1.setText(holder.format.format(holder.t.getDate_1()));
                holder.date1.setOnClickListener(this);
                holder.date1.setTag(position);

                holder.taskdes.setText(holder.t.getTaskdes());
                holder.taskdes.setOnClickListener(this);
                holder.taskdes.setTag(position);

                holder.ImageId.setImageResource(holder.t.getImageId());
                holder.ImageId.setOnClickListener(this);
                holder.ImageId.setTag(position);

                break;
        }


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
        public TextView taskp;
        public ImageView ImageId;
        public SimpleDateFormat format;
        public  task t;
    }
}