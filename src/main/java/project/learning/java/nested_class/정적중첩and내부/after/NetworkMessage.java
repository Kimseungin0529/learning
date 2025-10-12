package project.learning.java.nested_class.정적중첩and내부.after;

public class NetworkMessage {
    private String message;

    public NetworkMessage(String message) {
        this.message = message;
    }

    public void show() {
        System.out.println("Network Message: " + message);
    }
}
