package project.learning.java.멀티스레드_동시성.경쟁상태.cas.spinlock;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MySleep.sleep;

public class SpinLockBad {

    private volatile boolean lock = false;

    public void lock() {
        while (true) {
            if (!lock) {
                sleep(100);
                log("락 획득");
                lock = true;
                return;
            } else {
                log("락 획득 실패, 스핀 대기 중...");
            }
        }
    }

    public void unlock() {
        lock = false;
        log("락 해제");
    }

}
