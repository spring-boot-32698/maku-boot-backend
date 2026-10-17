package net.maku.monitor.model;

import lombok.Data;

/**
 * Disk
 *
 * @author Pure tea
 */
@Data
public class Disk {

    /**
     * Disk name
     */
    private String diskName;

    /**
     * Disk type
     */
    private String diskType;

    /**
     * Disk path
     */
    private String dirName;

    /**
     * Total size
     */
    private String total;

    /**
     * Remaining size
     */
    private String free;

    /**
     * Amount already used
     */
    private String used;

    /**
     * Resource usage rate
     */
    private double usage;

}
