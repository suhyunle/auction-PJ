package back.ctrl;

import java.util.Map;

import back.service.BidService;

public class BidController {

        private BidService service;

        public BidController(BidService service){
                this.service = service;
        }

        public BidController(){
        }

        public int updateBid(Map<String, Object> map){
                System.out.println(">>> debug updateBid");
               return service.updateService(map);
        }
}

/*. BidController.java
기능: 입찰 관련 로직을 처리하는 컨트롤러. 사용자가 입찰을 요청할 때 처리하고, 
입찰 금액을 DB에 저장하거나, 입찰 내역을 조회하는 역할.

주요 메서드:
입찰 요청 처리
입찰 기록 조회
입찰 상태 업데이트
*/