package project.learning.java.멀티스레드_동시성.경쟁상태.excutor.future;

import java.util.Random;
import java.util.concurrent.*;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MySleep.sleep;

public class FutureMain2 {
    /**
     * Future 을 사용하면 코드를 간단하게 줄일 수 있었다.  future.get();를 하는 시점에 메인 메서드 스레드는 블로킹 처리되어 MyCallable 객체를 실행하는
     * 스레드 종료를 기다린다. 이처럼 코드를 통해 외부 스레드의 결과를 받느 시점을 지정할 수 있다.
     *
     * 개인적으로는 DB 조회, 추가 연산 등 다른 작업을 메인 스레드에서 바로 작업할 수 있는 점을 통해 각각 스레드가 병렬처럼 동시에 동작하게 끔 하여
     * 작업 시간을 나눌 수 있다는 이점이 있어 이렇게 구현한 것으로 보인다.
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("submit() 호출");
        Future<Integer> future = es.submit(new MyCallable());
        log("future 즉시 반환, future = " + future);

        log("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 WAITING");
        Integer result = future.get();
        log("future.get() [블로킹] 메서드 호출 완료 -> , main 스레드 RUNNABLE");

        log("result value = " + result);
        log("future 완료, future = " + future);
        es.shutdown();
    }

    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() {
            log("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(10);
            log("create value = " + value);
            log("Callable 완료");
            return value;
        }
    }
}
