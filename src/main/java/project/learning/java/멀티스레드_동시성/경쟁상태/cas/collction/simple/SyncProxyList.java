package project.learning.java.멀티스레드_동시성.경쟁상태.cas.collction.simple;

/**
 * 프록시 구조와 inferface를 통해 다형성을 확보했다. 매번 사용하는 컬렉션마다 syncronized 처리를 할 필요가 없다.
 *
 * 구현체를 SyncProxyList 생성자 파라미터에 넣어주기만 하면 스레드 세이프를 보장하게 됐다. 이러한 과정을 최적화한 프록시 구조를 자바에서 제공한다.
 * SimpleListMainV2 에서 확인해보자.
 */
public class SyncProxyList implements SimpleList {
    private SimpleList target;

    public SyncProxyList(SimpleList target) {
        this.target = target;
    }

    @Override
    public synchronized void add(Object e) {
        target.add(e);
    }

    @Override
    public synchronized Object get(int index) {
        return target.get(index);
    }

    @Override
    public synchronized int size() {
        return target.size();
    }

    @Override
    public synchronized String toString() {
        return target.toString() + " by " + this.getClass().getSimpleName();
    }
}
