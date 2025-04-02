package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import back.model.domain.RequestDTO;

public class BidDAO {

    /*BidDAO.java
    기능: 입찰 관련 DB 연동을 담당. 입찰 내역 기록, 입찰 금액 갱신, 최고 입찰자 업데이트 등을 처리.

    주요 메서드:

    입찰 내역 삽입

    입찰 금액 갱신

    최고 입찰자 정보 업데이트 */
 
    public static final String DRIVER   = "oracle.jdbc.driver.OracleDriver";
    public static final String URL      = "jdbc:oracle:thin:@localhost:1521/xe";
    public static final String USER     = "hr";
    public static final String PASSWORD = "hr";

    public BidDAO(){
        try{
            Class.forName(DRIVER);
            System.out.println("1. Driver loading OK");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int insertRow(Map<String, Object> map){
        System.out.println(">>>> debug Biddao insertRow");
        int flag = 0;
        Connection          conn = null;
        PreparedStatement   pstmt = null;
        String insertSQL = "INSERT INTO JDBC_TODO_TBL(SEQ, TITLE, CONTENT, PRIORITY) VALUES(JDBC_SEQ.NEXTVAL,?,?,?)";
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, (String)(map.get("content")));
            pstmt.setString(2, (String)(map.get("status")));
            pstmt.setInt(3, (Integer)(map.get("seq")));
            flag = pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (conn != null) {
                    conn.close();   
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return flag;
    }

}
