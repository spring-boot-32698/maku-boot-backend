package net.maku.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.common.utils.TreeUtils;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.framework.security.user.UserDetail;
import net.maku.framework.security.utils.PreAuthorizeUtil;
import net.maku.system.dao.SysMenuDao;
import net.maku.system.entity.SysMenuEntity;
import net.maku.system.enums.SuperAdminEnum;
import net.maku.system.service.SysMenuService;
import net.maku.system.service.SysRoleMenuService;
import net.maku.system.vo.SysMenuVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Menu management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    private final SysRoleMenuService sysRoleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuVO vo) {
        SysMenuEntity entity = BeanUtil.copyProperties(vo, SysMenuEntity.class);

        // Save menu
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuVO vo) {
        SysMenuEntity entity = BeanUtil.copyProperties(vo, SysMenuEntity.class);

        // Parent menu cannot be itself
        if (entity.getId().equals(entity.getPid())) {
            throw new ServerException("Parent menu cannot be itself");
        }

        // Update menu
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // Delete menu
        removeById(id);

        // Delete role-menu relationship
        sysRoleMenuService.deleteByMenuId(id);
    }

    @Override
    public List<SysMenuVO> getMenuList(Integer type) {
        List<SysMenuEntity> menuList = baseMapper.getMenuList(type);

        return TreeUtils.build(BeanUtil.copyToList(menuList, SysMenuVO.class));
    }

    @Override
    public List<SysMenuVO> getUserMenuList(UserDetail user, Integer type) {
        List<SysMenuEntity> menuList;

        // System administrator, has the highest permissions
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            menuList = baseMapper.getMenuList(type);
        } else {
            menuList = baseMapper.getUserMenuList(user.getId(), type);
        }

        return TreeUtils.build(BeanUtil.copyToList(menuList, SysMenuVO.class));
    }

    @Override
    public Long getSubMenuCount(Long pid) {
        return count(new LambdaQueryWrapper<SysMenuEntity>().eq(SysMenuEntity::getPid, pid));
    }

    @Override
    public Set<String> getUserAuthority(UserDetail user) {
        // System administrator, has the highest permissions
        List<String> authorityList;
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            // authorityList = baseMapper.getAuthorityList();
            authorityList = PreAuthorizeUtil.getPreAuthorizeList();
        } else {
            authorityList = baseMapper.getUserAuthorityList(user.getId());
        }

        // User permission list
        Set<String> permsSet = new HashSet<>();
        for (String authority : authorityList) {
            if (StrUtil.isBlank(authority)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(authority.trim().split(",")));
        }

        return permsSet;
    }

}