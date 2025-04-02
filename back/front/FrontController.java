package back.front;

import back.ctrl.UserController;
import back.factory.BeanFactory;
import back.model.domain.RequestDTO;

public class FrontController {
    private BeanFactory factory ;

    // factory 객체 생성
    public FrontController() {
        factory = BeanFactory.getInstance() ;
    }
    
    public int signIn(String userId, String userPw, String userName) {
        System.out.println(" >>> FrontController SignIn");
        UserController userCtrl = (UserController) factory.getCtrl("signIn") ;
        RequestDTO request = RequestDTO.builder()
                            .userId(userId)
                            .userPw(userPw)
                            .userName(userName)
                            .build() ;
        
        int regiNum = userCtrl.signInCtrl(request) ;
        
        return regiNum ;

    }

    public String logIn(String userId, String userPw) {
        System.out.println(" >>> FrontController LogIn");
        UserController userCtrl = (UserController) factory.getCtrl("logIn") ;
        RequestDTO request = RequestDTO.builder()
                            .userId(userId)
                            .userPw(userPw)
                            .build() ;
        String logInUser = userCtrl.logInCtrl(request) ;

        if (logInUser == null) {
            return "로그인 실패" ;
        } else {
            return logInUser + "님, 로그인에 성공하셨습니다." ;
        }
    }
}