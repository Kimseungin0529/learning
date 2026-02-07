package project.learning.java.멀티스레드_동시성.경쟁상태.cas;

public class BasicIncrementInt implements IncrementInt {
    int value = 0;

    /**
     * 멀티 스레드 환경에서 임계영역이 보장되지 않기에 원자적 연산이 이뤄지지 않는다.
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
