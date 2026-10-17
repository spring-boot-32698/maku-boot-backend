package net.maku.quartz.utils;

import org.quartz.DisallowConcurrentExecution;

/**
 * Forbid concurrency
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 *
 */
@DisallowConcurrentExecution
public class ScheduleDisallowConcurrentExecution extends AbstractScheduleJob {

}
