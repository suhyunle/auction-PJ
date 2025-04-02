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

    public UserService() {
        userDAO = new UserDAO() ;

    }

    public int signInService(RequestDTO requset) {
        System.out.println("회원가입 진행 중: UserService");
        return userDAO.signInRow(requset) ;
    }

    public String logInService(RequestDTO requset) {
        System.out.println("로그인 진행 중: UserService");
        return userDAO.logInRow(requset) ;
    }
    
}
