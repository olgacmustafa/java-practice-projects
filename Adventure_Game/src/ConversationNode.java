import java.util.*;

public class ConversationNode {
    private String text;
    private List<ConversationChoice> choices = new ArrayList<>();

    public ConversationNode(String text) { this.text = text; }

    public void addChoice(ConversationChoice choice) { choices.add(choice); }

    public void startConversation() {
        Scanner sc = new Scanner(System.in);
        ConversationNode current = this;

        while (current != null) {
            System.out.println(current.text);
            if (current.choices.isEmpty()) break;

            for (int i = 0; i < current.choices.size(); i++)
                System.out.println((i+1) + ". " + current.choices.get(i).getText());

            int selection = -1;
            while (selection < 1 || selection > current.choices.size()) {
                System.out.print("Seçiminiz: ");
                try { selection = Integer.parseInt(sc.nextLine()); } 
                catch (Exception e) { selection = -1; }
            }

            current = current.choices.get(selection - 1).getNextNode();
        }
        sc.close();
    }
}