package project.learning.java.멀티스레드_동시성.경쟁상태.excutor.test;

public class OldOrderServiceTestMain {
    public static void main(String[] args) {
        String orderNo = "Order#1234"; // 예시 주문 번호
        OldOrderService orderService = new OldOrderService();
        orderService.order(orderNo);
    }
}
