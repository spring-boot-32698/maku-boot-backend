package net.maku.system.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.framework.operatelog.annotations.OperateLog;
import net.maku.framework.operatelog.enums.OperateTypeEnum;
import net.maku.system.entity.SysDictTypeEntity;
import net.maku.system.query.SysDictTypeQuery;
import net.maku.system.service.SysDictTypeService;
import net.maku.system.vo.SysDictTypeVO;
import net.maku.system.vo.SysDictVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Dictionary type
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/dict/type")
@Tag(name = "Dictionary type")
@AllArgsConstructor
public class SysDictTypeController {
    private final SysDictTypeService sysDictTypeService;

    @GetMapping("page")
    @Operation(summary = "Pagination")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<SysDictTypeVO>> page(@ParameterObject @Valid SysDictTypeQuery query) {
        PageResult<SysDictTypeVO> page = sysDictTypeService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "List")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<List<SysDictTypeVO>> list(Long pid) {
        List<SysDictTypeVO> list = sysDictTypeService.list(pid);

        return Result.ok(list);
    }

    @GetMapping("list/sql")
    @Operation(summary = "Dynamic SQL data")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<SysDictVO.DictData>> listSql(Long id) {
        List<SysDictVO.DictData> list = sysDictTypeService.getDictSql(id);

        PageResult<SysDictVO.DictData> page = new PageResult<>(list, list.size());

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "Information")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public Result<SysDictTypeVO> get(@PathVariable("id") Long id) {
        SysDictTypeEntity entity = sysDictTypeService.getById(id);

        return Result.ok(BeanUtil.copyProperties(entity, SysDictTypeVO.class));
    }

    @PostMapping
    @Operation(summary = "Save")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public Result<String> save(@RequestBody @Valid SysDictTypeVO vo) {
        sysDictTypeService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "Edit")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public Result<String> update(@RequestBody @Valid SysDictTypeVO vo) {
        sysDictTypeService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "Delete")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysDictTypeService.delete(idList);

        return Result.ok();
    }

    @GetMapping("all")
    @Operation(summary = "All dictionary data")
    public Result<List<SysDictVO>> all() {
        List<SysDictVO> dictList = sysDictTypeService.getDictList();

        return Result.ok(dictList);
    }

    @GetMapping("refreshTransCache")
    @Operation(summary = "Refresh dictionary translation cache data")
    @PreAuthorize("hasAuthority('sys:dict:refreshTransCache')")
    public Result<String> refreshTransCache() {
        sysDictTypeService.refreshTransCache();
        return Result.ok();
    }


}