package com.yootk.ssm.vo;

import java.io.Serializable;

public class Action implements Serializable {
    private String actid ;
    private String title ;
    private String rid ;

    @Override
    public String toString() {
        return "Action{" +
                "actid='" + actid + '\'' +
                ", title='" + title + '\'' +
                ", rid='" + rid + '\'' +
                '}';
    }

    public String getActid() {
        return actid;
    }

    public void setActid(String actid) {
        this.actid = actid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
