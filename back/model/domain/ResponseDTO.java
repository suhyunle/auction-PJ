package back.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@ToString
@Setter

public class ResponseDTO {
    private int itemId;         // 물품 ID
    private String sellerId;    // 판매자 ID
    private String title;       // 물품 이름
    private String description; // 물품 설명
    private int currentPrice;   // 현재 최고 입찰가
    private String startTime;   // 경매 시작 시간
    private String endTime;     // 경매 종료 시간
    private String status;      // 진행 상태 (진행중/완료)
}
