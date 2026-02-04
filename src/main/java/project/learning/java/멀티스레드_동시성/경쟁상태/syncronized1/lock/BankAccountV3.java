package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.lock;

import project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.BankAccount;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common.MySleep.sleep;

public class BankAccountV3 implements BankAccount {
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccountV3(int balance) {
        this.balance = balance;
    }

    /**
     * Lock, ReentrantLock 를 사용하여 동기화 처리
     * Syncronized (this) 또는 메서드 선언부와 달리 명시적으로 lock, unlock 을 호출해야 한다.
     * 특히나 비즈니스 로직(출금 처리)에서 예외가 발생하더라도 해당 임계 영역에서 사용되는 공유 자원의 락을
     * 해제해야 하는 것은 필수다. 따라서 finally 블록에서 lock.unlock() 을 필수적으로 호출해야 한다.
     *
     */

    /**
     * 참고로 BankMain 클래스에서 해당 락 방식을 사용하면 동시성 문제가 해결되는 것을 확인할 수 있다. t1, t2 스레드가 동시에 출금 요청을 하더라도
     * T1 스레드(먼저 요청한)가 출금 처리를 완료한 후에 T2 스레드가 출금 처리를 진행한다. 따라서 잔액이 부족하여 출금이 실패하는 것을 확인할 수 있다.
     * 출력 결과를 보면
     * [ main ] t1 state: TIMED_WAITING
     * [ main ] t2 state: WAITING
     * 가 나오는데 현재 실제 잔액 계산 과정을 생각하여 sleep(1000) 으로 1초 지연을 주었기 때문이다. t1 스레드는 계산 과정(sleep)에서 TIMED_WAITING 상태가 되고,
     * t2 스레드는 t1 스레드가 락을 해제할 때까지 대기해야 하기에 WAITING 상태가 된다.(lock,lock()은 waiting 상태로 만든다. synchronized 와 동일)
     *
     */

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작: " + getClass().getSimpleName());

        lock.lock();
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
