package back.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import back.model.domain.RequestDTO;

public class RegisterDAO {

    public static final String DRIVER   = "oracle.jdbc.driver.OracleDriver";
    public static final String URL      = "jdbc:oracle:thin:@localhost:1521/xe";
    public static final String USER     = "hr";
    public static final String PASSWORD = "hr";
    
    public RegisterDAO(){
        try{
            Class.forName(DRIVER);
            System.out.println("1. Driver loading OK");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public int insertRow(RequestDTO request){
        System.out.println(">>>> debug dao insertRow");
        int flag = 0;
        Connection          conn = null;
        PreparedStatement   pstmt = null;
        String insertSQL = "INSERT INTO JDBC_TODO_TBL(SEQ, TITLE, CONTENT, PRIORITY) VALUES(JDBC_SEQ.NEXTVAL,?,?,?)";
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, request.getName());
            pstmt.setString(2, request.getDescription());
            pstmt.setInt(3, request.getSellerId());
            flag = pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return flag;
    }
}
