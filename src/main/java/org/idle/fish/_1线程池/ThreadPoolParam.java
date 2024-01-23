package org.idle.fish._1线程池;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author idle fish
 * @wx Guest723
 * @service java/web技术支持，可添加上面的微信
 * @since 2024/1/9
 */
public class ThreadPoolParam {
    public static void main(String[] args) {
        // 线程池的参数，作用
        // =====注意：线程池的工作流程，是需要根据使用的workQueue来决定的，比如如果使用的是LinkedBlockQueue
        /*
         * corePoolSize：核心线程数，即线程池中最少的线程数，当线程池中的线程小于核心线程时，每次向线程池提交任务，都会新建一个线程（尽管workQueue还未满）
         * maximumPoolSize：线程池中允许的最大线程数，当workQueue满了，就会新建一个线程
         * keepAliveTime：非核心线程的存活时间，即非核心线程在空闲状态下的存活时间
         * workQueue：任务队列，用于存放等待执行的任务，当核心线程满了，会把任务放到任务队列，如果任务队列满了，但是还没达到最大线程，那么会创建新的线程，如果达到了最大线程，会执行拒绝策略
         *    ArrayBlockingQueue：数组队列，是一个有大小限制的队列，当队列满了，会抛出异常
         *    LinkedBlockingQueue：链表队列，是一个有大小限制的队列，当队列满了，会阻塞等待
         *    SynchronousQueue：同步队列，是一个没有大小限制的队列，当队列满了，会阻塞等待（只是用来做任务中转）
         * threadFactory：线程工厂，用于创建线程
         * handler：拒绝策略，当线程池中的线程数目达到maximumPoolSize，且workQueue也满了，如果还有任务，则会调用RejectedExecutionHandler的rejectedExecution方法，默认会抛出异常，可以通过这个方法，自定义线程池的拒绝策略
         *    ThreadPoolExecutor.AbortPolicy：直接抛出异常，默认策略
         *    ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理任务
         *    ThreadPoolExecutor.DiscardPolicy：丢弃任务，不抛出异常
         *    ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列中最旧的任务，然后重新尝试执行任务，如果还是失败，则抛出异常
         */
        // 线程池的工作原理

    }

    /**
     * 测试核心线程的初始化时机
     */
    @Test
    public void testInitCoreSize() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                System.out.println(Thread.currentThread().getName() + "初始化新的线程");
                return new Thread(r);
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());

        while (true) {
            try {
                threadPoolExecutor.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName());
                });
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
