public class RangedWeapon extends Weapon {
    private int ammo;

    public RangedWeapon(String name, int ammo, int damage) {
        super(name, damage);
        this.ammo = ammo;
    }

    @Override
    public boolean canUse() {
        return ammo > 0;
    }

    @Override
    public int remainingUses() {
        return ammo;
    }

    public void useAmmo() {
        if (ammo > 0) {
            ammo--;
        }
    }
}
