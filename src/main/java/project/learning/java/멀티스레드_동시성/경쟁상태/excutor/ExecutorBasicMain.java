package project.learning.java.멀티스레드_동시성.경쟁상태.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MySleep.sleep;
import static project.learning.java.멀티스레드_동시성.경쟁상태.excutor.ExecutorUtils.printState;

public class ExecutorBasicMain {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        log("== 초기 상태 ==");
        printState(es);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("taskD"));
        log("== 작업 수행 중 ==");
        printState(es);
        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(es);
        es.shutdown();
        log("== shutdown 완료 ==");
        printState(es);
        /**
         * ExecutorService 의 기본 구현체는 ThreadPoolExecutor 이다. 멀티 스레드 환경에서 주로 사용되는 스레드 풀이다.
         * 기본적으로 기본 스레드 개수, 최대 스레드 개수, 기본 스레드 개수가 넘어갔을떄, 유지할 수 있는 시간, 블로킹 큐(생산자-소비자 문제) 구현체
         * 를 지정할 수 있다.
         */
    }
}
