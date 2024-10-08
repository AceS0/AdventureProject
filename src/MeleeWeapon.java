public class MeleeWeapon extends Weapon{
    public MeleeWeapon(String name){
        super(name);
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
