package project.learning.java.멀티스레드_동시성.경쟁상태.lock.lock;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;
import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MyLogger.log;

public class LockSupportMainV1 {
    /**
     * LockSupport 는 스레드를 일시 정지(park) 및 재개(unpark)하는 데 사용되는 클래스이다.
     * Synchronized 블록이나 메서드와 달리, LockSupport 는 더 세밀한 제어를 제공하는 동기화 도구이다. Synchronized 키워드만으로 동시성 문제를 해결할 수 있는 큰 장점이 있다.
     * 특히 사용하기 간편한 장점이 있지만 반대로 인터럽트와 외부에서 동적으로 락을 해제하지 못하기에 무한 대기 위험성이 존재한다. 또한 선점에 대한 공정성을 확보하지 않기에 주의가 필요하다.
     * 최악의 경우, 가장 먼저 도착한 스레드가 무한정 대기할 수 있다. 따라서 이러한 문제점(무한 대기, 공정성 부족 등)을 해결하기 위해서 concurrent 패키지에서 세부적인 핸들링을 제공한다.
     * <p>
     * LockSupport는 park() 메서드를 사용하여 스레드를 일시 정지시키고, unpark() 메서드를 사용하여 일시 정지된 스레드를 재개시킨다. parkNanos()는 일정 나노 초 동안 스레드를 일시 정지시키는 메서드이다.
     * 따라서 Synchronized 보다는 LockSupport를 사용하는 것이 안전하다.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread parkThread = new Thread(new ParkTask());
        parkThread.start();

        sleep(1000);
        log("parkThread 상태 : " + parkThread.getState());

        log("main -> unpark(parkThread)");
        LockSupport.unpark(parkThread); // Lock에서 제공하는 자체 락으로 깨우기
        //parkThread.interrupt(); // 인터럽트로 꺠우기, park 상태에서 깨어나면 인터럽트 상태가 true로 변경
        /**
         * syncronized 와 달리 LockSupport.unpark() 와 Thread.interupt()를 통해 스레드를 깨울 수 있다.
         * 락에 대한 세밀한 제어가 가능하기에 훨씬 안전하고 다양한 상황에 적용할 수 있다.
         */
    }

    static class ParkTask implements Runnable {

        @Override
        public void run() {
            System.out.println("park 시작");
            LockSupport.park();
            System.out.println("park 종료, state : " + Thread.currentThread().getState());
            System.out.println("인터럽트 state : " + Thread.currentThread().isInterrupted());
        }
    }


}
