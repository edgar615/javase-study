package com.edgar.core.init;

import com.edgar.core.util.ExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 系统初始化类，在Spring的bean加载完毕后执行.
 *
 * @author Edgar
 * @version 1.0
 */
@Service
public class AppInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);
    private static final ExecutorService EXEC = Executors.newCachedThreadPool();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        LOGGER.info("{} resource initializing", context);

        Map<String, Initialization> map = context.getBeansOfType(Initialization.class);
        Collection<Initialization> initializations = Collections.unmodifiableCollection(map
                .values());
        final CountDownLatch LATCH = new CountDownLatch(initializations.size());
        init(initializations, LATCH);
        try {
            LATCH.await();
        } catch (InterruptedException e) {
            throw ExceptionFactory.appError();
        }
        LOGGER.info("{}resource initialized", context);
    }

    /**
     * 使用线程调用初始化类
     *
     * @param initializations 初始化类的集合
     * @param latch           闭锁
     */
    private void init(Collection<Initialization> initializations, final CountDownLatch latch) {
        for (final Initialization INITIAL : initializations) {
            EXEC.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        INITIAL.init();
                        // 如果初始化启动失败，则不允许系统继续运行
                        latch.countDown();
                    } catch (Exception e) {
                        LOGGER.error("System start failed, stop initialize! class:{} reson:{}",
                                INITIAL.getClass().getSimpleName(),
                                e.getMessage(), e);
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
    }
}
