package project.learning.java.멀티스레드_동시성.경쟁상태;


import project.learning.java.멀티스레드_동시성.경쟁상태.excutor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.excutor.ExecutorUtils.printState;

public class ExecutorShutdownMain {

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 4; i++) {
            RunnableTask task = new RunnableTask("task" + i, 11_000);
            es.submit(task);
        }
        printState(es);
        log("== shutdown 시작 ==");
        shutdownAndAwaitTermination(es);
        log("== shutdown 완료 ==");
        printState(es);
    }

    static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // non-blocking, 새로운 작업을 받지 않는다. 처리 중이거나, 큐에 이미 대기중인 작업은 처리한다.이후에 풀의 스레드를 종료한다.
        try { // 이미 대기중인 작업들을 모두 완료할 때 까지 10초 기다린다. log("서비스 정상 종료 시도");
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) { // 정상 종료가 너무 오래 걸리면...
                log("서비스 정상 종료 실패 -> 강제 종료 시도");
                es.shutdownNow(); // 작업이 취소될 때 까지 대기한다.
                /**
                 * 잘못 작성한 로직으로 인한 무한 반복 등 버그가 발생하거나 인터럽트해서 종료하지 못하는 스레드인 경우, 자바에서
                 * 강제 종료해야 제거할 수 있다. 따라서 shutdown 이후 일정 시간 내에 종료되지 않는다면 장애 혹은 버그가 있다고 판단하여
                 * 로그를 남기고 강제 종료해야 후조치가 가능하다.
                 */
                if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("서비스가 종료되지 않았습니다.");
                }
            }
        } catch (InterruptedException ex) { // awaitTermination()으로 대기중인 현재 스레드가 인터럽트 될 수 있다.
            es.shutdownNow();
        }
    }

}

