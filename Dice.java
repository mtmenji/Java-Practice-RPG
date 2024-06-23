import java.util.Random;

public class Dice {
    private static final Random RANDOM = new Random();

    public static int roll() {
        int die1 = RANDOM.nextInt(6) + 1; // Roll first die (1-6)
        int die2 = RANDOM.nextInt(6) + 1; // Roll second die (1-6)
        int sum = die1 + die2;
        return sum;
    }
}
