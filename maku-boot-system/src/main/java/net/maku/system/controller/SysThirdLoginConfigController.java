package net.maku.system.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.query.Query;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.system.entity.SysThirdLoginConfigEntity;
import net.maku.system.service.SysThirdLoginConfigService;
import net.maku.system.vo.SysThirdLoginConfigVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Third-party login settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/third/config")
@Tag(name = "Third-party login settings")
@AllArgsConstructor
public class SysThirdLoginConfigController {
    private final SysThirdLoginConfigService sysThirdLoginConfigService;

    @GetMapping("page")
    @Operation(summary = "Pagination")
    @PreAuthorize("hasAuthority('sys:third:config')")
    public Result<PageResult<SysThirdLoginConfigVO>> page(@ParameterObject @Valid Query query) {
        PageResult<SysThirdLoginConfigVO> page = sysThirdLoginConfigService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "Information")
    @PreAuthorize("hasAuthority('sys:third:config')")
    public Result<SysThirdLoginConfigVO> get(@PathVariable("id") Long id) {
        SysThirdLoginConfigEntity entity = sysThirdLoginConfigService.getById(id);

        return Result.ok(BeanUtil.copyProperties(entity, SysThirdLoginConfigVO.class));
    }

    @PostMapping
    @Operation(summary = "Save")
    @PreAuthorize("hasAuthority('sys:third:config')")
    public Result<String> save(@RequestBody SysThirdLoginConfigVO vo) {
        sysThirdLoginConfigService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "Edit")
    @PreAuthorize("hasAuthority('sys:third:config')")
    public Result<String> update(@RequestBody @Valid SysThirdLoginConfigVO vo) {
        sysThirdLoginConfigService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "Delete")
    @PreAuthorize("hasAuthority('sys:third:config')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysThirdLoginConfigService.delete(idList);

        return Result.ok();
    }
}