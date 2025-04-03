package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import back.model.domain.ResponseDTO;
import back.session.DBConnection;

public class AuctionDAO {
    // public static final String DRIVER   = "oracle.jdbc.driver.OracleDriver";
    // public static final String URL      = "jdbc:oracle:thin:@localhost:1521/xe";
    // public static final String USER     = "hr";
    // public static final String PASSWORD = "hr";
        /*
    AuctionDAO.java
    기능: 경매 관련 DB 연동을 담당. 경매 상태 관리 및 경매 종료 처리 등을 DB에서 처리.

    주요 메서드:

    경매 물품 조회 */
    // public AuctionDAO(){
    //     try{
    //         Class.forName(DRIVER);
    //         System.out.println("1. Driver loading OK");
    //     } catch (ClassNotFoundException e){
    //         e.printStackTrace();
    //     }
    // }

    public Optional<ResponseDTO> selectRow(int seq){ // Optional 리턴 메서드
        System.out.println(">>> debug selectDao");
        Optional<ResponseDTO> response = Optional.empty(); // Optional 객체 생성
        Connection          conn  = null;
        PreparedStatement   pstmt = null;
        ResultSet           rset  = null;
        String selectSQL = "SELECT * FROM ITEM_TB WHERE item_id = ?";
        try{
            conn = DBConnection.getConnection();  // 공통 DB 연결 사용
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setInt(1, seq);
            rset  = pstmt.executeQuery();
            while (rset.next()) {
                response = Optional.of(ResponseDTO.builder()
                                                .itemId(rset.getInt("item_id"))
                                                .sellerId(rset.getString("seller_id"))
                                                .title(rset.getString("title"))
                                                .description(rset.getString("description"))
                                                .currentPrice(rset.getInt("current_price"))
                                                .startTime(rset.getTimestamp("start_time").toString())
                                                .endTime(rset.getTimestamp("end_time").toString())
                                                .status(rset.getString("status"))
                                                .build());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return response;
    }

     public Optional<List<ResponseDTO>> selectRow(){ // Optional 리턴 메서드
        System.out.println(">>> debug selectDao");
        List<ResponseDTO> list = new ArrayList<>();
        Connection          conn  = null;
        PreparedStatement   pstmt = null;
        ResultSet           rset  = null;
        String selectSQL = "SELECT item_id, seller_id, title, description, current_price, start_time, end_time, status " +
                       "FROM ITEM_TB";
        try{
            conn = DBConnection.getConnection();  // 공통 DB 연결 사용
            pstmt = conn.prepareStatement(selectSQL);
            rset  = pstmt.executeQuery();
            while (rset.next()) {
                ResponseDTO response = ResponseDTO.builder()
                                .itemId(rset.getInt("item_id"))
                                .sellerId(rset.getString("seller_id"))
                                .title(rset.getString("title"))
                                .description(rset.getString("description"))
                                .currentPrice(rset.getInt("current_price"))
                                .startTime(rset.getTimestamp("start_time").toString())
                                .endTime(rset.getTimestamp("end_time").toString())
                                .status(rset.getString("status"))
                                .build();
                list.add(response);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }
}
