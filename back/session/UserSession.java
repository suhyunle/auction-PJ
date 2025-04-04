package back.session;

public class UserSession {
    private static String loggedInUser; // 현재 로그인된 사용자 ID

    public static void setLoggedInUser(String userId) {
        loggedInUser = userId;
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void logout() {
        loggedInUser = null;
    }
}
