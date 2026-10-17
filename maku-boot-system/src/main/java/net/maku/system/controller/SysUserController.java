package net.maku.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.framework.operatelog.annotations.OperateLog;
import net.maku.framework.operatelog.enums.OperateTypeEnum;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.query.SysUserQuery;
import net.maku.system.service.SysPostService;
import net.maku.system.service.SysUserPostService;
import net.maku.system.service.SysUserRoleService;
import net.maku.system.service.SysUserService;
import net.maku.system.vo.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * User management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/user")
@AllArgsConstructor
@Tag(name = "User management")
public class SysUserController {
    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final SysUserPostService sysUserPostService;
    private final SysPostService sysPostService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("page")
    @Operation(summary = "Pagination")
    public Result<PageResult<SysUserVO>> page(@ParameterObject @Valid SysUserQuery query) {
        PageResult<SysUserVO> page = sysUserService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "Information")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public Result<SysUserVO> get(@PathVariable("id") Long id) {
        SysUserEntity entity = sysUserService.getById(id);

        SysUserVO vo = BeanUtil.copyProperties(entity, SysUserVO.class);

        // User role list
        List<Long> roleIdList = sysUserRoleService.getRoleIdList(id);
        vo.setRoleIdList(roleIdList);

        // User post list
        List<Long> postIdList = sysUserPostService.getPostIdList(id);
        vo.setPostIdList(postIdList);

        return Result.ok(vo);
    }

    @GetMapping("info")
    @Operation(summary = "Logged-in user")
    public Result<SysUserVO> info() {
        SysUserVO user = BeanUtil.copyProperties(SecurityUser.getUser(), SysUserVO.class);

        // User post list
        List<Long> postIdList = sysUserPostService.getPostIdList(user.getId());
        user.setPostIdList(postIdList);

        // User post name list
        List<String> postNameList = sysPostService.getNameList(postIdList);
        user.setPostNameList(postNameList);

        return Result.ok(user);
    }

    @PutMapping("info")
    @Operation(summary = "Update logged-in user info")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public Result<String> loginInfo(@RequestBody @Valid SysUserBaseVO vo) {
        sysUserService.updateLoginInfo(vo);

        return Result.ok();
    }

    @PutMapping("avatar")
    @Operation(summary = "Update logged-in user avatar")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public Result<String> avatar(@RequestBody SysUserAvatarVO avatar) {
        sysUserService.updateAvatar(avatar);

        return Result.ok();
    }

    @PutMapping("signature")
    @Operation(summary = "Update logged-in user signature")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public Result<String> signature(@RequestBody SysUserSignatureVO signature) {
        sysUserService.updateSignature(signature);

        return Result.ok();
    }

    @PutMapping("password")
    @Operation(summary = "Change password")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public Result<String> password(@RequestBody @Valid SysUserPasswordVO vo) {
        // Old password is incorrect
        UserDetail user = SecurityUser.getUser();
        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return Result.error("Old password is incorrect");
        }

        // Change password
        sysUserService.updatePassword(user.getId(), passwordEncoder.encode(vo.getNewPassword()));

        return Result.ok();
    }

    @PostMapping
    @Operation(summary = "Save")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result<String> save(@RequestBody @Valid SysUserVO vo) {
        // New password cannot be empty
        if (StrUtil.isBlank(vo.getPassword())) {
            return Result.error("Password cannot be empty");
        }

        // Password encryption
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));

        // Save
        sysUserService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "Edit")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> update(@RequestBody @Valid SysUserVO vo) {
        // If the password is not empty, encrypt it
        if (StrUtil.isBlank(vo.getPassword())) {
            vo.setPassword(null);
        } else {
            vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        }

        sysUserService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "Delete")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        Long userId = SecurityUser.getUserId();
        if (idList.contains(userId)) {
            return Result.error("Cannot delete the currently logged-in user");
        }

        sysUserService.delete(idList);

        return Result.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "User name list")
    public Result<List<String>> nameList(@RequestBody List<Long> idList) {
        List<String> list = sysUserService.getRealNameList(idList);

        return Result.ok(list);
    }

    @GetMapping("orgUserList/{orgId}")
    @Operation(summary = "Organization user list")
    public Result<List<SysUserVO>> orgUserList(@PathVariable("orgId") Long orgId) {
        List<SysUserVO> list = sysUserService.getOrgUserList(orgId);

        return Result.ok(list);
    }

    @PostMapping("import")
    @Operation(summary = "Import users")
    @OperateLog(type = OperateTypeEnum.IMPORT)
    @PreAuthorize("hasAuthority('sys:user:import')")
    public Result<String> importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("Please select the file to upload");
        }
        sysUserService.importByExcel(file, passwordEncoder.encode("123456"));

        return Result.ok();
    }

    @GetMapping("export")
    @Operation(summary = "Export users")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:user:export')")
    public void export() {
        sysUserService.export();
    }
}
