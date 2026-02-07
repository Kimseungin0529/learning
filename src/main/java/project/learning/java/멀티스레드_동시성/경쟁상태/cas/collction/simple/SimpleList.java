package project.learning.java.멀티스레드_동시성.경쟁상태.cas.collction.simple;

public interface SimpleList {
    int size();

    void add(Object e);

    Object get(int index);
}
