package project.learning.java.멀티스레드_동시성.인터럽트;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static project.learning.java.멀티스레드_동시성.인터럽트.ThreadStopMain1.MyLogger.log;


public class MyPrinterV2 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();
        Scanner userInput = new Scanner(System.in); // 사용자 입력

        while (true) {
            System.out.println("프린터할 문서를 입력하세요. 종료 (q): ");
            String input = userInput.nextLine();
            if (input.equals("q")) {
                printerThread.interrupt();
                break;
            }
            printer.addJob(input);
        }
    }

    /**
     * 이전과 다른 점은 Thread.yield(); 메서드를 사용하여 CPU 자원 낭비를 줄였다는 점이다.
     * while 루프가 계속 실행되면서 인터럽트 상태를 확인하는 과정에서 jobQueue 안에 작업할 값이 존재한다면
     * 문제되지 않지만 없다면 불필요한 CPU 자원 낭비로 정작 필요한 스레드의 작업 효율이 저하된다. 이를 방지하기 위해 Thread.yield(); 메서드를 사용한다.
     * 이전에 sleep(1)과 같은 짧은 대기 시간을 추가하는 방법도 언급했지만 이 방법은 스레드의 실행이 잠시 멈추기 때문에 좋지 않다.
     * 또한 실행 작업 큐에서 대기큐(sleep(), wait() 등)로 이동하는 과정이 추가되어 오버헤드가 발생할 수 있다.(CPU 스케쥴링, 컨텍스트 스위칭)
     * 작업 큐 내부에 작업이 없다면 다른 스레드에게 작업을 양보하는 것이 합리적이다.
     *
     * jobQueue.isEmpty() 라면 OS가 할당한 스레드 작업 시간을 다른 스레드에게 양보하는 것이 낫다. 이러한 양보를 Thread.yield()가 해준다.
     */
    static class Printer implements Runnable {
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                if (jobQueue.isEmpty()) {
                    Thread.yield(); // CPU 자원 낭비 줄이기
                    continue;
                }
                try {
                    String job = jobQueue.poll();
                    log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
                    Thread.sleep(3000); //출력에 걸리는 시간
                    log("출력 완료: " + job);
                } catch (InterruptedException e) {
                    log("인터럽트!");
                    break;
                }
            }
            log("프린터 종료");
        }

        public void addJob(String input) {
            jobQueue.offer(input);
        }
    }
}