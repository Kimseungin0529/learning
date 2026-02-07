package project.learning.java.멀티스레드_동시성.경쟁상태.cas.collction.simple;

import java.util.Arrays;

import static project.learning.java.멀티스레드_동시성.경쟁상태.lock.common.MySleep.sleep;

/**\
 * 일반적인 컬렉션을 구현한 것이다. 단일 스레드 환경에서는 당연히 의도대로 동작할 것이다. 멀티스레드면 어떻게 될까?
 * add() 메서드를 동시에 접근하면 당연히 동시성이 보장되지 않을 것이다.
 */
public class BasicList implements SimpleList {
    private static final int DEFAULT_CAPACITY = 5;
    private Object[] elementData;
    private int size = 0;

    public BasicList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(Object e) {
        elementData[size] = e;
        sleep(100); // 멀티스레드 문제를 쉽게 확인하는 코드
        size++;
    }

    @Override
    public Object get(int index) {
        return elementData[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elementData, size)) + " size=" + size + ", capacity=" + elementData.length;
    }
}