package project.learning.java.algoritms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.PriorityQueue;

public class PriorityQueueTest {

    @DisplayName("우선순위큐_내림차순_정렬")
    @Test
    void 우선순위큐_내림차순_정렬() {
        // given
        List<Integer> integers = List.of(4, 3, 5, 10, 5);
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);

        // when
        pq.addAll(integers);
        // then
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }

}
