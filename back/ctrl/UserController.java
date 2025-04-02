package back.ctrl ;

import back.model.domain.RequestDTO;
import back.service.UserService;

public class UserController {
   private UserService userService ;

   public UserController() {

   }

   public UserController(UserService userService) {
      this.userService = userService ;
   }

   public int signInCtrl(RequestDTO request) {
      System.out.println("회원 가입 진행 중: UserController");
      return userService.signInService(request);
   }

   public String logInCtrl(RequestDTO request) {
      System.out.println("로그인 진행 중: UserController");
      return userService.logInService(request);
   }





    /*
     * . UserController.java
        기능: 사용자 관련 로직을 처리하는 컨트롤러. 로그인, 회원가입, 사용자 정보 수정 등을 담당.

        주요 메서드:
        로그인 처리
        회원가입 처리
     */

     
}
