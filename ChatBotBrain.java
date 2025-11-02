import java.util.*;

public class ChatBotBrain {
    private DatabaseManager db;

    public ChatBotBrain() {
        db = new DatabaseManager();
    }

    public void startSymptomCheck() {
        Scanner scanner = new Scanner(System.in);
        List<String> userSymptoms = new ArrayList<>();
        List<String> allSymptoms = db.getAllSymptoms();

        if (allSymptoms.isEmpty()) {
            System.out.println("No symptoms found in database.");
            return;
        }

        Collections.shuffle(allSymptoms);
        List<String> selectedSymptoms = allSymptoms.subList(0, Math.min(10, allSymptoms.size()));

        System.out.println("I will ask you about a few symptoms. Please answer with 'y' or 'n'. Type 'bye' anytime to exit.\n");

        for (int i = 0; i < selectedSymptoms.size(); i++) {
            String symptom = selectedSymptoms.get(i);
            String reply = "";

            while (true) {
                System.out.printf("(%d/%d) Do you have %s? (y/n): ", i + 1, selectedSymptoms.size(), symptom);
                reply = scanner.nextLine().trim().toLowerCase();

                if (reply.equals("bye")) {
                    System.out.println("Chat ended by user. Stay safe! 👋");
                    return;
                } else if (reply.equals("y")) {
                    userSymptoms.add(symptom);
                    break;
                } else if (reply.equals("n")) {
                    break;
                } else {
                    System.out.println("❌ Invalid input. Please type 'y' for yes or 'n' for no.");
                }
            }
        }

        Map<String, Integer> diseaseMatch = db.getDiseaseMatchCount(userSymptoms);
        if (diseaseMatch.isEmpty()) {
            System.out.println("No matching disease found.");
        } else {
            String bestDisease = null;
            int maxMatched = 0;

            for (Map.Entry<String, Integer> entry : diseaseMatch.entrySet()) {
                if (entry.getValue() > maxMatched) {
                    bestDisease = entry.getKey();
                    maxMatched = entry.getValue();
                }
            }

            if (bestDisease != null) {
                int total = db.getTotalSymptomsForDisease(bestDisease);
                double matchPercentage = (total > 0) ? ((double) maxMatched / total) * 100 : 0;
                String description = db.getDiseaseDescription(bestDisease);

                System.out.println("\nBased on your symptoms, the most likely disease is:");
                System.out.printf("%s — Match Percentage: %.1f%%\n", bestDisease, matchPercentage);
                System.out.println("Description: " + description);
            }
        }
    }
}