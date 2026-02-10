package project.learning.java.멀티스레드_동시성.경쟁상태.excutor.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;
import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MySleep.sleep;

public class OldOrderService {
    private ThreadPoolExecutor executor;

    public OldOrderService(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public void order(String orderNo) {
        InventoryWork inventoryWork = new InventoryWork(orderNo);
        ShippingWork shippingWork = new ShippingWork(orderNo);
        AccountingWork accountingWork = new AccountingWork(orderNo);
        // 작업 요청
        Future<Boolean> FutureInventoryResult = executor.submit(inventoryWork);
        Future<Boolean> FutureShippingResult = executor.submit(shippingWork);
        Future<Boolean> FutureAccountingResult = executor.submit(accountingWork);

        try {
            Boolean inventoryResult = FutureInventoryResult.get();
            Boolean shippingResult = FutureShippingResult.get();
            Boolean accountingResult = FutureAccountingResult.get();

            if (inventoryResult && shippingResult && accountingResult) {
                log("모든 주문 처리가 성공적으로 완료되었습니다.");
            }
        } catch (InterruptedException e) {
            log("문제가 발생하여 작업을 중단합니다.");
        } catch (ExecutionException e) {
            log("일부 작업이 실패했습니다.");
        }
    }

    static class InventoryWork implements Callable<Boolean> {
        private final String orderNo;

        public InventoryWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("재고 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class ShippingWork implements Callable<Boolean> {
        private final String orderNo;

        public ShippingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("배송 시스템 알림: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class AccountingWork implements Callable<Boolean> {
        private final String orderNo;

        public AccountingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("회계 시스템 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }
}
