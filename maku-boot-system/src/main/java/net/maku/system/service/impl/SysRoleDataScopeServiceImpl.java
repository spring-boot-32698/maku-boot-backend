package net.maku.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.system.dao.SysRoleDataScopeDao;
import net.maku.system.entity.SysRoleDataScopeEntity;
import net.maku.system.service.SysRoleDataScopeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Role data permission
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
public class SysRoleDataScopeServiceImpl extends BaseServiceImpl<SysRoleDataScopeDao, SysRoleDataScopeEntity>
        implements SysRoleDataScopeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> orgIdList) {
        // Organization ID list in the database
        List<Long> dbOrgIdList = getOrgIdList(roleId);

        // Organization IDs to add
        Collection<Long> insertOrgIdList = CollUtil.subtract(orgIdList, dbOrgIdList);
        if (CollUtil.isNotEmpty(insertOrgIdList)){
            List<SysRoleDataScopeEntity> orgList = insertOrgIdList.stream().map(orgId -> {
                SysRoleDataScopeEntity entity = new SysRoleDataScopeEntity();
                entity.setOrgId(orgId);
                entity.setRoleId(roleId);
                return entity;
            }).collect(Collectors.toList());

            // Batch add
            saveBatch(orgList);
        }

        // Organization IDs to delete
        Collection<Long> deleteOrgIdList = CollUtil.subtract(dbOrgIdList, orgIdList);
        if (CollUtil.isNotEmpty(deleteOrgIdList)){
            LambdaQueryWrapper<SysRoleDataScopeEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRoleDataScopeEntity::getRoleId, roleId);
            queryWrapper.in(SysRoleDataScopeEntity::getOrgId, deleteOrgIdList);

            remove(queryWrapper);
        }
    }

    @Override
    public List<Long> getOrgIdList(Long roleId) {
        return baseMapper.getOrgIdList(roleId);
    }

    @Override
    public void deleteByRoleIdList(List<Long> roleIdList) {
        remove(new LambdaQueryWrapper<SysRoleDataScopeEntity>().in(SysRoleDataScopeEntity::getRoleId, roleIdList));
    }
}