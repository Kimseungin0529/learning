package project.learning.java.멀티스레드_동시성.경쟁상태;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadModifyWrite_Atomic {

    public static void main(String[] args) throws InterruptedException {
        Add add = new Add();

        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            executor.submit(add::process);
        }
        executor.shutdown();
        // 모든 작업 완료 대기
        while (!executor.isTerminated()) {
            Thread.sleep(10);
        }

        add.printResult();

    }

    static class Add{
        int number = 0;

        synchronized void process(){
            number++;
        }

        void printResult(){
            System.out.println(number);
        }
    }
}
