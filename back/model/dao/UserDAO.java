package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import back.model.domain.RequestDTO;
import back.model.domain.ResponseDTO;

public class UserDAO {
    int dat = 1;  // 회원가입, 로그인이 성공적이면 1 반환
            /*UserDAO.java
        기능: 사용자 관련 DB 연동을 담당. 사용자 정보 삽입, 조회, 수정, 삭제 등의 CRUD 작업을 처리.

        주요 메서드:

        사용자 조회

        사용자 등록

        사용자 수정 및 삭제 */

    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver" ;    // 상수
    public static final String URL = "jdbc:oracle:thin:@localhost:1521/xe";
    public static final String USER = "hr";
    public static final String PASSWORD = "hr";


    public UserDAO() {
        try {
            Class.forName(DRIVER) ;
            System.out.println("1. Driver loading ok!!!") ;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isUserTaken(String userId) {
        boolean exists = false ;
        Connection conn = null ;
        PreparedStatement pstmt = null ;
        ResultSet               rset = null ;

        String idCheckSQL = "SELECT USER_ID FROM USER_TB WHERE USER_ID = ?" ;
        

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD) ;
            pstmt = conn.prepareStatement(idCheckSQL);
            pstmt.setString(1, userId) ;
            rset = pstmt.executeQuery() ;

            while (rset.next()) {
                if (rset.getString(1).equals(userId)) {
                    exists = true ;
                    break ;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close() ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exists ;
    }
    

    public int signInRow(RequestDTO request) {
        System.out.println(">>> dao insertRow") ;
        int flag = 0 ;
        Connection conn = null ;
        PreparedStatement pstmt = null ;

        if (isUserTaken(request.getUserId())) {
            System.out.println("중복되는 ID를 사용하실 수 없습니다. 다른 ID를 입력하세요.\n회원가입을 다시 시작합니다.");
            return flag ;
        }

        String insertSQL = "INSERT INTO USER_TB(USER_ID, PASSWORD, USERNAME) " +
                            "VALUES(?,?,?)" ;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD) ;
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, request.getUserId()) ;
            pstmt.setString(2, request.getUserPw()) ;
            pstmt.setString(3, request.getUserName()) ;
            flag = pstmt.executeUpdate() ;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close() ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (flag == 0) {
            System.out.println("회원가입에 실패했습니다");
        } else {
            System.out.println("회원가입에 성공했습니다");
        }
        return flag ;
    }

    public String logInRow(RequestDTO request) {
        System.out.println(">>>> dao selectRow");
        String logInUser = null ;
        Connection conn =       null ;
        PreparedStatement       pstmt = null ;
        ResultSet               rset = null ;
        String selectSQL = "SELECT USERNAME FROM USER_TB WHERE USER_ID = ? AND PASSWORD = ?" ;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD) ;
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setString(1, request.getUserId()) ;
            pstmt.setString(2, request.getUserPw()) ;
            rset = pstmt.executeQuery() ;

            while (rset.next()) {
                logInUser = rset.getString("USERNAME") ;
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close() ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return logInUser ;
    }

}
