package project.learning.java.nested_class.정적중첩and내부.after;

public class Network {

    public void sendMessage(String text) {
        NetworkMessage networkMessage = new NetworkMessage(text);
        networkMessage.show();
    }

    /**
     * 정적 중첩 클래스를 통해 캡슐화 강화했다. 다만 Network 내부에 속한 클래스가 아니다. 논리적으로도 이를 알고 가자.
     * static class 는 정적 클래스이다. 예시로 static 은 Method 영역에 저장된다. 따라서 Network 인스턴스 없이도 접근 가능해야 하기에
     * NetworkMessage 에 직접 접근하여 인스턴스를 생성할 수 있다.
     * 요약하면 정적 중첩 클래스는 바깥 클래스의 인스턴스 없이도 접근 가능하다. (static 이기에)
     * 따라서 긴밀한 관계를 표시하기 위함이지, 별개의 클래스다. 서로 독립적이기에 각 클래스의 필드 변수에 접근할 수 없다.(static 이 아니라면)
     */
    private static class NetworkMessage {
        private String message;

        /**
         * private static class 이기에 외부에서 정적 중첩 클래스 생성 불가
         * 오직 Network 클래스 내부에서만 생성 가능하다 -> 결속력 강화, 캡슐화 -> 하지만 이럴거면 내부 클래스로 가져도 된다고 판단된다.
         * 변경 가능성과 바깥 클래스의 필드 변수 접근성 등 고려해서 적용하면 될 것으로 보인다.
         */
        public NetworkMessage(String message) {
            this.message = message;
        }

        public void show() {
            System.out.println("Network Message: " + message);
        }
    }
}
