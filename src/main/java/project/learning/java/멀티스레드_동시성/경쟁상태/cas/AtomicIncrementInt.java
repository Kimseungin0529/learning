package project.learning.java.멀티스레드_동시성.경쟁상태.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIncrementInt implements IncrementInt {
    AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * Atomic 으로 처리되어 원자적 연산이 보장된다. CAS(Compare And Swap) 알고리즘을 사용하여 구현되어 있다.
     */
    @Override
    public void increment() {
        atomicInteger.getAndIncrement();
    }

    @Override
    public long get() {
        return atomicInteger.get();
    }
}
