package project.learning.java.멀티스레드_동시성.가시성_동시성_실습;

public class Visibility {

    static boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            while (running) {
                // do nothing
            }
            System.out.println("Thread 종료됨");
        });

        worker.start();

        Thread.sleep(1000); // 1초 후에 플래그 변경
        running = false;    // 종료 플래그 설정
        System.out.println("main 스레드 종료 플래그 설정");
    }
}
