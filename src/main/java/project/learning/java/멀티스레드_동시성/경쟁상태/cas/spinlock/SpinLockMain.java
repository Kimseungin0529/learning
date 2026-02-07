package project.learning.java.멀티스레드_동시성.경쟁상태.cas.spinlock;

import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MyLogger.log;

public class SpinLockMain {
    /**
     * [스핀 락 실행 과정 로그]
     13:01:23.874 [ thread-1] 락 획득
     13:01:23.874 [ thread-2] 락 획득
     13:01:23.876 [ thread-1] 비즈니스 로직 실행
     13:01:23.876 [ thread-2] 비즈니스 로직 실행
     13:01:23.876 [ thread-1] 락 해제
     13:01:23.877 [ thread-2] 락 해제
     *
     * 위 로그(실행마다 조금 다름)를 보면 스핀 락이 제대로 동작하지 않는 것을 알 수 있다.
     * 우리가 예상한 건 동시성이 보장되어야 한다. 하지만 동시에 두 스레드가 락을 획득하고 처리하는 것을 볼 수 있따.
     * 즉, SpinLockBad 클래스는 스핀 락의 개념을 흉내만 낸 것이지 실제로 동시성을 보장하지 못한다.
     * 이러한 이유는 임계 구역이 보장되지 않았다. 원자적 연산이 구성되지 않았기 때문이다.
     *  SpinLockBad 클래스의 lock() 메서드를 보면 다음과 같다.
     * if (!lock) {
     *   sleep(100);
     *   log("락 획득");
     *   lock = true;
     *   return;
     * } else
     *
     *if 문은 읽기 작업이 일어난다. 하지만 임계구역이 확보되지 않아 동시에 if 문을 통과하는 경우가 발생할 수 있다.
     */
    public static void main(String[] args) {
        SpinLockBad spinLock = new SpinLockBad();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                spinLock.lock();
                try {
                    log("비즈니스 로직 실행");
                } finally {
                    spinLock.unlock();
                }
            }
        };

        Thread thread1 = new Thread(task, "thread-1");
        Thread thread2 = new Thread(task, "thread-2");

        thread1.start();
        thread2.start();
    }
}
