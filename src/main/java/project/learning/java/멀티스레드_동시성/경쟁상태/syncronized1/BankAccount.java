package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1;


public interface BankAccount {
    boolean withdraw(int amount) throws InterruptedException;

    int getBalance();
}
