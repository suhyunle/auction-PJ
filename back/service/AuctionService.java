package back.service;

import java.util.List;
import java.util.Optional;

import back.model.dao.AuctionDAO;
import back.model.domain.ResponseDTO;

public class AuctionService {

        /*2. AuctionService.java
    기능: 경매 관련 비즈니스 로직을 처리. 경매 상품 상태 관리, 경매 종료 시 최고 입찰자 알림 등을 담당.

    주요 메서드:
    경매 상태 관리
    경매 종료 처리 및 알림
    경매 상품 목록 조회 */
    
    private AuctionDAO dao;
    
    public AuctionService(){
        dao = new AuctionDAO();
    }

    public Optional<ResponseDTO> auctionService(int seq){
        System.out.println(">>>> debug auctionService");
        // TodoResponseDTO resDao = dao.selectDao(num);
        return dao.selectRow(seq);
    }

    public Optional<List<ResponseDTO>> auctionService(){
        System.out.println(">>> debug selectService_all");
        return dao.selectRow();
    }
}
