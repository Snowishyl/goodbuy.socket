package io.snowishyl.ws;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @author: feiwoscun
 * @date: 2024/10/17
 * @email: 2825097536@qq.com
 * @description:
 */
public class AsyncTool {

    @Getter
    private static final ThreadPoolExecutor executor;

    static {

        int coreSize = Runtime.getRuntime().availableProcessors();
        int maxCoreSize = Math.max(2, coreSize * 2);

        executor = new ThreadPoolExecutor(maxCoreSize, maxCoreSize,
                5,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(),
                new MythreadFactory(Executors.defaultThreadFactory()),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static void doAsync(Consumer<ResultType> consumer, final ResultType resultType) {
        executor.execute(() -> consumer.accept(resultType));
    }

    public static <R> R doSync(Callable<R> callable) {
        return null;
    }

}

class MythreadFactory implements ThreadFactory {

    private final ThreadFactory threadFactory;

    public MythreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = threadFactory.newThread(r);
        thread.setUncaughtExceptionHandler(new ThreadExecptionHandler());
        return thread;
    }
}

@Slf4j
class ThreadExecptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("线程：" + t.getName() + "发生错误，", e);
    }
}