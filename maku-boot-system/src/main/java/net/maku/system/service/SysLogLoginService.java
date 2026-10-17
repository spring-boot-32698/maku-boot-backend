package net.maku.system.service;

import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.BaseService;
import net.maku.system.entity.SysLogLoginEntity;
import net.maku.system.query.SysLogLoginQuery;
import net.maku.system.vo.SysLogLoginVO;

/**
 * Login log
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysLogLoginService extends BaseService<SysLogLoginEntity> {

    /**
     * Page result.
     *
     * @param query the query
     * @return the page result
     */
    PageResult<SysLogLoginVO> page(SysLogLoginQuery query);

    /**
     * Save login log
     *
     * @param username  Username
     * @param status    Login status
     * @param operation Operation info
     */
    void save(String username, Integer status, Integer operation);

    /**
     * Export login logs
     */
    void export();
}