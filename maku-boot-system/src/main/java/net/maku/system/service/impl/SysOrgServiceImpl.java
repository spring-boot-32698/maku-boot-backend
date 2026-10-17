package net.maku.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.common.exception.ServerException;
import net.maku.framework.common.utils.TreeUtils;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.system.dao.SysOrgDao;
import net.maku.system.dao.SysUserDao;
import net.maku.system.entity.SysOrgEntity;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.service.SysOrgService;
import net.maku.system.vo.SysOrgVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Organization management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysOrgServiceImpl extends BaseServiceImpl<SysOrgDao, SysOrgEntity> implements SysOrgService {
    private final SysUserDao sysUserDao;

    @Override
    public List<SysOrgVO> getList() {
        Map<String, Object> params = new HashMap<>();

        // Data permission
        params.put(Constant.DATA_SCOPE, getDataScope("t1", "id"));

        // Organization list
        List<SysOrgEntity> entityList = baseMapper.getList(params);

        return TreeUtils.build(BeanUtil.copyToList(entityList, SysOrgVO.class));
    }

    @Override
    public List<SysOrgVO> listByPid(Long pid) {
        LambdaQueryWrapper<SysOrgEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (pid == null) {
            queryWrapper.isNull(SysOrgEntity::getPid);
        } else {
            queryWrapper.eq(SysOrgEntity::getPid, pid);
        }

        List<SysOrgEntity> list = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(list, SysOrgVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysOrgVO vo) {
        SysOrgEntity entity = BeanUtil.copyProperties(vo, SysOrgEntity.class);

        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysOrgVO vo) {
        SysOrgEntity entity = BeanUtil.copyProperties(vo, SysOrgEntity.class);

        // Parent organization cannot be itself
        if (entity.getId().equals(entity.getPid())) {
            throw new ServerException("Parent organization cannot be itself");
        }

        // Parent organization cannot be a subordinate
        List<Long> subOrgList = getSubOrgIdList(entity.getId());
        if (subOrgList.contains(entity.getPid())) {
            throw new ServerException("Parent organization cannot be a subordinate");
        }

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // Check whether there are child organizations
        long orgCount = count(new QueryWrapper<SysOrgEntity>().eq("pid", id));
        if (orgCount > 0) {
            throw new ServerException("Please delete the child organizations first");
        }

        // Check whether the organization has users
        long userCount = sysUserDao.selectCount(new QueryWrapper<SysUserEntity>().eq("org_id", id));
        if (userCount > 0) {
            throw new ServerException("The organization has users and cannot be deleted");
        }

        // Delete
        removeById(id);
    }

    @Override
    public List<Long> getSubOrgIdList(Long id) {
        // List of id and pid of all organizations
        List<SysOrgEntity> orgList = baseMapper.getIdAndPidList();

        // Recursively query the list of all child organization IDs
        List<Long> subIdList = new ArrayList<>();
        getTree(id, orgList, subIdList);

        // Also add this organization
        subIdList.add(id);

        return subIdList;
    }

    @Override
    public List<String> getNameList(List<Long> idList) {
        if (idList.isEmpty()) {
            return null;
        }

        return baseMapper.selectBatchIds(idList).stream().map(SysOrgEntity::getName).toList();
    }

    private void getTree(Long id, List<SysOrgEntity> orgList, List<Long> subIdList) {
        for (SysOrgEntity org : orgList) {
            if (ObjectUtil.equals(org.getPid(), id)) {
                getTree(org.getId(), orgList, subIdList);

                subIdList.add(org.getId());
            }
        }
    }
}
