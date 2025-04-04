package back.service;

import java.util.List;
import java.util.Optional;

import back.model.dao.AuctionDAO;
import back.model.dao.HistoryDAO;
import back.model.domain.ResponseDTO;
import back.model.domain.TranResDTO;

public class TransactionService {

    private HistoryDAO dao;
    
    public TransactionService(){
        dao = new HistoryDAO();
    }

    public Optional<List<TranResDTO>> tranService(){
        // System.out.println(">>> debug transactionService_all");
        return dao.selectRow();
    }
}
