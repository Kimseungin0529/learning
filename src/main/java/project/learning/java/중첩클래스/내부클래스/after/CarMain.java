package project.learning.java.중첩클래스.내부클래스.after;

public class CarMain {

    public static void main(String[] args) {
        Car car = new Car("Tesla Model S", 85);
        car.start();

        /**
         * 내부 클래스는 오직 외부 클래스를 통해 생성할 수 있다.
         *
         * [학습 중 개인적인 생각]
         * 이렇게 내부 클래스를 직접 생성하고 접근한다면 굳이 내부 클래스로 만들 필요가 있을까란 생각이 든다.
         * private으로 접근을 막는다면 모르겠지만, 내부 클래스의 장점이 외부 클래스와 밀접한 관계를 맺고 있다는 점인데
         * 이렇게 직접 접근하는 것은 외부에서 핸들링할 수 있기에 그 장점을 살리지 못하는 것 같다.
         * 오히려 이렇게 해야 한다는 감이 온다면 내부 클래스가 아닌 외부 클래스로 만드는 게 맞는 것 같다.
         */
        Car.Engine bmwI3 = new Car("BMW i3", 90).new Engine();
        // new Engine(); // 내부 클래스는 직접 생성이 불가능하다.
    }
}
