public class BaseBot implements Bot {
    @Override
    public void greet() {
        System.out.println("Welcome to Smart Health ChatBot!");
    }

    @Override
    public void respond(String input) {
        System.out.println("User said: " + input);
    }
}