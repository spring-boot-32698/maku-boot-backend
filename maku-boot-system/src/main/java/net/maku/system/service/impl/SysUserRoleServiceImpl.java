package net.maku.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.system.dao.SysUserRoleDao;
import net.maku.system.entity.SysUserRoleEntity;
import net.maku.system.service.SysUserRoleService;
import net.maku.system.service.SysUserTokenService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User-role relationship
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {
    private final SysUserTokenService sysUserTokenService;

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        // Role ID list in the database
        List<Long> dbRoleIdList = getRoleIdList(userId);

        // Role IDs to add
        Collection<Long> insertRoleIdList = CollUtil.subtract(roleIdList, dbRoleIdList);
        if (CollUtil.isNotEmpty(insertRoleIdList)) {
            List<SysUserRoleEntity> roleList = insertRoleIdList.stream().map(roleId -> {
                SysUserRoleEntity entity = new SysUserRoleEntity();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                return entity;
            }).collect(Collectors.toList());

            // Batch add
            saveBatch(roleList);
        }

        // Role IDs to delete
        Collection<Long> deleteRoleIdList = CollUtil.subtract(dbRoleIdList, roleIdList);
        if (CollUtil.isNotEmpty(deleteRoleIdList)) {
            LambdaQueryWrapper<SysUserRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
            remove(queryWrapper.eq(SysUserRoleEntity::getUserId, userId).in(SysUserRoleEntity::getRoleId, deleteRoleIdList));
        }
    }

    @Override
    public void saveUserList(Long roleId, List<Long> userIdList) {
        List<SysUserRoleEntity> list = userIdList.stream().map(userId -> {
            SysUserRoleEntity entity = new SysUserRoleEntity();
            entity.setUserId(userId);
            entity.setRoleId(roleId);
            return entity;
        }).collect(Collectors.toList());

        // Batch add
        saveBatch(list);

        // Update the user cached permissions
        userIdList.forEach(sysUserTokenService::updateCacheAuthByUserId);
    }

    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getRoleId, roleIdList));
    }

    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getUserId, userIdList));
    }

    @Override
    public void deleteByUserIdList(Long roleId, List<Long> userIdList) {
        LambdaQueryWrapper<SysUserRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        remove(queryWrapper.eq(SysUserRoleEntity::getRoleId, roleId).in(SysUserRoleEntity::getUserId, userIdList));

        // Update the user cached permissions
        userIdList.forEach(sysUserTokenService::updateCacheAuthByUserId);
    }

    @Override
    public List<Long> getRoleIdList(Long userId) {
        return baseMapper.getRoleIdList(userId);
    }

    @Override
    public List<Long> getExistsUserIdList(Long roleId) {
        return baseMapper.getExistsUserIdList(roleId);
    }
}