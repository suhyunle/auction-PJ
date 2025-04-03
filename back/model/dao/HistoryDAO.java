package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import back.model.domain.ResponseDTO;
import back.model.domain.TranResDTO;
import back.session.DBConnection;
import back.session.UserSession;

public class HistoryDAO {
    // public static final String DRIVER   = "oracle.jdbc.driver.OracleDriver";
    // public static final String URL      = "jdbc:oracle:thin:@localhost:1521/xe";
    // public static final String USER     = "hr";
    // public static final String PASSWORD = "hr";

    public HistoryDAO(){
        // try{
        //     Class.forName(DRIVER);
        //     System.out.println("1. Driver loading OK");
        // } catch (ClassNotFoundException e){
        //     e.printStackTrace();
        // }
    }
    
    public Optional<List<TranResDTO>> selectRow(){ // Optional 리턴 메서드
        System.out.println(">>> debug historyDao");
        List<TranResDTO> list = new ArrayList<>();
        String loggedInUser = UserSession.getLoggedInUser(); // 로그인한 사용자 ID 가져오기

        Connection          conn  = null;
        PreparedStatement   pstmt = null;
        ResultSet           rset  = null;

        String selectSQL = "SELECT t.transaction_id, t.item_id, i.seller_id, i.title, i.description, t.final_price, t.transaction_time, t.is_completed " +
                           "FROM TRANSACTION_TB t " +
                           "JOIN ITEM_TB i ON t.item_id = i.item_id " +
                           "WHERE t.buyer_id = ? " + // 🔹 로그인한 사용자만 필터링
                           "ORDER BY t.transaction_time DESC"; // 최신 거래 순 정렬
        try{
            // conn  = DriverManager.getConnection(URL, USER, PASSWORD);
            conn = DBConnection.getConnection();  // 공통 DB 연결 사용
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setString(1, loggedInUser); // 🔹 로그인한 사용자의 ID 바인딩
            rset  = pstmt.executeQuery();
            while (rset.next()) {
                TranResDTO response = TranResDTO.builder()
                                    .transactionId(rset.getInt("transaction_id"))
                                    .itemId(rset.getInt("item_id"))
                                    .sellerId(rset.getString("seller_id"))
                                    .title(rset.getString("title"))
                                    .description(rset.getString("description"))
                                    .finalPrice(rset.getDouble("final_price")) // 🔹 최종 낙찰가
                                    .transactionTime(rset.getTimestamp("transaction_time").toString()) // 거래 시간
                                    .isCompleted(rset.getInt("is_completed") == 1 ? "완료" : "진행중") // 🔹 거래 상태 표시
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

    public static void closeAuction(int itemId) {
        boolean isProcessed = false;
        
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr")) {
            conn.setAutoCommit(false); // 트랜잭션 시작
            
            // 🏷️ 판매자 ID 가져오기
            String sellerId = null;
            String findSeller = "SELECT seller_id FROM ITEM_TB WHERE item_id = ?";
            try (PreparedStatement pstmtFindSeller = conn.prepareStatement(findSeller)) {
                pstmtFindSeller.setInt(1, itemId);
                try (ResultSet rs = pstmtFindSeller.executeQuery()) {
                    if (rs.next()) {
                        sellerId = rs.getString("seller_id"); // 🔹 판매자 ID 저장
                    }
                }
            }
    
            // 1️⃣ 최고 입찰자 찾기
            String findHighestBid = "SELECT bidder_id, bid_amount FROM BID_TB WHERE item_id = ? ORDER BY bid_amount DESC";
            String findStartingPrice = "SELECT starting_price FROM ITEM_TB WHERE item_id = ?";
            
            String highestBidder = null;
            double finalPrice = 0.0;
    
            try (PreparedStatement pstmtFind = conn.prepareStatement(findHighestBid)) {
                pstmtFind.setInt(1, itemId);
                try (ResultSet rs = pstmtFind.executeQuery()) {
                    if (rs.next()) {
                        highestBidder = rs.getString("bidder_id");
                        finalPrice = rs.getInt("bid_amount");
                    }
                }
            }
    
            // 2️⃣ 입찰자가 없으면 시작가 적용
            if (highestBidder == null) {
                try (PreparedStatement pstmtFindPrice = conn.prepareStatement(findStartingPrice)) {
                    pstmtFindPrice.setInt(1, itemId);
                    try (ResultSet rs = pstmtFindPrice.executeQuery()) {
                        if (rs.next()) {
                            finalPrice = rs.getDouble("starting_price"); // 시작가를 최종 가격으로
                        }
                    }
                }
            }
    
            // 3️⃣ ITEM_TB 상태 업데이트
            String updateItemStatus = "UPDATE ITEM_TB SET status = '완료', current_price = ? WHERE item_id = ?";
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateItemStatus)) {
                pstmtUpdate.setDouble(1, finalPrice);
                pstmtUpdate.setInt(2, itemId);
                pstmtUpdate.executeUpdate();
            }
            System.out.println("🟢 [DEBUG] itemId: " + itemId);
            System.out.println("🟢 [DEBUG] highestBidder: " + highestBidder);
            System.out.println("🟢 [DEBUG] sellerId: " + sellerId);
            System.out.println("🟢 [DEBUG] finalPrice: " + finalPrice);
            // 4️⃣ TRANSACTION_TB에 자동 추가 (입찰자 없을 경우 seller_id 사용)
            String insertTransaction = "INSERT INTO TRANSACTION_TB (transaction_id, item_id, buyer_id, final_price, transaction_time, is_completed) VALUES (TRAN_SEQ.NEXTVAL, ?, ?, ?, SYSTIMESTAMP, 0)";
            try (PreparedStatement pstmtInsert = conn.prepareStatement(insertTransaction)) {
                pstmtInsert.setInt(1, itemId);
                pstmtInsert.setString(2, (highestBidder != null) ? highestBidder : sellerId); // 🔹 입찰자가 없으면 seller_id 사용
                pstmtInsert.setDouble(3, finalPrice);
                pstmtInsert.executeUpdate();
            }
    
            conn.commit(); // 트랜잭션 커밋
            isProcessed = true;
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        if (isProcessed) {
            System.out.println("✅ 경매 종료 및 거래 추가 완료: item_id = " + itemId);
        } else {
            System.out.println("❌ 오류 발생: 거래 데이터 추가 실패 (item_id = " + itemId + ")");
        }
    }
}
