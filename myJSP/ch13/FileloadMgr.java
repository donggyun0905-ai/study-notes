package ch13;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class FileloadMgr {
    private DBConnectionMgr pool;
    public final static String SAVEFOLDER = "C:/Jsp/myapp/src/main/webapp/ch13/storage/";
    public final static String ENCODING = "UTF-8";
    public final static int MAXSIZE = 1024*1024*50;//50mb

    public FileloadMgr() {
        pool = DBConnectionMgr.getInstance();
    }

    //file uploatd+insert
    public void uploadFile(HttpServletRequest request){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		String sql = null;
        		try {
                    MultipartRequest multi = new MultipartRequest(request,SAVEFOLDER,MAXSIZE,ENCODING, new DefaultFileRenamePolicy());
                    File f = multi.getFile("upFile");
                    long size = f.length();
                    String upFile = multi.getFilesystemName("upFile");
                    ////////////////////////////////////////////////////////////////////
        			con = pool.getConnection();
        			sql = "insert tblFileload (upFile,size) values (?,?)";
        			pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, upFile);
                    pstmt.setLong(2, size);
        			pstmt.executeUpdate();

        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt);
        		}
    }

    //file list
    public Vector<FileloadBean> listFile(){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<FileloadBean> vlist = new Vector<FileloadBean>();
        try {
            con = pool.getConnection();
            sql = "select * from tblFileload";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                FileloadBean bean = new FileloadBean();
                bean.setNum(rs.getInt(1));
                bean.setUpFile(rs.getString(2));
                bean.setSize(rs.getInt(3));
                vlist.addElement(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }
    //file delete: 파일 삭제 + 레코드 삭제
    public void deleteFile(int num[]){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		String sql = null;
        		try {
        			con = pool.getConnection();
                    for(int i=0;i<num.length;i++) {
                        String upFile = getFile(num[i]);//삭제할 파일명
                        File f= new File(SAVEFOLDER+upFile);
                        if(f.exists()) f.delete();//파일 삭제
                        sql = "delete from tblFileload where num=?";
                        pstmt = con.prepareStatement(sql);
                        pstmt.setInt(1,num[i]);
                        pstmt.executeUpdate();
                    }

        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt);
        		}
    }

    //file delete2(in연산자)
    public void deleteFile2(int num[]){
        Connection con = null;
        		PreparedStatement pstmt = null;
        		String sql = null;
        		try {
                    //파일 삭제 for
                    for(int i=0;i<num.length;i++) {
                        String upFile = getFile(num[i]);//삭제할 파일명
                        File f= new File(SAVEFOLDER+upFile);
                        if(f.exists()) f.delete();//파일 삭제
                    }
        			con = pool.getConnection();
        			sql = "delete from tblFileload where num in (" + MUtil.ph(num.length) + ")";
        			pstmt = con.prepareStatement(sql);
                    for ( int i = 0; i < num.length; i++ ) {
                        pstmt.setInt(1, num[i]);//num in (1,3,5)
                    }
        			pstmt.executeUpdate();//query문을 한번만 실행

        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			pool.freeConnection(con, pstmt);
        		}
    }
    //file info: delete 필요
    public String getFile(int num) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        String upFile = null;
        try {
            con = pool.getConnection();
            sql = "select upFile from tblFileload where num = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            if(rs.next())
                upFile = rs.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return upFile;
    }

}
