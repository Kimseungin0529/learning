package project.learning.java.멀티스레드_동시성.경쟁상태.cas.collction.simple;

import java.util.Collections;
import java.util.List;

import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MyLogger.log;

public class SimpleListMainV2 {

    public static void main(String[] args) throws InterruptedException {

        //test(new BasicList()); //동시성 확보 X
        test(new SyncProxyList(new BasicList())); // 프록시 구조를 활용한 동시성 확보
    }

    /**
     *14:52:34.498 [     main] BasicList
     * 14:52:34.601 [ Thread-2] Thread-2: list.add(B)
     * 14:52:34.601 [ Thread-1] Thread-1: list.add(A)
     * 14:52:34.602 [     main] [B, null] size=2, capacity=5
     *
     * 실행 로그 예시다.(실행마다 상황은 달라짐, CPU/멀티스레드 실행 환경 차이)
     * 결과적으로 멀티스레드 환경에서 동시성이 보장되지 않는다. SimpleList를 예시로 봤지만 실제 제공하는 ArrayList, HashSet, HashMap 등
     * 일반 컬렉션에서는 동시성을 보장하지 않는다. 그렇다면 어떻게 해야 할까? 간단하게는 syncronized 처리르 하면 된다.
     *
     * 그렇다면 동시성이 필요한 경우 전부다 syncronized 처릴르 하여 새롭게 만들어야 할까? 그렇다면 매우 불편할 것이다. 여기서 프록시 패턴을 이용하면
     * 반복적인 과정을 방지할 수 있다.
     * 기존 구조 : 고객(요청, 스레드) <-> 주방(구체)
     * 프록시 적용 구조 : 고객(요청, 스레드) <-> 홀 인원(프록시) <-> 주방(구체)
     *
     *
     */
    public static void test(SimpleList list) throws InterruptedException {
        log(list.getClass().getSimpleName());
        // A를 리스트에 저장하는 코드
        Runnable addA = new Runnable() {
            @Override
            public void run() {
                list.add("A");
                log("Thread-1: list.add(A)");
            }
        };
        // B를 리스트에 저장하는 코드
        Runnable addB = new Runnable() {
            @Override
            public void run() {
                list.add("B");
                log("Thread-2: list.add(B)");
            }
        };
        Thread thread1 = new Thread(addA, "Thread-1");
        Thread thread2 = new Thread(addB, "Thread-2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        log(list);
    }
}