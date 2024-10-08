public abstract class Weapon extends Item {

    public Weapon(String name){
        super(name);
    }

    public abstract boolean canUse();

    public int remainingUses(){
        return -1;
    }
}
