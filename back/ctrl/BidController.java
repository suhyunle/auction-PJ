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
                // System.out.println(">>> debug updateBid");
               return service.bidService(map);
        }
}