package back.service;

import back.model.dao.PaymentDAO;

public class PaymentService {
    private PaymentDAO paymentDAO ;

    public PaymentService() {
        paymentDAO = new PaymentDAO() ;

    }


    public String buyerIdService(int transaction_id) {
        System.out.println("구매자 ID 확인 중: PaymentService");
        return paymentDAO.buyerIdRow(transaction_id) ;
         // 임시로 1로 설정

    }

    public int transIdCheckService(int transaction_id) {
        System.out.println("거래 ID 확인 중: PaymentService");
        return paymentDAO.transIdCheckRow(transaction_id) ;
         // 임시로 1로 설정

    }

    public String statusCheckService(int transaction_id, String buyer_id) {
        System.out.println("거래 성사 여부 확인 중: Payment statusCheckService");
        // System.out.println(paymentDAO.statusCheckRow(transaction_id, payOpt));
        return paymentDAO.statusCheckRow(transaction_id, buyer_id) ;
         // 임시로 1로 설정

    }

    public int payTBLservice(int transaction_id, int payOpt, String paymentStatus) {
        return paymentDAO.TBLresultRow(transaction_id, payOpt, paymentStatus) ; // 임의로 0 설정

    }

    public String auctionCMPLTservice(int transaction_id) {
        return paymentDAO.auctionCMPLTrow(transaction_id) ;

    }

    
    
}
