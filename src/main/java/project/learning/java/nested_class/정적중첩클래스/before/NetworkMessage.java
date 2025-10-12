package project.learning.java.nested_class.정적중첩클래스.before;

/**
 * 현재 NetworkMessage 클래스는 Network 클래스에서만 사용된다. 다만 패키지를 보면 NetworkMessage, Network 가 분리되어 있기에 또다른 클래스와 상호
 * 작용하거나 별개의 역할로 판단될 수 있다. 따라서 긴밀한 관계를 가진 경우, 혹은 외부에 노출시키고 싶지 않은 경우라면 내부 클래스로 만드는 것이 좋다.
 * 이렇게 되면 각각의 클래스를 들어가서 볼 필요 없이 Network 클래스만 보면 되기에 코드의 가독성도 올라가고, 캡슐화도 강화된다.
 */
public class NetworkMessage {
    private String message;

    public NetworkMessage(String message) {
        this.message = message;
    }

    public void show() {
        System.out.println("Network Message: " + message);
    }
}
