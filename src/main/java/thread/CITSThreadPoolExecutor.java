package thread;

import Oplog.ServiceException;
import javaApiPractice.PropertyUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CITSThreadPoolExecutor {

    private static CITSThreadPoolExecutor instance = new CITSThreadPoolExecutor();

    private final ThreadPoolExecutor poolExecutor;
    Logger logger = Logger.getLogger(CITSThreadPoolExecutor.class);

    public static CITSThreadPoolExecutor getInstance() {
        return instance;
    }

    /**
     * Private constructor which initialize the threadPoolExecutor following properties 1) Max Number of Tasks 2)
     * Minimum number of threads 3) Max number of threads 4) TTL for idle threads The thread pool executor has abort
     * policy.
     */
    private CITSThreadPoolExecutor() {
        int corePoolSize = 15;
        int timeToLiveIdleThread = 30;
        // Read pool configuration from the properties file.
        try {

            corePoolSize = Integer.parseInt(PropertyUtil.getProperty("thread.pool.size"));
            timeToLiveIdleThread = Integer.parseInt(PropertyUtil.getProperty("thread.pool.timeout"));

            this.logger.info(String
                    .format("Thread Pool Initialized with : Pool Size : %s, TTL: %s", corePoolSize, timeToLiveIdleThread));

        } catch (final Exception e) {
            this.logger.error("Could not initialize Thread pool from property file using default values "
                    + e.toString(), e);
        }
        this.poolExecutor = new ThreadPoolExecutor(corePoolSize, corePoolSize, timeToLiveIdleThread, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * Adds the new thread task to the queue for execution. If a thread is available, then the task gets executed.
     * Otherwise, the task will be waiting in the queue and executes upon next available thread.
     *
     * @param baseThread
     */
    public void addTask(final Callable<Boolean> baseThread) throws ServiceException {
        try {
            this.poolExecutor.submit(baseThread);
        } catch (final Exception e) {
            this.logger.error(String
                    .format("Error while processing the multithreaded request. Exception: {%s}", e.toString()));
            logger.error("Exception : " + ExceptionUtils.getStackTrace(e));
            throw new ServiceException(e);
        }
    }

    public void shutdown() {
        this.logger.debug("extractor shutdown called");
        this.poolExecutor.shutdown();

    }

    public boolean isTerminated() {
        return this.poolExecutor.isTerminated();
    }

    public int getActiveTask() {
        return this.poolExecutor.getActiveCount();
    }

    public int getQueueSize() {
        return this.poolExecutor.getQueue().size();
    }

}
