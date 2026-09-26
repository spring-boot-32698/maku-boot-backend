package net.maku.framework.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.constant.DbConstant;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Database utility class
 *
 * @author Amu babamu@126.com
 */
@Slf4j
public class DatabaseUtils {

    /**
     * Get database product name
     *
     * @return Database product name
     */
    public static String getProductName() {
        DataSource dataSource = SpringUtil.getBean(DataSource.class);
        try (Connection connection = dataSource.getConnection()) {
            return getProductName(connection);
        } catch (Exception e) {
            log.error("Failed to get database product name", e);
        }

        return DbConstant.MySQL;
    }

    /**
     * Get database product name
     *
     * @return Database product name
     */
    public static String getProductName(Connection connection) {
        String productName = DbConstant.MySQL;
        try {
            productName = connection.getMetaData().getDatabaseProductName();

            // If it is Oracle, check the driver name, as it may be the Dameng database
            if (StrUtil.contains(productName, DbConstant.Oracle)) {
                String driverName = connection.getMetaData().getDriverName();
                if (driverName.contains("dm.jdbc.driver.DmDriver")) {
                    productName = DbConstant.DM;
                }
            }
        } catch (Exception e) {
            log.error("Failed to get database product name", e);
        }

        return productName;
    }

}