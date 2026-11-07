package net.maku.monitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.maku.monitor.model.*;
import net.maku.monitor.utils.ArityUtil;
import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.LinkedList;
import java.util.List;

/**
 * Server Info
 *
 * @author Pure tea
 */
@Tag(name = "Server info")
@Data
@Slf4j
public class Server {

    /**
     * Cpu Info
     */
    @Schema(description = "CPUInformation")
    private Cpu cpu;

    /**
     * Mem Info
     */
    @Schema(description = "Memory info")
    private Mem mem;

    /**
     * Jvm Info
     */
    @Schema(description = "JVMInformation")
    private Jvm jvm;

    /**
     * Sys Info
     */
    @Schema(description = "System info")
    private Sys sys;

    /**
     * SysFile Info
     */
    @Schema(description = "System file info")
    private List<Disk> disks = new LinkedList<>();

    public Server() {
        this.cpu = new Cpu();
        this.mem = new Mem();
        this.jvm = new Jvm();
        this.sys = new Sys();
        this.setDiskList();
        log.debug("Server Info => {}", this);
    }

    public Server(Disk disk) {
        this.setDiskList();
        log.debug("Server Info => {}", this);
    }

    /**
     * Set disk info
     */
    private void setDiskList() {
        SystemInfo systemInfo = new SystemInfo();
        FileSystem fileSystem = systemInfo.getOperatingSystem().getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            Disk disk = new Disk();
            disk.setDiskName(fs.getName());
            disk.setDiskType(fs.getType());
            disk.setDirName(fs.getMount());
            disk.setTotal(convertFileSize(total));
            disk.setFree(convertFileSize(free));
            disk.setUsed(convertFileSize(used));
            disk.setUsage(ArityUtil.mul(ArityUtil.div(used, total, 4), 100));
            this.disks.add(disk);
        }
    }

    /**
     * Byte conversion
     *
     * @param size Byte size
     * @return Converted value
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

}
