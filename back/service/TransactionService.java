package back.service;

import java.util.List;
import java.util.Optional;

import back.model.dao.AuctionDAO;
import back.model.dao.HistoryDAO;
import back.model.domain.ResponseDTO;
import back.model.domain.TranResDTO;

public class TransactionService {

            /*5. TransactionService.java
        기능: 트랜잭션 관련 서비스 처리. 입찰과 경매 종료 등을 트랜잭션으로 묶어 처리.

        주요 메서드:
        트랜잭션 관리
        입찰 및 경매 종료 시 데이터 일관성 유지
        트랜잭션 처리 */
    private HistoryDAO dao;
    
    public TransactionService(){
        dao = new HistoryDAO();
    }

    public Optional<List<TranResDTO>> tranService(){
        System.out.println(">>> debug transactionService_all");
        return dao.selectRow();
    }
}
