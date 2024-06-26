import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private int hp = 50;
    private String weapon = "";
    private int weaponAttack = 0;
    private int weaponDurability = 0;
    private String armor = "";
    private int armorDefense = 0;
    private int armorDurability = 0;
    // private final List<String> bag = new ArrayList<>();
    private final List<String> bag = new ArrayList<>(List.of("Test Weapon", "Test Armor"));


    public int getHp() {
        return hp;
    }

    public String getWeapon() {
        return weapon;
    }

    public int getWeaponAttack() {
        return weaponAttack;
    }

    public int getWeaponDurability() {
        return weaponDurability;
    }

    public String getArmor() {
        return armor;
    }

    public int getArmorDefense() {
        return armorDefense;
    }

    public int getArmorDurability() {
        return armorDurability;
    }

    public void equipWeapon(String weapon, int attack, int durability) {
        this.weapon = weapon;
        this.weaponAttack = attack;
        this.weaponDurability = durability;
    }

    public void equipArmor(String armor, int defense, int durability) {
        this.armor = armor;
        this.armorDefense = defense;
        this.armorDurability = durability;
    }

    public boolean isBagEmpty() {
        return bag.isEmpty();
    }

    public int equipItem(Scanner scanner) {
        if (bag.isEmpty()) {
            return 0; // Return 0 to indicate no valid choice was made
        }

        System.out.println("Bag items: " + bag);
        System.out.println("Equip Item - Options:");
        System.out.println("1. Equip a weapon.");
        System.out.println("2. Equip armor.");
        System.out.println("3. Cancel");
        int choice = scanner.nextInt();
    
        if (choice == 1) {
            List<String> weapons = new ArrayList<>();
            for (String item : bag) {
                if (item.contains("Weapon")) {
                    weapons.add(item);
                }
            }
    
            if (!weapons.isEmpty()) {
                System.out.println("Select a weapon to equip:");
                for (int i = 0; i < weapons.size(); i++) {
                    System.out.println((i + 1) + ". " + weapons.get(i));
                }
                int weaponChoice = scanner.nextInt() - 1;
                if (weaponChoice >= 0 && weaponChoice < weapons.size()) {
                    String selectedWeapon = weapons.get(weaponChoice);
                    equipWeapon("Blunt Sword", 2, 2); // Replace with actual weapon stats based on selectedWeapon
                    System.out.println("Equipped " + selectedWeapon + ".");
                    bag.remove(selectedWeapon);
                    return 1; // Return 1 to indicate weapon equipped
                } else {
                    System.out.println("Invalid choice.");
                    return 0; // Return 0 for invalid choice
                }
            } else {
                System.out.println("No weapons available to equip.");
                return 0; // Return 0 for no weapons available
            }
        } else if (choice == 2) {
            List<String> armors = new ArrayList<>();
            for (String item : bag) {
                if (item.contains("Armor")) {
                    armors.add(item);
                }
            }
    
            if (!armors.isEmpty()) {
                System.out.println("Select armor to equip:");
                for (int i = 0; i < armors.size(); i++) {
                    System.out.println((i + 1) + ". " + armors.get(i));
                }
                int armorChoice = scanner.nextInt() - 1;
                if (armorChoice >= 0 && armorChoice < armors.size()) {
                    String selectedArmor = armors.get(armorChoice);
                    equipArmor("Leather Armor", 2, 2); // Replace with actual armor stats based on selectedArmor
                    System.out.println("Equipped " + selectedArmor + ".");
                    bag.remove(selectedArmor);
                    return 2; // Return 2 to indicate armor equipped
                } else {
                    System.out.println("Invalid choice.");
                    return 0; // Return 0 for invalid choice
                }
            } else {
                System.out.println("No armor available to equip.");
                return 0; // Return 0 for no armor available
            }
        } else if (choice == 3) {
            System.out.println("Equip item cancelled.");
            return 3; // Return 3 to indicate cancel
        } else {
            System.out.println("Invalid choice.");
            return 0; // Return 0 for invalid choice
        }
    }

    public void discardItem(Scanner scanner) {
        System.out.println("Bag items: " + bag);
        if (bag.isEmpty()) {
            System.out.println("Your bag is empty.");
            return;
        }
        System.out.println("Enter the index of the item to discard (starting from 0):");
        int index = scanner.nextInt();
        if (index >= 0 && index < bag.size()) {
            bag.remove(index);
            System.out.println("Item discarded.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    public void attackEnemy(Enemy enemy) {
        int roll = Dice.roll();
        int normalDamage = switch (roll) {
            case 2 -> 6;
            case 3 -> 4;
            case 4 -> 3;
            case 5, 7, 9 -> 2;
            case 6, 8, 10 -> 0;
            case 11 -> 4;
            case 12 -> 5;
            default -> 0;
        };

        int totalDamage = normalDamage;
        boolean weaponBroke = false;

        if (normalDamage > 0) {
            totalDamage += weaponAttack;
            if (weaponDurability > 0) {
                weaponDurability--;
                if (weaponDurability == 0) {
                    weapon = "";
                    weaponAttack = 0;
                    weaponBroke = true;
                }
            }
        } else {
            System.out.println("Your attack missed!");
            return;
        }

        String attackMessage = "You attacked the enemy for " + totalDamage + " damage.";

        if (!weapon.isEmpty()) {
            attackMessage = "Your attack of " + normalDamage;
            attackMessage += " combined with your " + weapon + " attack of " + weaponAttack;
            attackMessage += " deals a total of " + totalDamage + " damage.";
        }

        enemy.decreaseHp(totalDamage);
        System.out.println(attackMessage);

        if (weaponBroke) {
            System.out.println("Your weapon broke!");
        }
    }

    public void healPlayer() {
        int roll = Dice.roll();
        int heal = switch (roll) {
            case 2 -> 7;
            case 3 -> 5;
            case 4 -> 4;
            case 5 -> 3;
            case 6, 8 -> 2;
            case 7, 9 -> 3;
            case 10 -> 4;
            case 11 -> 5;
            case 12 -> 6;
            default -> 0;
        };

        hp += heal;
        if (hp > 50) {
            hp = 50;  // Cap health at 50
        }
        System.out.println("You healed yourself for " + heal + " HP.");
    }

    public void gather() {
        int roll = Dice.roll();
        try (Scanner scanner = new Scanner(System.in)) {
            switch (roll) {
                case 2:
                    if (weapon.isEmpty() && armor.isEmpty()) {
                        System.out.println("Nothing to lose.");
                    } else {
                        System.out.println("You lost an equipped item.");
                        if (!weapon.isEmpty() && !armor.isEmpty()) {
                            if (new java.util.Random().nextBoolean()) {
                                weapon = "";
                                weaponAttack = 0;
                                weaponDurability = 0;
                            } else {
                                armor = "";
                                armorDefense = 0;
                                armorDurability = 0;
                            }
                        } else if (!weapon.isEmpty()) {
                            weapon = "";
                            weaponAttack = 0;
                            weaponDurability = 0;
                        } else {
                            armor = "";
                            armorDefense = 0;
                            armorDurability = 0;
                        }
                    }
                    break;
                case 3:
                    System.out.println("Nothing was found.");
                    break;
                case 4:
                    bag.add("Armor Chest");
                    System.out.println("Found an armor chest.");
                    break;
                case 5:
                    bag.add("Weapon Chest");
                    System.out.println("Found a weapon chest.");
                    break;
                case 6:
                    bag.add("Item Chest");
                    System.out.println("Found an item chest.");
                    break;
                case 7:
                    System.out.println("Found a mystery chest. Choose type (1. Weapon, 2. Armor, 3. Item):");
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1 -> bag.add("Weapon Chest");
                        case 2 -> bag.add("Armor Chest");
                        case 3 -> bag.add("Item Chest");
                    }
                    break;
                case 8:
                    System.out.println("Found a magic item chest. Choose type (1. Weapon, 2. Armor, 3. Item):");
                    int choice2 = scanner.nextInt();
                    switch (choice2) {
                        case 1 -> bag.add("Magic Weapon Chest");
                        case 2 -> bag.add("Magic Armor Chest");
                        case 3 -> bag.add("Magic Item Chest");
                    }
                    break;
                case 9:
                    System.out.println("Your bag slots have increased by 1.");
                    break;
                case 10:
                    System.out.println("Your bag slots have increased by 2.");
                    break;
                case 11:
                    equipWeapon("Sword", 4, 4);
                    System.out.println("Found and equipped a Sword.");
                    break;
                case 12:
                    equipArmor("Plate Armor", 4, 4);
                    System.out.println("Found and equipped Plate Armor.");
                    break;
            }
        }
    }

    public void decreaseArmorDurability() {
        if (armorDurability > 0) {
            armorDurability--;
        }
    }

    public void decreaseHp(int amount) {
        hp -= Math.max(amount, 0);
    }
}