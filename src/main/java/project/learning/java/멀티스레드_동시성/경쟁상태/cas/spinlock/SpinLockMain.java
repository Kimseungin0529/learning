package project.learning.java.멀티스레드_동시성.경쟁상태.cas.spinlock;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MySleep.sleep;

public class SpinLockMain {
    /**
     * SpinLockBad [스핀 락 실행 과정 로그]
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

    /**
     * SpinLock 실행
     * [실행 로그]
     * 13:20:38.927 [ thread-2] 락 획득
     * 13:20:38.927 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.929 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.929 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.929 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.929 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.929 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.929 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-2] 비즈니스 로직 실행
     * 13:20:38.930 [ thread-1] 락 획득 실패, 스핀 대기 중...
     * 13:20:38.930 [ thread-2] 락 해제
     * 13:20:38.931 [ thread-1] 락 획득
     * 13:20:38.932 [ thread-1] 비즈니스 로직 실행
     * 13:20:38.932 [ thread-1] 락 해제
     *
     * 위 로그는 Main 메서드에서 sleep(1)일때 실행 결과이다. 보면 스레드1ㅇ서 락 획득 실패로 반복문을 많이 반복했다. sleep(1) == 0.001ms이다.
     * sleep(10)만해도 급수적으로 증가한 로그가 생긴다. 여기서 보면 CPU 연산 속도가 매우 빠르기에 0.001ms 대기 시간으로도 많은 재시도(CPU 자원 소모)가 일어난
     * 것을 볼 수 있다. 비즈니스 로직 실행 시간이 매우 짧은 것이 아니라면 오히려 syncronized, Lock 기법보다 실행 시간 측면에서도 비효율적일 수 있다.
     *
     * 그렇다면 언제 사용해야 할까? 아래와 예제와 같이 단순히 값을 변경, 증가시키는 경우에는 적합하다. 예를 들어, 1초당 100만건이 요청이 오고 단순히 주문량에 대한
     * 수가 궁금하다면 CAS, Atomic 을 사용하여 적용하는 것이 나을 것이다.(실제로 CPU 연산은 1초에 억~수억번이 일어나기에 스레드 충돌이 발생할 경우가 적다)
     *DB
     * 하지만 DB 접근, 외부 API 호출 등은 아무리 빨라도 10~100ms 이상이 걸린다. 이처럼 대기가 필요한 상황은 CAS 연산보다는 락 자체를 거는 것이
     * CPU 자원을 효율적으로 사용하게 된다.
     */
    public static void main(String[] args) {
        //SpinLockBad spinLock = new SpinLockBad();
        SpinLock spinLock = new SpinLock();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                spinLock.lock();
                try {
                    sleep(1);
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
    /**
     * [추가 정리]
     *
     * CAS 는 읽은 값, 쓰려는 값(현재값, 기댓값) 비교를 통해 원자성을 보장한다. 하지만 의문점이 남아있다.
     *  private static int incrementAndGet(AtomicInteger atomicInteger) {
     *         int getValue;
     *         boolean result;
     *         do {
     *             getValue = atomicInteger.get();
     *             sleep(10); // 스레드 동시 실행을 위한 대기
     *             log("getValue: " + getValue);
     *             result = atomicInteger.compareAndSet(getValue, getValue + 1);
     *             log("result: " + result);
     *         } while (!result);
     *
     *         return getValue + 1;
     *     }
     *     위 메서드를 보면
     *     do 구문 안에 읽기값과 쓰기 값에 비교를 통해 원자성이 일어난다. 하지만
     *     getValue = atomicInteger.get();
     *     result = atomicInteger.compareAndSet(getValue, getValue + 1);
     *     사실 상, 동시에 실행되지는 않는다. 순차적으로 실행하기에 많은 스레드가 접근하는 순간에 동시에 같은 값을 읽을 수 있다.
     *     그런데도 어떻게 보장할까? 이는 CPU 하드웨어 관점에서 CAS를 사용하면 CPU 캐시와 메모리 처리 과정에서 원자성을 보장해준다.
     *     특히나 CPU 캐시, CPU 의 연산속도가 매우 빠르기에 CPU 하드웨어 측면에서 락을 걸더라도 성능에 영향이 없다!
     *
     *     마지막으로 락 이외 CAS를 직접 구현하여 사용하는 경우는 거의 없다. 즉, Atomic과 같이 이미 잘 만들어진 라이브러리를 사용하게 된다.
     *     추가적으로 CAS 연산을 활용하여 동시성을 보장하는 라이브러리를 알아보자.
     */
}
