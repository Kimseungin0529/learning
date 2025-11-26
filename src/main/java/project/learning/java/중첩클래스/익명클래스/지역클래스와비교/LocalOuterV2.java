package project.learning.java.중첩클래스.익명클래스.지역클래스와비교;

/**\
 * 익명 클래스와 비교하기 위해 지역 클래스 패키지에 있는 지역 클래스 복사
 */
public class LocalOuterV2 {
    private int outInstanceVar = 10;

    public Printer process(int value) {
        int localVar = 20; // 지역 변수

        /**
         * 특수한 기능을 사용하기 위해 지역 클래스르 사용했다. 내부 클래스와 달리 특정 메소드, 코드 블록에서만 한정적으로 사용하기에 지역 클래스를 사용한다.
         * 반복적으로 사용된다면 내부 클래스로 선언하거나 LocalOuterV2와 같이 하나의 클래스로서 존재하는 것이 더 적합할 것이다.
         * 지역 클래스를 활용하여 단편적 기능을 사용하기 위해 아래와 같이 작성헀다.
         *
         * 하지만 단순한 출력 기능인 print 메소드를 사용하기 위해 지역 클래스, 내부에 변수와 메소드 등 여러 코드를 작성했다.
         * 단순한 출력 하나를 위해 이렇게 많은 코드를 작성하는 것이 조금은 불편하게 느껴질 수 있다. 익명 클래스는 아주 미세하게나마 클래스 선언부를
         * 없얘줘 지역 클래스보다 저 적은 코드로 구현할 수 있는 장점이 있다.
         */
        class LocalInner implements Printer { // 지역 클래스
            private int innerClassVar = 30;

            @Override
            public void print() {
                System.out.println("outInstanceVar = " + outInstanceVar);
                System.out.println("value = " + value);
                System.out.println("localVar = " + localVar);
                System.out.println("innerClassVar = " + innerClassVar);
            }
        }

        return new LocalInner();
    }
}
