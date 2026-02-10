package project.learning.java.멀티스레드_동시성.경쟁상태.poolsize;

import project.learning.java.멀티스레드_동시성.경쟁상태.excutor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.excutor.ExecutorUtils.printState;

public class PoolSizeMain2 {
    public static void main(String[] args) {
        /** 메서드 구현체
            return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
         */
        ExecutorService es = Executors.newCachedThreadPool();


        /**
         * 캐시 풀 전략)
         * 요청이 들어오는 즉시 스레드를 생성하는 방법으로 요청마다 실시간으로 처리할 수 있어 처리량이 뛰어나다. 반면에 요청마다 스레드가 없다면 새롭게
         * 스레드를 생성하기에 기하급수적으로 요청량이 증가한다면 무한한 CPU, 메모리 자원을 사용해야 하기에 안정성이 부족하다.(서버 다운, 장애 등)
         *
         * 위 구현체와 같이 생성된 스레드는 60초 동안 생명주기를 유지할 수 있다. 스레드 풀은 일반적으로 버퍼(스레드 대기 큐)를 활용하지만 SynchronousQueue 는 사용하지 않는다.
         * 요청이 있어도 버퍼에 들어가지 않고 바로 처리하려고 하는 특징이 있기에 자원 소모량이 크다. 해당 예시를 동작하면 큐에 스레드가 대기하지 않고 요청 수만큼 스레드가 생성해서
         * 처리하는 것을 볼 수 있다.
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
