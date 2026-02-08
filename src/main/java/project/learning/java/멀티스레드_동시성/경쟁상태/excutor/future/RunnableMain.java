package project.learning.java.멀티스레드_동시성.경쟁상태.excutor.future;

import java.util.Random;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MySleep.sleep;

public class RunnableMain {
    /**
     * Runnable 의 불편함)
     * 아래 예시를 보면 임의로 0~9 중 하나의 정수를 가져오는 로직이다. 멀티 스레드 환경에서 단수히 하나의 값을 가져오는 데 많은 코드가 필요하다.
     * start()로 스레드를 실행시키면서 join()을 통해 다른 스레드의 동작이 끝날때까지 기다리는 것도 호출해야하고 직접 스레드의 내부 필드에 접근해서
     * 값을 반환해야 한다. 이러한 긴 코드, 불편함을 줄여주기 위해 Runnable 대신 Future 을 사용하면 된다.
     */

    public static void main(String[] args) throws InterruptedException {
        MyRunnable task = new MyRunnable();
        Thread thread = new Thread(task, "Thread-1");
        thread.start();
        thread.join();
        int result = task.value;
        log("result value = " + result);
    }

    static class MyRunnable implements Runnable {
        int value;

        @Override
        public void run() {
            log("Runnable 시작");
            sleep(2000);
            value = new Random().nextInt(10);
            log("create value = " + value);
            log("Runnable 완료");
        }
    }
}
