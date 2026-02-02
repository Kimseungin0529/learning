package project.learning.java.멀티스레드_동시성.인터럽트;

import static java.lang.Thread.sleep;
import static project.learning.java.멀티스레드_동시성.인터럽트.ThreadStopMain1.MyLogger.log;

public class ThreadStopMain1 {
    /**
     * 지정한 시점에 다른 스레드의 상태를 변경하여 종료하기 위해서 인터럽트를 사용한다. 인터럽트 대신 boolean 플래그 변수를 사용한다면 sleep, wait, join 등의 블로킹 메서드에서 대기 중인 스레드를 깨울 수 없다.
     * work 스레드는 인터럽트 상태를 확인하여 작업을 중단하고 자원을 정리하는 코드를 실행한다.메인 스레드에서 work 스레드의 인터럽트 호출로 work 스레드 반복문을 탈출한다(Thread.currentThread().isInterrupted()))
     * 이후, 스레드가 사용하는 자원 정리 과정이 필요하다. 이 부분은 try 블록에 사용되고 예외 처리는 catch 블록에서 수행된다. 중요한 건 Thread.currentThread().isInterrupted()) 는 인터럽트 상태
     * 를 변경하지 않는다. 따라서 자원 정리 로직에서 sleep 메서드를 호출하면 InterruptedException이 발생한다. 우리는 work 스레드의 동작을 멈추고 정상 상태로 되돌리기 위해
     * 인터럽트를 호출했지만 인터럽트 상태가 변경되지 않아 자원 정리 도중에 InterruptedException이 발생한 것이다. 만약 DB 커넥션과 같이 자원이 정리되지 않으면 병복 현상이 발생할 수 있다.
     * 물론 자원 정리 과정이 필요 없는 상황(예: 단순한 메모리 해제)이라면 문제가 되지 않지만 자원 정리가 필요한 상황이라면 문제가 될 수 있다. 또는 자원 정리보다 종료가 우선이라면 괜찮다.
     * 따라서 인터럽트를 발생했으면 인터럽트 상태를 다시 설정해 주어야 한다.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();
        sleep(100); // 시간을 줄임

        log("작업 중단 지시 - thread.interrupt()");
        thread.interrupt();
        log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) { // 인터럽트 상태 변경 log("작업 중");
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
