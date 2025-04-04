package back.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import back.ctrl.AuctionController;
import back.ctrl.BidController;
import back.ctrl.HistoryController;
import back.ctrl.PaymentController;
import back.ctrl.RegisterController;
import back.ctrl.UserController;
import back.factory.BeanFactory;
import back.model.domain.RequestDTO;
import back.model.domain.ResponseDTO;
import back.model.domain.TranResDTO;
import back.model.domain.UserDTO;
import back.session.UserSession;

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
    public int register(String itemName, String description, int startingPrice){
        System.out.println(">>>> FrontController register-");
        String sellerId = UserSession.getLoggedInUser(); // 로그인한 사용자 ID 가져오기
        RegisterController registerCtrl = (RegisterController)factory.getCtrl("register");
        RequestDTO request = RequestDTO.builder()
                                            .itemName(itemName)
                                            .description(description)
                                            .startingPrice(startingPrice)
                                            .sellerId(sellerId)
                                            .build();
        return registerCtrl.registerItem(request);
    }

    // 물품 입찰 컨트롤러
    public int updateBid(int itemId, int bidAmount, String userId){
        System.out.println(">>>> FrontController updateBid 호출");

        BidController bidCtrl = (BidController) factory.getCtrl("bid");
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);       // 올바른 키값으로 변경
        map.put("bidAmount", bidAmount); // 올바른 키값으로 변경
        map.put("userId", userId);       // 사용자 ID 추가

        return bidCtrl.updateBid(map);
    }

    public int signIn(String userId, String userPw, String userName) {
        System.out.println(" >>> FrontController SignIn");
        UserController userCtrl = (UserController) factory.getCtrl("signIn") ;
        UserDTO request = UserDTO.builder()
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
        UserDTO request = UserDTO.builder()
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

    // 거래 조회 컨트롤러
    public List<TranResDTO> watchTran(){

        System.out.println(">>>> FrontController tranlist");
        
        HistoryController historyCtrl = (HistoryController)factory.getCtrl("history");
        List<TranResDTO> list = historyCtrl.watchTran();
        return list;
    }
    // 결제
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

    public String payStatus(int transaction_id, String buyer_id) {
        System.out.println(" >>> FrontController payment 거래 진행");
        PaymentController payCtrl = (PaymentController) factory.getCtrl("payment") ;

        // 할 일: 거래 id랑 결제 수단 전달하고, 거래 성사 여부 확정
        String isPayStatus = payCtrl.isStatus(transaction_id, buyer_id);  // 거래가 성사되면 1을 반환, 성사 안되면 0 반환
        // return isPayStatus ;
        return "완료" ;  // 테스트용으로 완료 설정. 위에 것으로 변경하기 

    }

    public int payTBLInsrt(int transaction_id, int payOpt, String paymentStatus) {
        System.out.println(" >>> FrontController payment 결제 테이블 업데이트 진행");
        PaymentController payCtrl = (PaymentController) factory.getCtrl("payment") ;

        int isPayTBLinsert = payCtrl.payTBLinsert(transaction_id, payOpt, paymentStatus) ;
        
        return isPayTBLinsert ; 

    }

    public int auctionStatus(int transaction_id) {
        System.out.println(" >>> FrontController payment 경매 완료 여부 확인");
        PaymentController payCtrl = (PaymentController) factory.getCtrl("payment") ;

        int isAuctionCMPLT = payCtrl.auctionCMPLT(transaction_id) ;
        return isAuctionCMPLT ;

    }

    public int chargeBalance(String buyer_id, int amount) {
        PaymentController payCtrl = (PaymentController) factory.getCtrl("payment") ;
        return payCtrl.chargeBalance(buyer_id, amount);
    }
}
