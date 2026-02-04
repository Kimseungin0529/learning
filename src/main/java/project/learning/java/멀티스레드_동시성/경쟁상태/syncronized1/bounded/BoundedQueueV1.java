package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.bounded;

import java.util.ArrayDeque;
import java.util.Deque;

import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common.MyLogger.log;

public class BoundedQueueV1 implements BoundedQueue {
    private Deque<String> queue = new ArrayDeque<>();
    private int max;


    public BoundedQueueV1(int max) {
        this.max = max;
    }

    /**
     * 멀티스레드 생산자-소비자 문제를 학인하기 위한 예시 코드이다.
     * 생산자 <-> 버퍼링(대기큐) <-> 소비자 구조에서 버퍼링 역할을 하는 큐를 구현한 것이다.
     * 동시성 문제가 발생할 수 있기에 메서드에 synchronized 키워드를 사용하여 동기화 처리를 하였다. 예시이기에 toString 메서드에는 동시성 처리 X
     *
     */
    @Override
    public synchronized void put(String data) {
        if (queue.size() == max) {
            log("[put] 큐가 가득 참, 버림: " + data);
            return;
        }
        queue.offer(data);
    }

    @Override
    public synchronized String take() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}

