public class FriendlyNPC extends NPC {
    private ConversationNode conversationRoot;

    public FriendlyNPC(String name, ConversationNode conversationRoot) {
        super(name);
        this.conversationRoot = conversationRoot;
    }

    public void talk() {
        if (conversationRoot != null) conversationRoot.startConversation();
        else System.out.println(name + ": Merhaba!");
    }
}