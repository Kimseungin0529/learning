package project.learning.java.멀티스레드_동시성.경쟁상태;

import project.learning.java.멀티스레드_동시성.경쟁상태.lock.BankAccount;

public class WithdrawTask implements Runnable {
    private BankAccount account;
    private int amount;

    public WithdrawTask(BankAccount account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            account.withdraw(amount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}