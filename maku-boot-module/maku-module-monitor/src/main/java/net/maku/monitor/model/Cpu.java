package net.maku.monitor.model;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import lombok.Data;

/**
 * Cpu Info
 *
 * @author Pure tea
 */
@Data
public class Cpu {

    /**
     * Set the wait time, in milliseconds
     */
    private static final Long JOSHI_WAIT_SECOND = 360L;

    /**
     * CPUModel
     */
    private String cpuModel;

    /**
     * Number of cores
     */
    private int cpuNum;

    /**
     * CPUTotal usage rate
     */
    private double total;

    /**
     * CPUSystem usage rate
     */
    private double sys;

    /**
     * CPUUser usage rate
     */
    private double used;

    /**
     * CPUCurrent wait rate
     */
    private double wait;

    /**
     * CPUCurrent idle rate
     */
    private double free;

    public Cpu() {
        // Get CPU related info,1 second interval
        CpuInfo cpuInfo = OshiUtil.getCpuInfo(JOSHI_WAIT_SECOND);
        this.setCpuModel(cpuInfo.getCpuModel().split("\n")[0]);
        this.setCpuNum(cpuInfo.getCpuNum());
        this.setTotal(cpuInfo.getToTal());
        this.setSys(cpuInfo.getSys());
        this.setUsed(cpuInfo.getUser());
        this.setWait(cpuInfo.getWait());
        this.setFree(cpuInfo.getFree());
    }

}
