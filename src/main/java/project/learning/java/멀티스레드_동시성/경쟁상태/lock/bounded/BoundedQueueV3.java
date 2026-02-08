package project.learning.java.멀티스레드_동시성.경쟁상태.lock.bounded;

import java.util.ArrayDeque;
import java.util.Deque;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;

public class BoundedQueueV3 implements BoundedQueue {
    private Deque<String> queue = new ArrayDeque<>();
    private int max;


    public BoundedQueueV3(int max) {
        this.max = max;
    }

    /**
     * 이전과 다르게 락을 반환하고 대기(waiting)하는 구조로 변경했다. wait() 는 현재 스레드가 가지고 있는 락을 반환하고 대기 상태로 전환한다.
     * notify() 는 대기 상태에 있는 스레드 중 하나를 깨운다. 따라서 생산자 입장에서는 wait()로 락을 반환하여 소비자가 큐에서 데이터를 소비할 수 있도록 하고 notify()로
     * 대기 상태인 소비자 스레드를 깨운다. 소비자 입장에서는 wait()로 락을 반환하여 생산자가 큐에 데이터를 넣을 수 있도록 하고 notify()로 대기 상태인 생산자 스레드를 깨운다.
     * wait()로 대기 상태에 빠진다면 임계 영역에 있는 대기 큐에 들어가게 된다. 따라서 해당 임계 영역에서 락을 반납하고 notify()로 깨운다면 대기 큐 내부에서 임의의 스레드가
     * 선택되어 락을 획득하게 된다. 이때, 바로 waiting -> runnable 상태로 전환되는 것이 아니다. 왜냐하면 임계 영역에서는 오직 하나의 스레드만 동작해야 안전하기에
     * 임계 영역에서 바로 나가 Blocked 처리가 되고 모니터 락을 획득하여 runnable 상태로 전환된다. 물론 이것도 스레드 경합에 따라 바로 되지 않을수도 있다.
     *
     * 이러한 방법으로 무한 대기 상태(선점) 상태를 방지할 수 있다. 다만 개선점이 남아 있다. 위에서 언급했듯 대기 큐에서 임의의 스레드가 선택되어 락을 획득하게 된다.
     * 만약 대기큐의 소비자 스레드 10개, 생산자 스레드 1개가 있는 상황이라면 생산자 스레드를 먼저 깨우는 것이 합리적이다. 자바 자체에서 대기 큐에서 오래 기다린 스레드를 깨워줄 확률이 높지만
     * 이를 보장하지 않기에 최악의 경우 생산자 스레드는 계속 대기 상태에 빠지며(기아 현상) CPU 자원을 낭비할 수 있다.
     * 이를 해결하기 위한 방법으로는 notifyAll() 을 사용하여 대기 상태에 있는 모든 스레드를 깨우는 방법이 있다. 다만 이 방법도 결국 모든 스레드를 다시 시작하기에 성능 저하가 발생할 수 있다.
     *
     * 이러한 문제점에 대한 개선은 V4에서 다루도록 하겠다.
     */
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

