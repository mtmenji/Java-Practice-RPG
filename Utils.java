import java.util.Scanner;

public class Utils {
    private static final Scanner scanner = new Scanner(System.in);

    public static void clearConsole() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine(); // Wait for the player to press Enter
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }
}
