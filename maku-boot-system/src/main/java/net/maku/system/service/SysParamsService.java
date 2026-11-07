package net.maku.system.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysParamsEntity;
import net.maku.system.query.SysParamsQuery;
import net.maku.system.vo.SysParamsVO;

import java.util.List;

/**
 * Parameter management
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysParamsService extends BaseService<SysParamsEntity> {

    PageResult<SysParamsVO> page(SysParamsQuery query);

    void save(SysParamsVO vo);

    void update(SysParamsVO vo);

    void delete(List<Long> idList);

    /**
     * Get the string value by paramKey
     *
     * @param paramKey Parameter Key
     */
    String getString(String paramKey);

    /**
     * Get the integer value by paramKey
     *
     * @param paramKey Parameter Key
     */
    int getInt(String paramKey);

    /**
     * Get the boolean value by paramKey
     *
     * @param paramKey Parameter Key
     */
    boolean getBoolean(String paramKey);

    /**
     * Get the object value by paramKey
     *
     * @param paramKey  Parameter Key
     * @param valueType Type
     */
    <T> T getJSONObject(String paramKey, Class<T> valueType);
}