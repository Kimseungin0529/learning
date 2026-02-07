package project.learning.java.멀티스레드_동시성.경쟁상태.lock.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BoundedQueueV6 implements BoundedQueue {

    private BlockingQueue<String> queue;


    public BoundedQueueV6(int max) {
        this.queue = new ArrayBlockingQueue<>(max);
    }

    /**
     * BlockingQueue 는 이전 버전에서 학습했던 모든 문제를 이미 라이브러리 구현한 인터페이스이다.
     * array, deque linkedList 등을 사용하여 다양한 BlockingQueue 구현체가 존재한다. 내부적으로 락, condition 등을 사용하여 동시성 문제, 기아 현상, 무한 대기 현상 등을 모두 해결했다.
     * 따라서 BlockingQueue 를 사용하면 생산자-소비자 문제를 매우 간단하게 해결할 수 있다. 참고로 BlockingQueue 는 자바에서 제공하는 java.util.concurrent 패키지에 속해 있다.
     *
     * put(), offer(), offer(E e, long timeout, TimeUnit unit) / take(), poll(), poll(long timeout, TimeUnit unit) 와 같이 다양한 옵션이 존재한다.
     * 이전 ReentrantLock, Condition 등 사용하여 멀티스레드, 동시성 처리를 내부에서 최적화하여 구현했다. 상황에 맞는 메서드를 사용하기만 하면 된다.
     * BoundedMain 에서 점진적 발전을 확인하기 위해 BoundedQueue 인터페이스를 만들어 사용했지만 바로 BlockingQueue 를 사용해도 된다. 실제 프로젝라면
     * BlockingQueue 를 바로 사용하는 것이 중복 코드를 줄이고 명확하기에 더 낫다.
     */

    @Override
    public void put(String data) {
        try {
            queue.put(data);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}

