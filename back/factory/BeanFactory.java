package back.factory;

import java.util.HashMap;
import java.util.Map;

import back.ctrl.AuctionController;
import back.ctrl.BidController;
import back.ctrl.HistoryController;
import back.ctrl.RegisterController;
import back.service.AuctionService;
import back.service.BidService;
import back.service.RegisterService;

public class BeanFactory {
    private static BeanFactory instance;
    private final Map<String, Object> map;

    private BeanFactory() {
        map = new HashMap<>();
        AuctionService auctionService = new AuctionService();
        RegisterService registerService = new RegisterService();
        BidService bidService = new BidService();

        map.put("list", new AuctionController(auctionService));
        map.put("register", new RegisterController(registerService));
        map.put("bid", new BidController(bidService));
        // map.put("history", new HistoryController(auctionService));

    }

    public static BeanFactory getInstance() {
        if (instance == null) {
            instance = new BeanFactory();
        }
        return instance;
    }

    public Object getCtrl(String key) {
        return map.get(key);
    }
}
