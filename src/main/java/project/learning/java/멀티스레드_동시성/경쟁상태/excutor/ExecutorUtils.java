package project.learning.java.멀티스레드_동시성.경쟁상태.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static project.learning.java.멀티스레드_동시성.경쟁상태.common.MyLogger.log;

/**
 * 지금까지 멀티스레드 환경에서 동시성을 보장하기 위한 학습을 했다.
 * 스레드 생성, 조인, 양보(yield) 등 관리부터 메모리 가시성, 락(syncronized, Lock), 원자적 연산(Atomic, CAS, 동시성 컬렉션) 등 학습했다.
 * 실무에서는 다양한 작업이 동시에 실행되기에 자원을 효율적으로 사용해야 한다. 하지만 스레드를 생성하여 동작하는 것은 매우 무거운 행위이다.
 * 스레드 객체를 생성하는 것을 떠나 Thread.start()를 하게 되면 자신만의 호출 스택을 만들고, 메모리 공간을 할당하며 OS 단에서 system call이 호출된다.
 * CPU와 메모리 리소스를 소모하게 되며 스레드의 생성과 삭제가 반복되며 OS 스케줄러에서 이를 관리하는 데 오버헤드가 발생한다.
 *
 * 이러한 무거운 과정을 줄이기 위해 자바에서는 스레드 풀을 통해 관리한다. 미리 일정 스레드를 생성하고 풀에서 관리하는 방식이다.
 * 작업 요청이 오면 스레드 풀에 있는 스레드를 사용하고 만약 작업을 처리할 스레드가 없다면 작업은 대기했다가 스레드가 활동 가능한 순간
 * 처리된다. 이는 이전에 본 생산자-소비자 문제와 같다.
 *
 * 추가로 Runnable 은 반환값이 없기에 join()으로 해당 작업이 끝날떄까지 기다리는 스레드는 종료되지 못하고
 * 작업 완료된 추가 필드를 가져와야 한다. 또한 run()은 체크 예외를 던질 수 없기에 메서드 내부에서만 처리해야 하는 불편함이 있다.
 */


public class ExecutorUtils {
    public static void printState(ExecutorService executorService) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queuedTasks = poolExecutor.getQueue().size();
            long completedTask = poolExecutor.getCompletedTaskCount();
            log("[pool=" + pool + ", active=" + active + ", queuedTasks=" +
                    queuedTasks + ", completedTasks=" + completedTask + "]");
        } else {
            log(executorService);
        }
    }

    public static void printState(ExecutorService executorService, String taskName) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queuedTasks = poolExecutor.getQueue().size();
            long completedTask = poolExecutor.getCompletedTaskCount();
            log("[taskName= " + taskName + ", pool=" + pool + ", active=" + active + ", queuedTasks=" +
                    queuedTasks + ", completedTasks=" + completedTask + "]");
        } else {
            log(executorService);
        }
    }
}
