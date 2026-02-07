package project.learning.java.멀티스레드_동시성.경쟁상태.lock.bounded;

public interface BoundedQueue {
    void put(String data);

    String take();

}
