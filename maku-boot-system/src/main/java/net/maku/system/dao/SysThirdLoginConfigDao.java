package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysThirdLoginConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Third-party login settings
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface SysThirdLoginConfigDao extends BaseDao<SysThirdLoginConfigEntity> {

}