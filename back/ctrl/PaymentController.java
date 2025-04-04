package back.ctrl;

import back.service.PaymentService;

public class PaymentController {
    private PaymentService paymentService ;
    String buyerId ;

   public PaymentController() {

   }

   public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService ;
   }

   public String buyerId(int transaction_id) {
        return paymentService.buyerIdService(transaction_id) ;
   }

   public int tranIdCheck(int transaction_id) {
        System.out.println("거래 ID 확인 중: PaymentController");
        // return 1 ;
        return paymentService.transIdCheckService(transaction_id) ;
        // 테스트용으로 1 반환하게 설정함. DBMS와 연결해서 생각해볼 것.
   }

   public String isStatus(int transaction_id, String buyer_id) {
        System.out.println("거래 성사 여부 확인 중: PaymentController isStatus");
        return paymentService.statusCheckService(transaction_id, buyer_id) ;
   }

   public int payTBLinsert(int transaction_id, int payOpt, String paymentStatus) {
        System.out.println("payment_tb 업데이트 중: PaymentController payTBLinsert") ;
        return paymentService.payTBLservice(transaction_id, payOpt, paymentStatus) ;
   }

   public int auctionCMPLT(int transaction_id) {
        System.out.println("payment_tb 업데이트 중: PaymentController payTBLinsert") ;
        return paymentService.auctionCMPLTservice(transaction_id) ; // 임의의 값
    
   }

    public int chargeBalance(String buyer_id, int amount) {
        return paymentService.chargeBalance(buyer_id, amount);
    }
}
