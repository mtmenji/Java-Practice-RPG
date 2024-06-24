import java.util.List;
import java.util.Scanner;

public class RPGGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player = new Player();

        List<Enemy> enemies = List.of(
                new Enemy("Grunt 1", 10),
                new Enemy("Grunt 2", 10),
                new Enemy("Grunt 3", 10),
                new Enemy("Grunt 4", 10),
                new Enemy("Grunt 5", 10),
                new Enemy("Happy Grunt", 15)
        );
        
        Utils.clearConsole();
        System.out.println("Welcome to Grunt Dungeon. Your goal is to defeat the 5 grunts and the Happy Grunt. Good luck, hero!");

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            boolean equipCanceled = false; // Flag to track if equipItem was canceled

            while (enemy.getHp() > 0 && player.getHp() > 0) {
                Utils.clearConsole();
                displayStatus(player, enemy, i + 1);
                displayMenu(player);

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        if (!player.isBagEmpty()) {
                            int equipResult = player.equipItem(scanner);
                            if (equipResult == 3) {
                                System.out.println("TEST1");
                                equipCanceled = true;
                                continue;
                            }
                            // Handle other equipItem results as needed
                            break;
                        } else {
                            System.out.println("Your bag is empty. Gather some items first.");
                            continue;
                        }
                    case 2:
                        player.discardItem(scanner);
                        break;
                    case 3:
                        player.gather();
                        break;
                    case 4:
                        player.attackEnemy(enemy);
                        break;
                    case 5:
                        if (player.getHp() < 50) {
                            player.healPlayer();
                        } else {
                            System.out.println("Your health is full. Choose a different option.");
                            continue; // Skip enemy attack if choice is invalid
                        }
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                        continue; // Skip enemy attack if choice is invalid
                    }

                // After player's action, check if enemy is defeated
                if (enemy.getHp() <= 0) {
                    System.out.println("You defeated the " + enemy.getName() + "!");
                    Utils.clearConsole();
                    scanner.nextLine(); // Wait for player to press Enter before continuing
                    break; // Exit the loop for this enemy if defeated
                }

                // Check player's health to determine if enemy should attack
                if (player.getHp() > 0) {
                    enemy.attackPlayer(player);
                    if (player.getHp() <= 0) {
                        System.out.println("You have been defeated!");
                        return;
                    }
                }
            }
        }

        System.out.println("Congratulations! You cleared Room One.");
    }

    private static void displayStatus(Player player, Enemy enemy, int gruntNumber) {
        System.out.print("Player ------------- HP: " + player.getHp());

        // Display weapon information
        if (player.getWeapon() == "") {
            System.out.print(" -- Weapon: n/a");
        } else {
            System.out.print(" -- Weapon: " + player.getWeapon() +
                    " (ATK: " + player.getWeaponAttack() +
                    ", DUR: " + player.getWeaponDurability() + ")");
        }

        // Display armor information
        if (player.getArmor() == "") {
            System.out.print(" -- Armor: n/a");
        } else {
            System.out.print(" -- Armor: " + player.getArmor() +
                    " (DEF: " + player.getArmorDefense() +
                    ", DUR: " + player.getArmorDurability() + ")");
        }

        System.out.println();
        System.out.println("Enemy: " + enemy.getName() + " ----- HP: " + enemy.getHp());
        System.out.println("===============================================");
    }

    private static void displayMenu(Player player) {
        System.out.println("1. Equip a weapon/armor.");
        System.out.println("2. Discard a bag item.");
        System.out.println("3. Gather");
        System.out.println("4. Attack");
        if (player.getHp() == 50) {
            System.out.println("5. Heal (already at max health)");
        } else {
            System.out.println("5. Heal");
        }
        System.out.println("===============================================");
        System.out.print("Enter your choice: ");
    }
}