package com.example.administrator.timenote.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XuanWem Chen on 2018/6/17.
 */

public class BeanListInformation {
    public static List<BeanListInformation> allList = new ArrayList<>();
    private int userid;
    private int listid;
    private String listname;
    private Date createdate;
    private String backgroundcolor;
    private int listpriority;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getListid() {
        return listid;
    }

    public void setListid(int listid) {
        this.listid = listid;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    public int getListpriority() {
        return listpriority;
    }

    public void setListpriority(int listpriority) {
        this.listpriority = listpriority;
    }


}
