package project.learning.java.멀티스레드_동시성.경쟁상태.excutor.future;

import java.util.concurrent.*;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;

public class SumTaskMain2 {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(2);

        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        Future<Integer> future1 = es.submit(task1);
        Future<Integer> future2 = es.submit(task2);

        /**
         * 사실 Future 은 Thread 를 직접 생성/관리하는 법 대신 스레드 풀 Executor 를 사용하기에 필요하다.
         * 이전에는 동시 처리(병렬처럼)르 위해 각 thread 를 start 하고 메인 메서드에서 결과값을 받아 처리하기 위해
         * join() 으로 블로킹했다. 이처럼 효율적인 사용을 위해 Future 을 도입했고 각 스레드를 실행하기 위해 submit 메서드를 전부 호출한 뒤,
         * Future.get() 을 사용하여 동시 처리하듯 효율성을 뽑아낸 것이다.
         */
        Integer sum1 = future1.get();
        Integer sum2 = future2.get();

        log("task1.result=" + sum1);
        log("task2.result=" + sum2);
        int sumAll = sum1 + sum2;
        log("task1 + task2 = " + sumAll);
        log("End");

        es.shutdown();

    }

    static class SumTask implements Callable<Integer> {
        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() {
            log("작업 시작");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }
            log("작업 완료 result=" + sum);
            return sum;
        }
    }
}
