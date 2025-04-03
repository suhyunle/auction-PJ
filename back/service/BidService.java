package back.service;

import java.util.Map;

import back.model.dao.AuctionDAO;
import back.model.dao.BidDAO;

public class BidService {
    
    /*1. BidService.java
    기능: 입찰 관련 비즈니스 로직을 처리. 입찰 처리, 입찰 금액 갱신, 최고 입찰자 확인 등을 담당.

    주요 메서드:
    입찰 처리
    최고 입찰 금액 갱신
    입찰 내역 조회 */
    // private AuctionDAO aDao;
    private BidDAO dao;
    
    public BidService(){
        dao = new BidDAO();
        // aDao = new AuctionDAO();
    }

    public int bidService(Map<String, Object> map){
        System.out.println(">>> debug bidService");
        
        // 1. 유효성 검사 (입찰 데이터 체크)
        if (!map.containsKey("itemId") || !map.containsKey("userId") || !map.containsKey("bidAmount")) {
            throw new IllegalArgumentException("입찰 정보가 올바르지 않습니다.");
        }

        int itemId = (int) map.get("itemId");
        String userId = (String) map.get("userId");
        int bidAmount = (int) map.get("bidAmount");

        if (bidAmount <= 0) {
            throw new IllegalArgumentException("입찰 금액은 0보다 커야 합니다.");
        }

        // 2. 현재 최고 입찰 금액 확인 (DB 조회)
        Integer highestBid = dao.getHighestBid(itemId);

         // 🔹 만약 기존 입찰이 없다면, 해당 경매의 시작가(starting_price) 조회
        if (highestBid == null) {
            highestBid = dao.getStartingPrice(itemId); 
        }

        // 🔹 입찰 금액이 시작가보다 낮다면 실패 처리
        if (bidAmount < highestBid) {
            System.out.println("입찰 금액은 최소 " + highestBid + " 이상이어야 합니다.");
            return -1; // 입찰 실패
        }

        return dao.insertRow(map);
    }
}
