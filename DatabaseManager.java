import java.sql.*;
import java.util.*;

public class DatabaseManager implements DatabaseService {
    private Connection conn;

    public DatabaseManager(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<String> getAllSymptoms() {
        List<String> symptoms = new ArrayList<>();
        String query = "SELECT DISTINCT symptom FROM symptoms_diseases";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                symptoms.add(rs.getString("symptom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return symptoms;
    }

    @Override
    public Map<String, Integer> getDiseaseMatchCount(List<String> userSymptoms) {
        Map<String, Integer> matchCount = new HashMap<>();
        String query = "SELECT disease FROM symptoms_diseases WHERE symptom = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (String symptom : userSymptoms) {
                stmt.setString(1, symptom);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String disease = rs.getString("disease");
                        matchCount.put(disease, matchCount.getOrDefault(disease, 0) + 1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchCount;
    }

    @Override
    public int getTotalSymptomsForDisease(String disease) {
        String query = "SELECT COUNT(*) FROM symptoms_diseases WHERE disease = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, disease);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getDiseaseDescription(String disease) {
        String query = "SELECT description FROM symptoms_diseases WHERE disease = ? AND description IS NOT NULL LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, disease);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("description");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No description available.";
    }
}