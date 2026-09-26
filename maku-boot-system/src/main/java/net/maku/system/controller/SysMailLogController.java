package net.maku.system.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.system.entity.SysMailLogEntity;
import net.maku.system.query.SysMailLogQuery;
import net.maku.system.service.SysMailLogService;
import net.maku.system.vo.SysMailLogVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Mail log
 *
 * @author Amu babamu@126.com
 */
@RestController
@RequestMapping("sys/mail/log")
@Tag(name = "Mail log")
@AllArgsConstructor
public class SysMailLogController {
    private final SysMailLogService sysMailLogService;

    @GetMapping("page")
    @Operation(summary = "Pagination")
    @PreAuthorize("hasAuthority('sys:mail:log')")
    public Result<PageResult<SysMailLogVO>> page(@ParameterObject @Valid SysMailLogQuery query) {
        PageResult<SysMailLogVO> page = sysMailLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "Information")
    @PreAuthorize("hasAuthority('sys:mail:log')")
    public Result<SysMailLogVO> get(@PathVariable("id") Long id) {
        SysMailLogEntity entity = sysMailLogService.getById(id);

        return Result.ok(BeanUtil.copyProperties(entity, SysMailLogVO.class));
    }

    @DeleteMapping
    @Operation(summary = "Delete")
    @PreAuthorize("hasAuthority('sys:mail:log')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysMailLogService.delete(idList);

        return Result.ok();
    }
}