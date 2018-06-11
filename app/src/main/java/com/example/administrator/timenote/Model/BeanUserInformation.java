package com.example.administrator.timenote.Model;

import java.util.Date;

/**
 * Created by Sprou on 2018/5/27.
 */

public class BeanUserInformation {
    public static BeanUserInformation currentLoginUser = null;
    public static BeanUserInformation tryLoginUser = null;
    private int userid;
    private String username;
    private String userpassword;
    private String useremail;
    private int leavesid;
    private String creatdate;
    private String stopdate;
    private String usercalendar; // 数据库存储形式为Boolean
    private String usertypeface;
    private int achievement;
    private String authcode;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public int getLeavesid() {
        return leavesid;
    }

    public void setLeavesid(int leavesid) {
        this.leavesid = leavesid;
    }

    public String getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(String creatdate) {
        this.creatdate = creatdate;
    }

    public String getStopdate() {
        return stopdate;
    }

    public void setStopdate(String stopdate) {
        this.stopdate = stopdate;
    }

    public String getUsercalendar() {
        return usercalendar;
    }

    public void setUsercalendar(String usercalendar) {
        this.usercalendar = usercalendar;
    }

    public String getUsertypeface() {
        return usertypeface;
    }

    public void setUsertypeface(String usertypeface) {
        this.usertypeface = usertypeface;
    }

    public int getAchievement() {
        return achievement;
    }

    public void setAchievement(int achievement) {
        this.achievement = achievement;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
}