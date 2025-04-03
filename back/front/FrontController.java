package back.front;

import back.ctrl.PaymentController;
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

    public int payment(int transaction_id) {
        System.out.println(" >>> FrontController payment 거래 ID 확인");
        PaymentController payCtrl = (PaymentController) factory.getCtrl("payment") ;

        int paySuccessful = payCtrl.tranIdCheck(transaction_id);  // 거래 아이디를 찾으면 1을 반환
        return paySuccessful ;
    }

    public String buyerId(int transaction_id) {
        PaymentController payCtrl = (PaymentController) factory.getCtrl("payment") ;
        return payCtrl.buyerId(transaction_id) ;
    }

    public int payStatus(int transaction_id, String buyer_id) {
        System.out.println(" >>> FrontController payment 거래 진행");
        PaymentController payCtrl = (PaymentController) factory.getCtrl("payment") ;

        // 할 일: 거래 id랑 결제 수단 전달하고, 거래 성사 여부 확정
        int isPayStatus = payCtrl.isStatus(transaction_id, buyer_id);  // 거래가 성사되면 1을 반환, 성사 안되면 0 반환
        return isPayStatus ;

    }


}