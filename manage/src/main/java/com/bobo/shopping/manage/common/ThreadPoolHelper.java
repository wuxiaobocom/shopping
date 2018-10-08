package com.bobo.shopping.manage.common;

import com.bobo.shopping.manage.config.threadpool.ThreadPoolEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuxiaobo@didachuxing.com
 * @create 2018-09-30 10:53
 **/
public class ThreadPoolHelper {

    private Logger logger = LoggerFactory.getLogger(ThreadPoolHelper.class);

    /**
     * 默认线程池数量
     */
    private static final int CORE_POOL_SIZE = 10;

    /**
     * 默认最大线程池数量
     */
    private static final int MAX_POOL_SIZE = 20;

    /**
     * 多余线程的存活时间
     */
    private static final int KEEP_ALIVE_TIME = 3;

    /**
     * 存活时间的单位
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;


    /**
     * 线程工厂
     */
    private static final ThreadFactory THREAD_FACTORY = Executors.defaultThreadFactory();

    /**
     * 如果使用有界阻塞对列的时候，则有界阻塞对列的大小
     */
    private static final int CAPACITY = 20;

    /**
     * 拒绝策略
     */

    /**
     * 单例模式
     */
    private static ThreadPoolHelper threadPoolHelper;


    private Map<String, ThreadPoolExecutor> map = new ConcurrentHashMap<>();

    private static ThreadPoolEntity threadPoolEntity = null;

    public static ThreadPoolHelper getInstance() {
        Lock lock = new ReentrantLock();
        if (threadPoolHelper == null) {
            lock.lock();
            if (threadPoolHelper == null) {
                threadPoolHelper = new ThreadPoolHelper();
            }
            lock.unlock();
        }
        return threadPoolHelper;
    }

    /**
     * 默认值
     */

    private ThreadPoolHelper() {

    }
    public <T> Future<T> execute(String name, Callable<T> callable) {
        ThreadPoolExecutor executor = getExecutor(name);
        Future<T> future = executor.submit(callable);
        return future;
    }

    private ThreadPoolExecutor createThreadPool () {
        ThreadPoolExecutor poolExecutor = null;
        threadPoolEntity = (ThreadPoolEntity) SpringUtils.getBean("threadPoolEntity");
        // 默认情况是使用这个
        if (threadPoolEntity == null) {
            poolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT,
                    new ArrayBlockingQueue<Runnable>(CAPACITY), THREAD_FACTORY,
                    new ThreadPoolExecutor.DiscardOldestPolicy());
        } else {
            Integer corePoolSize = getCorePoolSize(threadPoolEntity.getCorePoolSize());
            Integer maxPoolSize = getMaxPoolSize(threadPoolEntity.getMaxPoolSize());
            Integer keepAliveTime = getKeepAliveTime(threadPoolEntity.getKeepAliveTime());
            TimeUnit timeUnit = getTimeUnit(threadPoolEntity.getUnit());
            BlockingQueue<Runnable> blockingQueue = getQueue(threadPoolEntity.getQueue(),
                    threadPoolEntity.getQueueCapacity());
            ThreadFactory threadFactory = getThreadFactory(threadPoolEntity.getThreadFactory());
            RejectedExecutionHandler handler = getHandler(threadPoolEntity.getRejectHandler());
            poolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, timeUnit,
                    blockingQueue, threadFactory, handler);
        }
        return poolExecutor;
    }
    private ThreadPoolExecutor getExecutor(String name) {
        ThreadPoolExecutor threadPoolExecutor =null;
        if (StringUtils.isNotBlank(name)) {
            threadPoolExecutor = map.get(name);
            Lock lock = new ReentrantLock();
            lock.lock();
            if (threadPoolExecutor == null) {
                threadPoolExecutor = createThreadPool();
                map.put(name,threadPoolExecutor);
            }
            lock.unlock();
        }
        return  threadPoolExecutor;
    }
    /**
     * 获取线程数量
     *
     * @param corePoolSize
     * @return
     * @throws Exception
     */
    private Integer getCorePoolSize(String corePoolSize) {
        try {
            if (StringUtils.isBlank(corePoolSize)) {
                return CORE_POOL_SIZE;
            }
            return Integer.valueOf(corePoolSize);
        } catch (Exception e) {
            logger.error("ThreadPoolHelper getCorePoolSize error, the error message:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取最大线程池数量
     *
     * @param maxPoolSize
     * @return
     * @throws Exception
     */
    private Integer getMaxPoolSize(String maxPoolSize) {
        try {
            if (StringUtils.isBlank(maxPoolSize)) {
                return MAX_POOL_SIZE;
            }
            return Integer.valueOf(maxPoolSize);
        } catch (Exception e) {
            logger.error("ThreadPoolHelper getMaxPoolSize error, the error message :{}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取多余线程的存活时间
     *
     * @param keepAliveTime
     * @return
     * @throws Exception
     */
    private Integer getKeepAliveTime(String keepAliveTime) {
        try {
            if (StringUtils.isBlank(keepAliveTime)) {
                return KEEP_ALIVE_TIME;
            }
            return Integer.valueOf(keepAliveTime);
        } catch (Exception e) {
            logger.error("ThreadPoolHelper getKeepAliveTime error, the error message :{}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取时间的单位
     *
     * @param unit
     * @return
     */
    private TimeUnit getTimeUnit(String unit) {
        // 默认使用的是秒级
        TimeUnit timeUnit = null;
        try {
            if (StringUtils.isBlank(unit)) {
                timeUnit = TimeUnit.SECONDS;
            }
            timeUnit = TimeUnit.valueOf(unit);
        } catch (Exception e) {
            logger.error("ThreadPoolHelper Error, error message :{}", e.getMessage());
        }
        return timeUnit;
    }

    private BlockingQueue<Runnable> getQueue(String queue, String capacity) {
        BlockingQueue<Runnable> blockingQueue = null;
        if (StringUtils.isBlank(queue)) {
            queue = "ArrayBlockingQueue";
        }
        if (StringUtils.isBlank(capacity)) {
            capacity = String.valueOf(CAPACITY);
        }
        switch (queue) {
            case "SynchronousQueue":
                blockingQueue = new SynchronousQueue<Runnable>();
                break;
            case "ArrayBlockingQueue":
                blockingQueue = new ArrayBlockingQueue<Runnable>(Integer.valueOf(capacity));
                break;
            case "LinkedBlockingQueue":
                blockingQueue = new LinkedBlockingDeque<Runnable>(Integer.valueOf(capacity));
                break;
            case "PriorityBlockingQueue":
                blockingQueue = new PriorityBlockingQueue<Runnable>(Integer.valueOf(capacity));
                break;
            default:
                break;
        }
        return blockingQueue;
    }

    /**
     * 获取线程工厂
     *
     * @param strFactory
     * @return
     */
    private ThreadFactory getThreadFactory(String strFactory) {
        ThreadFactory threadFactory = null;
        if (StringUtils.isBlank(strFactory)) {
            return Executors.defaultThreadFactory();
        }
        if ("DefaultThreadFactory".equals(strFactory)) {
            threadFactory = Executors.defaultThreadFactory();
        } else if ("PrivilegedThreadFactory".equals(strFactory)) {
            threadFactory = Executors.privilegedThreadFactory();
        }
        return threadFactory;
    }

    private RejectedExecutionHandler getHandler(String handler) {
        RejectedExecutionHandler rejectedExecutionHandler = null;
        if (StringUtils.isBlank(handler)) {
            return new ThreadPoolExecutor.DiscardOldestPolicy();
        }
        switch (handler) {
            case "DiscardOldestPolicy":
                rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            case "AbortPolicy":
                rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
                break;
            case "CallerRunsPolicy":
                rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            case "DiscardPolicy":
                rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            default:
                break;
        }
        return rejectedExecutionHandler;
    }
}
