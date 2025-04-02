package back.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString

public class ResponseDTO {
    private String userId ;
    private String userPw ;
    private String userName ;
}
