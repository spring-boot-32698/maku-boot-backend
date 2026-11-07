package net.maku.monitor.model;

import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.OshiUtil;
import lombok.Data;

/**
 * System Info
 *
 * @author Pure tea
 */
@Data
public class Sys {

    /**
     * Operating system
     */
    private String osName;

    /**
     * System architecture
     */
    private String osArch;

    /**
     * System version
     */
    private String osVersion;


    /**
     * Server name
     */
    private String computerName;

    /**
     * Server Ip
     */
    private String computerIp;

    public Sys() {
        this.setOsName(SystemUtil.getOsInfo().getName());
        this.setOsArch(SystemUtil.getOsInfo().getArch());
        this.setOsVersion(SystemUtil.getOsInfo().getVersion());
        this.setComputerName(OshiUtil.getOs().getNetworkParams().getHostName());
        this.setComputerIp(SystemUtil.getHostInfo().getAddress());
    }

}
