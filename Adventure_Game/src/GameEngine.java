
import java.util.*;

public class GameEngine {
    private Player player;
    private boolean running = true;
    private Scanner scanner = new Scanner(System.in);
    private List<NPC> npcs = new ArrayList<>();

    public GameEngine(Player player) { this.player = player; }
    public void setNpcList(List<NPC> npcList) { this.npcs = npcList; }

    public void start() {
        player.getCurrentRoom().describe();
        while (running) {
            System.out.print("\n> ");
            String input = scanner.nextLine();
            handleCommand(input);
        }
    }

    private void handleCommand(String command) {
        String[] parts = command.split(" ");
        switch (parts[0].toLowerCase()) {
            case "look": player.getCurrentRoom().describe(); break;
            case "go":
                if (parts.length < 2) 
                	System.out.println("Nereye gideceksin?");
                else 
                	player.move(parts[1]); 
                break;
            case "take":
                if 
                (parts.length < 2) System.out.println("Ne alacaksın?");
                else
                	player.take(parts[1]);
                break;
            case "use":
                if (parts.length < 2) System.out.println("Ne kullanacaksın?");
                else player.use(parts[1], this); break;
            case "inv": player.inventoryText(); break;
            case "talk":
                if (parts.length < 2)
                	System.out.println("Kiminle konuşacaksın?");
                else {
                    String npcName = parts[1];
                    boolean found = false;
                    for (NPC n : npcs) {
                        if (n.getName().equalsIgnoreCase(npcName) && n instanceof FriendlyNPC) {
                            ((FriendlyNPC) n).talk(); found = true; break;
                        }
                    }
                    if (!found)
                    	System.out.println("Böyle bir NPC yok veya konuşulamaz.");
                }
                break;
            case "attack":
                if (parts.length < 2)
                	System.out.println("Kimi atacaksın?");
                else {
                    String npcName = parts[1];
                    boolean found = false;
                    for (NPC n : npcs) {
                        if (n.getName().equalsIgnoreCase(npcName) && n instanceof EnemyNPC) {
                            player.attack((EnemyNPC) n);
                            if(((EnemyNPC)n).isAlive()) ((EnemyNPC)n).attack(player);
                            found = true; break;
                        }
                    }
                    if (!found) System.out.println("Böyle bir düşman yok.");
                }
                break;
            case "help": 
                System.out.println("Komutlar: look, go [yön], take [eşya], use [eşya], inv, talk [NPC], attack [NPC], help, quit");
                break;
            case "quit": running = false; System.out.println("Oyun bitti."); break;
            default: System.out.println("Bilinmeyen komut."); break;
        }
    }
}