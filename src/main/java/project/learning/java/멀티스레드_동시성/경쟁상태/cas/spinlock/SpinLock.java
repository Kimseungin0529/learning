package project.learning.java.멀티스레드_동시성.경쟁상태.cas.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;

public class SpinLock {

    private AtomicBoolean lock = new AtomicBoolean(false);

    public void lock() {
        while (!lock.compareAndSet(false, true)) {
            log("락 획득 실패, 스핀 대기 중...");
        }
        log("락 획득");
    }

    public void unlock() {
        lock.set(false);
        log("락 해제");
    }

}
