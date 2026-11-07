package net.maku.system.service;

import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysUserPostEntity;

import java.util.List;

/**
 * User-post relationship
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysUserPostService extends BaseService<SysUserPostEntity> {

    /**
     * Save or update
     * @param userId      User ID
     * @param postIdList  Post ID list
     */
    void saveOrUpdate(Long userId, List<Long> postIdList);

    /**
     * Delete user-post relationships by post id list
     * @param postIdList Post id list
     */
    void deleteByPostIdList(List<Long> postIdList);

    /**
     * Delete user-post relationships by user id list
     * @param userIdList User id list
     */
    void deleteByUserIdList(List<Long> userIdList);

    /**
     * Post ID list
     * @param userId  User ID
     */
    List<Long> getPostIdList(Long userId);
}