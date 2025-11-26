package project.learning.java.중첩클래스.익명클래스.지역클래스와비교;

public class LocalOuterMainV2 {
    public static void main(String[] args) {
        LocalOuterV2 localOuter = new LocalOuterV2();
        Printer print = localOuter.process(50);
        print.print();
    }
}
