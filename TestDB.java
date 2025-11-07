import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smartbotdb", "root", "As24fa02@hi");
            System.out.println("✅ Connected to MySQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}