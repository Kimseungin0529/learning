package project.learning.java.중첩클래스.지역클래스.v1;

public class LocalOuterV1 {
    private int outInstanceVar = 10;

    public void process(int value) {
        int localVar = 20; // 지역 변수

        /**
         * 참고로 지역 클래스는 반환값으로 사용할 수 없다. 메서드 내부, 코드 블록에서 선언했기에
         * 일반적인 내부 클래스와 같이 외부에서는 해당 내부 클래스를 알 수 없다.
         * 만약 지역 클래스를 반환값으로 내보내고 싶다면 상속 또는 구현을 통해 제공해야 한다.
         * 예시로, class LocalInner implements ... 가 있다.
         *
         * 이에 대한 전제로는 상속/구현할 인터페이스/추상 클래스는 내부 클래스가 아니여야 한다.
         */
        class LocalInner { // 지역 클래스
            private int innerClassVar = 30;

            public void print() {
                // 지역 클래스에서는 외부 클래스의 멤버에 접근 가능하다.
                System.out.println("outInstanceVar = " + outInstanceVar);
                // 지역 클래스에서는 메서드의 매개변수에도 접근 가능하다.
                System.out.println("value = " + value);
                // 지역 클래스에서는 메서드의 지역 변수에도 접근 가능하다.
                System.out.println("localVar = " + localVar);
                // 자신의 멤버에도 접근 가능하다.
                System.out.println("innerClassVar = " + innerClassVar);
            }
        }

        LocalInner localInner = new LocalInner();
        localInner.print();
    }

}
