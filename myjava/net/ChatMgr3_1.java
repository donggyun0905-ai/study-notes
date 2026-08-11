package net;

import member.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;


public class ChatMgr3_1 {
    private DBConnectionMgr pool;

    public ChatMgr3_1()  {
        pool = DBConnectionMgr.getInstance();
    }

    //Login
    public boolean loginChk(String id, String pwd){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        boolean flag = false;

        try {
            con = pool.getConnection();
            sql = "select count(id) from tblregister where id = ? and pwd = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.setString(2,pwd);
            rs = pstmt.executeQuery();
            //db 성능에서 카운트한 단 하나의 숫자 리턴이 더 유리하다
            if(rs.next() && rs.getInt(1) == 1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return flag;
    }

    //Message Insert
    public void insertMsg(MessageBean3 bean){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		String sql = null;
        		try {
        			con = pool.getConnection();
        			sql = "insert into tblmessage values(null, ?,?,?,now())";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1,bean.getFid());
                    pstmt.setString(2,bean.getTid());
                    pstmt.setString(3,bean.getMsg());
                    //DML (INSERT,UPDATE,DELECT)를 실행하기 위한 메소드, 리턴 값은 적용된 레코드 개수
        			pstmt.executeUpdate();

        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt);
        		}
    }

    //Message List
    public Vector<MessageBean3> getMsgList(String id /*tid or fid*/){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
        		Vector<MessageBean3> vlist = new Vector<MessageBean3>();

        		try {
        			con = pool.getConnection();
                    //모든 컬럼 리턴할때 *는 추천하지 않는다. 임시 또는 테스트 일때만 권장
        			sql = "select * from tblMessage where fid = ? or tid = ?";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, id);
                    pstmt.setString(2, id);
        			rs = pstmt.executeQuery();
                    while(rs.next()){
                        MessageBean3  bean = new MessageBean3();
                        bean.setNo(rs.getInt("no"));
                        bean.setFid(rs.getString("fid"));
                        bean.setTid(rs.getString("tid"));
                        bean.setMsg(rs.getString("msg"));
                        //DB에 있는 모든 data는 String 리턴 가능. timestamp 이지만 문자열 타입으로 리턴가능
                        bean.setMdate(rs.getString("mdate"));
                        //실제 Vector 객체의 주소값 저장이 되므로 동일한 변수명(bean)이라도 관계 없음
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
        ChatMgr3_1 mgr = new ChatMgr3_1();
        boolean result = mgr.loginChk("aaa","1234");
        System.out.println("result: " + result);

        MessageBean3 bean = new MessageBean3();
        bean.setFid("aaa");
        bean.setTid("bbb");
        bean.setMsg("밥 먹자~~");
        //mgr.insertMmg(bean);

        MessageBean3 bean2 = new MessageBean3();
        bean2.setFid("bbb");
        bean2.setTid("aaa");
        bean2.setMsg("밥 먹자~~");
        //mgr.insertMmg(bean2);

        Vector<MessageBean3> vlist = mgr.getMsgList("aaa");
        for(MessageBean3 bean3: vlist){
            System.out.println(bean3.getNo()+" : "
                    + bean3.getFid() + " : "
                    + bean3.getTid() + " : "
                    + bean3.getMsg() + " : "
                    + bean3.getMdate());
        }
    }

}

