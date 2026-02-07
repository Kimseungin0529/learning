package project.learning.java.멀티스레드_동시성.경쟁상태.lock.bounded;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MyLogger.log;

public class BoundedQueueV5 implements BoundedQueue {
    private Lock lock = new ReentrantLock();
    private Condition producerCondition = lock.newCondition();
    private Condition consumerCondition = lock.newCondition();

    private Deque<String> queue = new ArrayDeque<>();
    private int max;


    public BoundedQueueV5(int max) {
        this.max = max;
    }

    /**
     * 생성자, 소비자 대기 큐를 위해 2개의 condition (대기 큐) 을 생성했다. 이제는 생성자 스레드가 완료되면 소비자 대기 큐에서 소비자
     * 스레드를 깨우고, 소비자 스레드가 완료되면 생성자 대기 큐에서 생성자 스레드를 깨우도록 했다.
     * V4와 다르게 큐가 비어있는 상태에서 소비자 대기 큐에 있는 소비자 스레드를 깨우지 않고 생성자 대기 큐에 있는 생성자 스레드를 깨우기에
     * 비효율 문제가 해결되었다. 반대에 상황도 마찬가지다. 로그를 확인해보면(확률적으로) 로그량이 줄었다.
     *
     * 참고로 모니터 락(syncronized)과 Lock(ReentrantLock)이 제공하는 매커니즘은 매우 휴사하다.
     * 모니터 락, Lock에도 Blocked, Waiting/Timed_Waiting을 관리할 대기큐가 존재하고
     * 임계 영역에 있는 대기큐(synchronized 의 wait(), notify() 와 notifyAll() / ReentrantLock 의 await(), signal() 와 signalAll())도 존재한다.
     * 심지어 CPU 입장에서 Blocked, Waiting/Timed_Waiting 상태도 동일하게 관리된다. 다만, Lock, ReentrantLock이 제공하는 매커니즘이 더 세분화되어 있고
     * 다양한 기능을 제공한다는 차이가 있다. 현재 V5를 보면 동시성도 확실히 처리할 수 있고 기아 현상, 무한 대기 현상도 해결할 수 있다. 라이브러리로 사용해도 괜찮아 보인다.
     * 이러한 기능은 사실 이미 자바에서 제공하는 java.util.concurrent 패키지에 이미 구현되어 있다.(예: BlockingQueue)
     *
     * 다만 돌아오는 과정에서 생산자-소비자 문제를 통해 멀티스레드에 대한 기본기를 기른 의의를 가질 수 있다. 내부 동작 원리를 알기에 멀티스레드 환경 속
     * 어떠한 대응해야 할지 알 수 있게 됐고 V6에서 자바에서 제공하는 BlockingQueue를 사용하여 동일한 예제를 구현해보겠다.
     */
    @Override
    public void put(String data) {
        lock.lock();
        try {
            while (queue.size() == max) {
                log("[put] 큐가 가득 참, 생산자 대기: " + data);
                try {
                    producerCondition.await();
                    log("[put] 생산자 깨어남, " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.offer(data);
            consumerCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                log("[take] 큐에 데이터 없음, 소비자 대기");
                try {
                    consumerCondition.await();
                    log("[take] 소비자 깨어남, " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            String data = queue.poll();
            producerCondition.signal();
            return data;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}

