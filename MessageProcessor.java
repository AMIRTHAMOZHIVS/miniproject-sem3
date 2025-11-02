public class MessageProcessor {

    public static String cleanInput(String input) {
        return input.toLowerCase().trim();
    }

    public static boolean shouldExit(String input) {
        return input.equals("bye") || input.equals("exit") || input.equals("quit");
    }
}