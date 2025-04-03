package back.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserDTO {
    private String userId;
    private String userPw;
    private String userName;
}
