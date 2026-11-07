package net.maku.iot.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.iot.entity.IotDeviceEntity;
import net.maku.iot.query.IotDeviceQuery;
import net.maku.iot.service.IotDeviceService;
import net.maku.iot.vo.DeviceCommandVO;
import net.maku.iot.vo.DeviceReportAttributeDataVO;
import net.maku.iot.vo.IotDeviceVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Device table
 *
 * @author LSF maku_lsf@163.com
 */
@RestController
@RequestMapping("iot/device")
@Tag(name = "Device table")
@AllArgsConstructor
public class IotDeviceController {
    private final IotDeviceService iotDeviceService;

    @GetMapping("/page")
    @Operation(summary = "Pagination")
    @PreAuthorize("hasAuthority('iot:device:page')")
    public Result<PageResult<IotDeviceVO>> page(@ParameterObject @Valid IotDeviceQuery query) {
        PageResult<IotDeviceVO> page = iotDeviceService.page(query);

        return Result.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Information")
    @PreAuthorize("hasAuthority('iot:device:info')")
    public Result<IotDeviceVO> get(@PathVariable("id") Long id) {
        IotDeviceEntity entity = iotDeviceService.getById(id);

        return Result.ok(BeanUtil.copyProperties(entity, IotDeviceVO.class));
    }

    @PostMapping
    @Operation(summary = "Save")
    @PreAuthorize("hasAuthority('iot:device:save')")
    public Result<String> save(@RequestBody IotDeviceVO vo) {
        iotDeviceService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "Edit")
    @PreAuthorize("hasAuthority('iot:device:update')")
    public Result<String> update(@RequestBody @Valid IotDeviceVO vo) {
        iotDeviceService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "Delete")
    @PreAuthorize("hasAuthority('iot:device:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        iotDeviceService.delete(idList);

        return Result.ok();
    }

    @PostMapping("/asyncSendCommand")
    @Operation(summary = "Send command - do not wait for device reply")
    @PreAuthorize("hasAuthority('iot:device:send')")
    public Result<String> asyncSendCommand(@RequestBody DeviceCommandVO vo) {
        iotDeviceService.asyncSendCommand(vo);
        return Result.ok();
    }

    @PostMapping("/syncSendCommand")
    @Operation(summary = "Send command - wait for device reply")
    @PreAuthorize("hasAuthority('iot:device:send')")
    public Result<String> syncSendCommand(@RequestBody DeviceCommandVO vo) {
        iotDeviceService.syncSendCommand(vo);
        return Result.ok();
    }

    @PostMapping("/syncSendCommand/debug")
    @Operation(summary = "Send command - wait for device reply - debug")
    @PreAuthorize("hasAuthority('iot:device:send')")
    public Result<String> syncSendCommandDebug(@RequestBody DeviceCommandVO vo) {
        return Result.ok(iotDeviceService.syncSendCommandDebug(vo).getResponsePayload());
    }

    @PostMapping("/simulateDeviceReportAttributeData")
    @Operation(summary = "Simulate device property data report")
    @PreAuthorize("hasAuthority('iot:device:report')")
    public Result<String> simulateDeviceReportAttributeData(@RequestBody DeviceReportAttributeDataVO vo) {
        iotDeviceService.simulateDeviceReportAttributeData(vo);
        return Result.ok();
    }

}