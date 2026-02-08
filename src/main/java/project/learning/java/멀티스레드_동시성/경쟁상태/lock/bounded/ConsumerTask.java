package project.learning.java.멀티스레드_동시성.경쟁상태.lock.bounded;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;

/**
 * 소비자 작업을 수행하는 Runnable 구현체
 */
public class ConsumerTask implements Runnable{

    private BoundedQueue queue;

    public ConsumerTask(BoundedQueue queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        log("[소비 시도]  ? <- " + queue);
        String data = queue.take();
        log("[소비 완료] " + data + " <- " + queue);
    }
}
