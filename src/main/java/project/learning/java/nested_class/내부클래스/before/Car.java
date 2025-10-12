package project.learning.java.nested_class.내부클래스.before;

public class Car {
    private String model;
    private int chargeLevel;
    private Engine engine;

    public Car(String model, int chargeLevel) {
        this.model = model;
        this.chargeLevel = chargeLevel;
        this.engine = new Engine(this);
    }

    /**
     * getModel, getChargeLevel 메소드가 오직 Engine 클래스에서만 사용된다면, 굳이 외부에 노출시킬 필요가 없다.
     * 만약 다른 클래스에 사용한다면 이렇게 public으로 노출시키는 것이 맞다. 하지만 그렇지 않다면 어떻게 해야 할까?
     * 이를 내부 클래스로 만들어서 캡슐화하는 방법이 있다.
     */
    //Engine에서만 사용하는 메서드
    public String getModel() {
        return model;
    }

    //Engine에서만 사용하는 메서드
    public int getChargeLevel() {
        return chargeLevel;
    }

    public void start() {
        engine.start();
        System.out.println(model + " 시작 완료");
    }
}
