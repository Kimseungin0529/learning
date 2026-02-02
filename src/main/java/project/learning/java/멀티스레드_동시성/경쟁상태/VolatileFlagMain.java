package project.learning.java.멀티스레드_동시성.경쟁상태;

import static project.learning.java.멀티스레드_동시성.경쟁상태.VolatileFlagMain.MyLogger.log;

public class VolatileFlagMain {
    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        Thread t = new Thread(task, "work");
        log("runFlag = " + task.runFlag);
        t.start();
        Thread.sleep(1000);
        log("runFlag를 false로 변경 시도");
        task.runFlag = false;
        log("runFlag = " + task.runFlag);
        log("main 종료");
    }

    /**
     * 메모리 가시성은 멀티스레드 환경에서 한 스레드가 수정한 변수가 다른 스레드에서 즉시 보이는지를 의미한다.
     * CPU 캐시 메모리로 인해 각 스레드가 공유하는 변수가 다르게 보입니다. 캐시 메모리가 언제 메인 메모리에
     * 반영될 지 모르고 언제 스레드가 메인 메모리에 값을 다시 반영할지는 예측할 수 없습니다.
     * 따라서 멀티스레드 환경에서 공유하는 변수가 있는 경우, 동시성 처리를 해줘야한다.
     * 여기서는 메모리 가시성(volatility)을 확인핸다.
     * volatile 키워드를 사용하면 해당 변수가 캐시 메모리에 저장되지 않고 항상 메인 메모리에서 읽고 쓰도록 강제할 수 있다.
     */
    static class MyTask implements Runnable {
        boolean runFlag = true;
        //volatile boolean runFlag = true;

        @Override
        public void run() {
            log("task 시작");
            while (runFlag) {
                // runFlag가 false로 변하면 탈출
            }
            log("task 종료");
        }
    }

    static class MyLogger {
        static void log(String message) {
            System.out.println("[ " + Thread.currentThread().getName() + " ] " + message);
        }
    }
}
