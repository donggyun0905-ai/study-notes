package net;

import member.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/*
* ChatMgr3 만들기
1.table3.sql
 - tblRegister, tblMessage 생성
 - tblRegister 3개의 레코드 저장
2.tblMessage 빈즈 선언
 - MessageBean.java
 - tblRegister은 빈즈 안 만듬
3.ChatMgr3.java 생성
 - public boolean loginChk(String id, String pwd) {} <- 로그인
 - public void insertMsg(MessageBean bean) {} <- 쪽지입력
 - public Vector<MessageBean> getMsgList(String id){} <- 쪽지리스트
* */

public class ChatMgr3 {

    private DBConnectionMgr pool;

    public ChatMgr3() {
        pool = DBConnectionMgr.getInstance();
    }

    //로그인
    public boolean loginChk(String id, String pwd) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        boolean login = false;

        try {
            con = pool.getConnection();
            sql = "select * from tblRegister where id = ? and pw = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            rs = pstmt.executeQuery();
            if (rs.next()) login = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return login;
    }

    //쪽지 입력
    public void insertMsg(MessageBean bean) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;

        try {
            con = pool.getConnection();
            sql = "insert into tblMessage(num, sender_id, receiver_id, content, send_date) "
                    + "values (null, ?, ?, ?, now())";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bean.getSenderId());
            pstmt.setString(2, bean.getReceiverId());
            pstmt.setString(3, bean.getContent());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
    }

    //쪽지 리스트
    public Vector<MessageBean> getMsgList(String id) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<MessageBean> vlist = new Vector<MessageBean>();

        try {
            con = pool.getConnection();
            sql = "select * from tblMessage where receiver_id = ? order by send_date desc";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MessageBean bean = new MessageBean();
                bean.setNum(rs.getInt("num"));
                bean.setSenderId(rs.getString("sender_id"));
                bean.setReceiverId(rs.getString("receiver_id"));
                bean.setContent(rs.getString("content"));
                bean.setSendDate(rs.getTimestamp("send_date"));

                vlist.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    public static void main(String[] args) {
        ChatMgr3 mgr = new ChatMgr3();

        //로그인
        System.out.println("로그인 결과: " + mgr.loginChk("aaa", "aaa"));

        //쪽지
        MessageBean bean = new MessageBean();
        bean.setSenderId("aaa");
        bean.setReceiverId("aaa");
        bean.setContent("다 때려 박아 범퍼카");
        mgr.insertMsg(bean);

        //쪽지 fltmxm
        Vector<MessageBean> list = mgr.getMsgList("aaa");
        for (MessageBean m : list) {
            System.out.println("[" + m.getNum() + "] " + m.getSenderId()
                    + " -> " + m.getReceiverId() + " : " + m.getContent()
                    + " (" + m.getSendDate() + ")");
        }
    }
}
