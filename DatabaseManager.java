import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private Connection conn;

    public DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smartbotdb", "root", "As24fa02@hi");
            System.out.println("MySQL connected!");
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public List<String> getAllSymptoms() {
        List<String> symptoms = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT symptom FROM symptoms_diseases";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                symptoms.add(rs.getString("symptom"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching symptoms: " + e.getMessage());
        }
        return symptoms;
    }

    public Map<String, Integer> getDiseaseMatchCount(List<String> userSymptoms) {
        Map<String, Integer> matchCount = new HashMap<>();
        try {
            for (String symptom : userSymptoms) {
                String query = "SELECT disease FROM symptoms_diseases WHERE symptom = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, symptom);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String disease = rs.getString("disease");
                    matchCount.put(disease, matchCount.getOrDefault(disease, 0) + 1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error matching diseases: " + e.getMessage());
        }
        return matchCount;
    }

    public int getTotalSymptomsForDisease(String disease) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM symptoms_diseases WHERE disease = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, disease);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error counting symptoms: " + e.getMessage());
        }
        return count;
    }

    public String getDiseaseDescription(String disease) {
        String description = "No description available.";
        try {
            String query = "SELECT description FROM symptoms_diseases WHERE disease = ? LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, disease);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                description = rs.getString("description");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching description: " + e.getMessage());
        }
        return description;
    }
}