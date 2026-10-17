package net.maku.storage.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import net.maku.storage.properties.StorageProperties;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Date;

/**
 * Storage service
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public abstract class StorageService {
    public StorageProperties properties;

    /**
     * Generate a new timestamped file name from the file name
     *
     * @param fileName File name
     * @return Return the file name with a timestamp
     */
    public String getNewFileName(String fileName) {
        // Main file name, excluding extension
        String prefix = FileNameUtil.getPrefix(fileName);
        // File extension
        String suffix = FileNameUtil.getSuffix(fileName);
        // Convert the time of day HH:mm:ss into seconds
        long time = DateUtil.timeToSecond(DateUtil.formatTime(new Date()));
        // New file name
        return prefix + "_" + time + "." + suffix;
    }

    /**
     * Generate the path, excluding the file name
     *
     * @return Return the generated path
     */
    public String getPath() {
        // File path
        String path = DateUtil.format(new Date(), "yyyyMMdd");

        // If there is a prefix, include it too
        if (StringUtils.hasText(properties.getConfig().getPrefix())) {
            path = properties.getConfig().getPrefix() + "/" + path;
        }

        return path;
    }

    /**
     * Generate a path from the file name
     *
     * @param fileName File name
     * @return Generate file path
     */
    public String getPath(String fileName) {
        return getPath() + "/" + getNewFileName(fileName);
    }

    /**
     * File upload
     *
     * @param data File byte array
     * @param path File path, including file name
     * @return Return http address
     */
    public abstract String upload(byte[] data, String path);

    /**
     * File upload
     *
     * @param inputStream Byte stream
     * @param path        File path, including file name
     * @return Return http address
     */
    public abstract String upload(InputStream inputStream, String path);

}
