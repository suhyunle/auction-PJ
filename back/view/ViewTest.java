package back.view;

import java.util.Scanner;

import back.front.FrontController;

public class ViewTest {
    private FrontController front ;
    Scanner scan = new Scanner(System.in) ;
    public ViewTest() {
        front = new FrontController() ;
    }

    // 회원가입/로그인 선택
    public void signLogMenu() {
        while (true) {
            try {
                System.out.println(">>> 회원가입 / 로그인 페이지 <<<");
                System.out.println("1. 회원가입 | 2. 로그인 | 99. 종료");
                System.out.println("원하시는 번호를 선택하세요");

                int number = scan.nextInt();
                scan.nextLine() ;

                switch (number) {
                    case 1:
                        signIn() ; break;
                
                    case 2:
                        logIn() ; break ;
                    
                    case 99:
                        System.out.println("시스템을 종료하시겠습니까?(y/n)");
                        String answer = scan.nextLine() ;
                        if (answer.equals("y")) {
                            System.exit(0);
                        } else if(answer.equals("n")) {
                            signLogMenu() ;
                        }
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }   
    }

    public void signIn() {
        String userId ;
        String userPw ;

        while (true) {
            System.out.println(">>> 회원가입을 시작합니다 <<<");
            System.out.println("1단계. 사용하실 ID를 입력하세요: ");
            userId = scan.nextLine() ;

            if (isValidUserId(userId)) {
                break ;
            } else {
                System.out.println("오류: 아이디는 영문(대소문자), 숫자, 특수문자(-_@) 조합으로 가능하며, 5~15자 이내여야 합니다.\n회원가입을 다시 시작합니다");
            }
        }
        
        while (true) {
            System.out.println("2단계. 사용하실 패스워드를 입력하세요.");
            userPw = scan.nextLine() ;

            if (isValidUserPw(userPw)) {
                break;
            } else {
                System.out.println("오류: 비밀번호는 영문(대소문자), 숫자, 특수문자(!@#$%^&*()-_=+) 조합이 필수이며, 5~15자 이내여야 합니다.\n 비밀번호 작성을 다시 시작합니다");
            }    
        }

        System.out.println("마지막 단계. 사용하실 회원 이름을 입력하세요.");
        String userName = scan.nextLine() ;
        int signInResult = front.signIn(userId, userPw, userName) ;
        System.out.println(signInResult);

    }

    public void logIn() {
        System.out.println(">>> 로그인을 시작합니다 <<<");
        System.out.println("1단계. ID를 입력하세요: ");
        String userId = scan.nextLine() ;
        System.out.println("2단계. 패스워드를 입력하세요.");
        String userPw = scan.nextLine() ;

        String logInResult = front.logIn(userId, userPw) ;
        System.out.println(logInResult);
    }

    private boolean isValidUserId(String userId) {
        return userId.matches("^(?=.*[a-zA-Z])[a-zA-Z\\d\\-_@]{5,15}$$");
        /* 영문(대소문자)은 필수

        숫자와 특수문자는 선택적으로 포함 가능

        숫자만으로 이루어진 아이디는 불가능

        특수문자만으로 이루어진 아이디도 불가능

        최소 5자 이상, 최대 15자 이하 */
    }

    private boolean isValidUserPw(String userPw) {
        return userPw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+])[a-zA-Z\\d!@#$%^&*()\\-_=+]{5,15}$");
        // 비밀번호에서 영문(대소문자), 숫자, 특수문자가 모두 반드시 포함
    }


}
