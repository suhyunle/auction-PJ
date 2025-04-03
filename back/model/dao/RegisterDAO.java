package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import back.model.domain.RequestDTO;
import back.session.DBConnection;

public class RegisterDAO {

    // public static final String DRIVER   = "oracle.jdbc.driver.OracleDriver";
    // public static final String URL      = "jdbc:oracle:thin:@localhost:1521/xe";
    // public static final String USER     = "hr";
    // public static final String PASSWORD = "hr";
    
    // public RegisterDAO(){
    //     try{
    //         Class.forName(DRIVER);
    //         System.out.println("1. Driver loading OK");
    //     } catch (ClassNotFoundException e){
    //         e.printStackTrace();
    //     }
    // }

    public int insertRow(RequestDTO request){
        System.out.println(">>>> debug registerdao insertRow");
        int flag = 0;
        Connection          conn = null;
        PreparedStatement   pstmt = null;
        String insertSQL = "INSERT INTO ITEM_TB (item_id, seller_id, title, description, starting_price, current_price, start_time, end_time, status) " +
                       "VALUES (IID_SEQ.NEXTVAL, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP + 0.001, '진행중')";
        try{
            // conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn = DBConnection.getConnection();  // 공통 DB 연결 사용
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, request.getSellerId());  // 판매자 ID
            pstmt.setString(2, request.getItemName());  // 상품명
            pstmt.setString(3, request.getDescription());  // 설명
            pstmt.setInt(4, request.getStartingPrice());  // 시작가
            pstmt.setInt(5, request.getStartingPrice()); //현재가
            
            // pstmt.setString(5, request.getEndTime());  // 종료 시간 (문자열 형식, 필요 시 변환)

            flag = pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return flag;
    }
}
