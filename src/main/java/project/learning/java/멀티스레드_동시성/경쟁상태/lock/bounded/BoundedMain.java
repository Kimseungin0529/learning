package project.learning.java.멀티스레드_동시성.경쟁상태.lock.bounded;

import java.util.ArrayList;
import java.util.List;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MySleep.sleep;

/**
 * 멀티스레드 생산자-소비자 문제를 확인하기 위한 예제
 *
 *
 */
public class BoundedMain {
    public static void main(String[] args) {
        //BoundedQueue queue = new BoundedQueueV1(2);
        //BoundedQueue queue = new BoundedQueueV2(2);
        //BoundedQueue queue = new BoundedQueueV3(2);
        //BoundedQueue queue = new BoundedQueueV4(2);
        //BoundedQueue queue = new BoundedQueueV5(2);
        BoundedQueue queue = new BoundedQueueV6(2);

        /**
         * 생산자 먼저 실행 또는 소비자 먼저 실행 하나만 실행해야 한다.
         */
        //producerFirst(queue);
        consumerFirst(queue);

    }

    private static void producerFirst(BoundedQueue queue) {
        log("== [생산자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllState(queue, threads);
        startConsumer(queue, threads);
        printAllState(queue, threads);
        log("== [생산자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void consumerFirst(BoundedQueue queue) {
        log("== [소비자 먼저 실행] 시작, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        log("== [소비자 먼저 실행] 종료, " + queue.getClass().getSimpleName() + " ==");
    }


    private static void printAllState(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("현재 상태 출력, 큐 데이터: " + queue);
        for (Thread thread : threads) {
            log(thread.getName() + ": " + thread.getState());
        }
    }

    private static void startProducer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("-- 생산자 스레드 시작 --");

        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data-" + i), "Producer-" + i);
            threads.add(producer);
            producer.start();
            sleep(100); // 로그를 보기 좋게 하기 위해 약간의 지연 추가
        }
    }

    private static void startConsumer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("소비자 시작");
        for (int i = 1; i <= 3; i++) {
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer" + i);
            threads.add(consumer);
            consumer.start();
            sleep(100);
        }
    }
}