package project.learning.java.멀티스레드_동시성.인터럽트;

import static java.lang.Thread.sleep;
import static project.learning.java.멀티스레드_동시성.인터럽트.ThreadStopMain1.MyLogger.log;

public class ThreadStopMain2 {
    /**
     * ThreadStopMain1 에서는 인터럽트 상태가 변경되지 않아 자원 정리 도중에 InterruptedException이 발생했다.
     * 따라서 while 조건을 Thread.currentThread().isInterrupted()에서 Thread.interrupted()로 변경해야 한다.
     * 이렇게 한다면 인터럽트 상태를 변경하여 의도치 않은 인터럽트 예외를 발생시키지 않는다.
     * 이렇게 자원 정리에 대한 문제가 해결되었지만, 자원 사용의 비효율이 남아 있다. 바로 while 루프가 인터럽트 상태를 확인하기 위해 계속 실행된다.
     * CPU 자원을 낭비하는 셈이다. 따라서 인터럽트 상태를 확인하는 while 루프에 sleep(1)과 같은 짧은 대기 시간을 추가하여 CPU 자원 낭비를 줄이는 방법도 고려할 수 있다.
     * 혹은 스레드의 실행 순서를 양보하는 방법도 있다. 다음 챕터에서 확인해보자.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        log("작업 중단 지시 - thread.interrupt()");
        thread.interrupt();
        log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) { // 인터럽트 상태 변경 log("작업 중");
                log("작업 중");
            }
            log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted());

            try {
                log("자원 정리 시도");
                sleep(1000);
                log("자원 정리 완료");
            } catch (InterruptedException e) {
                log("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
                log("work 스레드 인터럽트 상태3 = " + Thread.currentThread().isInterrupted());
            }
            log("작업 종료");
        }
    }

    static class MyLogger {
        static void log(String message) {
            System.out.println("[ " + Thread.currentThread().getName() + " ] " + message);
        }
    }
}
