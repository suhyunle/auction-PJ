package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import back.model.domain.RequestDTO;
import back.model.domain.UserDTO;
import back.session.DBConnection;

public class UserDAO {
    int dat = 1;  // 회원가입, 로그인이 성공적이면 1 반환

    public boolean isUserTaken(String userId) {
        boolean exists = false ;
        Connection conn = null ;
        PreparedStatement pstmt = null ;
        ResultSet               rset = null ;

        String idCheckSQL = "SELECT USER_ID FROM USER_TB WHERE USER_ID = ?" ;
        

        try {
            conn = DBConnection.getConnection();  // 공통 DB 연결 사용
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

    public int signInRow(UserDTO request) {
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
            // conn = DriverManager.getConnection(URL, USER, PASSWORD) ;
            conn = DBConnection.getConnection();  // 공통 DB 연결 사용
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

    public String logInRow(UserDTO request) {
        System.out.println(">>>> dao selectRow");
        String logInUser = null ;
        Connection conn =       null ;
        PreparedStatement       pstmt = null ;
        ResultSet               rset = null ;
        String selectSQL = "SELECT USERNAME FROM USER_TB WHERE USER_ID = ? AND PASSWORD = ?" ;
        try {
            conn = DBConnection.getConnection();  // 공통 DB 연결 사용
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
