package net.maku.system.service;

import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysOrgEntity;
import net.maku.system.vo.SysOrgVO;

import java.util.List;

/**
 * Organization management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysOrgService extends BaseService<SysOrgEntity> {

    List<SysOrgVO> getList();

    List<SysOrgVO> listByPid(Long pid);

    void save(SysOrgVO vo);

    void update(SysOrgVO vo);

    void delete(Long id);

    /**
     * Get the child organization ID list by organization ID(Includes this organization ID)
     *
     * @param id Organization ID
     */
    List<Long> getSubOrgIdList(Long id);

    /**
     * Get the organization name list by organization ID list
     *
     * @param idList Organization ID list
     */
    List<String> getNameList(List<Long> idList);
}