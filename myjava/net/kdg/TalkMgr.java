package net.kdg;

import member.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TalkMgr {

    private DBConnectionMgr pool;

    public TalkMgr() {
        this.pool = DBConnectionMgr.getInstance();
    }

    public boolean loginChk(String id, String pwd){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        boolean flag = false;

        try{
            con = pool.getConnection();
            sql = "select count(id) from tblRegister where id=? and pwd=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.setString(2,pwd);
            rs = pstmt.executeQuery();
            if(rs.next()&&rs.getInt(1) == 1){
                flag = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            pool.freeConnection(con,pstmt,rs);
        }
        return flag;
    }

    public String nameChk(String id){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        String name = "";

        try{
            con = pool.getConnection();
            sql = "select name from tblRegister where id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                name = rs.getString("name");
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            pool.freeConnection(con,pstmt,rs);
        }

        return name;
    }
}
