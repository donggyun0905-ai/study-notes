package ch14;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class MemberMgr {
    private DBConnectionMgr pool;

    // <jsp:useBean>은 항상 매개변수 없는 생성자로 객체를 만듦 (guestbook_2 참고)
    public MemberMgr() {
        this.pool = DBConnectionMgr.getInstance();
    }

    public MemberMgr(DBConnectionMgr pool) {
        this.pool = pool;
    }

    //로그인 성공 : true
    public boolean loginMember(String id, String pwd) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "select id from tblMember where id = ? and pwd = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            rs = pstmt.executeQuery();
            flag = rs.next();//일치하는 id가 있으면 true 없으면 false
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return flag;
    }

    //id 중복체크: 중복 ->true
    public boolean duplicationId(String id){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
                boolean check = false;
        		try {
        			con = pool.getConnection();
        			sql = "select id from tblMember where id =? ";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1,id);
        			rs = pstmt.executeQuery();
                    check =rs.next();
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt, rs);
        		}
        		return check;
    }

    //우편번호 검색
    public Vector<ZipcodeBean> searchZipcode(String area3/*광평로*/){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
        		String sql = null;
                Vector<ZipcodeBean> vlist = new Vector<ZipcodeBean>();
        		try {
        			con = pool.getConnection();
        			sql = "select * from tblZipcode where area3 like ?";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1,"%" + area3 +"%");
        			rs = pstmt.executeQuery();
                    while(rs.next())
                        vlist.add(new ZipcodeBean(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4)));
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt, rs);
        		}
        		return vlist;
    }

    //회원가입
    public boolean insertMember(MemberBean bean) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "insert tblMember values (?,?,?,?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bean.getId());
            pstmt.setString(2, bean.getPwd());
            pstmt.setString(3, bean.getName());
            pstmt.setString(4, bean.getGender());
            pstmt.setString(5, bean.getBirthday());
            pstmt.setString(6, bean.getEmail());
            pstmt.setString(7, bean.getZipcode());
            pstmt.setString(8, bean.getAddress());
            String lists[] = { "인터넷", "여행", "게임", "영화", "운동" };
            String hobby[] = bean.getHobby();
            char hb[] = {'0', '0', '0', '0', '0'};
            for (int i = 0; i < hobby.length; i++) {
                for (int j = 0; j < lists.length; j++) {
                    if(hobby[i].equals(lists[j])) {
                        hb[j] = '1';
                        break;
                    }
                }
            }
            pstmt.setString(9, new String(hb));
            pstmt.setString(10, bean.getJob());
            if(pstmt.executeUpdate()==1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

    //회원정보
    public MemberBean getMember(String id) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        MemberBean bean = null;
        try {
            con = pool.getConnection();
            sql = "select * from tblMember where id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                String hobby = rs.getString("hobby");
                String hb[] = new String[hobby.length()];
                for (int i = 0; i < hb.length; i++) {
                    hb[i] = hobby.substring(i, i+1);
                }
                bean = new MemberBean(
                        rs.getString("id"),
                        rs.getString("pwd"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("birthday"),
                        rs.getString("email"),
                        rs.getString("zipcode"),
                        rs.getString("address"),
                        hb,
                        rs.getString("job"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return bean;
    }

    //회원수정
    public boolean updateMember(MemberBean bean) {
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "update tblMember set pwd=?, name=?, gender=?,"
                    + "birthday=?, email=?, zipcode=?, address=?,  "
                    + "hobby=?, job=? where id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bean.getPwd());
            pstmt.setString(2, bean.getName());
            pstmt.setString(3, bean.getGender());
            pstmt.setString(4, bean.getBirthday());
            pstmt.setString(5, bean.getEmail());
            pstmt.setString(6, bean.getZipcode());
            pstmt.setString(7, bean.getAddress());
            String lists[] = { "인터넷", "여행", "게임", "영화", "운동" };
            String hobby[] = bean.getHobby();
            char hb[] = {'0', '0', '0', '0', '0'};
            for (int i = 0; i < hobby.length; i++) {
                for (int j = 0; j < lists.length; j++) {
                    if(hobby[i].equals(lists[j])) {
                        hb[j] = '1';
                        break;
                    }
                }
            }
            pstmt.setString(8, new String(hb));
            pstmt.setString(9, bean.getJob());
            pstmt.setString(10, bean.getId());
            if(pstmt.executeUpdate()==1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }
}

