package net.maku.system.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.ExceptionUtils;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.framework.operatelog.annotations.OperateLog;
import net.maku.framework.operatelog.enums.OperateTypeEnum;
import net.maku.sms.SmsContext;
import net.maku.sms.config.SmsConfig;
import net.maku.sms.service.SmsService;
import net.maku.system.entity.SysSmsConfigEntity;
import net.maku.system.query.SysSmsConfigQuery;
import net.maku.system.service.SysSmsConfigService;
import net.maku.system.vo.SysSmsConfigVO;
import net.maku.system.vo.SysSmsSendVO;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SMS settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/sms/config")
@Tag(name = "SMS settings")
@AllArgsConstructor
public class SysSmsConfigController {
    private final SysSmsConfigService sysSmsConfigService;
    private final SmsService smsService;

    @GetMapping("page")
    @Operation(summary = "Pagination")
    @PreAuthorize("hasAuthority('sys:sms:config')")
    public Result<PageResult<SysSmsConfigVO>> page(@ParameterObject @Valid SysSmsConfigQuery query) {
        PageResult<SysSmsConfigVO> page = sysSmsConfigService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "List")
    public Result<List<SysSmsConfigVO>> list(Integer platform) {
        List<SysSmsConfigVO> list = sysSmsConfigService.list(platform);

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "Information")
    @PreAuthorize("hasAuthority('sys:sms:config')")
    public Result<SysSmsConfigVO> get(@PathVariable("id") Long id) {
        SysSmsConfigEntity entity = sysSmsConfigService.getById(id);

        return Result.ok(BeanUtil.copyProperties(entity, SysSmsConfigVO.class));
    }

    @PostMapping
    @Operation(summary = "Save")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:sms:config')")
    public Result<String> save(@RequestBody SysSmsConfigVO vo) {
        sysSmsConfigService.save(vo);

        return Result.ok();
    }

    @PostMapping("send")
    @Operation(summary = "Send SMS")
    @OperateLog(type = OperateTypeEnum.OTHER)
    @PreAuthorize("hasAuthority('sys:sms:config')")
    public Result<String> send(@RequestBody SysSmsSendVO vo) {
        SysSmsConfigEntity entity = sysSmsConfigService.getById(vo.getId());
        SmsConfig config = BeanUtil.copyProperties(entity, SmsConfig.class);

        // SMS parameters
        Map<String, String> params = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(vo.getParamValue())) {
            params.put(vo.getParamKey(), vo.getParamValue());
        }

        try {
            // Send SMS
            new SmsContext(config).send(vo.getMobile(), params);

            // Save log
            smsService.saveLog(config, vo.getMobile(), params, null);

            return Result.ok();
        } catch (Exception e) {
            // Save log
            smsService.saveLog(config, vo.getMobile(), params, e);

            return Result.error(ExceptionUtils.getExceptionMessage(e));
        }
    }

    @PutMapping
    @Operation(summary = "Edit")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:sms:config')")
    public Result<String> update(@RequestBody @Valid SysSmsConfigVO vo) {
        sysSmsConfigService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "Delete")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:sms:config')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysSmsConfigService.delete(idList);

        return Result.ok();
    }
}