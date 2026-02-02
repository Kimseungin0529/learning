package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.적용;

import static project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.적용.SyncTest2BadMain.MyCounter.MyLogger.log;

public class SyncTest2BadMain {

    public static void main(String[] args) throws InterruptedException {
        MyCounter myCounter = new MyCounter();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                myCounter.count();
            }
        };
        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();
    }

    static class MyCounter {
        public void count() {
            int localValue = 0;
            for (int i = 0; i < 1000; i++) {
                localValue = localValue + 1;
            }
            log("결과: " + localValue);
        }

        static class MyLogger {
            static void log(String message) {
                System.out.println("[ " + Thread.currentThread().getName() + " ] " + message);
            }
        }
    }

}
