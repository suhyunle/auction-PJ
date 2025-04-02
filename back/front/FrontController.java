package back.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import back.ctrl.AuctionController;
import back.ctrl.BidController;
import back.ctrl.RegisterController;
import back.factory.BeanFactory;
import back.model.domain.RequestDTO;
import back.model.domain.ResponseDTO;

public class FrontController {
    
    private BeanFactory factory;
    public FrontController(){
        factory = BeanFactory.getInstance();
    }

    // 물품 조회 컨트롤러
    public List<ResponseDTO> watchItem(){
        // auctionController로 연결 -> 경매 물품 조회

        System.out.println(">>>> FrontController list");
        //   factory로 부터 instance를 반환받는 형식으로 바뀌어야한다.
        
        AuctionController auctionCtrl = (AuctionController)factory.getCtrl("list");
        List<ResponseDTO> list = auctionCtrl.watchItem();
        return list;
    }

    // 물품 등록 Controller 
    public int register(String name, String content, int priorty){
        System.out.println(">>>> FrontController register");
        RegisterController registerCtrl = (RegisterController)factory.getCtrl("register");
        RequestDTO request = RequestDTO.builder()
                                            .name(name)
                                            .content(content)
                                            .priority(priorty)
                                            .build();
        return registerCtrl.registerItem(request);
    }

    // 물품 입찰 컨트롤러
    public int update(int seq, String content, String check){
        System.out.println(">>>> FrontController update");
        BidController bidCtrl = (BidController)factory.getCtrl("bid");
        Map<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        map.put("content", content);
        map.put("status", check);
        return bidCtrl.updateBid(map);
    }
}
