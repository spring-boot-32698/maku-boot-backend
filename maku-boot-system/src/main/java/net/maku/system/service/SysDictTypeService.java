package net.maku.system.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysDictTypeEntity;
import net.maku.system.query.SysDictTypeQuery;
import net.maku.system.vo.SysDictTypeVO;
import net.maku.system.vo.SysDictVO;

import java.util.List;

/**
 * Data dictionary
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysDictTypeService extends BaseService<SysDictTypeEntity> {

    PageResult<SysDictTypeVO> page(SysDictTypeQuery query);

    List<SysDictTypeVO> list(Long pid);

    void save(SysDictTypeVO vo);

    void update(SysDictTypeVO vo);

    void delete(List<Long> idList);

    /**
     * Get dynamic SQL data
     */
    List<SysDictVO.DictData> getDictSql(Long id);

    /**
     * Get all dictionary list
     */
    List<SysDictVO> getDictList();

    /**
     * Refresh dictionary cache
     */
    void refreshTransCache();

}