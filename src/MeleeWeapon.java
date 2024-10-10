public class MeleeWeapon extends Weapon {
    public MeleeWeapon(String name, int damage) {
        super(name, damage);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public int remainingUses() {
        return -1;
    }
}
