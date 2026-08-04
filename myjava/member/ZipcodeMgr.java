package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ZipcodeMgr {
    private DBConnectionMgr pool;

    public ZipcodeMgr(){
        pool = DBConnectionMgr.getInstance();
    }

    //우편번호 검색
    public Vector<ZipcodeBean> searchZipcode(String area3){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<ZipcodeBean> vlist = new Vector<ZipcodeBean>();
        try {
            con = pool.getConnection();
            sql = "select * from tblZipcode where area3 like ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,"%" + area3 + "%");//area3 like '%강남대로%'
            rs = pstmt.executeQuery();
            while(rs.next()){
                ZipcodeBean bean = new ZipcodeBean();
                bean.setZipcode(rs.getString(1));
                bean.setArea1(rs.getString(2));
                bean.setArea2(rs.getString(3));
                bean.setArea3(rs.getString(4));

                vlist.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

}
