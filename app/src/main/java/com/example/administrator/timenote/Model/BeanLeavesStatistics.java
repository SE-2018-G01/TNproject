package com.example.administrator.timenote.Model;


import java.util.Date;

/**
 * Created by XuanWem Chen on 2018/6/22.
 */

public class BeanLeavesStatistics {
    public static BeanLeavesStatistics leavesStatistics;
    public static BeanLeavesStatistics todayleaves;
    private Date leavesdate;
    private int leavesamount;
    private int focustime;
    private int userid;

    public Date getLeavesdate() {
        return leavesdate;
    }

    public void setLeavesdate(Date leavesdate) {
        this.leavesdate = leavesdate;
    }

    public int getLeavesamount() {
        return leavesamount;
    }

    public void setLeavesamount(int leavesamount) {
        this.leavesamount = leavesamount;
    }

    public int getFocustime() {
        return focustime;
    }

    public void setFocustime(int focustime) {
        this.focustime = focustime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

}
