package project.learning.java.nested_class.내부클래스;

public class InnerOuter {
    private static int outClassValue = 3;
    private int outInstanceValue = 2;

    // 내부 클래스
    class Inner {
        private int innerInstanceValue = 1;

        // 참고로 같은 내부 클래스라도 외부 클래스 안에 있기에 private 멤버에 접근 가능하다.
        public void print() {
            // 자신의 멤버이기에 당연히 가능하다.
            System.out.println(innerInstanceValue);
            // 외부 클래스의 인스턴스 멤버에 접근 가능, private도 접근 가능 -> Inner 인스턴스에서 InnerOuter 필드 변수에 접근하기 위해서는 InnerOuter 인스턴스의 존재가 전제 조건이다.
            System.out.println(outInstanceValue);
            // 외부 클래스의 클래스 멤버에는 접근 가능. private도 접근 가능 -> 내부/외부 클래스 관계를 떠나서 정적 변수이기에 어디서든 접근 가능하다.
            System.out.println(InnerOuter.outClassValue);
        }
    }
}
