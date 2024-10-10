public abstract class Weapon extends Item {
    private String name;
    private int damage;

    public Weapon(String name, int damage) {
        super(name);
        this.name = name;
        this.damage = damage;
    }

    public abstract boolean canUse();

    public int remainingUses() {
        return -1;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }
}
