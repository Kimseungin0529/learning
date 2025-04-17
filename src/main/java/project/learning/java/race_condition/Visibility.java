package project.learning.java.race_condition;

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
