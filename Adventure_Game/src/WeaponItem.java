public class WeaponItem extends Item {
    private int attackPower;

    public WeaponItem(String id, String name, String description, int attackPower) {
        super(id, name, description);
        this.attackPower = attackPower;
    }

    @Override
    public void onUse(Player p, GameEngine ctx) {
        p.increaseAttack(attackPower);
        System.out.println("Saldırı gücün " + attackPower + " arttı.");
    }
}