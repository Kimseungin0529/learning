package project.learning.java.nested_class.내부클래스.after;

public class Car {
    private String model;
    private int chargeLevel;
    private Engine engine;

    public Car(String model, int chargeLevel) {
        this.model = model;
        this.chargeLevel = chargeLevel;
        this.engine = new Engine();
    }

    public void start() {
        engine.start();
        System.out.println(model + " 시작 완료");
    }

    /**
     * 내부 클래스로 가져오면서 Car 클래스의 멤버에 직접 접근할 수 있게 되었다.
     * 따라서 굳이 Car 클래스의 멤버를 외부에 노출시키지 않아도 된다.
     * 이렇게 되면 캡슐화가 강화되고, 코드의 응집도가 올라가게 된다.
     *
     * 외부에 불필요한 메소드, 정보를 노출시키지 않기에 다른 곳에서 잘못된 사용을 방지할 수도 있다.
     */
    class Engine {

        public void start() {
            System.out.println("충전 레벨 확인: " + chargeLevel);
            System.out.println(model + "의 엔진을 구동합니다.");
        }
    }
}
