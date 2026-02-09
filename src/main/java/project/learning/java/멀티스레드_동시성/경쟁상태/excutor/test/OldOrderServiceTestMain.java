package project.learning.java.멀티스레드_동시성.경쟁상태.excutor.test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class OldOrderServiceTestMain {
    public static void main(String[] args) {
        String orderNo = "Order#1234"; // 예시 주문 번호
        ThreadPoolExecutor es = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        OldOrderService orderService = new OldOrderService(es);
        orderService.order(orderNo);
    }
}
