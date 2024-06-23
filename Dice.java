import java.util.Random;

public class Dice {
    private static final Random RANDOM = new Random();

    public static int roll() {
        return RANDOM.nextInt(11) + 2; // Dice rolls between 2 and 12
    }
}
