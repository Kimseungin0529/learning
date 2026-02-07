package project.learning.java.멀티스레드_동시성.경쟁상태.cas;

public class VolatileIncrementInt implements IncrementInt {
    volatile int value = 0;

    /**
     * // 원자적 연산, 락 처리로 보일 수 있지만 그렇지 않다. 메모리 가시성만 보장할 뿐 원자적 연산은 보장하지 않는다.
     * // value = value + 1; 과 동일하기에 읽기 연산과 쓰기 연산이 분리되어 있다. 2개의 스레드가 동시에 실행하더라도 둘다 동일하기 0을 읽는다면
     * // thread1 이 0 + 1 = 1 을 수행하고 thread2 도 0 + 1 = 1 을 수행하여 최종 값이 1이 될 수 있다.
     */

    @Override
    public void increment() {
        value++;

    }

    @Override
    public long get() {
        return value;
    }
}
