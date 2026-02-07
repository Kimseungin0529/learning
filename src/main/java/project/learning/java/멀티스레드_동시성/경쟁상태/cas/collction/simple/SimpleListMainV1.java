package project.learning.java.멀티스레드_동시성.경쟁상태.cas.collction.simple;

public class SimpleListMainV1 {
    // 단일스레드지만 멀티스레드 환경이면 당연히 A, B 중 하나가 씹힐수도 있다. 왜?
    // add 는 원자적 연산으로 보이지만 BasicList 를 보자.

    /**
     *
     * @Override
     *     public void add(Object e) {
     *         elementData[size] = e;
     *         sleep(100); // 멀티스레드 문제를 쉽게 확인하는 코드
     *         size++;
*         }
     *         연산 작업이 2가지로 나눠진다(elementData[size] = e; / size++;)
     *         따라서 동시에 실행된다면 A, B 추가는 size == 0 인 위치에서 덮여지고 size++;는 2번 호출하여 2가 된다.
     *         결과는 A, B 중 하나의 값만 있지만 size = 2로 예상된다.
     *         SimpleListMainV2 에서 멀티스레드 환경을 직접 보자.
     */
    public static void main(String[] args) {
        SimpleList list = new BasicList();
        list.add("A");
        list.add("B");
        System.out.println("list = " + list);
    }
}
