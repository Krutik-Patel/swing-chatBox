public class trial {
    public static void main(String[] args) throws Exception {
        Chat thisChat = Chat.init();
        while (true) {
            thisChat.pushMessageToUser("Hello");
            System.out.println("Message -> " + thisChat.getMessageFromUser());   
        }
    }
}
