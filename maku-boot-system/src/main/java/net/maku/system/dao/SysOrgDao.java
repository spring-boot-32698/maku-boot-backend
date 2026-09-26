package net.maku.system.dao;

import net.maku.framework.mybatis.dao.BaseDao;
import net.maku.system.entity.SysOrgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Organization management
 * 
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface SysOrgDao extends BaseDao<SysOrgEntity> {

    List<SysOrgEntity> getList(Map<String, Object> params);

    /**
     * Get the id and pid list of all organizations
     */
    List<SysOrgEntity> getIdAndPidList();

}