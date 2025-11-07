import java.util.*;

class BaseChatBot {
    protected DatabaseService db;

    public BaseChatBot(DatabaseService db) {
        this.db = db;
    }

    public void greetUser() {
        System.out.println(" Hi! Let's begin your symptom check.");
        System.out.println("Type 'bye' anytime to exit.\n");
    }

    public List<String> getShuffledSymptoms(int maxSymptoms) {
        List<String> allSymptoms = db.getAllSymptoms();
        Collections.shuffle(allSymptoms);
        return allSymptoms.subList(0, Math.min(maxSymptoms, allSymptoms.size()));
    }
}

public class ChatBotBrain extends BaseChatBot {

    public ChatBotBrain(DatabaseService db) {
        super(db); // ✅ Inherit db from BaseChatBot
    }

    public void startSymptomCheck() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Type 'hi' to start: ");
        String startInput = scanner.nextLine().trim().toLowerCase();
        if (!startInput.equals("hi")) {
            System.out.println(" Invalid start. Type 'hi' to begin.");
            return;
        }

        greetUser(); // ✅ inherited method

        List<String> selectedSymptoms = getShuffledSymptoms(10);
        List<String> userSymptoms = new ArrayList<>();

        for (int i = 0; i < selectedSymptoms.size(); i++) {
            String symptom = selectedSymptoms.get(i);
            while (true) {
                System.out.printf("(%d/%d) Do you have %s? (y/n): ", i + 1, selectedSymptoms.size(), symptom);
                String response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("bye")) {
                    System.out.println(" Bye! Take care.");
                    return;
                } else if (response.equals("y")) {
                    userSymptoms.add(symptom);
                    break;
                } else if (response.equals("n")) {
                    break;
                } else {
                    System.out.println(" Invalid input. Please type 'y' or 'n'.");
                }
            }
        }

        Map<String, Integer> diseaseMatch = db.getDiseaseMatchCount(userSymptoms);

        if (diseaseMatch.isEmpty()) {
            System.out.println("\n No matching disease found based on your symptoms.");
        } else {
            List<DiseaseMatch> matchList = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : diseaseMatch.entrySet()) {
                String disease = entry.getKey();
                int matchedCount = entry.getValue();
                int totalSymptoms = db.getTotalSymptomsForDisease(disease);

                double percentage = (totalSymptoms > 0)
                    ? ((double) matchedCount / totalSymptoms) * 100
                    : 0;

                matchList.add(new DiseaseMatch(disease, matchedCount, totalSymptoms, percentage));
            }

            matchList.sort((a, b) -> Double.compare(b.percentage, a.percentage));

          System.out.println("\n🩺 Most Likely Disease Match:");

DiseaseMatch top = matchList.get(0); // only top 1

System.out.printf("\nDisease: %s\nMatched: %d/%d\nMatch Percentage: %.1f%%\n",
                  top.disease, top.matchedCount, top.totalSymptoms, top.percentage);

String description = db.getDiseaseDescription(top.disease);
System.out.println("Description: " + description);
        }

        System.out.println("\n Chat ended. Stay healthy!");
    }

    private static class DiseaseMatch {
        String disease;
        int matchedCount;
        int totalSymptoms;
        double percentage;

        DiseaseMatch(String disease, int matchedCount, int totalSymptoms, double percentage) {
            this.disease = disease;
            this.matchedCount = matchedCount;
            this.totalSymptoms = totalSymptoms;
            this.percentage = percentage;
        }
    }
}