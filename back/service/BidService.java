package back.service;

import java.util.Map;

import back.model.dao.BidDAO;

public class BidService {
    
    /*1. BidService.java
    기능: 입찰 관련 비즈니스 로직을 처리. 입찰 처리, 입찰 금액 갱신, 최고 입찰자 확인 등을 담당.

    주요 메서드:
    입찰 처리
    최고 입찰 금액 갱신
    입찰 내역 조회 */

    private BidDAO dao;
    
    public BidService(){
        dao = new BidDAO();
    }

    public int updateService(Map<String, Object> map){
        System.out.println(">>> debug bidService");
        
        // 1. 유효성 검사 (입찰 데이터 체크)
        if (!map.containsKey("itemId") || !map.containsKey("userId") || !map.containsKey("bidAmount")) {
            throw new IllegalArgumentException("입찰 정보가 올바르지 않습니다.");
        }

        int itemId = (int) map.get("itemId");
        int userId = (int) map.get("userId");
        int bidAmount = (int) map.get("bidAmount");

        if (bidAmount <= 0) {
            throw new IllegalArgumentException("입찰 금액은 0보다 커야 합니다.");
        }

        // 2. 현재 최고 입찰 금액 확인 (DB 조회)
        int highestBid = dao.getHighestBid(itemId);

        if (bidAmount <= highestBid) {
            System.out.println("❌ 현재 최고 입찰 금액보다 높은 금액을 입력하세요.");
            return -1; // 입찰 실패
        }

        return dao.insertRow(map);
    }
}
