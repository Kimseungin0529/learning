package project.learning.java.멀티스레드_동시성.인터럽트;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static project.learning.java.멀티스레드_동시성.인터럽트.ThreadStopMain1.MyLogger.log;


public class MyPrinterV1 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();
        Scanner userInput = new Scanner(System.in); // 사용자 입력

        while (true) {
            System.out.println("프린터할 문서를 입력하세요. 종료 (q): ");
            String input = userInput.nextLine();
            if (input.equals("q")) {
                printer.work = false;
                printerThread.interrupt();
                break;
            }
            printer.addJob(input);
        }
    }

    /**
     * work 변수를 통해 우리는 인터럽트가 발생하는 경우, 즉각적으로 해당 스레드의 동작(while)을 멈추도록 할 수 있다. 여기서 나아가 work 변수를 없앨 수 있다.
     * 앞서 학습한 Thread.interrupted() 메서드를 활용하면 된다. 이 메서드는 호출하는 순간 인터럽트 상태를 확인하고, true를 반환한 후 인터럽트 상태를 false로 변경한다.
     * Printer, Printer2 두 가지 방식을 비교해보자. Printer2는 변수를 줄일 수 있는 좋은 방법이다. 굳이 혼란을 줄 수 있는 변수를 두지 않아야 한다.
     * 다만 이전 ThreadStopMain에서 언급했듯 CPU 자원 낭비가 발생할 수 있다. 이 부분은 상황에 맞게 선택하면 된다. MyPrinterV2에서 개선된 방식을 확인해보자.
     *
     */
    static class Printer implements Runnable {
        volatile boolean work = true;
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (work) {
                if (jobQueue.isEmpty()) {
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
    static class Printer2 implements Runnable {
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (Thread.interrupted()) {
                if (jobQueue.isEmpty()) {
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