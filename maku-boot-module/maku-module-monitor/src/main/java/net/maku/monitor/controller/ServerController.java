package net.maku.monitor.controller;

import net.maku.framework.common.utils.Result;
import net.maku.monitor.model.*;
import net.maku.monitor.vo.Server;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Server monitoring
 *
 * @author Pure tea
 */
@RestController
@RequestMapping("monitor/server")
public class ServerController {

    /**
     * Server related info
     */
    @GetMapping("info")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Server> getServerInfo() {
        Server server = new Server();
        return Result.ok(server);
    }

    /**
     * CPURelated info
     */
    @GetMapping("cpu")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Cpu> getCpuInfo() {
        Cpu cpu = new Cpu();
        return Result.ok(cpu);
    }

    /**
     * Memory related info
     */
    @GetMapping("mem")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Mem> getMemInfo() {
        Mem mem = new Mem();
        return Result.ok(mem);
    }

    /**
     * JVMRelated info
     */
    @GetMapping("jvm")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Jvm> getJvmInfo() {
        Jvm jvm = new Jvm();
        return Result.ok(jvm);
    }

    /**
     * System related info
     */
    @GetMapping("sys")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Sys> getSysInfo() {
        Sys sys = new Sys();
        return Result.ok(sys);
    }

    /**
     * System file related info
     */
    @GetMapping("disk")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<List<Disk>> getSysFileInfo() {
        Server server = new Server(new Disk());
        return Result.ok(server.getDisks());
    }

}
