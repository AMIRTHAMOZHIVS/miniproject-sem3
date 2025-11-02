import java.time.LocalTime;

public class ChatUtils {

    public static String getGreeting() {
        int hour = LocalTime.now().getHour();
        if (hour < 12) {
            return "Good morning!";
        } else if (hour < 18) {
            return "Good afternoon!";
        } else {
            return "Good evening!";
        }
    }

    public static void printLine() {
        //System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}