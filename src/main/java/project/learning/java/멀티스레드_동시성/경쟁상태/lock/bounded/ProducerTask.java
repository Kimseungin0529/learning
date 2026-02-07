package project.learning.java.멀티스레드_동시성.경쟁상태.lock.bounded;

import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MyLogger.log;

/**
 * 생산자 작업을 수행하는 Runnable 구현체
 */
public class ProducerTask implements Runnable{

    private BoundedQueue queue;
    private String request;

    public ProducerTask(BoundedQueue queue, String request) {
        this.queue = queue;
        this.request = request;
    }


    @Override
    public void run() {
        log("[생산 시도] " + request + " -> " + queue);
        queue.put(request);
        log("[생산 완료] " + request + " -> " + queue);
    }
}
