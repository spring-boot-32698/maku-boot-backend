package net.maku.api.module.system;

import java.io.InputStream;

/**
 * Storage service API
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface StorageApi {

    /**
     * Generate a new timestamped file name from the file name
     *
     * @param fileName File name
     * @return Return the file name with a timestamp
     */
    String getNewFileName(String fileName);

    /**
     * Generate the path, excluding the file name
     *
     * @return Return the generated path
     */
    String getPath();

    /**
     * Generate a path from the file name
     *
     * @param fileName File name
     * @return Generate file path
     */
    String getPath(String fileName);

    /**
     * File upload
     *
     * @param data File byte array
     * @param path File path, including file name
     * @return Return http address
     */
    String upload(byte[] data, String path);

    /**
     * File upload
     *
     * @param inputStream Byte stream
     * @param path        File path, including file name
     * @return Return http address
     */
    String upload(InputStream inputStream, String path);
}
