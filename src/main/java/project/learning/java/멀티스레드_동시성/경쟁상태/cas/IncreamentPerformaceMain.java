package project.learning.java.멀티스레드_동시성.경쟁상태.cas;

public class IncreamentPerformaceMain {
    private static final int COUNT = 100_000_000;

    /**
     * [실행 로그]
     * Testing BasicIncrementInt
     * total time: 100000000, 93ms
     * Testing VolatileIncrementInt
     * total time: 100000000, 722ms
     * Testing SyncronizedIncrementInt
     * total time: 100000000, 1418ms
     * Testing AtomicIncrementInt
     * total time: 100000000, 777ms
     *
     * BasicIncrementInt는 메모리 가시성, 임계구역 등 조건이 없기에 가장 빠른 연산 속도를 보인다. 다만 그렇기에 멀티스레드에서 동시성은 보장되지 않는다.
     * VolatileIncrementInt는 메모리 가시성은 보장하지만 임계구역이 보장되지 않기에 원자적 연산이 보장되지 않는다. 그럼에도 메인 메모리에 접근하기에 연산속도가 느리다.
     * SyncronizedIncrementInt는 임계구역이 보장되어 원자적 연산이 보장된다. 다만 모니터 락을 사용하기에 가장 느린 연산 속도를 보인다.
     *
     * AtomicIncrementInt는 CAS 연산으로 임계구역이 보장되어 원자적 연산이 보장된다. 락을 사용하지 않기에 SyncronizedIncrementInt보다는 빠른 연산 속도를 보인다.
     * 어떤 원리로 원자성을 보장하면서 synchronized 보다 빠를까?
     */

    public static void main(String[] args) throws InterruptedException {

        test(new BasicIncrementInt());
        test(new VolatileIncrementInt());
        test(new SyncronizedIncrementInt());
        test(new AtomicIncrementInt());

    }

    public static void test(IncrementInt incrementInt) {
        System.out.println("Testing " + incrementInt.getClass().getSimpleName());
        long start = System.currentTimeMillis();
        for (long i = 0; i < COUNT; i++) {
            incrementInt.increment();
        }
        long end = System.currentTimeMillis();
        System.out.println("total time: " + incrementInt.get() + ", " + (end - start) + "ms");
    }
}
