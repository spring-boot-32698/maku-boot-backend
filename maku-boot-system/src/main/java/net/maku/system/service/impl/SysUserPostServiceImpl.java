package net.maku.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.system.dao.SysUserPostDao;
import net.maku.system.entity.SysUserPostEntity;
import net.maku.system.service.SysUserPostService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User-post relationship
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
public class SysUserPostServiceImpl extends BaseServiceImpl<SysUserPostDao, SysUserPostEntity> implements SysUserPostService {

    @Override
    public void saveOrUpdate(Long userId, List<Long> postIdList) {
        // Post ID list in the database
        List<Long> dbPostIdList = getPostIdList(userId);

        // Post IDs to add
        Collection<Long> insertPostIdList = CollUtil.subtract(postIdList, dbPostIdList);
        if (CollUtil.isNotEmpty(insertPostIdList)){
            List<SysUserPostEntity> postList = insertPostIdList.stream().map(postId -> {
                SysUserPostEntity entity = new SysUserPostEntity();
                entity.setUserId(userId);
                entity.setPostId(postId);
                return entity;
            }).collect(Collectors.toList());

            // Batch add
            saveBatch(postList);
        }

        // Post IDs to delete
        Collection<Long> deletePostIdList = CollUtil.subtract(dbPostIdList, postIdList);
        if (CollUtil.isNotEmpty(deletePostIdList)){
            LambdaQueryWrapper<SysUserPostEntity> queryWrapper = new LambdaQueryWrapper<>();
            remove(queryWrapper.eq(SysUserPostEntity::getUserId, userId).in(SysUserPostEntity::getPostId, deletePostIdList));
        }
    }

    @Override
    public void deleteByPostIdList(List<Long> postIdList) {
        remove(new LambdaQueryWrapper<SysUserPostEntity>().in(SysUserPostEntity::getPostId, postIdList));
    }

    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        remove(new LambdaQueryWrapper<SysUserPostEntity>().in(SysUserPostEntity::getUserId, userIdList));
    }

    @Override
    public List<Long> getPostIdList(Long userId) {
        return baseMapper.getPostIdList(userId);
    }
}