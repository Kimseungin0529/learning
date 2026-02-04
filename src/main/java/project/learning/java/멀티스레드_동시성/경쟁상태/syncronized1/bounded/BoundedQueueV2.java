package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.bounded;

import java.util.ArrayDeque;
import java.util.Deque;

import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common.MySleep.sleep;

public class BoundedQueueV2 implements BoundedQueue {
    private Deque<String> queue = new ArrayDeque<>();
    private int max;


    public BoundedQueueV2(int max) {
        this.max = max;
    }

    /**
     * 멀티스레드 생산자-소비자 문제를 학인하기 위한 예시 코드이다.
     * V2 에서는 생산자-소비자 문제에서 발생할 수 있는 문제를 해결하기 위해 생산자 입장에서 큐가 가득 차 있다면 큐 소비가 일어날 때까지 기다리고 소비자 입장에서 큐가 비어 있다면 큐 생산이
     * 일이날 때까지 대기하기 위해 while 루프와 sleep 메서드를 사용하여 대기하도록 구현하였다. 과도한 로그 출력을 방지하기 위해 sleep(1000) 으로 1초 대기 후 다시 상태를 확인하도록 하였다.
     *
     * producerFirst(queue)가 실행했을 때 로그를 순차적으로 보면서 확인해보자.
     *
     14:41:27.257 [Producer-1] [생산 시도] data-1 -> []
     14:41:27.257 [Producer-1] [생산 완료] data-1 -> [data-1]
     14:41:27.355 [Producer-2] [생산 시도] data-2 -> [data-1]
     14:41:27.356 [Producer-2] [생산 완료] data-2 -> [data-1, data-2]
     14:41:27.456 [Producer-3] [생산 시도] data-3 -> [data-1, data-2]
     14:41:27.457 [Producer-3] [put] 큐가 가득 참, 생산자 대기: data-3

     14:41:27.557 [     main] 현재 상태 출력, 큐 데이터: [data-1, data-2]
     14:41:27.558 [     main] Producer-1: TERMINATED
     14:41:27.558 [     main] Producer-2: TERMINATED
     14:41:27.558 [     main] Producer-3: TIMED_WAITING
     큐 최대치를 2로 설정하여 3번째 생산에는 생산 대기에 의해 TIMED_WAITING(sleep(1000)) 상태가 된 것을 확인할 수 있다.
     이제 소비자 스레드의 동작으로 큐의 소비가 일어나고 큐의 생산이 될 것으로 예상된다. 하지만

     14:41:27.558 [     main] 소비자 시작
     14:41:27.559 [consumer1] [소비 시도]  ? <- [data-1, data-2]
     14:41:27.660 [consumer2] [소비 시도]  ? <- [data-1, data-2]
     14:41:27.761 [consumer3] [소비 시도]  ? <- [data-1, data-2]
     // 소비 시도는 일어나지만 실제 큐에서는 소비가 되지 않는다.
     14:41:27.862 [     main] 현재 상태 출력, 큐 데이터: [data-1, data-2]
     14:41:27.863 [     main] Producer-1: TERMINATED
     14:41:27.863 [     main] Producer-2: TERMINATED
     14:41:27.863 [     main] Producer-3: TIMED_WAITING
     14:41:27.863 [     main] consumer1: BLOCKED
     14:41:27.863 [     main] consumer2: BLOCKED
     14:41:27.864 [     main] consumer3: BLOCKED
     14:41:27.864 [     main] == [생산자 먼저 실행] 종료, BoundedQueueV2 ==
     소비자 스레드는 BLOCKED 처리가 된 상태이다. 심지어 이후 로그는 아래 로그가 반복되며 프로그램이 종료되지 않는다.

     14:41:28.458 [Producer-3] [put] 큐가 가득 참, 생산자 대기: data-3
     14:41:29.460 [Producer-3] [put] 큐가 가득 참, 생산자 대기: data-3
     ...
     왜 그럴까? 바로 synchronized 키워드에 의한 락(Lock) 때문이다. synchronized 는 동시성을 확실히 보장해주고 간단하다. 다만 무기한 대기와 공정성 문제가 있었따. 특히나 인터럽트로 인한 해제도
     불가능하기에 자체적으로 락을 해제할 수 없다. 결과적으로 임계 구역에 들어간 로직이 완료되어야만 종료된다. 현재 구조에서는 생산자가 큐가 가득 찼을 때 대기 상태에 들어가면 락이
     해제되지 않기에 소비자가 큐에서 데이터에 접근할 수 없어 BLOCKED 상태가 되어 무한정 대기하게 된 것이다. 즉, 데드락(교착 상태, Dead Lock) 상황이 발생한 것이다.
     consumerFirst(queue) 로 실행했을 때도 동일한 문제가 발생한다.  큐가 비어 있을 때 소비자가 대기 상태에 들어가면 락이 해제되지 않기에 생산자가 큐에 접근할 수 없어 무한정 대기하게 된다.

     마치 열쇠(공유 자원)를 가지고 들어가 방(임계 영역)을 잠그고 누군가 방에 들어와 열쇠를 가져가길 기다리는 꼴이다. 그렇다면 기다리더라도 임계 영역 밖에서 기다리면 어떨까?
     그렇다면 밖에 기다리는 스레드가 임계 영역에 들어갈 수 있는 기회가 생기게 된다. 현재 상황에서는 V3에서 wait(), notify() 메서드를 사용하여 이 문제를 해결해보자.

     */
    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log( "[put] 큐가 가득 참, 생산자 대기: " + data);
            sleep(1000);
        }
        queue.offer(data);
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log( "[take] 큐에 데이터 없음, 소비자 대기");
            sleep(1000);
        }
        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}

