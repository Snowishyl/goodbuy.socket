package io.snowishyl.ws;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author: feiwoscun
 * @date: 2024/10/17
 * @email: 2825097536@qq.com
 * @description:
 */
@Slf4j
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

    public static void doAsync(final Consumer<ResultType> consumer, final ResultType resultType) {
        executor.execute(() -> consumer.accept(resultType));
    }

    public static void doAsync(final BiConsumer<ResultType, Channel> consumer, final ResultType resultType, final Channel channel) {
        executor.execute(() -> consumer.accept(resultType, channel));
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
    public Thread newThread(@NonNull Runnable r) {
        Thread thread = threadFactory.newThread(r);
        thread.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        return thread;
    }
}

@Slf4j
class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("线程：{}发生错误", t.getName(), e);
    }
}