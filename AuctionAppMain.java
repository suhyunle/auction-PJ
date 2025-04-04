import back.session.Scheduler;
import back.view.ViewTest;

public class AuctionAppMain {
    public static void main(String[] args) {
        ViewTest view = new ViewTest() ;
        Scheduler.startScheduler();
        view.menu() ;
    }    
}
