package back.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import back.model.dao.AuctionDAO;
import back.model.dao.HistoryDAO;

public class Scheduler {
    private static final int CHECK_INTERVAL = 10; // 10초마다 체크

    public static void startScheduler() {
        System.out.println("start s");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                checkAndCloseAuctions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, CHECK_INTERVAL, TimeUnit.SECONDS);
    }

    private static void checkAndCloseAuctions() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
    
            // 🔍 현재 시간이 종료 시간을 지난 경매 찾기
            String findExpiredAuctions = "SELECT item_id FROM ITEM_TB WHERE CAST(end_time AS TIMESTAMP) <= SYSTIMESTAMP AND TRIM(status) = '진행중'";
            pstmt = conn.prepareStatement(findExpiredAuctions);
            rs = pstmt.executeQuery();
    
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                System.out.println("🔹 경매 종료 처리: item_id = " + itemId);
                try {
                    HistoryDAO.closeAuction(itemId); // 개별 경매 종료 실행
                } catch (Exception ex) {
                    System.err.println("❌ 오류 발생: item_id = " + itemId + " 경매 종료 실패");
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
