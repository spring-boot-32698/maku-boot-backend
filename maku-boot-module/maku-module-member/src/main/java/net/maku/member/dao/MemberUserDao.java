package net.maku.member.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.member.entity.MemberUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Member management
 *
 * @author Amu babamu@126.com
 */
@Mapper
public interface MemberUserDao extends BaseDao<MemberUserEntity> {

}