package project.learning.java.멀티스레드_동시성.경쟁상태.lock.lock;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;
import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MyLogger.log;

public class LockSupportMainV2 {
    /**
     * LockSupport park() 에 이어 parkNanos() 메서드는 일정 나노 초 동안 스레드를 일시 정지시키는 메서드이다.
     * 아래와 같은 시간 설정으로 스레드를 일시 정지(TIME_WAITING)시킬 수 있다. 설정한 시간이 지나면 락이 해제되며 스레드가 수행된다.
     * parkThread.getState()를 비교해보면 잠금 시간 동안은 TIME_WAITING 상태, 잠금 해제 후에는 TERMINATED 상태로 변경(스레드 종료, 진행중이라면 RUNNABLE)되는 것을 확인할 수 있다.
     * <p>
     * 다만 LockSupport는 락 기능을 구현하려면 syncronized와 달리 직접 구현해야 한다는 단점이 있다.
     * <p>
     * if (!lock.tryLock(10초)) { // 내부에서 parkNanos() 사용 log("[진입 실패] 너무 오래 대기했습니다.");
     * return false;
     * }
     * //임계 영역 시작
     * -- 구현 코드 --
     * //임계 영역 종료
     * <p>
     * lock.unlock() // 내부에서 unpark() 사용
     * <p>
     * 위 코드처럼 구현 기능과 달리 매번 임계 영역과 락 핸들링 처리를 해줘야 한다. 만약 스레드가 30가 동시에 접근한다면
     * 오직 1개 스레드만 접근할 수 있어야 하고 나머지 29개 스레드는 대기해야 한다. 그렇다면 대기해야 하는 스레드를
     * 관리할 자료 구조와 락 해제 시점에 대기 중인 스레드를 깨우는(unpark) 기능을 직접 구현해야 한다.
     * 따라서 구현할때는 LockSupport 보다는 concurrent 패키지에서 제공하는 ReentrantLock(Lock 인터페이스의 구현체) 을 사용하는 것이 좋다.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread parkThread = new Thread(new ParkNanoTask());
        parkThread.start();

        sleep(1000);
        log("parkThread 상태 : " + parkThread.getState());

        sleep(2000);
        log("parkThread 상태 : " + parkThread.getState());

    }

    static class ParkNanoTask implements Runnable {

        @Override
        public void run() {
            System.out.println("park 시작, 2초 대기");
            LockSupport.parkNanos(2_000_000_000); // 2초 동안 대기
            System.out.println("park 종료, state : " + Thread.currentThread().getState());
            System.out.println("스레드 state : " + Thread.currentThread().getState());
            System.out.println("인터럽트 state : " + Thread.currentThread().isInterrupted());
        }
    }
}
