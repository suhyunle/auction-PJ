package back.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RequestDTO {
    private String itemName;
    private String description;
    private int startingPrice; // 시작가
    private int currentPrice;  // ✅ 추가 필요
    private String endTime; // 경매 종료 시간
    private String sellerId; // 로그인한 사용자 ID
}
