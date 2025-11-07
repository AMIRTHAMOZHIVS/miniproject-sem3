import java.util.List;
import java.util.Map;

public interface DatabaseService {
    List<String> getAllSymptoms();
    Map<String, Integer> getDiseaseMatchCount(List<String> userSymptoms);
    int getTotalSymptomsForDisease(String disease);
    String getDiseaseDescription(String disease);
}