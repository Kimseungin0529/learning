package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1;

import project.learning.java.멀티스레드_동시성.경쟁상태.WithdrawTask;

import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.BankMain.MyLogger.log;


public class BankMain {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccountV(1000);

        Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
        Thread t2 = new Thread(new WithdrawTask(account, 800), "t2");
        t1.start();
        t2.start();

        Thread.sleep(500); // 검증 완료까지 잠시 대기
        log("t1 state: " + t1.getState());
        log("t2 state: " + t2.getState());

        t1.join();
        t2.join();

        log("최종 잔액: " + account.getBalance());
    }

    static class MyLogger {
        static void log(String message) {
            System.out.println("[ " + Thread.currentThread().getName() + " ] " + message);
        }
    }

}
