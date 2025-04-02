package back.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RequestDTO {
    private String name;
    private String description;
    private int sellerId; // 판매자 ID
    private int startingPrice; // 시작가
    private String endTime; // 경매 종료 시간
}
