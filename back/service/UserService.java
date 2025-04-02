package back.service;

import back.model.dao.UserDAO;
import back.model.domain.RequestDTO;

public class UserService {

            /*3. UserService.java
        기능: 사용자 관련 비즈니스 로직을 처리. 로그인, 회원가입, 사용자 정보 수정 등의 처리를 담당.

        주요 메서드:
        로그인 처리
        회원가입 처리
        사용자 정보 수정
        */

    private UserDAO userDAO ;
    private static final String USERID_REGEX = "^[a-zA-Z]+$";

    public UserService() {
        userDAO = new UserDAO() ;

    }

    // public boolean isValidUserId(String userId) {
    //     return userId.matches(USERID_REGEX);
    // }

    // public boolean isValidUserPw(String userPw) {
    //     return userPw.matches(USERID_REGEX);
    // }

    public int signInService(RequestDTO requset) {

        // if (isValidUserId(requset.getUserId())) {
        //     System.out.println("회원가입 진행 중: UserService");
            
        // } else {
        //     System.out.println("ID에는 영문과 숫자만 사용 가능합니다");
        //     System.out.println("회원가입 실패");
        // }
        System.out.println("회원가입 진행 중: UserService");
        return userDAO.signInRow(requset) ;
    }

    public String logInService(RequestDTO requset) {
        System.out.println("로그인 진행 중: UserService");
        return userDAO.logInRow(requset) ;
    }
    
}
