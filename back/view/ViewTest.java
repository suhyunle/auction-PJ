package back.view;

import java.util.List;
import java.util.Scanner;

import back.front.FrontController;
import back.model.domain.ResponseDTO;

    public class ViewTest {

    private FrontController front;
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
                        signUp(); break;
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
        loginMenu();
    }
    
    // signUp
    public void signUp(){
        System.out.println("signUp");

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
                System.out.println(">>> login menu");
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
        
        System.out.print("제목 : ");
        String title = scan.nextLine();
        System.out.print("내용 : ");
        String content = scan.nextLine();
        System.out.print("우선순위 : ");
        int priority = scan.nextInt();

        int insertFlag = front.register(title, content, priority);
        System.out.println(insertFlag);
    }
    
    // buyItem
    public void buyItem(){
        System.out.println("경매 참여");

    }

    // logout
    public void logOut(){
        System.out.println("로그 아웃");
        menu();
    }
    
    // checkLog
    public void checkLog(){
        System.out.println("거래 내역 확인");
        // transaction table 조회
    }
}
