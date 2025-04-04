package back.view;

import java.util.List;
import java.util.Scanner;

import back.front.FrontController;
import back.model.domain.ResponseDTO;
import back.model.domain.TranResDTO;
import back.session.UserSession;

public class ViewTest {
    private int transaction_id;
    private String buyer_id ;

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
        UserSession.setLoggedInUser(userId);  // ✅ 로그인 정보 저장
        loginMenu();
    }
    
    // signin
    public void signIn(){
        System.out.println("signIn");
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

    // id 유효성 테스트 메서드
    private boolean isValidUserId(String userId) {
        return userId.matches("^(?=.*[a-zA-Z])[a-zA-Z\\d\\-_@]{5,15}$$");
    }
    // 비밀번호 유효성 테스트 메서드
    private boolean isValidUserPw(String userPw) {
        return userPw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+])[a-zA-Z\\d!@#$%^&*()\\-_=+]{5,15}$");
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
                // System.out.println("----------------------------------------------------");
                System.out.println(">>> 로그인 메뉴 <<<");
                System.out.println("현재 로그인된 ID: " + UserSession.getLoggedInUser());
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("1.경매 물품보기 | 2.경매 물품등록 | 3.경매 참여 | 4.로그아웃 | 5.거래내역 확인 | 6. 결제");
                System.out.print("원하시는 번호를 선택하세요 : ");
                Scanner scan = new Scanner(System.in); // in: System 클래스가 갖고 있는 static 메서드
                int num = scan.nextInt();
                scan.nextLine(); // 잘못된 입력 제거
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
                    case 6:
                        payment();
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
        List<ResponseDTO> lst = front.watchItem();
        lst.removeIf(item -> "완료".equals(item.getStatus()));
        // 입찰 가능한 물품이 없는 경우

        if (lst.isEmpty()) {
            System.out.println("입찰 가능한 경매 물품이 없습니다. 메인 메뉴로 돌아갑니다.");
            loginMenu(); // 메서드 종료
        }

        System.out.println();
        System.out.println(">>> 현재 입찰 가능한 물품 List <<<");
        
        for(ResponseDTO value : lst){
            System.out.println(value);
        }
        System.out.print("입찰할 상품ID을 입력하세요: ");
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
        
        for (TranResDTO value : lst) {
            // 완료 여부 표시
            String statusIcon = "완료".equals(value.getIsCompleted()) ? "✔ 완료됨" : "⏳ 진행중";
            
            System.out.println(value);
            System.out.println("   → 결제상태: " + statusIcon);
            System.out.println("----------------------------------------------------");
        }
    }

    // PAYMENT PART: 결제 파트. 결제가 성공적으로 완료되면 1 반환, 실패하면 0 반환
    public void payment() {

        String buyer_id ;

        while (true) {
            System.out.println("결제 페이지에 오신 것을 환영합니다");
            System.out.println("거래 내역 확인");
            List<TranResDTO> lst = front.watchTran();
            lst.removeIf(tran -> "완료".equals(tran.getIsCompleted()));

            if (lst.isEmpty()) {
                System.out.println("결제 가능한 거래가 없습니다. 메인 메뉴로 돌아갑니다.");
                loginMenu();;
            }
            System.out.println("----------------------------------------------------");
            System.out.println(">>> 현재 결제 해야하는 품목");
            for(TranResDTO value : lst){
                System.out.println(value);
            }
            System.out.println("거래 ID를 입력하세요: ");
            System.out.println("----------------------------------------------------");
            transaction_id = scan.nextInt() ;

            int paymentResult = front.payment(transaction_id) ;
            if (paymentResult == 1) {
                System.out.println("거래 ID를 확인했습니다.");
                buyer_id = front.buyerId(transaction_id);
                System.out.println(buyer_id);
                subPayMenu();
                break ;
        } else {
            System.out.println("거래 ID를 찾을 수 없습니다.");
        }  
        }
    }
    public void subPayMenu() {
        // System.out.println("subPayMenu 메서드 실행 확인");
        
        System.out.print("잔액을 충전하시겠습니까? (Y/N): ");
        System.out.println("----------------------------------------------------");
        String chargeOpt = scan.next();

        if (chargeOpt.equalsIgnoreCase("Y")) {
            System.out.print("충전할 금액을 입력하세요: ");
            System.out.println("----------------------------------------------------");
            int chargeAmount = scan.nextInt();
            int chargeResult = front.chargeBalance(buyer_id, chargeAmount); // 🔹추가 메서드 호출
            if (chargeResult > 0) {
                System.out.println("잔액이 성공적으로 충전되었습니다.");
            } else {
                System.out.println("충전에 실패했습니다.");
            }
        }

        System.out.println("결제방법을 선택합니다.");
        System.out.println("1. 신용카드 | 2. 계좌이체 | 3. PayPal | 4. 기타");
        System.out.println("----------------------------------------------------");
        int payOpt = scan.nextInt() ;

        String paymentStatus = front.payStatus(transaction_id, buyer_id) ;
        System.out.println("결제 성공 여부: " +paymentStatus);

        int auctionCMPLT = front.auctionStatus(transaction_id) ;   // 경매 거래 완료 여부!!!!!!!!!!!!!!!!!!!!!!!!


        if (paymentStatus.equals("완료") && (auctionCMPLT == 1)) {
            int paymentTblStatus = front.payTBLInsrt(transaction_id, payOpt, paymentStatus) ; // 결제방법, 결제 상태 추가
            System.out.println("payment_tb 업데이트 여부: " +paymentTblStatus);
        }

        

    }

}
