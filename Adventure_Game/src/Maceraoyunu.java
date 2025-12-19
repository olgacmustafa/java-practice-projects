import java.util.*;

public class Maceraoyunu {
    public static void main(String[] args) {
        // Odalar
        Room salon = new Room("r1", "Salon", "Geniş bir salon.");
        Room silahOdasi = new Room("r2", "Silah Odası", "Eski silahların bulunduğu oda.");
        Room bahce = new Room("r3", "Bahçe", "Huzurlu bahçe.");

        // Bağlantılar
        salon.connect("güney", bahce);
        bahce.connect("kuzey", salon);
        silahOdasi.connect("batı", salon); // Silah Odası'ndan geri dönüş

        // Eşyalar
        KeyItem redKey = new KeyItem("key_red", "kırmızı_anahtar", "Kapıları açar.", silahOdasi, "doğu");
        WeaponItem sword = new WeaponItem("sword", "Kılıç", "Güçlü bir kılıç", 20);
        PotionItem potion = new PotionItem("potion", "Can iksiri", "50 can verir", 50);

        salon.addItem(redKey);
        silahOdasi.addItem(sword);
        silahOdasi.addItem(potion);

        // NPC konuşma
        ConversationNode node1 = new ConversationNode("Muhafız: Selam, yolcunun amacı nedir?");
        ConversationNode node2 = new ConversationNode("Muhafız: Anladım, dikkatli ol!");
        ConversationNode node3 = new ConversationNode("Muhafız: Cesur adamsın, geçebilirsin.");
        node1.addChoice(new ConversationChoice("Yardım istiyorum", node2));
        node1.addChoice(new ConversationChoice("Geçmek istiyorum", node3));

        FriendlyNPC muhafiz = new FriendlyNPC("Muhafız", node1);
        salon.getNpcs().add(muhafiz);

        // Düşman NPC
        EnemyNPC goblin = new EnemyNPC("Goblin", 50, 10);
        silahOdasi.getNpcs().add(goblin);

        // Oyuncu
        Player player = new Player(salon);

        // Oyun motoru
        GameEngine engine = new GameEngine(player);
        engine.setNpcList(Arrays.asList(muhafiz, goblin));

        // Başlat
        engine.start();
    }
}