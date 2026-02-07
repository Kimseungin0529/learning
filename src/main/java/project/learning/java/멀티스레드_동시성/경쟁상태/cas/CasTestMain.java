package project.learning.java.멀티스레드_동시성.경쟁상태.cas;

import java.util.ArrayList;
import java.util.List;

import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MySleep.sleep;

public class CasTestMain {

    public static void main(String[] args) throws InterruptedException {
        test(new BasicIncrementInt());
        test(new VolatileIncrementInt());
        test(new SyncronizedIncrementInt());
        test(new AtomicIncrementInt());
    }


    public static void test(IncrementInt incrementInt) throws InterruptedException {
        System.out.println("Testing " + incrementInt.getClass().getSimpleName());
        long start = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new IntegerThead(incrementInt));
            threads.add(thread);
            thread.start();
        }

        // main 스레드에서 모든 작업 스레드가 종료된 결과를 보기 위해 join() 호출
        for (Thread thread : threads) {
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("total time: " + incrementInt.get() + ", " + (end - start) + "ms");
    }

    public static void timeTest(IncrementInt incrementInt) throws InterruptedException {
        System.out.println("Testing " + incrementInt.getClass().getSimpleName());
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new IntegerThead(incrementInt));
            threads.add(thread);
            thread.start();
        }

        // main 스레드에서 모든 작업 스레드가 종료된 결과를 보기 위해 join() 호출
        for (Thread thread : threads) {
            thread.join();
        }

    }

    static class IntegerThead implements Runnable {
        private final IncrementInt incrementInt;

        public IntegerThead(IncrementInt incrementInt) {
            this.incrementInt = incrementInt;
        }

        @Override
        public void run() {
            sleep(10);
            incrementInt.increment();
        }
    }

}
