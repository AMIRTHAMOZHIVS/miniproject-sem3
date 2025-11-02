import java.util.*;

public class ChatMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatBotBrain bot = new ChatBotBrain();

        System.out.println("Type 'hi' to start the Smart Health ChatBot or 'bye' to exit.");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("hi")) {
                System.out.println("Hello! I will ask you about a few symptoms to help identify possible diseases.");
                System.out.println("Please respond with 'y' for yes and 'n' for no.\n");
                bot.startSymptomCheck();
                System.out.println("\nIf you want to check again, type 'hi'. To exit, type 'bye'.");
            } else if (input.equals("bye")) {
                System.out.println("Goodbye! Stay healthy and take care.");
                break;
            } else {
                System.out.println("Please type 'hi' to start or 'bye' to exit.");
            }
        }

        scanner.close();
    }
}