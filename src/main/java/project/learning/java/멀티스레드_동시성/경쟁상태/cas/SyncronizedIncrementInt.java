package project.learning.java.멀티스레드_동시성.경쟁상태.cas;

public class SyncronizedIncrementInt implements IncrementInt {
    int value = 0;

    /**
     * synchronized 키워드로 임계영역이 보장되어 원자적 연산이 보장된다(모니터 락)
     */
    @Override
    public synchronized void increment() {
        value++;
    }

    @Override
    public synchronized long get() {
        return value;
    }
}
