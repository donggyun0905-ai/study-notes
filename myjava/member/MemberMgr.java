package member;

import javax.swing.plaf.metal.MetalMenuBarUI;
import javax.swing.text.html.HTMLDocument;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

//db 관련 메소드 선언하는 클래스
public class MemberMgr {
    private DBConnectionMgr pool;

    public MemberMgr() {
        // db 연결 객체(connection)를 미리 10개 생성
        pool = DBConnectionMgr.getInstance();
    }

    // db 연동 테스트 하기 위해서 Member 테이블에 저장된 레코드 개수 리턴
    public int selectCnt(){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        int cnt = 0;

        try{
            con = pool.getConnection();//Connection 객체를 pool에 렌탈
            sql = "select count(*) from tblMember";
            pstmt = con.prepareStatement(sql);//sql문을 db서버가 요구하는 형식으로 pstmt로 만듬
            rs = pstmt.executeQuery();//실제 실행하고 결과값을 rs로 리턴
            //rs.next()는 결과값이 있으면 true 없으면 false
            //rs.getInt(1) 현재 커서가 있는 레코드에 첫 번째 data를 리턴.타입이 int형
            if(rs.next()) cnt = rs.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //con 반납, pstmt,rs 는 사용후에 close
            pool.freeConnection(con,pstmt,rs);
        }
        return cnt;
    }

    //저장
    public boolean insertMember(MemberBean bean){
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            //?:매개변수로 입력된 값을 동적으로 만들기 위한 문법기능
            sql = "insert tblmember values (null,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            //(null,'홍길동','010-2222-9999','서울시 강남구', '배우')
            pstmt.setString(1,bean.getName()); //첫 번째 ?로 '장동건'
            pstmt.setString(2,bean.getPhone());
            pstmt.setString(3,bean.getAddress());
            pstmt.setString(4,bean.getTeam());
            //실행된 레코드의 개수를 리턴
            int cnt = pstmt.executeUpdate();//DML 실행 메소드
            if(cnt==1) flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }

        return flag;
    }
    //리스트
    public Vector<MemberBean> listMember(){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<MemberBean> vlist = new Vector<MemberBean>();
        try {
            con = pool.getConnection();
            sql = "select * from tblmember";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                MemberBean bean = new MemberBean();
                bean.setNum(rs.getInt("num")); //"num" 컬럼명
                bean.setName(rs.getString("name"));
                bean.setPhone(rs.getString("phone"));
                bean.setAddress(rs.getString("address"));
                bean.setTeam(rs.getString("team"));

                vlist.add(bean);//빈즈를 Vector에 저장
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }

    //레코드 한개
    public MemberBean getMember(int num){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        MemberBean bean = new MemberBean();
        try {
            con = pool.getConnection();
            sql = "select * from tblmember where num = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();
            if(rs.next()){
                bean.setNum(rs.getInt(1));//컬럼의 index 번호값 or 컬럼명
                bean.setName(rs.getString(2));
                bean.setPhone(rs.getString(3));
                bean.setAddress(rs.getString(4));
                bean.setTeam(rs.getString(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return bean;
    }
    //수정
    public boolean updateMember(MemberBean bean){
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            //?:매개변수로 입력된 값을 동적으로 만들기 위한 문법기능
            sql = "update tblmember set name = ? , phone = ?, address = ?, team = ? "
                    + "where num = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,bean.getName());
            pstmt.setString(2,bean.getPhone());
            pstmt.setString(3,bean.getAddress());
            pstmt.setString(4,bean.getTeam());
            pstmt.setInt(5,bean.getNum());
            int cnt = pstmt.executeUpdate();//DML 실행 메소드
            if(cnt==1) flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }

        return flag;
    }
    //삭제
    public boolean deleteMember(int num){
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;

        try {
            con = pool.getConnection();
            sql = "delete from tblmember where num = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,num);
            if(pstmt.executeUpdate() == 1) flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }

    //phone 번호 중복 체크 : 중복이면 true 리턴
    public boolean isDuplicatePhone(String phone){
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            con = pool.getConnection();
            sql = "SELECT phone FROM tblmember where phone = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,phone);
            rs = pstmt.executeQuery();
            if(rs.next()) check = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            pool.freeConnection(con,pstmt,rs);
        }
        return check;
    }

    //팀 이름 리스트(중복 제거)
    public Vector<String> getTeamList(){
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        ResultSet rs = null;
        Vector<String> vlist = new Vector<String>();

        try {
            con = pool.getConnection();
            sql = "SELECT distinct team FROM tblmember";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) vlist.add(rs.getString(1));
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            pool.freeConnection(con,pstmt,rs);
        }
        return vlist;
    }

    public static void main(String[] args) {
        MemberMgr mgr = new MemberMgr();
        System.out.println(mgr.selectCnt());
    }
}
