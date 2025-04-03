package back.factory;

import java.util.HashMap;
import java.util.Map;

import back.ctrl.AuctionController;
import back.ctrl.BidController;
import back.ctrl.HistoryController;
import back.ctrl.RegisterController;
import back.ctrl.UserController;
import back.service.AuctionService;
import back.service.BidService;
import back.service.RegisterService;
import back.service.TransactionService;
import back.service.UserService;

public class BeanFactory {
    private static BeanFactory instance;
    private final Map<String, Object> map;

    private BeanFactory() {
        map = new HashMap<>();
        AuctionService auctionService = new AuctionService();
        RegisterService registerService = new RegisterService();
        BidService bidService = new BidService();
        UserService userService = new UserService() ;
        TransactionService transactionService = new TransactionService();

        map.put("list", new AuctionController(auctionService));
        map.put("register", new RegisterController(registerService));
        map.put("bid", new BidController(bidService));
        map.put("signIn", new UserController(userService)) ;
        map.put("logIn", new UserController(userService)) ;
        map.put("history", new HistoryController(transactionService));

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
