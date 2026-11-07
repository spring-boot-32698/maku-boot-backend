package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysMailConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mail settings
 *
 * @author Amu babamu@126.com
 */
@Mapper
public interface SysMailConfigDao extends BaseDao<SysMailConfigEntity> {

}