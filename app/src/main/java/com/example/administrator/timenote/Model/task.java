package com.example.administrator.timenote.Model;

import android.widget.CheckBox;

import java.util.Date;

public class task {
    private Date date_1;
    private String datetype;
    private String taskdes;
    private String taskname;
    private String taskp;
    private int checkBox;
    private int eventid;
    private int imageId;
    private int ViewId;
    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }
    public int getImageId() {
        return imageId;
    }
    public int getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(int checkBox) {
        this.checkBox = checkBox;
    }
    public int getViewId() {
        return ViewId;
    }



    public String getTaskdes() {
        return taskdes;
    }

    public String getTaskp() {
        return taskp;
    }
    public task(Date date_1){
        this.date_1=date_1;
    }

    public task(String datetype){
        this.setDatetype(datetype);
    }


    public task(String taskname, Date date_1,String taskdes,int imageId,int viewId){
        this.date_1=date_1;
        this.taskname=taskname;
        this.taskdes=taskdes;
        this.imageId=imageId;
        this.ViewId=viewId;
    }
    public task(String taskname, Date date_1,String taskdes,int imageId,int viewId,int checkBox,int evevtid){
        this.date_1=date_1;
        this.taskname=taskname;
        this.taskdes=taskdes;
        this.imageId=imageId;
        this.ViewId=viewId;
        this.checkBox = checkBox;
        this.eventid = evevtid;
    }
    public String getTaskname() {
        return taskname;
    }


    public Date getDate_1() {
        return date_1;
    }


    public String getDatetype() {
        return datetype;
    }

    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }
}
