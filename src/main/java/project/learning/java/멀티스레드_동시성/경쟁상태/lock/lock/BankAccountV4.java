package project.learning.java.멀티스레드_동시성.경쟁상태.lock.lock;

import project.learning.java.멀티스레드_동시성.경쟁상태.lock.BankAccount;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MySleep.sleep;

public class BankAccountV4 implements BankAccount {
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccountV4(int balance) {
        this.balance = balance;
    }


    /**
     * ReentrantLock 의 tryLock() 메서드를 사용하여 동기화 처리
     * tryLock() 은 락을 획득할 수 있으면 true 를 반환, 그렇지 않으면 false 를 반환한다.
     * 다만 tryLock() 은 대기하지 않고 즉시 반환되기에 락을 획득하지 못한 스레드는 임계 영역에 진입하지 못한다.
     * 만약 대기해서 재시도 처리를 하려면 따로 구현해야 한다. BankMain 클래스에서 해당 방식을 사용하면
     * t1, t2 스레드가 동시에 출금 요청을 하더라도 락을 획득한 스레드만 출금 처리를 진행하고, 락을 획득하지 못한 스레드는 출금에 실패하는 것을 확인할 수 있다.
     * [ main ] t1 state: TIMED_WAITING
     * [ main ] t2 state: TERMINATED
     * 와 같이 실패한 스레드는 대기해서 진행되지 않기에 종료(TERMINATED) 상태가 된다.
     */

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작: " + getClass().getSimpleName());

        if (!lock.tryLock()) {
            log("[진입 실패] 이미 처리 중인 작업이 있습니다.");
            return false;
        }

        try {
            log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
                return false;
            }
            sleep(1000);
            balance = balance - amount;
            log("[출금 완료] 출금액: " + amount + ", 변경 잔액: " + balance);
        } finally {
            lock.unlock();
        }
        log("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
