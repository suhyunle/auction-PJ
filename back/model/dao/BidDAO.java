package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import back.model.domain.RequestDTO;
import back.session.DBConnection;

public class BidDAO {

    public int insertRow(Map<String, Object> map){
        // System.out.println(">>>> debug Biddao insertRow");
        int flag = 0;
        Connection          conn = null;
        PreparedStatement   pstmt = null;
        PreparedStatement   updateStmt = null;
        String insertSQL = "INSERT INTO BID_TB (bid_id, item_id, bidder_id, bid_amount, bid_time) " +
                   "VALUES (BID_SEQ.NEXTVAL, ?, ?, ?, SYSTIMESTAMP)";
        String updateItemSQL = "UPDATE ITEM_TB SET current_price = ? WHERE item_id = ?";
        try{
            // conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn = DBConnection.getConnection();  // 공통 DB 연결 사용
            pstmt = conn.prepareStatement(insertSQL);
            System.out.println("🟢 [DEBUG] map: " + map);
            // Map에서 값 가져와 바인딩
            pstmt.setInt(1, (Integer) map.get("itemId")); // 입찰할 상품 ID
            pstmt.setString(2, (String) map.get("userId")); // 입찰자 ID
            pstmt.setInt(3, (Integer) map.get("bidAmount")); // 입찰 금액

            Object bidAmountObj = map.get("bidAmount");
            int bidAmount = (bidAmountObj != null) ? ((Number) bidAmountObj).intValue() : 0;
            System.out.println("🟢 [DEBUG] bid_amount: " + bidAmount);

            // 2️⃣ ITEM_TB의 current_price 업데이트
            updateStmt = conn.prepareStatement(updateItemSQL);
            updateStmt.setInt(1, bidAmount);
            updateStmt.setInt(2, (Integer) map.get("itemId"));
            updateStmt.executeUpdate();

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
    public Integer getHighestBid(int itemId) {
        Integer highestBid = null;
        String sql = "SELECT MAX(bid_amount) FROM BID_TB WHERE item_id = ?";

        
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    highestBid = rs.getInt(1);
                    if (rs.wasNull()) {
                        highestBid = null; // 입찰 기록이 없으면 null 반환
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return highestBid;
    }

    public Integer getStartingPrice(int itemId) {
        Integer startingPrice = null;
        String sql = "SELECT starting_price FROM ITEM_TB WHERE item_id = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, itemId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    startingPrice = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return startingPrice;
    }

}
