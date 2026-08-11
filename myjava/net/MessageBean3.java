package net;

import java.sql.Timestamp;

public class MessageBean3 {
    private int num;
    private String fid;
    private String tid;
    private String msg;
    private String sendDate;

    public int getNo() {
        return num;
    }

    public void setNo(int num) {
        this.num = num;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMdate() {
        return sendDate;
    }

    public void setMdate(String sendDate) {
        this.sendDate = sendDate;
    }
}
