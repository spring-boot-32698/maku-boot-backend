package net.maku.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Test scheduled task
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Slf4j
@Service
public class TestTask {

    public void run(String params) throws InterruptedException {
        log.info("I am testTask.run(), parameters:{} is being executed.", params);
        Thread.sleep(1000);
    }
}
