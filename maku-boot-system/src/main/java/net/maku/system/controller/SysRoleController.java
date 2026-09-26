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
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.system.entity.SysRoleEntity;
import net.maku.system.query.SysRoleQuery;
import net.maku.system.query.SysRoleUserQuery;
import net.maku.system.service.*;
import net.maku.system.vo.SysMenuVO;
import net.maku.system.vo.SysRoleDataScopeVO;
import net.maku.system.vo.SysRoleVO;
import net.maku.system.vo.SysUserVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Role management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/role")
@Tag(name = "Role management")
@AllArgsConstructor
public class SysRoleController {
    private final SysRoleService sysRoleService;
    private final SysUserService sysUserService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysRoleDataScopeService sysRoleDataScopeService;
    private final SysMenuService sysMenuService;
    private final SysUserRoleService sysUserRoleService;

    @GetMapping("page")
    @Operation(summary = "Pagination")
    @PreAuthorize("hasAuthority('sys:role:page')")
    public Result<PageResult<SysRoleVO>> page(@ParameterObject @Valid SysRoleQuery query) {
        PageResult<SysRoleVO> page = sysRoleService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "List")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result<List<SysRoleVO>> list() {
        List<SysRoleVO> list = sysRoleService.getList(new SysRoleQuery());

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "Information")
    @PreAuthorize("hasAuthority('sys:role:info')")
    public Result<SysRoleVO> get(@PathVariable("id") Long id) {
        SysRoleEntity entity = sysRoleService.getById(id);

        // Convert object
        SysRoleVO role = BeanUtil.copyProperties(entity, SysRoleVO.class);

        // Query the menus of the role
        List<Long> menuIdList = sysRoleMenuService.getMenuIdList(id);
        role.setMenuIdList(menuIdList);

        // Query the data permission of the role
        List<Long> orgIdList = sysRoleDataScopeService.getOrgIdList(id);
        role.setOrgIdList(orgIdList);

        return Result.ok(role);
    }

    @PostMapping
    @Operation(summary = "Save")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result<String> save(@RequestBody @Valid SysRoleVO vo) {
        sysRoleService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "Edit")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> update(@RequestBody @Valid SysRoleVO vo) {
        sysRoleService.update(vo);

        return Result.ok();
    }

    @PutMapping("data-scope")
    @Operation(summary = "Data permission")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> dataScope(@RequestBody @Valid SysRoleDataScopeVO vo) {
        sysRoleService.dataScope(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "Delete")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysRoleService.delete(idList);

        return Result.ok();
    }

    @GetMapping("menu")
    @Operation(summary = "Role menu")
    @PreAuthorize("hasAuthority('sys:role:menu')")
    public Result<List<SysMenuVO>> menu() {
        UserDetail user = SecurityUser.getUser();
        List<SysMenuVO> list = sysMenuService.getUserMenuList(user, null);

        return Result.ok(list);
    }

    @GetMapping("user/page")
    @Operation(summary = "Role users - paginated")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<PageResult<SysUserVO>> userPage(@Valid SysRoleUserQuery query) {
        PageResult<SysUserVO> page = sysUserService.roleUserPage(query);

        return Result.ok(page);
    }

    @DeleteMapping("user/{roleId}")
    @Operation(summary = "Delete role users")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> userDelete(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
        sysUserRoleService.deleteByUserIdList(roleId, userIdList);

        return Result.ok();
    }

    @PostMapping("user/{roleId}")
    @Operation(summary = "Assign roles to user list")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> userSave(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
        if(userIdList.isEmpty()) {
            return Result.error("UserId is empty!");
        }
        //Query the user list of this role in the database
        List<Long> existsUserIdList = sysUserRoleService.getExistsUserIdList(roleId);

        //Take out the list of users to be added
        List<Long> addUserIdList = userIdList.stream()
            .filter(userId -> !existsUserIdList.contains(userId))
            .collect(Collectors.toList());

        if(addUserIdList.isEmpty()) {
            return Result.error("UserId existed!");
        }
        sysUserRoleService.saveUserList(roleId, addUserIdList);

        return Result.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "Name list")
    public Result<List<String>> nameList(@RequestBody List<Long> idList) {
        List<String> list = sysRoleService.getNameList(idList);

        return Result.ok(list);
    }
}