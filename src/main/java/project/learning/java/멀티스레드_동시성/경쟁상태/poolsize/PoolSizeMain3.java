package project.learning.java.멀티스레드_동시성.경쟁상태.poolsize;

import project.learning.java.멀티스레드_동시성.경쟁상태.excutor.RunnableTask;

import java.util.concurrent.*;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.excutor.ExecutorUtils.printState;

public class PoolSizeMain3 {
    // static final int TASK_SIZE = 1100; // 1. 일반

    /**
     * 1100개 요청이 순간적으로 들어오면 어떻게 될까?
     * 스레드 풀의 스레드 개수는 100개이기에 100번의 요청은 100개의 스레드가 바로 동작할 것이다. 이후 남은 1000개 요청은 비즈니스 로직(sleep 1초) 수행되기에
     * 대기 큐에서 기다릴 것이다. 대기큐의 최대 크기(1000)을 넘지 않았기에 추가적인 스레드는 생성되지 않고 기본 스레드 100개로만 처리된다.
     */

     //static final int TASK_SIZE = 1200; // 2. 긴급

    /**
     * 1200개 요청인 경우는 다음과 같다. 기본 스레드 개수(100) + 대기 큐에 들어간 요청 수(1000)개 = 1100 개
     * 즉, 대기 큐 용량을 넘어섰기에 최대 스레드 개수까지 스레드가 추가로 생성되고 대기 큐에 진입 없이 바로 처리된다.
     * 최대 스레드 개수는 200개 이므로 추가적으로 100개가 생성된다. 즉, 스레드 200개가 200개 요청 + 대기큐 용량 1000이 1000개 요청을 처리 및 저장
     * 하기에 버려지는 요청이 없다. 참고로 스레드가 생성되며 총 스레드는 200개이므로 처리 시간은 6초로 위 방법인 12초보다 6초 단축됐다.
     */
     static final int TASK_SIZE = 1201; // 3. 거절

    /**
     * 1200개가 넘어가는 순간부터는 요청이 거절되는 순간이 발생한다. 일반적으로 스레드 풀의 대기 큐의 용량을 넘어서면 RejectedExecutionException 가 발생한다.
     * 따라서 RejectedExecutionException 를 catch 통해 처리했고 로그에도 해당 정보가 나타난다. 즉, 마지막 1201번째 요청만 거절되고 2번째 방법과 똑같이 수행됐다.
     */


    public static void main(String[] args) throws InterruptedException {
        /** 사용자 정의 풀 전략)
         * 고정 풀 전략, 캐시 풀 전략을 보면 안정성 vs 성능 에 대한 양날의 검이다. 각각의 이점만 챙길 수 있다면 훨씬 합리적인 선택이다.
         * 직적 풀 전략을 설정하여 이를 챙겨보자.
         */
        ExecutorService es = new ThreadPoolExecutor(100, 200, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        printState(es);
        long startMs = System.currentTimeMillis();
        for (int i = 1; i <= TASK_SIZE; i++) {
            String taskName = "task" + i;
            try {
                es.execute(new RunnableTask(taskName));
                printState(es, taskName);
            } catch (RejectedExecutionException e) {
                log(taskName + " -> " + e);
            }
        }
        /**
         * 스레드 풀도 결국 요청을 수행하는 데 한계가 있다. 서버 자원은 한정적이기에 일정 수준에서는 요청을 대기시키거나 거절해야 한다. 스레드 풀은 이를 위해
         * 각각에 대한 예외 정책이 있다. 기본으로 RejectedExecutionException 를 발생(AbortPolicy)시키지만 사용자 정의 혹은 초과 요청 무시(DiscardPolicy),
         * 호출한 스레드가 직접 처리하기(CallerRunsPolicy)와 같은 방법이 있다. 상황에 맞게 사용하면 된다.
         */
        es.shutdown();
        long endMs = System.currentTimeMillis();
        log("time: " + (endMs - startMs));
    }
}
