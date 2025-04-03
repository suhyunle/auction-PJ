package back.service;

import back.model.dao.RegisterDAO;
import back.model.domain.RequestDTO;

public class RegisterService {
    private RegisterDAO dao;
    
    public RegisterService(){
        dao = new RegisterDAO();
    }

    public int registerService(RequestDTO request){
        System.out.println(">>> debug register Service");

        // 2. 데이터 보정 (Trim 및 기본값 설정)
        RequestDTO updatedRequest = RequestDTO.builder()
                                .itemName(request.getItemName().trim())
                                .description(request.getDescription().trim())
                                .startingPrice(request.getStartingPrice())
                                .sellerId(request.getSellerId()) // 로그인한 판매자 ID 유지
                                .build();

        // 3. 트랜잭션 처리 및 등록 실행
        int result = dao.insertRow(updatedRequest);

        // 4. 로그 기록
        if (result > 0) {
            System.out.println("경매 물품 등록 완료: " + updatedRequest.getItemName());
        } else {
            System.out.println("경매 물품 등록 실패");
        }

        return result;
    }
}
