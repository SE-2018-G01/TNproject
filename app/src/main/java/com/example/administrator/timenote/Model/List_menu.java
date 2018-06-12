package com.example.administrator.timenote.Model;

import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;

public class List_menu {
    private Bitmap imageView;
    private String list_menu_name;
    public List_menu(){};
    public List_menu(Bitmap imageView,String list_menu_name){
        this.imageView = imageView;
        this.list_menu_name = list_menu_name;
    }
    public Bitmap getImageView() {
        return imageView;
    }

    public void setImageView(Bitmap imageView) {
        this.imageView = imageView;
    }

    public String getList_menu_name() {
        return list_menu_name;
    }

    public void setList_menu_name(String list_menu_name) {
        this.list_menu_name = list_menu_name;
    }



}
