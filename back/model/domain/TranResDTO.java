package back.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@ToString
@Setter
public class TranResDTO {
    private int transactionId;     // 거래 ID
    private int itemId;            // 상품 ID
    private String sellerId;       // 판매자 ID
    private String title;          // 상품명
    private String description;    // 상품 설명
    private double finalPrice;     // 최종 낙찰가
    private String transactionTime; // 거래 시간
    private String isCompleted;   // 거래 완료 여부
}
