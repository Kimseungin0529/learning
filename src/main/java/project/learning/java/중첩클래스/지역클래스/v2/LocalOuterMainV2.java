package project.learning.java.중첩클래스.지역클래스.v2;

public class LocalOuterMainV2 {
    public static void main(String[] args) {
        LocalOuterV2 localOuter = new LocalOuterV2();
        Printer print = localOuter.process(50);
        print.print();
    }
}
