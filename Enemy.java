public class Enemy {
    private final String name;
    private int hp;

    public Enemy(String name, int hp) {
        this.name = name;
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void decreaseHp(int amount) {
        hp -= Math.max(amount, 0);
    }

    public void attackPlayer(Player player) {
        int roll = Dice.roll();
        int damage = switch (roll) {
            case 2, 3, 4 -> 6;
            case 5, 6 -> 4;
            case 7, 8 -> 3;
            case 9, 10 -> 2;
            case 11, 12 -> 0;
            default -> 0;
        };

        if (damage > 0) {
            damage -= player.getArmorDefense();
            if (player.getArmorDurability() > 0) {
                player.decreaseArmorDurability();
                if (player.getArmorDurability() == 0) {
                    player.equipArmor("", 0, 0);
                    System.out.println("Your armor broke!");
                }
            }
        } else {
            System.out.println("The enemy tried to attack, but missed!");
            return;
        }

        player.decreaseHp(Math.max(damage, 0));
        System.out.println("The enemy attacked you for " + damage + " damage.");
    }
}
