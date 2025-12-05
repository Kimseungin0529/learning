package project.learning.java;

/**
 * static 메서드 내부에서는 static 메서드만 호출 가능하다.
 * 인스턴스 메서드는 호출 불가능하다.
 */
public class StaticTest {
    public static void main(String[] args) {
        System.out.println("Static Test");
        StaticParent.init();
    }
}
class StaticParent {
    static void init() {
        System.out.println("Static Parent Init");
    }
}
