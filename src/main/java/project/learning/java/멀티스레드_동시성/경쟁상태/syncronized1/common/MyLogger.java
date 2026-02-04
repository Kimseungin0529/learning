package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common;

public class MyLogger {
    public static void log(String message) {
        System.out.println(String.format("[ %9s ]", Thread.currentThread().getName())+ message);
    }

}
