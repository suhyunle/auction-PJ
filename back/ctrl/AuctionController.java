package back.ctrl;

import java.util.List;
import java.util.Optional;

import back.model.domain.ResponseDTO;
import back.service.AuctionService;

public class AuctionController {
            /* AuctionController.java
        기능: 경매 관련 로직을 처리하는 컨트롤러. 경매 상품 정보 조회, 경매 종료 처리, 경매 상태 관리 등을 담당.

        주요 메서드:
        경매 상품 목록 조회
        경매 상품 상세 정보 조회
        경매 종료 및 알림 */
    private AuctionService service;

    public AuctionController(AuctionService service){
        this.service = service;
    }

    public AuctionController(){
        // service = new TodoService();
    }

    public List<ResponseDTO> watchItem(){
        
        System.out.println(">>> debug selectTodo_all");
        
        Optional<List<ResponseDTO>> response = service.auctionService();
        if(response.isPresent()){
            return response.get();
        }else{
            return null;
        }
    }



}
