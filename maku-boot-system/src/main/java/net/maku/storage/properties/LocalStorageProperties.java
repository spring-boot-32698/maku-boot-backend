package net.maku.storage.properties;

import lombok.Data;

/**
 * Local storage settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class LocalStorageProperties {
    /**
     * Local storage path
     */
    private String path;
    /**
     * Resource start path
     */
    private String url = "upload";
}
