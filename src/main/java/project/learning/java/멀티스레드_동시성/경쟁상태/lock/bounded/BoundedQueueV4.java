package project.learning.java.멀티스레드_동시성.경쟁상태.lock.bounded;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;

public class BoundedQueueV4 implements BoundedQueue {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Deque<String> queue = new ArrayDeque<>();
    private int max;


    public BoundedQueueV4(int max) {
        this.max = max;
    }

    /**
     * 이전에 synchronized 키워드를 사용하여 임계 영역을 설정하고, 내부에서 sleep() 등을 사용하여 대기하는 구조였다.
     * 무한 대기와 공정성 해결을 위해 Lock, ReentrantLock, Condition 등을 사용하여 개선할 수 있다고 했다.
     * synchronized wait(), notify() 를 사용하여 무한 대기를 해결한 경우에도 생성자 스레드, 사용자 스레드는
     * 하나의 임계 구역에 있는 대기 큐에서 관리되기에 비효율 문제(큐에 데이터가 없다면 생성자 스레드가 동작해야 하는데 소비자 스레드 동작,
     * 큐에 데이터가 있다면 소비자 스레드가 동작해야 하는데 생성자 스레드 동작)가 발생할 수 있다.
     * -> synchronized 임계 영역 오직 하나의 대기 큐만 존재하여 생성자 스레드, 소비자 스레드를 나눌 수 없다.
     * <p>
     * 이를 Lock, ReentrantLock를 통해 비효율 문제를 해결할 수 있다. Condition 은 모니터 락, 임계 영역에 있는 대기 큐와
     * 유사한 역할을 한다. 다만 Condition 은 여러 개를 생성할 수 있기에 생성자 스레드용, 소비자 스레드용 Condition 을
     * 각각 생성하여 사용할 수 있다. 이를 통해 생성자 스레드와 소비자 스레드가 각각 자신의 Condition 대기 큐에서 대기하도록 할 수 있다.
     * V4에서는 ReentrantLock 로만 변환하는 과정을 거치고 V5에서 Condition 을 사용하여 비효율 문제를 해결하도록 하겠다.
     */


    /**
     * await(), signal()
     * 모니터 락에는 Object 의 wait(), notify() 와 notifyAll() 가 있듯
     * Condition 에는 await(), signal() 와 signalAll() 이 있다. 모니터 락, ReentrantLock 자체 락인지 다를 뿐 기능은 유사하다.
     */
    @Override
    public void put(String data) {
        lock.lock();
        try {
            while (queue.size() == max) {
                log("[put] 큐가 가득 참, 생산자 대기: " + data);
                try {
                    condition.await();
                    log("[put] 생산자 깨어남, " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.offer(data);
            condition.signal();
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
                    condition.await();
                    log("[take] 소비자 깨어남, " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            String data = queue.poll();
            condition.signal();
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

