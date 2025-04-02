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
        System.out.println(">>> 회원가입을 시작합니다 <<<");
        System.out.println("1단계. 사용하실 ID를 입력하세요: ");
        String userId = scan.next() ;
        System.out.println("2단계. 사용하실 패스워드를 입력하세요.");
        String userPw = scan.next() ;
        System.out.println("마지막 단계. 사용하실 회원 이름을 입력하세요.");
        String userName = scan.next() ;

        int signInResult = front.signIn(userId, userPw, userName) ;
        System.out.println(signInResult);

    }

    public void logIn() {
        System.out.println(">>> 로그인을 시작합니다 <<<");
        System.out.println("1단계. ID를 입력하세요: ");
        String userId = scan.next() ;
        System.out.println("2단계. 패스워드를 입력하세요.");
        String userPw = scan.next() ;

        String logInResult = front.logIn(userId, userPw) ;
        System.out.println(logInResult);
    }


}
