package com.example.administrator.timenote.Ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.example.administrator.timenote.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<String> implements OnClickListener {
    private int resourceId;
    private InnerItemOnclickListener mListener;

    public ListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource,objects);
        resourceId=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder=null;
        @SuppressLint("ViewHolder")
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null,false);
        holder=new ViewHolder();
        holder.mButton= (Button) view.findViewById(R.id.list_select_button);
        holder.mButton.setText(getItem(position));
        holder.mButton.setOnClickListener(this);
        holder.mButton.setTag(position);
        return view;

        }

    interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
        this.mListener=listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
    static class ViewHolder{
        Button mButton;
    }
}