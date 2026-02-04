package project.learning.java.멀티스레드_동시성.경쟁상태.syncronized1.common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MyLogger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static void log(Object obj) {
        String time = LocalTime.now().format(formatter);
        System.out.printf("%s [%9s] %s\n", time, Thread.currentThread().getName(), obj);
    }

}
