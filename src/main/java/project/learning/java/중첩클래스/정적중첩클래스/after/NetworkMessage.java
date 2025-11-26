package project.learning.java.중첩클래스.정적중첩클래스.after;

public class NetworkMessage {
    private String message;

    public NetworkMessage(String message) {
        this.message = message;
    }

    public void show() {
        System.out.println("Network Message: " + message);
    }
}
