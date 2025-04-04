package back.ctrl;

import java.util.List;
import java.util.Optional;

import back.model.domain.ResponseDTO;
import back.model.domain.TranResDTO;
import back.service.AuctionService;
import back.service.TransactionService;

public class HistoryController {
    private TransactionService service;

    public HistoryController(TransactionService service){
        this.service = service;
    }

    public HistoryController(){
        // service = new TodoService();
    }

    public List<TranResDTO> watchTran(){
        
        // System.out.println(">>> debug selectTodo_all");
        
        Optional<List<TranResDTO>> response = service.tranService();
        if(response.isPresent()){
            return response.get();
        }else{
            return null;
        }
    }
}
