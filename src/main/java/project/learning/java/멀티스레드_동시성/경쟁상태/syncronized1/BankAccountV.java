package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1;


import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.BankMain.MyLogger.log;

public class BankAccountV implements BankAccount {
    private int balance;

    public BankAccountV(int initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * synchronized 를 사용하면 동시성 문제를 처리할 수 있다. 아래와 같이 여러 스레드가 동시에 계좌 잔액에 접근하여 값을 변경할 때는
     * 메모리 가시성이 해결되도 데이터 일관성에 문제가 발생한다. 왜냐하면 한 스레드가 잔액을 읽고 검증하는 사이에 다른 스레드가 잔액을 변경할 수 있기 때문이다.
     * <p>
     * synchronized 는 메서드, 블록 구문으로 사용 가능하다. 참고로 synchronized 키워드는 객체 단위로 락을 걸기 때문에 동일 객체에 대해서만 동기화가 적용된다.
     * 예를 들어 BankAccountV 객체가 2개가 있다면 각각의 객체에 대해서는 동기화가 적용되지 않는다.
     * <p>
     * 추가로 synchronized 블록 구문을 사용하면 동기화 범위를 좁힐 수 있다. 동기화 범위가 좁을수록 성능이 향상될 수 있다.
     * 1000개 스레드가 해당 공유 자원에 접근하는 경우, 1개씩 처리할 수 밖에 없기에 동기화 범위를 최소화해야 한다.
     * 아래와 같이 로그 출력 등 공유 자원 접근에 문제가 없다면 메서드 대신 블록 처리하여 최소화할 수 있다.
     */
    @Override
    public boolean withdraw(int amount) {
        log("거래 시작: " + getClass().getSimpleName());

        synchronized (this) {
            log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
                return false;
            }

            // 잔고가 출금액 보다 많으면, 진행
            log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
            //sleep(1000); // 출금에 걸리는 시간으로 가정
            balance = balance - amount;
            log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);
        }

        log("거래 종료");
        return true;
    }

    @Override
    public synchronized int getBalance() {
        return balance;
    }
}
