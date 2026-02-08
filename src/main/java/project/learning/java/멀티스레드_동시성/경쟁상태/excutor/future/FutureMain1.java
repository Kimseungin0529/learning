package project.learning.java.멀티스레드_동시성.경쟁상태.excutor.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MySleep.sleep;

public class FutureMain1 {

    /**
     * Runnable -> Callable 로 변경했다.
     * 멀티스레드를 사용하는 main 메서드에서는 Thread 관련 코드가 없어져 단일 스레드 실행 환경과 같이 간단해졌다.
     * 또한 Callable 은 반환값을 제공하기에 추가적인 필드를 생성하고 사용할 필요가 없어 더욱 단순하다.
     *
     * 이전에는 스레드의 실행 시간으로 join()을 통해 블로킹 처리를 했지만 여기서는 이러한 과정이 없다. 만약 main 메서드에서 외부 스레드의 작업이
     * 끝나지 않았다면 log("result value = " + result); 의 실행은 어떻게 될까?
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Integer result = es.submit(new MyCallable()).get();
        log("result value = " + result);

    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() {
            log("Runnable 시작");
            sleep(2000);
            int value = new Random().nextInt(10);
            log("create value = " + value);
            log("Runnable 완료");
            return value;
        }
    }
}
