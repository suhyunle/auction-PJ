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
    private int auctionId;
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String status;  // 진행 중 / 완료
    private int highestBid;
}
