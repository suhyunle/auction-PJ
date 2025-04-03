package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import back.model.domain.RequestDTO;

public class PaymentDAO {
    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver" ;    // 상수
    public static final String URL = "jdbc:oracle:thin:@localhost:1521/xe";
    public static final String USER = "hr";
    public static final String PASSWORD = "hr";


    // 구매자 id 확인
    public String buyerIdRow(int transaction_id) {
        System.out.println(">>>> 구매자 ID 확인 중: dao transIdCheckRow");
        String buyerId = null ;
        Connection conn =       null ;
        PreparedStatement       pstmt = null ;
        ResultSet               rset = null ;
        String buyerIdSelectSQL = "SELECT BUYER_ID FROM TRANSACTION_TB WHERE TRANSACTION_ID = ?" ;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD) ;
            pstmt = conn.prepareStatement(buyerIdSelectSQL);
            pstmt.setInt(1, transaction_id) ;
            rset = pstmt.executeQuery() ;

            while (rset.next()) {
                buyerId = rset.getString("BUYER_ID") ;
                break ;
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
        return buyerId ;
    }

    // 거래 id 존재 여부 확인
    public int transIdCheckRow(int transaction_id) {
        System.out.println(">>>> 거래 ID 확인 중: dao transIdCheckRow");
        int checkTransId = 0 ;
        Connection conn =       null ;
        PreparedStatement       pstmt = null ;
        ResultSet               rset = null ;
        String selectSQL = "SELECT COUNT(*) FROM TRANSACTION_TB WHERE TRANSACTION_ID = ?" ;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD) ;
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setInt(1, transaction_id) ;
            rset = pstmt.executeQuery() ;

            while (rset.next()) {
                checkTransId = rset.getInt("COUNT(*)") ;
                if (checkTransId == 1) {
                    return checkTransId ;
                }
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
        return checkTransId ;
    }

    // 낙찰가와 user 잔액 차이 계산, 거래 성사여부 1(성공) 0(실패)로 확인
    public String statusCheckRow(int transaction_id, String buyer_id) {
        System.out.println(">>>> 거래 ID 확인 중: dao statusCheckRow");
        int userBalance = 0 ;
        int leftUserBalance = 0 ;
        int auctionPrice = 0 ;
        String finalPayStatus = "실패" ;
        Connection conn =       null ;
        PreparedStatement       pstmt_price = null ;
        PreparedStatement       pstmt_balance = null ;
        ResultSet               rset_balance = null ;
        ResultSet               rset_price = null ;
        String selectSQL_balance = "SELECT BALANCE FROM USER_TB WHERE USER_ID = ?" ;
        String selectSQL_finalPrice = "SELECT FINAL_PRICE FROM TRANSACTION_TB WHERE TRANSACTION_ID = ?" ;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD) ;
            pstmt_price = conn.prepareStatement(selectSQL_finalPrice);
            pstmt_price.setInt(1, transaction_id) ;

            pstmt_balance = conn.prepareStatement(selectSQL_balance) ;
            pstmt_balance.setString(1, buyer_id);

            rset_price = pstmt_price.executeQuery() ;
            rset_balance = pstmt_balance.executeQuery() ;

            while (rset_balance.next()) {
                userBalance = rset_balance.getInt("BALANCE") ;
                auctionPrice = rset_price.getInt("FINAL_PRICE") ;
                leftUserBalance = userBalance - auctionPrice ;

                if (leftUserBalance >= 0) {
                    System.out.println("결제를 성공적으로 마쳤습니다.");
                    System.out.println("잔액은 " +leftUserBalance +"원입니다.");
                    finalPayStatus = "완료" ;

                } else {
                    leftUserBalance = userBalance ;
                    System.out.println("잔액이 부족합니다.");
                    System.out.println("잔액은 " +leftUserBalance +"원입니다.");
                }

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
        System.out.println("결제에 실패했습니다.");
        return finalPayStatus; // 성공시 1, 실패시 0
    }

    // 결제 테이블에 insert
    public int TBLresultRow(int transaction_id, int payOpt, String paymentStatus) {
        System.out.println(">>> dao insertRow") ;
        int insertFlag = 0 ;
        Connection conn = null ;
        PreparedStatement pstmt = null ;

        String insertSQL = "INSERT INTO PAYMENT_TB(PAYMENT_ID, TRANSACTION_ID, PAYMENT_METHOD, PAYMENT_STATUS, PAYMENT_TIME) " +
                            "VALUES(PAYMENT_ID_SEQ.NEXTVAL, ?, ?, ?, SYSTIMESTAMP)" ;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD) ;
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setInt(1, transaction_id) ;

            switch (payOpt) {
                case 1:
                    pstmt.setString(2, "카드") ;
                case 2:
                    pstmt.setString(2, "계좌이체") ;
                case 3:
                    pstmt.setString(2, "페이팔") ;
                case 4:
                    pstmt.setString(2, "기타") ;

            }
            
            pstmt.setString(3, paymentStatus) ;
            insertFlag = pstmt.executeUpdate() ;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close() ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (insertFlag == 0) {
            System.out.println("결제 테이블 업데이트에 실패했습니다");
        } else {
            System.out.println("결제 테이블 업데이트에 성공했습니다");
        }
        return insertFlag ; // 임의의 수 
    }

    // 경매 완료 여부 확인
    public String auctionCMPLTrow(int transaction_id) {
        System.out.println(">>>> 경매 완료 여부 확인 중: dao auctionCMPLTrow");
        String checkAuctionCMPLT = null ;
        Connection conn =       null ;
        PreparedStatement       pstmt = null ;
        ResultSet               rset = null ;
        String selectSQL = "SELECT IS_COMPLETED FROM TRANSACTION_TB WHERE TRANSACTION_ID = ?" ;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD) ;
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setInt(1, transaction_id) ;
            rset = pstmt.executeQuery() ;

            while (rset.next()) {
                checkAuctionCMPLT = rset.getString("IS_COMPLETED") ;
                if (checkAuctionCMPLT.equals("1")) {
                    return checkAuctionCMPLT ;
                }
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
        System.out.println("경매가 완료되지 않았습니다.");
        return checkAuctionCMPLT ;
    }

}
