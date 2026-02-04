package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.bounded;

import java.util.ArrayDeque;
import java.util.Deque;

import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common.MySleep.sleep;

public class BoundedQueueV3 implements BoundedQueue {
    private Deque<String> queue = new ArrayDeque<>();
    private int max;


    public BoundedQueueV3(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log( "[put] 큐가 가득 참, 생산자 대기: " + data);
            try {
                wait();
                log( "[put] 생산자 깨어남, " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.offer(data);
        notify();
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log( "[take] 큐에 데이터 없음, 소비자 대기");
            try {
                wait();
                log( "[take] 소비자 깨어남, " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        notify();
        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}

