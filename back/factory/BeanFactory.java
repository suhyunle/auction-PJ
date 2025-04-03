package back.factory;

import java.util.HashMap;
import java.util.Map;

import back.ctrl.PaymentController;
import back.ctrl.UserController;
import back.service.PaymentService;
import back.service.UserService;

public class BeanFactory {
    public Map<String, Object> map ;
    private static BeanFactory instance ;

    private BeanFactory() {
        map = new HashMap<>() ;

        // 회원가입 컨트롤러
        UserService userService = new UserService() ;
        map.put("signIn", new UserController(userService)) ;
        map.put("logIn", new UserController(userService)) ;

        // NEW: 결제 컨트롤러
        PaymentService paymentService = new PaymentService() ;
        map.put("payment", new PaymentController(paymentService)) ;
    }

    public static BeanFactory getInstance() {
        if (instance == null) {
            instance = new BeanFactory() ;
        }
        return instance ;
    }

    public Object getCtrl(String key) {
        return map.get(key) ;
    }
    
}
