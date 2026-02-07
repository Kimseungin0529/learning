package project.learning.java.멀티스레드_동시성.경쟁상태.cas.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class CasMain1 {
    /**
     * 대부분 동시성 처리에는 synchronized, Lock 등을 사용한다. 하지만
     * '락 획득 -> 없다면 대기 -> 락 획득 후, 작업 수행 -> 락 해체 및 다른 스레드에 락 양도' 과정을 거치며 추가적인 연산이 많이 일어난다.
     * 작업 가능한 스레드를 CPU 스케줄링에 넣고 대기 or 블로킹 상태인 스레드는 대기 큐에 넣는 작업이 일어난다. 이러한 과정에서 컨텍스칭 스위치(Context Switch)가 발생하며 오버헤드가 발생한다.
     * CAS(Compare And Swap) 알고리즘은 이러한 오버헤드를 줄이기 위해 고안된 알고리즘이다. CAS 알고리즘은 락을 사용하지 않고도 원자적 연산을 가능하게 한다.
     * 참고로 대부분 상황에는 락이 필수적이며 작은 단위, 범위에만 CAS 알고리즘을 적용하는 것이 좋다.(만능은 아니다.)
     *
     * CAS 알고리즘은 다음과 같은 세 가지 주요 연산으로 구성된다.
     * 1. 비교(Compare): 메모리의 현재 값과 예상 값을 비교한다.
     * 2. 교환(Swap): 만약 현재 값이 예상 값과 일치하면, 새로운 값으로 교환한다.
     * 3. 반복(Retry): 만약 현재 값이 예상 값과 일치하지 않으면, 다시 시도한다.
     * CAS 알고리즘은 주로 멀티코어 프로세서 환경에서 사용되며, Java에서는 java.util.concurrent.atomic 패키지에
     * 포함된 AtomicInteger, AtomicLong, AtomicReference 등의 Atomic 클래스를 통해 제공된다.
     *
     * 아래 코드 예시를 보면 compare and set 연산이 어떻게 동작하는지 알 수 있다. result1 은 true 가 나오며 값이 0 에서 1 로 변경된다.
     * 반면에 result2 는 false 가 나오며 값이 변경되지 않는다.(기대값과 다르기 때문에 반영되지 않는다.) 다음으로 어떤 원리로 인해 락을 사용하지 않는데도 이렇게 원자적 연산이
     * 가능한지 알아보자.
     */
    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("start value =  " + atomicInteger.get());

        boolean result1 = atomicInteger.compareAndSet(0, 1);
        System.out.println("result1 = " + result1 + ", value = " + atomicInteger.get());

        boolean result2 = atomicInteger.compareAndSet(0, 1);
        System.out.println("result2 = " + result2 + ", value = " + atomicInteger.get());
    }
}
