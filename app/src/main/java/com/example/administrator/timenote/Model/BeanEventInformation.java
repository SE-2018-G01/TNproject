package com.example.administrator.timenote.Model;

import java.util.Date;

/**
 * Created by Sprou on 2018/6/9.
 */

public class BeanEventInformation {
    private int userid;
    private int eventid;
    private int dreminid;
    private int listid;
    private String eventname;
    private String eventnote;
    private int eventpriority;
    private Date eventdate;
    private Date dreminddate;
    private String leaveseventsign;// 数据库存储形式为Boolean

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

    public int getDreminid() {
        return dreminid;
    }

    public void setDreminid(int dreminid) {
        this.dreminid = dreminid;
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
}
