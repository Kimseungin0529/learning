package project.learning.java.멀티스레드_동시성.경쟁상태.lock.bounded;

import java.util.ArrayDeque;
import java.util.Deque;

import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MyLogger.log;

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
     * 현재 구조에는 다음과 같은 문제가 있다. 생산자 입장에서 지속적으로 생산하는 데 버퍼(대기 큐)가 가득 찼다면 어떻게 될까? 생산해야 할 정보를 버리게 된다.
     * 반대로 소비자 입장에서 지속적으로 소비하는 데 버퍼(대기 큐)가 비어 있다면 어떻게 될까? 소비자는 아무것도 소비하지 못한다.
     * 결국, 특정 요청에 대해 정상적인 반환을 하지 못하는 문제가 있다. (생산을 원하는데 생산 실패, 소비하려 했는데 소비 실패)
     * 모든 생산과 소비에 대한 처리를 성공하려면 어떻게 할까? 1차적으로 기다리면 된다. 큐가 꽉 찼다면 비워질 때까지 기다리고, 큐가 비어 있다면 채워질 때까지 기다리는 것이다.
     * 그렇게 하면 모든 생산과 소비가 정상적으로 처리될 수 있다.
     * 단순하게 sleep, yield 등과 같이 기다리게 하면 된다. V2에서 처리해보자.
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

