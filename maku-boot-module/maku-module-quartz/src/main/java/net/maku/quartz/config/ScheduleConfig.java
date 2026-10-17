//package net.maku.quartz.config;
//
//import net.maku.framework.common.constant.Constant;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
/// **
// * Scheduled task settings
// *
// * @author Amu babamu@126.com
// * <a href="https://maku.net">MAKU</a>
// */
//@Configuration
//public class ScheduleConfig {
//    @Value("${spring.datasource.dynamic.datasource.master.driver-class-name}")
//    private String driver;
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
//        // quartzParameter
//        Properties prop = new Properties();
//        prop.put("org.quartz.scheduler.instanceName", "MakuScheduler");
//        prop.put("org.quartz.scheduler.instanceId", "AUTO");
//        // Thread pool settings
//        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
//        prop.put("org.quartz.threadPool.threadCount", "20");
//        prop.put("org.quartz.threadPool.threadPriority", "5");
//        // jobStoreSettings
//        prop.put("org.quartz.jobStore.class", "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
//        // Cluster settings
//        prop.put("org.quartz.jobStore.isClustered", "true");
//        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
//        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
//        prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");
//
//        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
//        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
//        prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");
//
//        // PostgreSQLDatabase settings
//        if (Constant.PGSQL_DRIVER.equals(driver)) {
//            prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
//        }
//
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setSchedulerName("MakuScheduler");
//        factory.setDataSource(dataSource);
//        factory.setQuartzProperties(prop);
//        // Delayed start
//        factory.setStartupDelay(10);
//        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
//        // Update existing Jobs at startup, so there is no need to delete the matching record in the qrtz_job_details table every time targetObject is changed
//        factory.setOverwriteExistingJobs(true);
//
//        return factory;
//    }
//}
