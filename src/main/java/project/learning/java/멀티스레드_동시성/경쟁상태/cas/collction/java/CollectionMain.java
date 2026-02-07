package project.learning.java.멀티스레드_동시성.경쟁상태.cas.collction.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionMain {
    public static void main(String[] args) {
        // 단일 스레드 환경이지만 자바에서 제공하는 동시성 라이브러리를 활용하면 각종 다양한 동시성 기법(Lock, CAS, 분할 등)이 최적화로 적용된 것이 사용된다.
        ArrayList<Integer> list = new ArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        synchronizedList.add(1);
        synchronizedList.add(3);
        synchronizedList.add(2);

        System.out.println("list = " + list);
        /**
         * 위 Collections.synchronizedList() 는 SyncProxyList와 같이 모든 구문을 syncronized 처리를 했다. 이 또한 부담이 될 수 있기에
         * 자바는 각 컬렉션마다 더욱 최적화한 라이브러리를 제공한다.
         *
         *  List` -
         *  `CopyOnWriteArrayList` (ArrayList` 의 대안 )
         *
         *  `Set` -
         *  CopyOnWriteArraySet` (HashSet` 의 대안)
         *  `ConcurrentSkipListSet``TreeSet` 의대안(정렬된순서유지, `Comparator` 사용가능)

         * `Map`
         * `ConcurrentHashMap` : `HashMap` 의 대안
         * `ConcurrentSkipListMap` : `TreeMap` 의 대안(정렬된 순서 유지, `Comparator` 사용 가능)
         *
         * `Queue`
         * ConcurrentLinkedQueue` : 동시성 큐, 비 차단(non-blocking) 큐이다.

         * `Deque`
         * `ConcurrentLinkedDeque` : 동시성 데크, 비 차단(non-blocking) 큐이다.
         *
         * 등 위 예시보다 더 많은 동시성 컬렉션이 존재한다. 메모리와 CPU 관점에서 Collections.synchronizedList 혹은 CopyOnWriteArrayList와 같이 최적화 컬렉션을
         * 사용하면 된다. 대부분 최적화가 되었기에 자바에서 제공하는 라이브러리를 사용하는 것이 효율적일 것이다.
          */
    }
}
