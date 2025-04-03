package back.view;

import java.util.Scanner;

import back.front.FrontController;

public class ViewTest {
    private FrontController front ;
    Scanner scan = new Scanner(System.in) ;
    public ViewTest() {
        front = new FrontController() ;
    }

    private int transaction_id;
    private String buyer_id ;

    // USER PART: 회원가입/로그인 선택
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
                e.printStackTrace(); break ;
            }
        }   
    }

    public void signIn() {
        String userId ;
        String userPw ;

        while (true) {
            System.out.println(">>> 회원가입을 시작합니다 <<<");
            System.out.println("1단계. 사용하실 ID를 입력하세요.");
            System.out.println("아이디는 영문(대소문자), 숫자, 특수문자(-_@) 조합으로 가능하며, 5~15자 이내여야 합니다.");
            System.out.print("ID 입력: ");
            userId = scan.nextLine() ;

            if (isValidUserId(userId)) {
                break ;
            } else {
                System.out.println("오류: 형식에 맞는 ID를 입력하세요.\n회원가입을 다시 시작합니다");
            }
        }
        
        while (true) {
            System.out.println("2단계. 사용하실 패스워드를 입력하세요.");
            System.out.println("비밀번호는 영문(대소문자), 숫자, 특수문자(!@#$%^&*()-_=+) 조합이 필수이며, 5~15자 이내여야 합니다.");
            userPw = scan.nextLine() ;

            if (isValidUserPw(userPw)) {
                break;
            } else {
                System.out.println("오류: 형식에 맞는 패스워드를 입력하세요.\n 비밀번호 작성을 다시 시작합니다");
            }    
        }

        System.out.println("마지막 단계. 사용하실 회원 이름을 입력하세요.");
        String userName = scan.nextLine() ;
        int signInResult = front.signIn(userId, userPw, userName) ;
        System.out.println(signInResult);

    }

    public void logIn() {
        String userId ;
        String userPw ;
        
        System.out.println(">>> 로그인을 시작합니다 <<<");

        while (true) {
            System.out.println("1단계. ID를 입력하세요: ");
            userId = scan.nextLine() ;

            if (isValidUserId(userId)) {
                break ;
            } else {
                System.out.println("오류: 형식에 맞는 ID를 입력하세요. 로그인을 다시 시작합니다");
            }
        }

        while (true) {
            System.out.println("2단계. 패스워드를 입력하세요.");
            userPw = scan.nextLine() ;

            if (isValidUserPw(userPw)) {
                break ;
            } else {
                System.out.println("오류: 형식에 맞는 패스워드를 입력하세요. 로그인을 다시 시작합니다") ;
            }
            
        }

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


    // PAYMENT PART: 결제 파트. 결제가 성공적으로 완료되면 1 반환, 실패하면 0 반환
    public void payment() {
        int transaction_id ;
        int payment_id ;
        String buyer_id ;

        while (true) {
            System.out.println("결제 페이지에 오신 것을 환영합니다");

            System.out.println("거래 ID를 입력하세요: ");
            transaction_id = scan.nextInt() ;
            // System.out.println("결제 ID를 입력하세요.");  // 결제 아이디가 이미 있을 경우
            // payment_id = scan.nextInt() ;

            int paymentResult = front.payment(transaction_id) ;
            if (paymentResult == 1) {
                System.out.println("거래 ID를 확인했습니다.");
                buyer_id = front.buyerId(transaction_id);
                subPayMenu();
                break ;
        } else {
            System.out.println("거래 ID를 찾을 수 없습니다.");
        }  
        }
    }

    public void subPayMenu() {
        System.out.println("subPayMenu 메서드 실행 확인");
        System.out.println("결제방법을 선택합니다.");
        System.out.println("1. 신용카드 | 2. 계좌이체 | 3. PayPal | 4. 기타");
        int payOpt = scan.nextInt() ;

        int paymentStatus = front.payStatus(transaction_id, buyer_id) ;
        System.out.println(paymentStatus);

    }

    


}
