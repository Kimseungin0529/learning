package project.learning.java.멀티스레드_동시성.경쟁상태;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckThenAct {

    static int count = 0;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 50; i++) {
            executor.submit(() -> {
                count++; // Act (먼저 행동)
                if (count < 30) { // Check (그 후 검사)
                    try {
                        Thread.sleep(1); // 처리 지연 시뮬레이션
                    } catch (InterruptedException ignored) {}
                    System.out.println("30까지 얼마 남지 않았습니다! Count = " + count);
                }
            });
        }

        executor.shutdown();
    }
}
