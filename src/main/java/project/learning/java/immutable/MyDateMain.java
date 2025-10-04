package project.learning.java.immutable;

public class MyDateMain {
    public static void main(String[] args) {
        // date1, date2 변수에서 공유 참조가 발생한다.
        MyDate date1 = new MyDate(2024,1,1);
        MyDate date2 = date1;
        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);

        // 공유 참조 된 객체의 상태가 변경된다. 의도치 않은 사이드 이펙트(data2도 변경됨)가 발생한다.
        System.out.println("2025 -> date1");
        date1.setYear(2025);
        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);
    }
}
