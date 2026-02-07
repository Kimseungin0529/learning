package project.learning.java.멀티스레드_동시성.경쟁상태.cas.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MySleep.sleep;

public class CasMain2 {
    private static final int THREAD_COUNT = 100;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value = " + atomicInteger.get());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                incrementAndGet(atomicInteger);
            }
        };
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        int result = atomicInteger.get();
        System.out.println(atomicInteger.getClass().getSimpleName() + " resultValue: " + result);
    }

    /**
     * 낙관적 락 방식 CAS 연산 구현
     *
     * value = 0 인 상태에서 동시에 100개 스레드가 접근할 때, value++; 100 이 되어야 하지만 경쟁 상태로 인해 1~100 사이의 값이 나올 수 있다.
     * CAS 연산을 통해 경쟁 상태를 해결한다. 읽기, 쓰기 작업이 분리되었기 때문에 원자적 연산이 보장되지 않아 실패했다.
     * 아래는 읽기 작업과 쓰기 작업 사이에 다른 스레드가 개입했을 때, 다시 시도하는 로직이 포함되어 있다.
     * 즉, 읽은 값(getValue)과 현재 값(atomicInteger.get())이 다르면 다시 시도한다. 이러한 방법으로 락 없이도 원자적 연산이 가능하다.
     *
     * sleep으로 스레드 동시 실행을 유도했다(CPU 연산이 너무 빠르기에) 실제로 기댓값과 다르면 false를 반환하며 다시 시도하는 것을 볼 수 있다.
     */
    private static int incrementAndGet(AtomicInteger atomicInteger) {
        int getValue;
        boolean result;
        do {
            getValue = atomicInteger.get();
            sleep(10); // 스레드 동시 실행을 위한 대기
            log("getValue: " + getValue);
            result = atomicInteger.compareAndSet(getValue, getValue + 1);
            log("result: " + result);
        } while (!result);

        return getValue + 1;
    }

    /**
     * 위에서 낙관적 락을 언급했다. 기존 synchronized, Lock 기법은 비관적 락이다. 어떤 차이가 있을까?
     *
     * 비관적 락: 비관적 락은 데이터 충돌이 자주 발생할 것으로 예상되는 상황에서 사용된다.
     * 스레드가 공유 자원에 접근할 때마다 락을 획득하여 다른 스레드가 접근하지 못하도록 막는다.
     * 이는 데이터 무결성을 보장하지만, 락 획득과 해제 과정에서 오버헤드가 발생할 수 있다.
     *
     * ex) synchronized, ReentrantLock / JPA, DB 트랜잭션의 격리 수준 중 '비관적 락(Pessimistic Lock)'
     *
     * 낙관적 락: 낙관적 락은 데이터 충돌이 드물 것으로 예상되는 상황에서 사용된다.
     * 스레드는 공유 자원에 자유롭게 접근하며, 작업이 끝난 후에야 충돌이 발생했는지 확인한다.
     * 만약 충돌이 발생했다면, 작업을 다시 시도한다. 이는 락 획득과 해제 과정이 없기에 오버헤드가 적지만,
     * 충돌이 자주 발생하는 상황에서는 성능이 저하될 수 있다.
     *
     * ex) CAS(Compare And Swap) 알고리즘 / JPA, DB 트랜잭션의 격리 수준 중 '낙관적 락(Optimistic Lock)'
     * JPA 낙관적 락은 버전 정보를 활용하여 충돌을 감지한다. 엔티티에 버전 필드를 추가한다.(실제로 썻던 내용을 깊이 있게 학습하니 더 잘 이해가 된다. 재밌다 ~.~)
     *
     */
}
