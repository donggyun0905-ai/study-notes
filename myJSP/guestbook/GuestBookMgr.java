package guestbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class GuestBookMgr {
    private DBConnectionMgr pool;
    private final SimpleDateFormat SDF_DATE =
            new SimpleDateFormat("yyyy'년'  M'월' d'일' (E)");
    private final SimpleDateFormat SDF_TIME =
            new SimpleDateFormat("H:mm:ss");

    // <jsp:useBean> 은 항상 매개변수 없는 생성자로 객체를 만듦
    public GuestBookMgr() {
        this.pool = DBConnectionMgr.getInstance();
    }

    public GuestBookMgr(DBConnectionMgr pool) {
        this.pool = pool;
    }

    //Join Login
    //sql = "select id from tblJoin where id =? and pwd = ?";
    public boolean loginJoin(String id, String pwd){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
                boolean flag= false;
        		try {
        			con = pool.getConnection();
        			sql = "select id from tblJoin where id =? and pwd = ?";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, id);
                    pstmt.setString(2, pwd);
        			rs = pstmt.executeQuery();
                    flag = rs.next();
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt, rs);
        		}
        		return flag;
    }
    //Join Information
    //sql = "select * from tblJoin where id = ?";
    public JoinBean getJoin(String id){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
                JoinBean bean =null;
        		try {
        			con = pool.getConnection();
        			sql = "select * from tblJoin where id = ?";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, id);
        			rs = pstmt.executeQuery();
                    if(rs.next()){
                        bean = new JoinBean();
                        bean.setId(rs.getString(1));
                        bean.setPwd(rs.getString(2));
                        bean.setName(rs.getString(3));
                        bean.setEmail(rs.getString(4));
                        bean.setHp(rs.getString(5));
                        // grade는 DB에 char(2)("0"/"1")로 저장되지만 JoinBean.setGrade는 boolean을 받음
                        bean.setGrade("1".equals(rs.getString(6)));
                    }
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt, rs);
        		}
        		return bean;
    }

    //GB List(비밀글: 본인과 관리자만 볼 수 있다.)
    public Vector<GuestBookBean> listGuestBooks(String id, String grade) {
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
                Vector<GuestBookBean> vlist = new Vector<GuestBookBean>();
        		try {
        			con = pool.getConnection();
                    if(grade.equals("1")/*관리자*/){
                        sql = "select * from tblGuestBook order by num desc";
                        pstmt = con.prepareStatement(sql);
                    }else if(grade.equals("0")){
                        sql = "select * from tblGuestBook where id =? or secret = '0'"
                                + " order by num desc";
                        pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, id);
                    }
        			rs = pstmt.executeQuery();
                    while(rs.next()){
                        vlist.add(new GuestBookBean(
                                rs.getInt("num"),
                                rs.getString("id"),
                                rs.getString("contents"),
                                rs.getString("ip"),
                                SDF_DATE.format(rs.getDate("regdate")),
                                SDF_TIME.format(rs.getTime("regtime")),
                                rs.getString("secret")));
                    }

        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt, rs);
        		}
        		return vlist;
    }

    //GB Insert
    //sql = "insert tblGuestBook(id,contents,ip,regdate,regtime,secret)"
    //+ " values(?,?,?,now(),now(),?)";
    public void insertGuestBook(GuestBookBean bean){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		String sql = null;
        		try {
        			con = pool.getConnection();
        			sql = "insert tblGuestBook(id,contents,ip,regdate,regtime,secret)"
                            + " values(?,?,?,now(),now(),?)";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, bean.getId());
                    pstmt.setString(2, bean.getContents());
                    pstmt.setString(3, bean.getIp());
                    pstmt.setString(4, bean.getSecret());
        			pstmt.executeUpdate();
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt);
        		}
    }
    //GB Read
    //sql = "select * from tblGuestBook where num =?";
    public GuestBookBean getGuestBook(int num){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
                GuestBookBean bean =null;
        		try {
        			con = pool.getConnection();
        			sql = "select * from tblGuestBook where num =?";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1, num);
        			rs = pstmt.executeQuery();
                    if(rs.next()){
                        bean = new GuestBookBean(
                                rs.getInt("num"),
                                rs.getString("id"),
                                rs.getString("contents"),
                                rs.getString("ip"),
                                SDF_DATE.format(rs.getDate("regdate")),
                                SDF_TIME.format(rs.getTime("regtime")),
                                rs.getString("secret"));
                    }
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt, rs);
        		}
        		return bean;
    }

    //GB Update
    //sql = "update tblGuestBook set contents = ?, ip = ?, secret = ? where num = ?";
    public void updateGuestBook(GuestBookBean bean){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		String sql = null;
        		try {
        			con = pool.getConnection();
        			sql = "update tblGuestBook set contents = ?, ip = ?, secret = ? where num = ?";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, bean.getContents());
                    pstmt.setString(2, bean.getIp());
                    pstmt.setString(3, bean.getSecret());
                    pstmt.setInt(4, bean.getNum());
        			pstmt.executeUpdate();
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt);
        		}
    }

    //GB Delete
    //sql = "delete from tblGuestBook where num = ?";
    public void deleteGuestBook(int num){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		String sql = null;
        		try {
        			con = pool.getConnection();
        			sql = "delete from tblGuestBook where num = ?";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1, num);
        			pstmt.executeUpdate();
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt);
        		}
    }
}
