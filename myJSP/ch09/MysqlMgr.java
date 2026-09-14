package ch09;

import javax.imageio.stream.ImageInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlMgr {
    private DBConnectionMgr pool;

    public MysqlMgr() {
        pool = DBConnectionMgr.getInstance();
    }

    public int getTeamCount(){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
                int count = 0;
        		try {
        			con = pool.getConnection();
        			sql = "select count(*)from tblTeam";
        			pstmt = con.prepareStatement(sql);
        			rs = pstmt.executeQuery();
                    if(rs.next()){count = rs.getInt(1);}
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt, rs);
        		}
        		return count;
    }

    public static void main(String[] args) {
        MysqlMgr mgr = new MysqlMgr();
        System.out.println(mgr.getTeamCount());
    }
}