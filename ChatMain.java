import java.sql.Connection;
import java.sql.DriverManager;

public class ChatMain {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smartbotdb", "root", "As24fa02@hi");

            DatabaseService db = new DatabaseManager(conn);
            ChatBotBrain bot = new ChatBotBrain(db);
            bot.startSymptomCheck();

        } catch (Exception e) {
            System.err.println(" Error connecting to database or running chatbot:");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    System.out.println("✅ Database connection closed.");
                }
            } catch (Exception e) {
                System.err.println("⚠️ Error closing database connection:");
                e.printStackTrace();
            }
        }
    }
}