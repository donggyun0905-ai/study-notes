package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLConnection {

    private DBConnectionMgr pool;

    public MySQLConnection() {
        try{
            pool = DBConnectionMgr.getInstance();
            System.out.println("연결성공");;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listExCnt(){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        try {
            con = pool.getConnection();
            sql = "select count(*) from tblEx";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next())
                System.out.println(rs.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
    }

    public static void main(String[] args) {
        MySQLConnection my = new MySQLConnection();
        my.listExCnt();
    }
}
