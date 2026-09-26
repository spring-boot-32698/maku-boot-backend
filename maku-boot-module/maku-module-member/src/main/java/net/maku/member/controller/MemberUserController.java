package net.maku.member.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.member.entity.MemberUserEntity;
import net.maku.member.query.MemberUserQuery;
import net.maku.member.service.MemberUserService;
import net.maku.member.vo.MemberUserVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Member management
 *
 * @author Amu babamu@126.com
 */
@RestController
@RequestMapping("member/user")
@Tag(name = "Member management")
@AllArgsConstructor
public class MemberUserController {
    private final MemberUserService memberUserService;

    @GetMapping("page")
    @Operation(summary = "Pagination")
    @PreAuthorize("hasAuthority('member:user:all')")
    public Result<PageResult<MemberUserVO>> page(@ParameterObject @Valid MemberUserQuery query) {
        PageResult<MemberUserVO> page = memberUserService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "Information")
    @PreAuthorize("hasAuthority('member:user:all')")
    public Result<MemberUserVO> get(@PathVariable("id") Long id) {
        MemberUserEntity entity = memberUserService.getById(id);

        return Result.ok(BeanUtil.copyProperties(entity, MemberUserVO.class));
    }

    @PostMapping
    @Operation(summary = "Save")
    @PreAuthorize("hasAuthority('member:user:all')")
    public Result<String> save(@RequestBody MemberUserVO vo) {
        memberUserService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "Edit")
    @PreAuthorize("hasAuthority('member:user:all')")
    public Result<String> update(@RequestBody @Valid MemberUserVO vo) {
        memberUserService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "Delete")
    @PreAuthorize("hasAuthority('member:user:all')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        memberUserService.delete(idList);

        return Result.ok();
    }
}