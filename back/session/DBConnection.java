package back.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/xe";
    private static final String USER = "hr";
    private static final String PASSWORD = "hr";

    static {
        try {
            Class.forName(DRIVER);
            System.out.println("1. Driver loading OK");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Connection 객체를 반환하는 메서드
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
