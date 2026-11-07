package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysUserPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* User-post relationship
*
* @author Amu babamu@126.com
*/
@Mapper
public interface SysUserPostDao extends BaseDao<SysUserPostEntity> {

    /**
     * Post ID list
     * @param userId  User ID
     */
    List<Long> getPostIdList(@Param("userId") Long userId);
}