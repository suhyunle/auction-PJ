package back.factory;

import java.util.HashMap;
import java.util.Map;

import back.ctrl.UserController;
import back.service.UserService;

public class BeanFactory {
    public Map<String, Object> map ;
    private static BeanFactory instance ;

    private BeanFactory() {
        map = new HashMap<>() ;
        UserService userService = new UserService() ;
        map.put("signIn", new UserController(userService)) ;
        map.put("logIn", new UserController(userService)) ;

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
