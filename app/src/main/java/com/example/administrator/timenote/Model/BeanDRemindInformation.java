package com.example.administrator.timenote.Model;

/**
 * Created by XuanWem Chen on 2018/6/21.
 */

public class BeanDRemindInformation {
    public static BeanDRemindInformation defaultset;
    public static BeanDRemindInformation tsset;
    private int dremindid;
    private int userid;
    private int dremindring;
    private int dremindrepeat;
    private int aheadtime;
    private int eventid;
    private int leavestime;
    private int pid;
    private String defaulttime;
    private String dremindvib;// 数据库中为Boolean

    public int getDremindid() {
        return dremindid;
    }

    public void setDremindid(int dremindid) {
        this.dremindid = dremindid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getDremindring() {
        return dremindring;
    }

    public void setDremindring(int dremindring) {
        this.dremindring = dremindring;
    }

    public int getDremindrepeat() {
        return dremindrepeat;
    }

    public void setDremindrepeat(int dremindrepeat) {
        this.dremindrepeat = dremindrepeat;
    }

    public int getAheadtime() {
        return aheadtime;
    }

    public void setAheadtime(int aheadtime) {
        this.aheadtime = aheadtime;
    }

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public int getLeavestime() {
        return leavestime;
    }

    public void setLeavestime(int leavestime) {
        this.leavestime = leavestime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDefaulttime() {
        return defaulttime;
    }

    public void setDefaulttime(String defaulttime) {
        this.defaulttime = defaulttime;
    }

    public String getDremindvib() {
        return dremindvib;
    }

    public void setDremindvib(String dremindvib) {
        this.dremindvib = dremindvib;
    }

}
