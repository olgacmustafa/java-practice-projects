import java.util.*;

public class Player {
    private Room currentRoom;
    private List<Item> inventory = new ArrayList<>();
    private int hp = 100;
    private int attackPower = 10;

    public Player(Room startingRoom) { this.currentRoom = startingRoom; }

    public Room getCurrentRoom() { return currentRoom; }

    public void move(String direction) {
        Room next = currentRoom.getExit(direction);
        if (next != null) {
            currentRoom = next;
            currentRoom.describe();
        } else {
            System.out.println("Oraya gidemezsin.");
        }
    }

    public void take(String itemName) {
        Item found = null;
        for (Item i : currentRoom.getItems()) {
            if (i.getName().equalsIgnoreCase(itemName)) found = i;
        }
        if (found != null) {
            inventory.add(found);
            currentRoom.removeItem(found);
            System.out.println(found.getName() + " aldın.");
        } else System.out.println("Burada böyle bir eşya yok.");
    }

    public void use(String itemName, GameEngine ctx) {
        Item found = null;
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(itemName)) found = i;
        }
        if (found != null) found.onUse(this, ctx);
        else System.out.println("Envanterinde böyle bir eşya yok.");
    }

    public void inventoryText() {
        System.out.print("Envanter: ");
        if (inventory.isEmpty()) System.out.println("boş");
        else for (Item i : inventory) System.out.print(i.getName() + " ");
        System.out.println();
    }

    public void heal(int amount) { hp += amount; System.out.println("Can: " + hp); }
    public void increaseAttack(int amount) { attackPower += amount; System.out.println("Saldırı gücü: " + attackPower); }

    public void attack(EnemyNPC enemy) {
        System.out.println("Saldırıyorsun! " + attackPower + " hasar verdin.");
        enemy.takeDamage(attackPower);
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        System.out.println("Hasar aldın: " + dmg + ", kalan canın: " + hp);
        if (hp <= 0) System.out.println("Öldün! Oyun bitti.");
    }
}