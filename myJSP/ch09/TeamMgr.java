package ch09;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class TeamMgr {
    private DBConnectionMgr pool;

    public TeamMgr() {
        pool = DBConnectionMgr.getInstance();
    }

    //list
    public Vector<TeamBean> listTeam(){
            Connection con = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = null;
            Vector<TeamBean> vlist = new Vector<TeamBean>();
            try {
                con = pool.getConnection();
                sql = "select * from tblteam";
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    vlist.add(new TeamBean(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pool.freeConnection(con, pstmt, rs);
            }
            return vlist;
    }
    //insert
    public void insertTeam(TeamBean bean){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		String sql = null;
        		try {
        			con = pool.getConnection();
        			sql = "insert tblteam values(null,?,?,?,?)";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1,bean.getName());
                    pstmt.setString(2,bean.getCity());
                    pstmt.setInt(3,bean.getAge());
                    pstmt.setString(4,bean.getTeam());
        			pstmt.executeUpdate();
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt);
        		}
    }
    //read
    public TeamBean getTeam(int num){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
                TeamBean bean = null;
        		try {
        			con = pool.getConnection();
        			sql = "select * from tblteam where num = ?";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1,num);
        			rs = pstmt.executeQuery();
                    if(rs.next()){
                        bean=new TeamBean(
                          rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4),
                                rs.getString(5)
                        );
                    }

        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt, rs);
        		}
        		return bean;
    }
    //update
    public void updateTeam(TeamBean bean){
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        try {
            con = pool.getConnection();
            sql = "update tblTeam set name=?, city=?, age=?, team=? where num=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,bean.getName());
            pstmt.setString(2,bean.getCity());
            pstmt.setInt(3,bean.getAge());
            pstmt.setString(4,bean.getTeam());
            pstmt.setInt(5,bean.getNum());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
    }
    //delete
    public void deleteTeam(int num){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		String sql = null;
        		try {
        			con = pool.getConnection();
        			sql = "delete from tblTeam where num = ?";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1,num);
        			pstmt.executeUpdate();
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt);
        		}
    }
    //team list
    public Vector<String> teamList(){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
                Vector<String> vlist = new Vector<String>();
        		try {
        			con = pool.getConnection();
        			sql = "select distinct team from tblteam";
        			pstmt = con.prepareStatement(sql);
        			rs = pstmt.executeQuery();
                    while(rs.next())
                        vlist.add(rs.getString(1));
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt, rs);
        		}
        		return vlist;
    }

    //이름 중복 확인 — 이미 쓰고 있는 이름이면 true
    public boolean isNameDuplicate(String name){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean duplicate = false;
        try {
            con = pool.getConnection();
            String sql = "select count(*) from tblteam where name = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                duplicate = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return duplicate;
    }
}
