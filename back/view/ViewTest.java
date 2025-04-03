package back.view;

import java.util.List;
import java.util.Scanner;

import back.front.FrontController;
import back.model.domain.ResponseDTO;
import back.model.domain.TranResDTO;
import back.session.UserSession;

    public class ViewTest {

    private FrontController front;
    Scanner scan = new Scanner(System.in) ;
    public ViewTest(){
        front = new FrontController();
    }
    public void menu(){
        while (true) {
            try{
                System.out.println(">>> Open menu");
                System.out.println("1.로그인 | 2.회원가입 | 3.물품보기 | 99. 종료");
                System.out.print("원하시는 번호를 선택하세요 : ");
                Scanner scan = new Scanner(System.in); // in: System 클래스가 갖고 있는 static 메서드
                int num = scan.nextInt();
                switch (num) {
                    case 1:
                        login(); break;
                    case 2:
                        signIn(); break;
                    case 3:
                        watchItem(); break;
                    case 99:
                        System.exit(0);                            
                }
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("정확한 값을 입력해주세요 ");
            }
        }
    }
    // method 정의
    // login
    public void login(){
        System.out.println("login");
        System.out.println(">>> 로그인을 시작합니다 <<<");
        System.out.println("1단계. ID를 입력하세요: ");
        String userId = scan.nextLine() ;
        System.out.println("2단계. 패스워드를 입력하세요.");
        String userPw = scan.nextLine() ;

        String logInResult = front.logIn(userId, userPw) ;
        System.out.println(logInResult);
        UserSession.setLoggedInUser(userId);  // ✅ 로그인 정보 저장
        loginMenu();
        // if (logInResult.equals(userId+"님, 로그인에 성공하셨습니다.")) { 
        //     UserSession.setLoggedInUser(userId); // 로그인 정보 저장
        //     loginMenu();
        // } else {
        //     System.out.println("로그인 실패! 다시 시도하세요.");
        // }
    }
    
    // signin
    public void signIn(){
        System.out.println("signIn");
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
    // id 유효성 테스트 메서드
    private boolean isValidUserId(String userId) {
        return userId.matches("^(?=.*[a-zA-Z])[a-zA-Z\\d\\-_@]{5,15}$$");
        /* 영문(대소문자)은 필수

        숫자와 특수문자는 선택적으로 포함 가능

        숫자만으로 이루어진 아이디는 불가능

        특수문자만으로 이루어진 아이디도 불가능

        최소 5자 이상, 최대 15자 이하 */
    }
    // 비밀번호 유효성 테스트 메서드
    private boolean isValidUserPw(String userPw) {
        return userPw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+])[a-zA-Z\\d!@#$%^&*()\\-_=+]{5,15}$");
        // 비밀번호에서 영문(대소문자), 숫자, 특수문자가 모두 반드시 포함
    }

    // watchitem
    public void watchItem(){
        System.out.println("경매물품 보기");
        List<ResponseDTO> lst = front.watchItem();
        System.out.println();
        System.out.println(">>> 물품 List <<<");
        
        for(ResponseDTO value : lst){
            System.out.println(value);
        }
    }

    // loginMenu
    public void loginMenu(){
        while (true) {
            try{
                System.out.println(">>> 로그인 메뉴 <<<");
                System.out.println("현재 로그인된 ID: " + UserSession.getLoggedInUser());
                System.out.println("1.경매 물품보기 | 2.경매 물품등록 | 3.경매 참여 | 4.로그아웃 | 5.거래내역 확인 | 6. 결제");
                System.out.print("원하시는 번호를 선택하세요 : ");
                Scanner scan = new Scanner(System.in); // in: System 클래스가 갖고 있는 static 메서드
                int num = scan.nextInt();

                switch (num) {
                    case 1:
                        watchItem();                        
                        break;
                    case 2:
                        sellItem(); // 해당하는 controller
                        break;
                    case 3:
                        buyItem(); // 해당하는 controller
                        break;
                    case 4:
                        logOut();
                        return;
                    case 5:
                        checkLog();
                        break;
                    default:
                        break;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    // sellItem
    public void sellItem(){
        System.out.println("경매 물품 등록");
        Scanner scan = new Scanner(System.in);
        
        System.out.print("물품명 : ");
        String itemName = scan.nextLine();
        System.out.print("설명 : ");
        String description = scan.nextLine();
        System.out.print("입찰시작 가 : ");
        int startingPrice = scan.nextInt();

        int insertFlag = front.register(itemName, description, startingPrice);
        System.out.println(insertFlag);
    }
    
    // buyItem
    public void buyItem(){
        System.out.println("경매 참여");
        System.out.print("입찰할 상품명을 입력하세요: ");
        int itemId = scan.nextInt();
        scan.nextLine();
        int bidAmount = 0;

        while (true) {
            System.out.print("입찰 금액을 입력하세요: ");
            String bidInput = scan.nextLine().trim(); // 입력값 공백 제거
            
            if (bidInput.isEmpty()) {
                System.out.println("입찰 금액을 입력해야 합니다.");
                continue; // 다시 입력 요청
            }
            
            try {
                bidAmount = Integer.parseInt(bidInput);
                if (bidAmount <= 0) {
                    System.out.println("입찰 금액은 0보다 커야 합니다.");
                    continue;
                }
                break; // 정상 입력 시 루프 종료
            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자를 입력하세요.");
            }
        }

        // 현재 로그인한 사용자 ID 가져오기
        String userId = UserSession.getLoggedInUser();

        int insertFlag = front.updateBid(itemId, bidAmount, userId);
        System.out.println("입찰 결과: " + (insertFlag > 0 ? "성공" : "실패"));
    }

    // logout
    public void logOut(){
        System.out.println("로그 아웃");
        menu();
    }
    
    // checkLog
    public void checkLog(){
        System.out.println("거래 내역 확인");
        List<TranResDTO> lst = front.watchTran();
        System.out.println();
        
        for(TranResDTO value : lst){
            System.out.println(value);
        }
    }


}
