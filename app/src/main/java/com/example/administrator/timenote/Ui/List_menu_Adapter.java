package com.example.administrator.timenote.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.timenote.Model.List_menu;
import com.example.administrator.timenote.Model.task;
import com.example.administrator.timenote.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class List_menu_Adapter extends ArrayAdapter<List_menu> implements View.OnClickListener {
    private int resourceId;
    private ListAdapter.InnerItemOnclickListener mListener;

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
        holder.button.setOnClickListener(this);
        holder.button.setTag(position);
        return view;
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