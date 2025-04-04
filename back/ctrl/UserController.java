package back.ctrl ;

import back.model.domain.RequestDTO;
import back.model.domain.UserDTO;
import back.service.UserService;

public class UserController {
   private UserService userService ;

   public UserController() {

   }

   public UserController(UserService userService) {
      this.userService = userService ;
   }

   public int signInCtrl(UserDTO request) {
      // System.out.println("회원 가입 진행 중: UserController");
      return userService.signInService(request);
   }

   public String logInCtrl(UserDTO request) {
      // System.out.println("로그인 진행 중: UserController");
      return userService.logInService(request);
   }
}
