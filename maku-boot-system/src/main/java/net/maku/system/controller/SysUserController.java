package net.maku.system.controller;

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
import net.maku.system.convert.SysUserConvert;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.query.SysUserQuery;
import net.maku.system.service.SysPostService;
import net.maku.system.service.SysUserPostService;
import net.maku.system.service.SysUserRoleService;
import net.maku.system.service.SysUserService;
import net.maku.system.vo.SysUserAvatarVO;
import net.maku.system.vo.SysUserBaseVO;
import net.maku.system.vo.SysUserPasswordVO;
import net.maku.system.vo.SysUserVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 用户管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/user")
@AllArgsConstructor
@Tag(name = "用户管理")
public class SysUserController {
    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final SysUserPostService sysUserPostService;
    private final SysPostService sysPostService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<SysUserVO>> page(@ParameterObject @Valid SysUserQuery query) {
        PageResult<SysUserVO> page = sysUserService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public Result<SysUserVO> get(@PathVariable("id") Long id) {
        SysUserEntity entity = sysUserService.getById(id);

        SysUserVO vo = SysUserConvert.INSTANCE.convert(entity);

        // 用户角色列表
        List<Long> roleIdList = sysUserRoleService.getRoleIdList(id);
        vo.setRoleIdList(roleIdList);

        // 用户岗位列表
        List<Long> postIdList = sysUserPostService.getPostIdList(id);
        vo.setPostIdList(postIdList);

        return Result.ok(vo);
    }

    @GetMapping("info")
    @Operation(summary = "登录用户")
    public Result<SysUserVO> info() {
        SysUserVO user = SysUserConvert.INSTANCE.convert(SecurityUser.getUser());

        // 用户岗位列表
        List<Long> postIdList = sysUserPostService.getPostIdList(user.getId());
        user.setPostIdList(postIdList);

        // 用户岗位名称列表
        List<String> postNameList = sysPostService.getNameList(postIdList);
        user.setPostNameList(postNameList);

        return Result.ok(user);
    }

    @PutMapping("info")
    @Operation(summary = "修改登录用户信息")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public Result<String> loginInfo(@RequestBody @Valid SysUserBaseVO vo) {
        sysUserService.updateLoginInfo(vo);

        return Result.ok();
    }

    @PutMapping("avatar")
    @Operation(summary = "修改登录用户头像")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public Result<String> avatar(@RequestBody SysUserAvatarVO avatar) {
        sysUserService.updateAvatar(avatar);

        return Result.ok();
    }

    @PutMapping("password")
    @Operation(summary = "修改密码")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public Result<String> password(@RequestBody @Valid SysUserPasswordVO vo) {
        // 原密码不正确
        UserDetail user = SecurityUser.getUser();
        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return Result.error("原密码不正确");
        }

        // 修改密码
        sysUserService.updatePassword(user.getId(), passwordEncoder.encode(vo.getNewPassword()));

        return Result.ok();
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result<String> save(@RequestBody @Valid SysUserVO vo) {
        // 新增密码不能为空
        if (StrUtil.isBlank(vo.getPassword())) {
            return Result.error("密码不能为空");
        }

        // 密码加密
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));

        // 保存
        sysUserService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> update(@RequestBody @Valid SysUserVO vo) {
        // 如果密码不为空，则进行加密处理
        if (StrUtil.isBlank(vo.getPassword())) {
            vo.setPassword(null);
        } else {
            vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        }

        sysUserService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        Long userId = SecurityUser.getUserId();
        if (idList.contains(userId)) {
            return Result.error("不能删除当前登录用户");
        }

        sysUserService.delete(idList);

        return Result.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "用户姓名列表")
    public Result<List<String>> nameList(@RequestBody List<Long> idList) {
        List<String> list = sysUserService.getRealNameList(idList);

        return Result.ok(list);
    }

    @GetMapping("orgUserList/{orgId}")
    @Operation(summary = "机构用户列表")
    public Result<List<SysUserVO>> orgUserList(@PathVariable("orgId") Long orgId) {
        List<SysUserVO> list = sysUserService.getOrgUserList(orgId);

        return Result.ok(list);
    }

    @PostMapping("import")
    @Operation(summary = "导入用户")
    @OperateLog(type = OperateTypeEnum.IMPORT)
    @PreAuthorize("hasAuthority('sys:user:import')")
    public Result<String> importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的文件");
        }
        sysUserService.importByExcel(file, passwordEncoder.encode("123456"));

        return Result.ok();
    }

    @GetMapping("export")
    @Operation(summary = "导出用户")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:user:export')")
    public void export() {
        sysUserService.export();
    }
}
