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
        // 1. 입력 값 검증 (Validation)
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("상품 제목을 입력하세요.");
        }
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("상품 내용을 입력하세요.");
        }
        if (request.getPriority() < 0) {
            throw new IllegalArgumentException("우선순위는 0 이상이어야 합니다.");
        }

        // 2. 데이터 보정 (Trim 및 기본값 설정)
        RequestDTO updatedRequest = RequestDTO.builder()
                            .name(request.getName().trim())
                            .content(request.getContent().trim())
                            .priority(request.getPriority())
                            .createdAt(new Date()) // 현재 시간을 등록 시간으로 설정
                            .status("진행 중") // 기본 상태값 설정
                            .build();

        // 3. 트랜잭션 처리 및 등록 실행
        int result = dao.insertRow(updatedRequest);

        // 4. 로그 기록
        if (result > 0) {
            System.out.println("경매 물품 등록 완료: " + updatedRequest.getName());
        } else {
            System.out.println("경매 물품 등록 실패");
        }

        return dao.insertRow(request);
    }
}
