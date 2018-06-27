package com.example.administrator.timenote.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sprou on 2018/6/9.
 */

public class BeanEventInformation {
    public static List<BeanEventInformation> allEventList = new ArrayList<>();
    private int userid;
    private int eventid;
    private int dremindid;
    private int listid;
    private String eventname;
    private String eventnote;
    private int eventpriority;
    private Date eventdate;
    private Date dreminddate;
    private String leaveseventsign;// 数据库存储形式为Boolean
    private int checkBox;
    private String datetype; // 标题
    private Date date_1; // 标题
    private int pid;

    public Date getDate_1() {
        return date_1;
    }

    public void setDate_1(Date date_1) {
        this.date_1 = date_1;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDatetype() {
        return datetype;
    }

    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }

    public int getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(int cheackBox) {
        this.checkBox = cheackBox;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public int getDremindid() {
        return dremindid;
    }

    public void setDremindid(int dreminid) {
        this.dremindid = dreminid;
    }

    public int getListid() {
        return listid;
    }

    public void setListid(int listid) {
        this.listid = listid;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventnote() {
        return eventnote;
    }

    public void setEventnote(String eventnote) {
        this.eventnote = eventnote;
    }

    public int getEventpriority() {
        return eventpriority;
    }

    public void setEventpriority(int eventpriority) {
        this.eventpriority = eventpriority;
    }

    public Date getEventdate() {
        return eventdate;
    }

    public void setEventdate(Date eventdate) {
        this.eventdate = eventdate;
    }

    public Date getDreminddate() {
        return dreminddate;
    }

    public void setDreminddate(Date dreminddate) {
        this.dreminddate = dreminddate;
    }

    public String getLeaveseventsign() {
        return leaveseventsign;
    }

    public void setLeaveseventsign(String leaveseventsign) {
        this.leaveseventsign = leaveseventsign;
    }

    public BeanEventInformation(){

    }
    public BeanEventInformation(String datetype){
        this.setDatetype(datetype);
    }

}
