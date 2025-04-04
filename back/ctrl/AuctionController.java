package back.ctrl;

import java.util.List;
import java.util.Optional;

import back.model.domain.ResponseDTO;
import back.service.AuctionService;

public class AuctionController {
    private AuctionService service;

    public AuctionController(AuctionService service){
        this.service = service;
    }

    public AuctionController(){
        // service = new TodoService();
    }

    public List<ResponseDTO> watchItem(){
        
        // System.out.println(">>> debug selectTodo_all");
        
        Optional<List<ResponseDTO>> response = service.auctionService();
        if(response.isPresent()){
            return response.get();
        }else{
            return null;
        }
    }



}
