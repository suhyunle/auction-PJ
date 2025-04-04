package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import back.session.DBConnection;
import back.session.UserSession;

public class PaymentDAO {

    // 구매자 id 확인
    public String buyerIdRow(int transaction_id) {
        // System.out.println(">>>> 구매자 ID 확인 중: dao transIdCheckRow");
        String buyerId = null ;
        PreparedStatement       pstmt = null ;
        ResultSet               rset = null ;
        String buyerIdSelectSQL = "SELECT BUYER_ID FROM TRANSACTION_TB WHERE TRANSACTION_ID = ?" ;
        
        try {
            Connection conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(buyerIdSelectSQL);
            pstmt.setInt(1, transaction_id) ;
            rset = pstmt.executeQuery() ;

            if (rset.next()) {
                buyerId = rset.getString("BUYER_ID");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return buyerId ;
    }

    // 거래 id 존재 여부 확인
    public int transIdCheckRow(int transaction_id) {
        // System.out.println(">>>> 거래 ID 확인 중: dao transIdCheckRow");
        int checkTransId = 0 ;
        PreparedStatement       pstmt = null ;
        ResultSet               rset = null ;
        String selectSQL = "SELECT COUNT(*) FROM TRANSACTION_TB WHERE TRANSACTION_ID = ?" ;
        try {
            Connection conn = DBConnection.getConnection();
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
        } 
        
        return checkTransId ;
    }

    // 낙찰가와 user 잔액 차이 계산, 거래 성사여부 1(성공) 0(실패)로 확인
    public String statusCheckRow(int transaction_id, String buyer_id) {
        // System.out.println(">>>> 거래 ID 확인 중: dao statusCheckRow");
        int userBalance = 0 ;
        int leftUserBalance = 0 ;
        int auctionPrice = 0 ;
        String finalPayStatus = "실패" ;
        PreparedStatement       pstmt_price = null ;
        PreparedStatement       pstmt_balance = null ;
        ResultSet               rset_balance = null ;
        ResultSet               rset_price = null ;

        // String loggedInUser = UserSession.getLoggedInUser(); // 로그인한 사용자 ID 가져오기

        String selectSQL_balance = "SELECT BALANCE FROM USER_TB WHERE USER_ID = ?" ;
        String selectSQL_finalPrice = "SELECT FINAL_PRICE FROM TRANSACTION_TB WHERE TRANSACTION_ID = ?" ;

        try {
            Connection conn = DBConnection.getConnection(); // 공통 DB 연결 사용
            pstmt_price = conn.prepareStatement(selectSQL_finalPrice);
            pstmt_price.setInt(1, transaction_id) ;

            pstmt_balance = conn.prepareStatement(selectSQL_balance) ;
            pstmt_balance.setString(1, buyer_id);

            rset_price = pstmt_price.executeQuery() ;
            rset_balance = pstmt_balance.executeQuery() ;

            while (rset_balance.next()) {
                userBalance = rset_balance.getInt("BALANCE") ;
                if (rset_price.next()) {
                    auctionPrice = rset_price.getInt("FINAL_PRICE");
                }
                leftUserBalance = userBalance - auctionPrice ;

                if (leftUserBalance >= 0) {
                    System.out.println("결제를 성공적으로 마쳤습니다.");
                    System.out.println("잔액은 " +leftUserBalance +"원입니다.");
                    finalPayStatus = "완료" ;
                    if (finalPayStatus.equals("완료")) {
                        int price = getFinalPrice(transaction_id); // 따로 final_price 조회하는 메서드 필요
                        deductBalance(buyer_id, price);
                        completeTransaction(transaction_id);
                        // TBLresultRow(transaction_id, 1, finalPayStatus); // payOpt 하드코딩 또는 파라미터화
                    }

                } else {
                    leftUserBalance = userBalance ;
                    System.out.println("잔액이 부족합니다.");
                    finalPayStatus = "실패";
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return finalPayStatus; // 성공시 1, 실패시 0
    }

    // 결제 테이블에 insert
    public int TBLresultRow(int transaction_id, int payOpt, String paymentStatus) {
        // System.out.println(">>> dao insertRow") ;
        // System.out.println("삽입 시도 중인 transaction_id: " + transaction_id);
        int insertFlag = 0 ;
        PreparedStatement pstmt = null ;

        String insertSQL = "INSERT INTO PAYMENT_TB(PAYMENT_ID, TRANSACTION_ID, PAYMENT_METHOD, PAYMENT_STATUS, PAYMENT_TIME) " +
                            "VALUES(PAY_SEQ.NEXTVAL, ?, ?, ?, SYSTIMESTAMP)" ;
        try {
            Connection conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setInt(1, transaction_id) ;

            switch (payOpt) {
                case 1:
                    pstmt.setString(2, "신용카드") ;
                    break;
                case 2:
                    pstmt.setString(2, "계좌이체") ;
                    break;
                case 3:
                    pstmt.setString(2, "페이팔") ;
                    break;
                case 4:
                    pstmt.setString(2, "기타") ;
                    break;
                default:
                    pstmt.setString(2, "기타"); // 혹시 모르니 기본값도 설정

            }
            
            pstmt.setString(3, paymentStatus) ;
            insertFlag = pstmt.executeUpdate() ;
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (insertFlag == 0) {
            System.out.println("결제 테이블 업데이트에 실패했습니다");
        } else {
            System.out.println("결제 테이블 업데이트에 성공했습니다");
        }
        return insertFlag ; // 임의의 수 
    }

    // 경매 완료 여부 확인
    public int auctionCMPLTrow(int transaction_id) {
        // System.out.println(">>>> 경매 완료 여부 확인 중: dao auctionCMPLTrow");
        int checkAuctionCMPLT = 0 ;
        PreparedStatement       pstmt = null ;
        ResultSet               rset = null ;
        String selectSQL = "SELECT IS_COMPLETED FROM TRANSACTION_TB WHERE TRANSACTION_ID = ?" ;
        try {
            Connection conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(selectSQL);
            pstmt.setInt(1, transaction_id) ;
            rset = pstmt.executeQuery() ;

            while (rset.next()) {
                checkAuctionCMPLT = rset.getInt("IS_COMPLETED") ;
                return checkAuctionCMPLT ;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return checkAuctionCMPLT ;
    }

    public int updateBalance(String buyer_id, int amount) {
        PreparedStatement pstmt = null;
        int result = 0;
    
        String updateSQL = "UPDATE USER_TB SET BALANCE = BALANCE + ? WHERE USER_ID = ?";
    
        try {
            String loggedInUser = UserSession.getLoggedInUser(); // 로그인한 사용자 ID 가져오기
            Connection conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(updateSQL);
            pstmt.setInt(1, amount);
            pstmt.setString(2, loggedInUser);
    
            result = pstmt.executeUpdate(); // 성공 시 1 반환
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return result;
    }

    // 잔액 차감 메서드 추가
    public int deductBalance(String buyer_id, int amount) {
        PreparedStatement pstmt = null;
        int result = 0;
        String updateSQL = "UPDATE USER_TB SET BALANCE = BALANCE - ? WHERE USER_ID = ?";

        try {
            Connection conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(updateSQL);
            pstmt.setInt(1, amount);
            pstmt.setString(2, buyer_id);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 경매 완료 상태로 변경
    public int completeTransaction(int transaction_id) {
        PreparedStatement pstmt = null;
        int result = 0;
        String updateSQL = "UPDATE TRANSACTION_TB SET IS_COMPLETED = 1 WHERE TRANSACTION_ID = ?";

        try {
            Connection conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(updateSQL);
            pstmt.setInt(1, transaction_id);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public int getFinalPrice(int transaction_id) {
        int price = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = "SELECT FINAL_PRICE FROM TRANSACTION_TB WHERE TRANSACTION_ID = ?";
    
        try {
            Connection conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, transaction_id);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                price = rset.getInt("FINAL_PRICE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return price;
    }
}
