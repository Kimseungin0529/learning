package project.learning.java.nested_class.지역클래스.v2;

public class LocalOuterV2 {
    private int outInstanceVar = 10;

    public Printer process(int value) {
        int localVar = 20; // 지역 변수

        /**
         * 외부에 있는 인터페이스를 통해 지역 클래스임에도 반환할 수 있게 만들었다.
         * 여기서 중요한 점은 반환 객체를 통해서 process 메소드에 있던 지역 변수, 메소드 변수까지 접근할 수 있다는 점이다.
         * 사실상, 위 변수는 메소드가 끝나면 스택 프레임이 사라지면서 같이 사라진다.
         * 하지만 해당 Printer.print() 메소드를 실행하면 지역 변수, 메소드 변수가 정상적으로 출력된다.
         *
         * 우리가 아는 스택 영역, 스택 프레임 기준으로는 메소드가 종료되면 지역 변수가 사라지는 게 정상이다. 어떻게 된걸까?
         * 이는 지역 클래스의 특징은 지역 캡처 변수(내부적으로 final 처리됨) 기능이 있기 때문에 가능하다.
         */
        class LocalInner implements Printer{ // 지역 클래스
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
