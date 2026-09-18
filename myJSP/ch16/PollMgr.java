package ch16;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class PollMgr {
	private DBConnectionMgr pool;
	
	public PollMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	//Max Num:가장 최신의 num값 리턴
	public int getMaxNum() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int maxNum = 0;
		
		try {
			con = pool.getConnection();
			sql = "select max(num) from tblPollList";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) maxNum = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return maxNum;
	}
	
	//Poll Insert:설문과 Item 같이 저장
	public boolean insertPoll(PollListBean plBean, PollItemBean piBean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		
		try {
			con = pool.getConnection();
			sql = "insert tblPollList(question,sdate,edate,wdate,type)"
					+ "values(?,?,?,now(),?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, plBean.getQuestion());
			pstmt.setString(2, plBean.getSdate());
			pstmt.setString(3, plBean.getEdate());
			pstmt.setInt(4, plBean.getType()); //1번은 복수선택, 0번은 단일선택
			int cnt = pstmt.executeUpdate();
			pstmt.close();
			
			if(cnt==1) {//정상적인 tblPollList 저장이 되었을때
				sql = "insert tblPollitem values(?, ?, ?, 0)";
				pstmt = con.prepareStatement(sql);
				int listNum = getMaxNum(); //방금 저장한 리스트의 Num값
				String item[] = piBean.getItem();
				for (int i = 0; i < item.length; i++) {
					if(item[i]==null||item[i].trim().equals("")){
						break;
					}
					pstmt.setInt(1, listNum);
					pstmt.setInt(2, i);
					pstmt.setString(3, item[i]);
					if(pstmt.executeUpdate()==1) {
						flag = true;
					}//--for
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	//Poll List:질문목록 가져오기
	public Vector<PollListBean> getPollList(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<PollListBean> vlist = new Vector<PollListBean>();
		
		try {
			con = pool.getConnection();
			sql = "select num, question, sdate, edate, wdate, type, active from tblPollList"
					+ " order by num desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PollListBean plBean = new PollListBean();
				plBean.setNum(rs.getInt("num"));
				plBean.setQuestion(rs.getString("question"));
				plBean.setSdate(rs.getString("sdate"));
				plBean.setEdate(rs.getString("edate"));
				vlist.addElement(plBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	//Poll Read:하나의 질문 가져오기
	public PollListBean getPoll(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		PollListBean plBean = new PollListBean();
		
		try {
			con = pool.getConnection();
			sql = "select num, question, type, active "
					+ "from tblPollList where num = ?";
			pstmt = con.prepareStatement(sql);
			if(num==0){
				num = getMaxNum();
			}
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				plBean.setNum(rs.getInt(1));
				plBean.setQuestion(rs.getString(2));
				plBean.setType(rs.getInt(3));				
				plBean.setActive(rs.getInt(4));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return plBean;
	}
	
	//Poll Item List:아이템 목록 가져오기
	public Vector<String> getItemList(int listNum){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<String> vlist = new Vector<String>();
		try {
			con = pool.getConnection();
			sql = "select item from tblPollItem where listNum = ?";
			pstmt = con.prepareStatement(sql);
			if(listNum==0) { //설문을 선택하지 않았을 경우
				listNum=getMaxNum(); //가장 최신 설문의 아이템 리턴
			}
			pstmt.setInt(1, listNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vlist.addElement(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
		
	//Count Sum:설문 투표수
	public int getSumCount(int listNum){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int sum = 0;
		
		try {
			con = pool.getConnection();
			sql = "select sum(count) from tblPollItem where listnum = ?";
			pstmt = con.prepareStatement(sql);
			if(listNum==0) { //listNum이 Int형이라서 값이 없으면 Null이 아니라 0임 객체는 null이고 int는 0인상태
				listNum = getMaxNum();
			}
			pstmt.setInt(1, listNum);
			rs = pstmt.executeQuery();
			if(rs.next()){
				sum = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return sum;
	}
	
	//Poll Update:투표 실행
	public boolean updatePoll(int listNum, String itemNum[]) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		
		try {
			con = pool.getConnection();
			sql = "update tblPollItem set count = count + 1 "
					+ "where listNum = ? and itemNum = ?";
			pstmt = con.prepareStatement(sql);
			if(listNum==0) {
				listNum=getMaxNum();
			}
			for (int i = 0; i < itemNum.length; i++) {
				pstmt.setInt(1, listNum);
				pstmt.setInt(2, Integer.parseInt(itemNum[i]));
				if(pstmt.executeUpdate()==1) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	//Poll View:결과보기
	//결과보기를 위해서는 Beans(아이템빈)의 구조를 알아야한다:처음에 배열로 선언했으니 배열로 리턴되어야 함
	//한 행이 하나의 Beans로 리턴시키고 Vector에 넣을거임
	public Vector<PollItemBean> getView(int listNum){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<PollItemBean> vlist = new Vector<PollItemBean>();
		
		try {
			con = pool.getConnection();
			sql = "select item, count from tblPollItem where listNum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, listNum==0?getMaxNum():listNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PollItemBean piBean = new PollItemBean();
				String item[] = new String[1];
				item[0] = rs.getString("item");
				piBean.setItem(item);
				piBean.setCount(rs.getInt("count"));
				vlist.addElement(piBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	//Max Item Count:해당 설문에서 가장 높은 투표수
	public int getMaxCount(int listNum){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int max = 0;
		
		try {
			con = pool.getConnection();
			sql = "select max(count) from tblPollItem where listnum = ?";
			pstmt = con.prepareStatement(sql);
			//삼항 연산자를 사용하여 처리(CountSum 메소드에 있는내용과 같은뜻)
			pstmt.setInt(1, listNum==0?getMaxNum():listNum);
			rs = pstmt.executeQuery();
			if(rs.next()){
				max = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return max;
	}
}