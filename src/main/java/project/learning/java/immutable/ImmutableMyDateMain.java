package project.learning.java.immutable;

public class ImmutableMyDateMain {
    public static void main(String[] args) {
        // date1, date2 변수에서 공유 참조가 발생한다.
        ImmutableMyDate date1 = new ImmutableMyDate(2024,1,1);
        ImmutableMyDate date2 = date1;
        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);

        /**
         * 불변 객체이기에 임의로 상태를 변경할 수 없다. 데이터의 변경도 새롭게 객체를 생성하는 방식으로 이루어진다.
         * 따라서 필연적으로 사이드 이펙트가 발생하지 않는다. 공유 참조를 해도 안전하다.
          */

        System.out.println("2025 -> date1");
        date1 = date1.withYear(2025);
        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);

    }
}
