package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common;

public class MySleep {
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
