package guestbook;

public class GuestBookBean {
    private int num;
    private String id;
    private String contents;
    private String ip;
    private String regdate;
    private String regtime;
    private String secret;

    public GuestBookBean() {
    }

    public GuestBookBean(int num, String id, String contents, String ip, String regdate, String regtime, String secret) {
        this.num = num;
        this.id = id;
        this.contents = contents;
        this.ip = ip;
        this.regdate = regdate;
        this.regtime = regtime;
        this.secret = secret;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
