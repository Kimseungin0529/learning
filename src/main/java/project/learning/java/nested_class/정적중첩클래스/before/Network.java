package project.learning.java.nested_class.정적중첩클래스.before;

public class Network {

    public void sendMessage(String text) {
        NetworkMessage networkMessage = new NetworkMessage(text);
        networkMessage.show();
    }
}
