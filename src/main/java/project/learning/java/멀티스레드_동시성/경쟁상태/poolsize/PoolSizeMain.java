package project.learning.java.멀티스레드_동시성.경쟁상태.poolsize;

import org.apache.catalina.Executor;
import project.learning.java.멀티스레드_동시성.경쟁상태.excutor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.excutor.ExecutorUtils.printState;

public class PoolSizeMain {
    public static void main(String[] args) {
        /**
         *  return new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
         *  newFixedThreadPool 메소드 내부 구현체
         */
        ExecutorService es = Executors.newFixedThreadPool(2);


        /**
         * 고정 풀 전략)
         * 요청(생산자) 빈도와 관계없이 작업 스레드(소비자)의 수를 고정시키는 방법으로 CPU, 메모리 등 일정한 자원을 소모하기에 안정적이다.
         * 다만 존재하는 스레드보다 기하급수적인 요청이 들어오면 이를 처리할 수 없는 문제가 발생한다.(동시에 10만건이 몰린다면 10만번째 요청자는 거의 무한정 대기이다. [1개 요청 당 1초가 걸린다면?])
         */
        log("pool 생성");
        printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }
        es.shutdown();
        log("== shutdown 완료 ==");

    }
}
