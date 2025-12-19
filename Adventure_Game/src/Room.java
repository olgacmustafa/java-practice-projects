import java.util.*;

public class Room {
    public String id;
    public String name;
    public String description;
    private Map<String, Room> exits = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private List<NPC> npcs = new ArrayList<>();

    public Room(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void connect(String direction, Room room) {
        exits.put(direction.toLowerCase(), room);
    }

    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public void describe() {
        System.out.println("=== Mini Macera ===");
        System.out.println("Şu anda bulunduğun yer: " + name);
        System.out.print("Çıkışlar: ");
        if (exits.isEmpty()) System.out.println("yok");
        else System.out.println(String.join(", ", exits.keySet()));

        if (!items.isEmpty()) {
            System.out.print("Eşyalar: ");
            for (Item i : items) System.out.print(i.getName() + " ");
            System.out.println();
        }

        if (!npcs.isEmpty()) {
            System.out.print("Karakterler: ");
            for (NPC n : npcs) System.out.print(n.getName() + " ");
            System.out.println();
        }
    }

    public List<Item> getItems() { 
    	return items; 
    }
    public List<NPC> getNpcs() {
    	return npcs;
    }
    public void addItem(Item item) { 
    	items.add(item); 
    	}
    public void removeItem(Item item) { 
    	items.remove(item); 
    	}
    
    
}



