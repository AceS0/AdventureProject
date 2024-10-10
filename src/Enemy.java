public class Enemy {
    private String name;
    private String description;
    private int health;
    private Weapon weapon;
    private Player player;

    public Enemy(String name, String description, int health, Weapon weapon) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void hit(int damage) {
        health -= damage;
        if (!isAlive()) {
        }
    }

    public void attack(Player player) {
        if (isAlive()) {
            player.adjustHpfight(-weapon.getDamage());
            player.getDisplayMessage(name + " attacks you for " + weapon.getDamage() + " damage!");
        } else {
            player.getDisplayMessage(name + " is defeated and cannot attack.");
        }
    }

    public Weapon dropWeapon() {
        return weapon;
    }

    public String getDescription() {
        return description;
    }
}
